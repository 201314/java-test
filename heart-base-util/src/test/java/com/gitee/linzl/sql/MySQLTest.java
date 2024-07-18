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
        String sql = "CREATE TABLE `abs_loan_assign_condition_sub` (\n" +
                "    `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '物理主键',\n" +
                "    `special_plan_no` varchar(64) NOT NULL DEFAULT '' COMMENT '专项计划编号',\n" +
                "    `third_code` varchar(20) NOT NULL DEFAULT '' COMMENT '合作方代码',\n" +
                "    `date_loan` date DEFAULT NULL COMMENT '借款日期',\n" +
                "    `valid_comp_end_date` date DEFAULT NULL COMMENT '企业信息核查截止日期',\n" +
                "    `refuse_loan_source` varchar(255) NOT NULL DEFAULT '' COMMENT '借款渠道',\n" +
                "    `accept_underly_assets` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否承接兜底资产',\n" +
                "    `api_channels` varchar(300) NOT NULL DEFAULT '' COMMENT 'api渠道英文逗号隔开',\n" +
                "    `invalid_date` datetime DEFAULT NULL COMMENT '失效日期',\n" +
                "    `status` varchar(2) NOT NULL DEFAULT '1' COMMENT '状态 0/1',\n" +
                "    `remark` varchar(200) DEFAULT NULL COMMENT '备注',\n" +
                "    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',\n" +
                "    `created_by` varchar(32) NOT NULL DEFAULT 'sys' COMMENT '记录创建者',\n" +
                "    `date_updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',\n" +
                "    `updated_by` varchar(32) NOT NULL DEFAULT 'sys' COMMENT '记录修改者',\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    UNIQUE KEY `uniq_condition_supp` (`special_plan_no`(20), `third_code`)\n" +
                "    USING\n" +
                "        BTREE\n" +
                ") ENGINE = InnoDB AUTO_INCREMENT = 73 DEFAULT CHARSET = utf8 COMMENT = 'ABS入池条件补充表'";

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
