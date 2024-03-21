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
        String sql = "CREATE TABLE `product_info` (\n" +
            "    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '物理主键',\n" +
            "    `adjust_score_protect` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '产品列表保护期调节分数',\n" +
            "    `adjust_score_protect_effective` datetime DEFAULT NULL COMMENT '保护期开始时间',\n" +
            "    `adjust_score_protect_expire` datetime DEFAULT NULL COMMENT '保护期开始时间',\n" +
            "    `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "    `created_by` varchar(100) NOT NULL DEFAULT 'sys' COMMENT '创建人',\n" +
            "    `date_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
            "    `updated_by` varchar(100) NOT NULL DEFAULT 'sys' COMMENT '修改人',\n" +
            "    PRIMARY KEY (`id`),\n" +
            "    UNIQUE KEY `uk_product` (`partner_no`, `product_code`, `product_version`)\n" +
            ") ENGINE = InnoDB AUTO_INCREMENT = 88 DEFAULT CHARSET = utf8 COMMENT = '合作方产品配置表'";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        MySqlToHiveOutputVisitor visitor = new MySqlToHiveOutputVisitor("pda");
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
        });
        System.out.println(visitor.getContent());
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

    @Test
    public void mysqlToView(){
        String sql = "CREATE TABLE `ln_loan` (\n" +
                "    `id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '物理主键',\n" +
                "    `loan_req_no` varchar(64) NOT NULL DEFAULT '' COMMENT '放款请求流水号',\n" +
                "    `loan_no` varchar(64) NOT NULL DEFAULT '' COMMENT '借据号',\n" +
                "    `busi_loan_no` varchar(20) NOT NULL DEFAULT '' COMMENT '业务借据号',\n" +
                "    `contract_no` varchar(64) NOT NULL DEFAULT '' COMMENT '合同号',\n" +
                "    `cust_no` varchar(64) NOT NULL DEFAULT '' COMMENT '客户号',\n" +
                "    `loan_amt` decimal(17, 2) NOT NULL DEFAULT '0.00' COMMENT '借款金额',\n" +
                "    `term` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '期数',\n" +
                "    `rpy_type` varchar(2) NOT NULL DEFAULT '' COMMENT '还款方式:00-等额本金，01-等额本息，02-先息后本',\n" +
                "    `date_loan` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '资金动用日期',\n" +
                "    `date_cash` datetime DEFAULT NULL COMMENT '资金到账日期',\n" +
                "    `date_inst` date DEFAULT NULL COMMENT '起息日',\n" +
                "    `rp_day` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '还款日',\n" +
                "    `date_end` date DEFAULT NULL COMMENT '贷款止期',\n" +
                "    `bank_code` varchar(10) NOT NULL DEFAULT '' COMMENT '银行编号',\n" +
                "    `card_id` varchar(64) NOT NULL DEFAULT '' COMMENT '银行卡ID',\n" +
                "    `db_acct` varchar(30) NOT NULL DEFAULT '' COMMENT '放款卡号',\n" +
                "    `db_acct_encryptx` varchar(64) NOT NULL DEFAULT '' COMMENT '还款卡号密文',\n" +
                "    `db_acct_md5x` varchar(32) NOT NULL DEFAULT '' COMMENT '还款卡号MD5',\n" +
                "    `db_acct_name` varchar(100) NOT NULL DEFAULT '' COMMENT '放款账户名称',\n" +
                "    `db_acct_name_encryptx` varchar(128) NOT NULL DEFAULT '' COMMENT '还款账户名称密文',\n" +
                "    `db_acct_name_md5x` varchar(32) NOT NULL DEFAULT '' COMMENT '还款账户名称MD5',\n" +
                "    `loan_source` varchar(20) NOT NULL DEFAULT '' COMMENT '资金动用渠道',\n" +
                "    `date_settle` date DEFAULT NULL COMMENT '结清日期',\n" +
                "    `date_bd` date DEFAULT NULL COMMENT '呆账日期',\n" +
                "    `date_wo` date DEFAULT NULL COMMENT '坏账日期',\n" +
                "    `date_accrued` date DEFAULT NULL COMMENT '转非应计日期',\n" +
                "    `date_compensate` date DEFAULT NULL COMMENT '代偿日期',\n" +
                "    `over_due_days` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '逾期天数',\n" +
                "    `over_due_status` varchar(10) DEFAULT 'M0' COMMENT '拖欠周期状态码',\n" +
                "    `loan_bal` decimal(17, 2) NOT NULL DEFAULT '0.00' COMMENT '剩余本金',\n" +
                "    `free_days` int(11) unsigned DEFAULT '0' COMMENT '免息天数',\n" +
                "    `date_stat` date DEFAULT NULL COMMENT '核算日期',\n" +
                "    `status` varchar(2) NOT NULL DEFAULT '' COMMENT '借据状态:AP-待放款,\n" +
                "RP-还款中,\n" +
                "OD-逾期中,\n" +
                "FP-结清,\n" +
                "BD-呆账,\n" +
                "WO-坏账',\n" +
                "    `accrual_type` varchar(5) DEFAULT 'AC' COMMENT '会计类型:AC应计,\n" +
                "NAC非应计',\n" +
                "    `compensate_type` varchar(5) DEFAULT 'NCP' COMMENT '代偿类型,\n" +
                "NCP未代偿,\n" +
                "ECP提前代偿,\n" +
                "OCP逾期代偿',\n" +
                "    `sub_product_ver` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '子产品版本号',\n" +
                "    `sub_product_code` varchar(64) NOT NULL DEFAULT '' COMMENT '子产品代码',\n" +
                "    `seq_no` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '借款次数序号',\n" +
                "    `first_loan` varchar(1) DEFAULT NULL COMMENT '首贷标识:Y-是;N-否',\n" +
                "    `third_code` varchar(64) NOT NULL DEFAULT '' COMMENT '第三方编码',\n" +
                "    `offer_req_no` varchar(64) DEFAULT NULL COMMENT '报盘流水号',\n" +
                "    `pay_order_no` varchar(64) DEFAULT NULL COMMENT '支付订单号',\n" +
                "    `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "    `created_by` varchar(100) NOT NULL DEFAULT 'sys' COMMENT '创建人',\n" +
                "    `date_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
                "    `updated_by` varchar(100) NOT NULL DEFAULT 'sys' COMMENT '修改人',\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    UNIQUE KEY `i_lc_ll_req` (`loan_req_no`),\n" +
                "    UNIQUE KEY `i_lc_ll_loan` (`loan_no`),\n" +
                "    KEY `i_lc_ll_con` (`contract_no`),\n" +
                "    KEY `i_lc_ll_ct` (`cust_no`),\n" +
                "    KEY `idx_third_rp_day` (`third_code`, `rp_day`)\n" +
                ") ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '借据信息表'";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        MySqlToHiveOutputVisitor visitor = new MySqlToHiveOutputVisitor("pda", Boolean.TRUE);
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
        });
        System.out.println(visitor.getContent());
    }

}
