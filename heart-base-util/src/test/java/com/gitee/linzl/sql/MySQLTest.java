package com.gitee.linzl.sql;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.visitor.SQLTableAliasCollectVisitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author linzhenlie-jk
 * @date 2023/5/26
 */
@Slf4j
public class MySQLTest {
    @Test
    public void mysqlToHive() {
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
            ") ENGINE = InnoDB DEFAULT CHARSET = utf8";

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
        String sql = "CREATE TABLE `car_owner_loan_appl` (\n" +
                "    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                "    `user_no` varchar(32) NOT NULL COMMENT '用户号',\n" +
                "    `appl_no` varchar(64) NOT NULL COMMENT '申请单号',\n" +
                "    `out_appl_no` varchar(64) NOT NULL COMMENT '机构单号',\n" +
                "    `partner_code` varchar(32) NOT NULL COMMENT '机构编号',\n" +
                "    `product_code` varchar(32) NOT NULL COMMENT '产品编码',\n" +
                "    `product_name` varchar(32) NOT NULL COMMENT '产品名称',\n" +
                "    `name_encryptx` varchar(2048) NOT NULL COMMENT '姓名 - 加密',\n" +
                "    `name_md5x` varchar(32) NOT NULL COMMENT '姓名 - MD5',\n" +
                "    `phone_encryptx` varchar(2048) NOT NULL COMMENT '手机号 - 加密',\n" +
                "    `phone_md5x` varchar(32) NOT NULL COMMENT '手机号 - MD5',\n" +
                "    `plate_no` varchar(8) NOT NULL COMMENT '车牌号',\n" +
                "    `city_code` varchar(6) NOT NULL COMMENT '城市编码',\n" +
                "    `city_name` varchar(32) DEFAULT NULL COMMENT '城市名称',\n" +
                "    `source` varchar(32) NOT NULL DEFAULT 'ONLINE' COMMENT '车主贷订单来源 CarOrderSourceEnum',\n" +
                "    `recommend_phone_encryptx` varchar(2048) NOT NULL DEFAULT '' COMMENT '推荐人手机号-加密串',\n" +
                "    `recommend_phone_md5x` varchar(32) NOT NULL DEFAULT '' COMMENT '推荐人手机号-MD5',\n" +
                "    `purchase_method` varchar(32) NOT NULL DEFAULT '' COMMENT '购车方式 CarPurchaseMethodEnum',\n" +
                "    `mortgaged_state` char(1) DEFAULT '' COMMENT '抵押状态，Y:抵押中 N:无抵押',\n" +
                "    `old_car_state` char(1) DEFAULT '' COMMENT '老车龄状态，Y:是老车龄 N:非老车龄',\n" +
                "    `authorized` char(1) DEFAULT '' COMMENT '协议授权状态，1:已授权 0:未授权',\n" +
                "    `apply_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',\n" +
                "    `state` varchar(3) DEFAULT NULL COMMENT '申请状态 APQ：已发送队列 APR：审核中（已发送机构）ARJ：拒绝 APS：申请成功',\n" +
                "    `sub_state` varchar(32) NOT NULL COMMENT '子状态',\n" +
                "    `outer_code` varchar(32) DEFAULT NULL COMMENT '机构返回码',\n" +
                "    `outer_msg` varchar(1024) DEFAULT NULL COMMENT '机构返回描述',\n" +
                "    `outer_sub_code` varchar(32) NOT NULL COMMENT '机构子返回码',\n" +
                "    `outer_sub_msg` varchar(1024) NOT NULL COMMENT '机构子返回描述',\n" +
                "    `loan_amount` decimal(10, 2) NOT NULL DEFAULT '0.00' COMMENT '放款金额',\n" +
                "    `loan_num` int(11) NOT NULL DEFAULT '0' COMMENT '融资期限',\n" +
                "    `rate` decimal(10, 2) NOT NULL DEFAULT '0.00' COMMENT '利率',\n" +
                "    `outer_ext_result` varchar(1024) NOT NULL DEFAULT '' COMMENT '机构返回的一些额外的信息',\n" +
                "    `out_state_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '机构状态变更时间（非我方从机构同步结果的时间）',\n" +
                "    `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "    `created_by` varchar(32) NOT NULL DEFAULT 'sys' COMMENT '创建者',\n" +
                "    `date_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',\n" +
                "    `updated_by` varchar(32) NOT NULL DEFAULT 'sys' COMMENT '修改者',\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    UNIQUE KEY `uni_appl_no` (`appl_no`),\n" +
                "    KEY `idx_status_date_created` (`state`, `date_created`),\n" +
                "    KEY `idx_user_product` (`user_no`, `product_code`)\n" +
                ") ENGINE = InnoDB AUTO_INCREMENT = 1468389 DEFAULT CHARSET = utf8 COMMENT = '车主贷申请表'";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        MySqlToHiveOutputVisitor visitor = new MySqlToHiveOutputVisitor("pda", Boolean.TRUE);
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
        });
        System.out.println(visitor.getContent());
    }

    @Test
    public void mysqlColumnCnt() {
        String sql = "CREATE TABLE `hj_os_case_22` (\n" +
                "    `jid` bigint(20) unsigned NOT NULL,\n" +
                "    `id` varchar(64) NOT NULL DEFAULT '0',\n" +
                "    `case_code` varchar(64) NOT NULL COMMENT '案件号',\n" +
                "    `customer_account` varchar(64) NOT NULL COMMENT '客户号',\n" +
                "    `receipt_no` varchar(64) DEFAULT NULL COMMENT '借据号',\n" +
                "    `contract_no` varchar(64) DEFAULT NULL COMMENT '合同号',\n" +
                "    `loan_source` varchar(20) DEFAULT NULL COMMENT '动支渠道',\n" +
                "    `product_code` varchar(50) DEFAULT '' COMMENT '产品编码',\n" +
                "    `batch_num` varchar(20) DEFAULT NULL COMMENT '委外批次号',\n" +
                "    `os_company_id` varchar(64) NOT NULL COMMENT '诉调机构',\n" +
                "    `case_type` int(1) unsigned NOT NULL COMMENT '案件类型（1手工 2自动）',\n" +
                "    `case_status` int(1) unsigned NOT NULL COMMENT '案件状态 1待分配 2待法催 4法催中 5到期退案 6提前退案',\n" +
                "    `outsourcing_times` int(4) unsigned NOT NULL COMMENT '委外手别',\n" +
                "    `os_start_date` date DEFAULT NULL COMMENT '委外开始日',\n" +
                "    `os_end_date` date DEFAULT NULL COMMENT '预计退案日期：预计退案日期=委案日期+委案周期',\n" +
                "    `is_retained` char(1) NOT NULL DEFAULT 'N' COMMENT '是否留案',\n" +
                "    `collection_status` varchar(64) NOT NULL COMMENT '逾期阶段',\n" +
                "    `overdue_days` int(4) NOT NULL COMMENT '逾期天数',\n" +
                "    `overdue_total_amount` bigint(20) NOT NULL COMMENT '欠款总额',\n" +
                "    `remaining_amount` int(11) DEFAULT NULL COMMENT '剩余本金',\n" +
                "    `os_amount` int(11) NOT NULL DEFAULT '0' COMMENT '委案金额',\n" +
                "    `is_return` char(1) NOT NULL DEFAULT 'N' COMMENT '是否退案',\n" +
                "    `os_count` int(4) unsigned DEFAULT '0' COMMENT '外包委外次数',\n" +
                "    `return_case_reason` varchar(64) DEFAULT NULL COMMENT '退案原因：委案时留空',\n" +
                "    `actual_return_case_date` date DEFAULT NULL COMMENT '实际退案日期',\n" +
                "    `queue` varchar(64) DEFAULT NULL COMMENT '队列',\n" +
                "    `queue_change_time` datetime DEFAULT NULL COMMENT '队列变化时间',\n" +
                "    `status` int(1) DEFAULT '1' COMMENT '是否出催 1未出催 2出催',\n" +
                "    `out_date` datetime DEFAULT NULL COMMENT '出催时间',\n" +
                "    `customer_name_encryptx` varchar(500) DEFAULT NULL COMMENT '姓名密文',\n" +
                "    `customer_name_md5x` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名MD5',\n" +
                "    `identity_card_encryptx` varchar(500) DEFAULT NULL COMMENT '身份证密文',\n" +
                "    `identity_card_md5x` varchar(32) DEFAULT NULL COMMENT '身份证MD5',\n" +
                "    `cust_mobile_encryptx` varchar(100) DEFAULT NULL,\n" +
                "    `cust_mobile_md5x` varchar(32) DEFAULT NULL,\n" +
                "    `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',\n" +
                "    `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "    `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',\n" +
                "    `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "    `remark` varchar(255) DEFAULT NULL COMMENT '备注',\n" +
                "    `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',\n" +
                "    `update_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用于标记数据增量更新',\n" +
                "    `permanent_user_id` varchar(64) DEFAULT NULL COMMENT '永久催收员',\n" +
                "    `permanent_group_id` varchar(64) DEFAULT NULL COMMENT '永久催收员组id',\n" +
                "    `buss_type` int(1) DEFAULT '1' COMMENT '业务类型 1-诉调 2-诉讼',\n" +
                "    `disposal_method` varchar(64) DEFAULT NULL COMMENT '处置方式',\n" +
                "    `in_office_time` datetime DEFAULT NULL COMMENT '进入机构时间',\n" +
                "    `in_case_time` datetime DEFAULT NULL COMMENT '进入个人收件箱时间',\n" +
                "    `last_action_code` varchar(64) DEFAULT NULL COMMENT '最近行动码',\n" +
                "    `last_action_time` datetime DEFAULT NULL COMMENT '最近行动码时间',\n" +
                "    `product_type` varchar(64) DEFAULT NULL COMMENT '产品类型',\n" +
                "    `debt_transfer_company` varchar(64) DEFAULT NULL COMMENT '债转公司',\n" +
                "    `last_laws_status` varchar(16) DEFAULT NULL COMMENT '最后诉讼状态',\n" +
                "    `last_laws_date` date DEFAULT NULL COMMENT '最后诉讼日期',\n" +
                "    `marker` varchar(64) DEFAULT NULL COMMENT '案件标色',\n" +
                "    `total_os_amount` bigint(20) DEFAULT NULL COMMENT '客户总委案金额',\n" +
                "    `coll_total_arrears_amount` int(11) DEFAULT NULL COMMENT '催收欠款总额',\n" +
                "    `coll_remain_amount` int(11) DEFAULT NULL COMMENT '催收剩余本金',\n" +
                "    `real_coll_total_arrears_amount` int(11) DEFAULT NULL COMMENT '实时催收欠款总额',\n" +
                "    `real_coll_remain_amount` int(11) DEFAULT NULL COMMENT '实时催收剩余本金',\n" +
                "    `real_os_amount` int(11) DEFAULT NULL COMMENT '实时诉调金额',\n" +
                "    `real_collection_status` varchar(64) DEFAULT NULL COMMENT '实时逾期阶段',\n" +
                "    `real_overdue_days` int(4) DEFAULT NULL COMMENT '实时逾期天数',\n" +
                "    `calc_amount_method` int(2) DEFAULT NULL COMMENT '计算金额方式 1-诉调金额按公式算出,\n" +
                " 2-诉调金额按催收欠款总额补充,\n" +
                " 3-诉讼金额按代偿金额计算,\n" +
                " 4-诉讼金额按剩余本金补充',\n" +
                "    `customer_tags` text COMMENT '客户标签',\n" +
                "    PRIMARY KEY (`jid`),\n" +
                "    UNIQUE KEY `idx_id` (`id`),\n" +
                "    UNIQUE KEY `uniq_case_code` (`case_code`),\n" +
                "    KEY `idx_receipt_no` (`receipt_no`),\n" +
                "    KEY `idx_customer_account` (`customer_account`),\n" +
                "    KEY `idx_case_status` (`case_status`),\n" +
                "    KEY `idx_times_queue` (`outsourcing_times`, `queue`),\n" +
                "    KEY `idx_os_company_id` (`os_company_id`),\n" +
                "    KEY `idx_os_end_date` (`os_end_date`),\n" +
                "    KEY `idx_update_timestamp` (`update_timestamp`),\n" +
                "    KEY `idx_batch_num` (`batch_num`),\n" +
                "    KEY `idx_out_date` (`out_date`),\n" +
                "    KEY `idx_status` (`status`),\n" +
                "    KEY `idx_cust_name` (`customer_name_md5x`),\n" +
                "    KEY `idx_overdue_total_amount` (`overdue_total_amount`)\n" +
                ") ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '法催委外案件'";

        StringBuilder sbd = new StringBuilder("select count(1) cnt,");
        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        String tableName = null;
        for (SQLStatement sqlStatement : stmtList) {
            if (sqlStatement instanceof SQLCreateTableStatement){
                SQLCreateTableStatement createTableStatement = (SQLCreateTableStatement) sqlStatement;
                tableName = replaceChar(createTableStatement.getTableName());
                createTableStatement.getColumnDefinitions().forEach(columnDefinition -> {
                        String columnName = replaceChar(columnDefinition.getColumnName());
                        sbd.append("1-count(").append(columnName).append(")/count(1) AS ").append(columnName).append(
                                "_rate,");
                });
            }
        }
        sbd.deleteCharAt(sbd.length()-1).append(" from ").append(tableName);
        System.out.println(sbd);
    }

    private String replaceChar(String str) {
        if (StringUtils.isBlank(str)){
            return StringUtils.EMPTY;
        }
        return str.trim().replaceAll("`", "")
                .replaceAll(" ", "")
                .replaceAll("：", ":")
                .replaceAll(";", "；")
                .replaceAll("（", "(")
                .replaceAll("）", ")")
                .replaceAll("\r\n", "")
                .replaceAll("\n", "");
    }
}
