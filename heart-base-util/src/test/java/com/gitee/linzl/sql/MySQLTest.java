package com.gitee.linzl.sql;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.alibaba.druid.sql.ast.SQLIndexDefinition;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.visitor.SQLTableAliasCollectVisitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * @author linzhenlie-jk
 * @date 2023/5/26
 */
@Slf4j
public class MySQLTest {
    @Test
    public void uniquePk() {
        String sql = "CREATE TABLE `ln_loan_extension_record` (\n" +
            "    `id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '物理主键',\n" +
            "    `request_no` varchar(80) NOT NULL DEFAULT '' COMMENT '其他系统请求流水号',\n" +
            "    `applicant` varchar(20) NOT NULL DEFAULT '' COMMENT '申请系统，如客服KF，催收CS',\n" +
            "    `cust_no` varchar(64) NOT NULL DEFAULT '' COMMENT '客户号',\n" +
            "    `contract_no` varchar(64) NOT NULL DEFAULT '' COMMENT '合同号',\n" +
            "    `loan_no` varchar(64) NOT NULL DEFAULT '' COMMENT '借据号',\n" +
            "    `tr_proc_rp_no` varchar(64) DEFAULT NULL COMMENT '展期关联入账减免流水',\n" +
            "    `before_loan_amt` decimal(17, 2) NOT NULL DEFAULT '0.00' COMMENT '原始借款本金',\n" +
            "    `after_loan_amt` decimal(17, 2) NOT NULL DEFAULT '0.00' COMMENT '展期后借款本金',\n" +
            "    `before_term` int(10) unsigned DEFAULT NULL COMMENT '原始借款期数',\n" +
            "    `after_term` int(10) unsigned DEFAULT NULL COMMENT '展期后借款期数',\n" +
            "    `start_term` int(10) unsigned DEFAULT NULL COMMENT '展期当时期数',\n" +
            "    `apply_date` date DEFAULT NULL COMMENT '展期申请日期，客服申请日期',\n" +
            "    `valid_date` date DEFAULT NULL COMMENT '展期截止日期，如多少天内可申请展期',\n" +
            "    `extension_date` datetime DEFAULT NULL COMMENT '展期操作日期，系统延期成功日期',\n" +
            "    `product_code` varchar(50) DEFAULT NULL COMMENT '产品代码',\n" +
            "    `status` varchar(2) NOT NULL DEFAULT '' COMMENT '借据展期状态:00初始化,\n" +
            "02处理中,\n" +
            "03展期成功,\n" +
            "04展期失败,\n" +
            "99系统异常',\n" +
            "    `before_rpy_type` varchar(2) DEFAULT NULL COMMENT '原始还款方式:00-等额本金，01-等额本息，02-先息后本',\n" +
            "    `after_rpy_type` varchar(2) DEFAULT NULL COMMENT '展期后还款方式:00-等额本金，01-等额本息，02-先息后本',\n" +
            "    `before_rpy_date` int(10) unsigned DEFAULT NULL COMMENT '原始还款日',\n" +
            "    `after_rpy_date` int(10) unsigned DEFAULT NULL COMMENT '展期后还款日',\n" +
            "    `before_date_end` date DEFAULT NULL COMMENT '原始贷款止期',\n" +
            "    `after_date_end` date DEFAULT NULL COMMENT '原始贷款止期',\n" +
            "    `before_loan_status` varchar(2) DEFAULT NULL COMMENT '原始借据状态:RP-正确,\n" +
            "OD-逾期',\n" +
            "    `over_due_days` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '展期时候客户级别当前逾期天数',\n" +
            "    `max_over_days` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '展期时候客户级别最高逾期天数',\n" +
            "    `over_due_amt` decimal(17, 2) NOT NULL DEFAULT '0.00' COMMENT '展期时候客户级别当前逾期金额',\n" +
            "    `max_over_amt` decimal(17, 2) NOT NULL DEFAULT '0.00' COMMENT '展期时候客户级别最高逾期金额',\n" +
            "    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "    `created_by` varchar(100) NOT NULL DEFAULT 'sys' COMMENT '创建人',\n" +
            "    `date_updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
            "    `updated_by` varchar(100) NOT NULL DEFAULT 'sys' COMMENT '修改人',\n" +
            "    PRIMARY KEY (`id`),\n" +
            "    UNIQUE KEY `uniq_re_ap_loan` (`request_no`, `applicant`, `loan_no`)\n" +
            "    USING\n" +
            "        BTREE,\n" +
            "        KEY `idx_ct` (`cust_no`)\n" +
            "    USING\n" +
            "        BTREE,\n" +
            "        KEY `idx_loan` (`loan_no`)\n" +
            "    USING\n" +
            "        BTREE\n" +
            ") ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '借据延期申请及处理表'";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        parser.getExprParser();

        List<SQLStatement> stmtList = parser.parseStatementList();
        // 将AST通过visitor输出
        StringBuilder out = new StringBuilder();
        MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);
        MySqlCreateTableStatement createTable = (MySqlCreateTableStatement) stmtList.get(0);
        List<SQLTableElement> list = createTable.getTableElementList();
        list.stream().forEach(sqlTableElement -> {
            if (sqlTableElement instanceof MySqlUnique) {
                MySqlUnique mySqlUnique = (MySqlUnique) sqlTableElement;
                SQLIndexDefinition indexDefinition = mySqlUnique.getIndexDefinition();
                log.info("唯一索引:" + indexDefinition.getColumns());
            }
        });
    }
    @Test
    public void mysqlToHive() {
        String sql = "CREATE TABLE `u_obas_event_extend` (\n" +
            "    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
            "    `event_type` varchar(64) NOT NULL DEFAULT '' COMMENT '事件类型',\n" +
            "    `base_id` bigint(20) DEFAULT NULL COMMENT '基础事件ID用于关联',\n" +
            "    `request_no` varchar(128) NOT NULL DEFAULT '' COMMENT '流水号',\n" +
            "    `event_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '事件发生时间',\n" +
            "    `field_name` varchar(64) NOT NULL DEFAULT '' COMMENT '字段名称',\n" +
            "    `field_value` varchar(1024) DEFAULT NULL COMMENT '字段值',\n" +
            "    `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "    `created_by` varchar(100) DEFAULT 'sys' COMMENT '创建人',\n" +
            "    `date_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
            "    `updated_by` varchar(100) DEFAULT 'sys' COMMENT '修改人',\n" +
            "    PRIMARY KEY (`id`, `event_time`),\n" +
            "    UNIQUE KEY `i_lc_ll_req` (`request_no`),\n" +
            "    KEY `IDX_EVENT_TIME` (`event_time`)\n" +
            "    USING\n" +
            "        BTREE,\n" +
            "        KEY `IDX_REQUEST_NO` (`request_no`)\n" +
            "    USING\n" +
            "        BTREE,\n" +
            "        KEY `IDX_BASE_ID` (`base_id`)\n" +
            ") ENGINE = InnoDB AUTO_INCREMENT = 6410625106168528899 DEFAULT CHARSET = utf8 ROW_FORMAT = COMPRESSED KEY_BLOCK_SIZE = 8 COMMENT = '用户事件拓展字段表'\n" +
            "/*!50100 PARTITION BY RANGE (UNIX_TIMESTAMP(event_time))\n" +
            " (PARTITION p20230809 VALUES LESS THAN (1691596800) ENGINE = InnoDB,\n" +
            " PARTITION p20230810 VALUES LESS THAN (1691683200) ENGINE = InnoDB,\n" +
            " PARTITION p20230811 VALUES LESS THAN (1691769600) ENGINE = InnoDB,\n" +
            " PARTITION p20230812 VALUES LESS THAN (1691856000) ENGINE = InnoDB,\n" +
            " PARTITION p20230813 VALUES LESS THAN (1691942400) ENGINE = InnoDB,\n" +
            " PARTITION p20230814 VALUES LESS THAN (1692028800) ENGINE = InnoDB,\n" +
            " PARTITION p20230815 VALUES LESS THAN (1692115200) ENGINE = InnoDB,\n" +
            " PARTITION p20230816 VALUES LESS THAN (1692201600) ENGINE = InnoDB,\n" +
            " PARTITION p20230817 VALUES LESS THAN (1692288000) ENGINE = InnoDB,\n" +
            " PARTITION p20230818 VALUES LESS THAN (1692374400) ENGINE = InnoDB,\n" +
            " PARTITION p20230819 VALUES LESS THAN (1692460800) ENGINE = InnoDB,\n" +
            " PARTITION p20230820 VALUES LESS THAN (1692547200) ENGINE = InnoDB,\n" +
            " PARTITION p20230821 VALUES LESS THAN (1692633600) ENGINE = InnoDB,\n" +
            " PARTITION p20230822 VALUES LESS THAN (1692720000) ENGINE = InnoDB,\n" +
            " PARTITION p20230823 VALUES LESS THAN (1692806400) ENGINE = InnoDB) */";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        MySqlToHiveOutputVisitor visitor = new MySqlToHiveOutputVisitor();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
        });
    }
    @Test
    public void mysqlFileToHive() throws IOException {
        File file = new File("D:\\mysql.sql");
        File file2 = new File(file.getParentFile(),file.getName()+".txt");
        String sqlContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        MySqlStatementParser parser = new MySqlStatementParser(sqlContent);
        List<SQLStatement> stmtList = parser.parseStatementList();
        SQLTableAliasCollectVisitor visitor = new SQLTableAliasCollectVisitor();

        //MySqlToHiveOutputVisitor visitor = new MySqlToHiveOutputVisitor("pyi");
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            visitor.getTableSources();
        });
    }
}
