package com.gitee.linzl.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLIndexDefinition;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.visitor.SQLTableAliasCollectVisitor;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author linzhenlie-jk
 * @date 2023/5/26
 */
@Slf4j
public class MySQLTest {
    private String getIdxName(SQLIndexDefinition idxDefinition, int idxIndex) {
        StringBuilder idxSb = new StringBuilder();
        idxDefinition.getColumns().forEach(item -> {
            SQLExpr sqlExpr = item.getExpr();
            String name = null;
            if (sqlExpr instanceof SQLMethodInvokeExpr) {
                SQLMethodInvokeExpr invokeExpr = (SQLMethodInvokeExpr) sqlExpr;
                name = invokeExpr.getMethodName();
            }

            if (sqlExpr instanceof SQLIdentifierExpr) {
                SQLIdentifierExpr invokeExpr = (SQLIdentifierExpr) sqlExpr;
                name = invokeExpr.getName();
            }
            idxSb.append(replaceChar(name)).append(",");
        });
        StringBuilder sb = new StringBuilder();
        if (idxSb.length() > 0) {
            sb.append("业务主键").append(idxIndex).append(":");
            sb.append(idxSb.deleteCharAt(idxSb.length() - 1));
        }
        return sb.toString();
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
            if (sqlStatement instanceof SQLCreateTableStatement) {
                SQLCreateTableStatement createTableStatement = (SQLCreateTableStatement) sqlStatement;
                tableName = replaceChar(createTableStatement.getTableName());
                createTableStatement.getColumnDefinitions().forEach(columnDefinition -> {
                    String columnName = replaceChar(columnDefinition.getColumnName());
                    sbd.append("1-count(").append(columnName).append(")/count(1) AS ").append(columnName).append(
                            "_rate,");
                });
            }
        }
        sbd.deleteCharAt(sbd.length() - 1).append(" from ").append(tableName);
        System.out.println(sbd);
    }

    @Test
    public void mysqlFileToHive() throws IOException {
        File file = new File("D:\\mysql.sql");
        File file2 = new File(file.getParentFile(), file.getName() + ".txt");
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
    public void mysqlToHive() {
        String sql = "CREATE TABLE `hj_os_case` (\n" +
                "    `jid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "    `id` varchar(150) NOT NULL DEFAULT '0',\n" +
                "    `batch_num` varchar(100) DEFAULT NULL COMMENT '委外批次号',\n" +
                "    `customer_account` varchar(150) NOT NULL COMMENT '客户号',\n" +
                "    `collection_case_id` varchar(150) NOT NULL COMMENT '案件ID',\n" +
                "    `collection_case_code` varchar(150) NOT NULL COMMENT '案件编号',\n" +
                "    `case_type` tinyint(1) unsigned NOT NULL COMMENT '案件类型（手工、自动）',\n" +
                "    `case_status` tinyint(1) unsigned NOT NULL COMMENT '案件状态 1待分配 2待委外 3委外确认 4委外中',\n" +
                "    `outsourcing_times` tinyint(1) unsigned NOT NULL COMMENT '委外手别',\n" +
                "    `os_company_id` varchar(150) NOT NULL COMMENT '外包公司',\n" +
                "    `os_start_date` date DEFAULT NULL COMMENT '委外开始日',\n" +
                "    `os_end_date` date DEFAULT NULL COMMENT '预计退案日期：预计退案日期=委案日期+委案周期',\n" +
                "    `is_return` char(1) NOT NULL DEFAULT 'N' COMMENT '是否已回收',\n" +
                "    `is_retained` char(1) NOT NULL DEFAULT 'N' COMMENT '是否留案',\n" +
                "    `collection_status` varchar(150) NOT NULL COMMENT '逾期阶段',\n" +
                "    `overdue_days` int(4) NOT NULL COMMENT '逾期天数',\n" +
                "    `overdue_total_amount` bigint(20) NOT NULL COMMENT '欠款总额',\n" +
                "    `remaining_amount` int(11) DEFAULT NULL COMMENT '剩余本金',\n" +
                "    `overdue_amount` int(11) NOT NULL DEFAULT '0' COMMENT '逾期总额',\n" +
                "    `over_due_prin` int(11) DEFAULT NULL COMMENT '逾期本金',\n" +
                "    `int_amts` int(11) DEFAULT NULL COMMENT '逾期利息',\n" +
                "    `loan_penalty` int(11) DEFAULT NULL COMMENT '罚息',\n" +
                "    `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',\n" +
                "    `create_by` varchar(150) DEFAULT NULL COMMENT '创建者',\n" +
                "    `create_date` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "    `update_by` varchar(150) DEFAULT NULL COMMENT '更新者',\n" +
                "    `update_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "    `remark` varchar(255) DEFAULT NULL COMMENT '备注',\n" +
                "    `os_cuishou_type` varchar(32) NOT NULL DEFAULT 'WWCS' COMMENT '委外催收方式：外包催收（WWCS）、打包催收（DBCS）',\n" +
                "    `os_count` tinyint(1) unsigned DEFAULT '0' COMMENT '外包委外次数',\n" +
                "    `return_case_reason` varchar(64) DEFAULT NULL COMMENT '退案原因：委案时留空',\n" +
                "    `actual_return_case_date` date DEFAULT NULL COMMENT '实际退案日期',\n" +
                "    `cumulative_amount` bigint(20) DEFAULT '0' COMMENT '累计业绩金额：委案时统一置为0',\n" +
                "    `os_remaining_amount` bigint(20) DEFAULT '0' COMMENT '委案剩余金额，当前字段值=欠款总额',\n" +
                "    `before_os_repay_amount` bigint(20) DEFAULT '0' COMMENT '委前还款金额',\n" +
                "    `current_os_amount` bigint(20) DEFAULT '0' COMMENT '当前委案金额',\n" +
                "    `finance_account` decimal(15, 2) DEFAULT '0.00' COMMENT '财务调账,\n" +
                "如交易渠道（channel）等于1,\n" +
                "财务调账=原值＋交易入账金额',\n" +
                "    `classification_mark` varchar(64) DEFAULT '' COMMENT '分类标记',\n" +
                "    `os_db_count` int(10) DEFAULT '0' COMMENT '委外打包次数',\n" +
                "    `before_company_id` varchar(50) DEFAULT NULL COMMENT '前手公司ID',\n" +
                "    `before_company_name` varchar(50) DEFAULT NULL COMMENT '前手公司名称',\n" +
                "    `os_judicial_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '司法委外次数',\n" +
                "    `sex` varchar(100) DEFAULT NULL COMMENT '性别 1男 2女',\n" +
                "    `risk_category` varchar(64) DEFAULT NULL COMMENT '风险分类',\n" +
                "    `case_strategy` varchar(64) DEFAULT NULL COMMENT '案件策略',\n" +
                "    `channel` varchar(100) DEFAULT NULL COMMENT '来源渠道',\n" +
                "    `account_num` int(11) DEFAULT NULL COMMENT '账户数(借据数)',\n" +
                "    `repayment_time` datetime DEFAULT NULL COMMENT '还款日',\n" +
                "    `customer_id` varchar(64) DEFAULT NULL COMMENT '客户id',\n" +
                "    `queue` varchar(100) DEFAULT NULL COMMENT '队列 AUTO自动催收 UNAUTO非自动催收',\n" +
                "    `quota` int(11) unsigned DEFAULT '0' COMMENT '当前欠款总额度',\n" +
                "    `principal` int(11) DEFAULT NULL COMMENT '案件本金(当前剩余本金)',\n" +
                "    `receive_amount` int(11) DEFAULT '0' COMMENT '一催收到的额度 账务系统确认收款，单位分',\n" +
                "    `in_collection_time` datetime DEFAULT NULL COMMENT '进入催收日期',\n" +
                "    `action_code` varchar(20) DEFAULT NULL COMMENT '最后一次行动码',\n" +
                "    `action_time` datetime DEFAULT NULL COMMENT '最后一次行动日期',\n" +
                "    `last_action_user_id` varchar(64) DEFAULT NULL COMMENT '最后一次行动催收员',\n" +
                "    `review_time` datetime DEFAULT NULL COMMENT '复核日期',\n" +
                "    `lost_contact_mark` tinyint(1) DEFAULT NULL COMMENT '失联标记',\n" +
                "    `repayment_mark` tinyint(1) DEFAULT NULL COMMENT '承诺还款标记',\n" +
                "    `permanent_user_id` varchar(64) DEFAULT NULL COMMENT '永久催收员',\n" +
                "    `permanent_group_id` varchar(64) DEFAULT NULL COMMENT '永久催收员组id',\n" +
                "    `tem_action_user_id` varchar(64) DEFAULT NULL COMMENT '临时催收员',\n" +
                "    `tem_expire_time` datetime DEFAULT NULL COMMENT '临时案件到期日',\n" +
                "    `current_user_id` varchar(64) DEFAULT NULL COMMENT '当前催收员id',\n" +
                "    `current_group_id` varchar(64) DEFAULT NULL COMMENT '当前催收员组id',\n" +
                "    `out_date` datetime DEFAULT NULL COMMENT '出催时间',\n" +
                "    `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',\n" +
                "    `collection_id` varchar(64) DEFAULT NULL COMMENT '催收id',\n" +
                "    `email_id` varchar(64) DEFAULT NULL COMMENT 'email模板 id',\n" +
                "    `letter_id` varchar(64) DEFAULT NULL COMMENT '信函模板id',\n" +
                "    `sms_id` varchar(64) DEFAULT NULL COMMENT '短信模板id',\n" +
                "    `frozen_id` varchar(64) DEFAULT NULL COMMENT '冻结 id',\n" +
                "    `wechat_id` varchar(64) DEFAULT NULL COMMENT 'wechat 模板id',\n" +
                "    `app_id` varchar(64) DEFAULT NULL COMMENT 'app 模板id',\n" +
                "    `queue_method` varchar(20) DEFAULT NULL COMMENT '队列处理级别或方式 例如M1..M7 或委外等',\n" +
                "    `queue_event` varchar(20) DEFAULT NULL COMMENT '队列事件 IVR,\n" +
                "hand手动,\n" +
                " auto_dial自拨',\n" +
                "    `status` tinyint(1) DEFAULT '1' COMMENT '是否出催 1未出催 2出催',\n" +
                "    `overdue_status` tinyint(1) DEFAULT '1' COMMENT '1 逾期 2未逾期',\n" +
                "    `marker` varchar(64) DEFAULT NULL COMMENT '案件标色',\n" +
                "    `in_case_time` datetime DEFAULT NULL COMMENT '进入收件箱时间',\n" +
                "    `dist_time` datetime DEFAULT NULL COMMENT '最后一次分按时间',\n" +
                "    `today_pd_case` varchar(20) NOT NULL DEFAULT 'N' COMMENT '是否是今日待分案件N否 日期+Y是例如20160913Y',\n" +
                "    `init_mobile` varchar(20) NOT NULL DEFAULT 'N' COMMENT '是否是今日重新初始化通话详单通讯录件N否 日期+Y是 例如20160913Y',\n" +
                "    `over_principal` bigint(20) NOT NULL DEFAULT '0' COMMENT '剩余本金',\n" +
                "    `is_outsourcing` char(1) DEFAULT '1' COMMENT '案件状态1待分配2待委外3委外确认4委外中',\n" +
                "    `stage_id` varchar(11) DEFAULT NULL COMMENT '策略编号',\n" +
                "    `office_id` varchar(64) DEFAULT NULL COMMENT '机构id',\n" +
                "    `in_office_time` datetime DEFAULT NULL COMMENT '进入机构时间',\n" +
                "    `repayment_file_aAddress` varchar(255) DEFAULT NULL COMMENT '还款文件地址',\n" +
                "    `center_id` varchar(64) NOT NULL DEFAULT '' COMMENT '中心id',\n" +
                "    `queue_change_time` datetime DEFAULT NULL COMMENT '队列变化时间',\n" +
                "    `predictive_user_id` varchar(64) DEFAULT NULL COMMENT '预分配催收员',\n" +
                "    `predictive_group_id` varchar(64) DEFAULT NULL COMMENT '预分配催收员组id',\n" +
                "    `update_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用于标记数据增量更新',\n" +
                "    PRIMARY KEY (`jid`),\n" +
                "    UNIQUE KEY `id_idx` (`id`),\n" +
                "    KEY `batch_num_idx` (`batch_num`),\n" +
                "    KEY `index_case_id` (`collection_case_id`),\n" +
                "    KEY `customer_account_index` (`customer_account`),\n" +
                "    KEY `case_status_index` (`case_status`),\n" +
                "    KEY `times_company_index` (`outsourcing_times`, `os_company_id`),\n" +
                "    KEY `idx_update_timestamp` (`update_timestamp`),\n" +
                "    KEY `idx_os_company_id` (`os_company_id`)\n" +
                ") ENGINE = InnoDB AUTO_INCREMENT = 388938 DEFAULT CHARSET = utf8 COMMENT = '委外案件'";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        MySqlToHiveOutputVisitor visitor = new MySqlToHiveOutputVisitor("pda", Boolean.TRUE);
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
        });
        System.out.println(visitor.getContent());
    }

    @Test
    public void mysqlToView() {
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

    /**
     * 获取唯一索引
     */
    @Test
    public void parseUk() {
        List<MysqlExcelColumn> list = new ArrayList<>();
        EasyExcel.read("C:\\Users\\linzhenlie-jk\\Desktop\\ddl.xlsx", MysqlExcelColumn.class,
                        new ReadListener<MysqlExcelColumn>() {
                            @Override
                            public void onException(Exception exception, AnalysisContext context) throws Exception {

                            }

                            @Override
                            public void invokeHead(Map headMap, AnalysisContext context) {

                            }

                            @Override
                            public void invoke(MysqlExcelColumn data, AnalysisContext context) {
                                list.add(data);
                            }

                            @Override
                            public void doAfterAllAnalysed(AnalysisContext context) {

                            }

                            @Override
                            public boolean hasNext(AnalysisContext context) {
                                return true;
                            }
                        })
                // 在 read 方法之后， 在 sheet方法之前都是设置ReadWorkbook的参数
                .sheet()
                .doRead();

        File file = new File("D:\\", "主键索引.xlsx");
        WriteSheet totalSheet = EasyExcel.writerSheet(1, "汇总").build();
        // 解析 SQL 语句
        try (ExcelWriter excelWriter = EasyExcel.write(file).build()) {
            for (MysqlExcelColumn mysqlExcelColumn : list) {
                List<SQLStatement> stmtList = SQLUtils.parseStatements(mysqlExcelColumn.getDdlSql(), "mysql");
                StringBuilder ukSb = new StringBuilder();
                StringBuilder pkSb = new StringBuilder();
                for (SQLStatement stmt : stmtList) {
                    if (stmt instanceof SQLCreateTableStatement) {
                        SQLCreateTableStatement createTable = (SQLCreateTableStatement) stmt;
                        int idxIndex = 0;
                        List<SQLTableElement> tableElementList = createTable.getTableElementList();
                        for (int i = 0, length = tableElementList.size(); i < length; i++) {
                            SQLTableElement tableElement = tableElementList.get(i);
                            // 获取索引
                            if (tableElement instanceof MySqlUnique) {
                                MySqlUnique mySqlUnique = (MySqlUnique) tableElement;
                                SQLIndexDefinition idxDefinition = mySqlUnique.getIndexDefinition();
                                ukSb.append(getIdxName(idxDefinition, idxIndex++));
                                ukSb.append(System.lineSeparator());
                                continue;
                            }

                            if (tableElement instanceof MySqlPrimaryKey) {
                                MySqlPrimaryKey mySqlPrimaryKey = (MySqlPrimaryKey) tableElement;
                                SQLIndexDefinition idxDefinition = mySqlPrimaryKey.getIndexDefinition();
                                pkSb.append(getIdxName(idxDefinition, idxIndex++));
                                pkSb.append(System.lineSeparator());
                                continue;
                            }
                        }
                    }
                }
                List<Object> rowData = new ArrayList<>();
                rowData.add(mysqlExcelColumn.getDbTableName());
                rowData.add(StringUtils.split(mysqlExcelColumn.getDbTableName(), ".")[1]);
                if (StringUtils.isNotBlank(ukSb.toString())) {
                    rowData.add(ukSb.toString());
                } else {
                    rowData.add(pkSb.toString());
                }
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(rowData);
                excelWriter.write(objects, totalSheet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String replaceChar(String str) {
        if (StringUtils.isBlank(str)) {
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
