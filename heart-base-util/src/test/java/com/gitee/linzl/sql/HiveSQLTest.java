package com.gitee.linzl.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateViewStatement;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.dialect.hive.parser.HiveStatementParser;
import com.alibaba.druid.sql.dialect.hive.stmt.HiveCreateTableStatement;
import com.alibaba.druid.sql.dialect.hive.visitor.HiveSchemaStatVisitor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linzhenlie-jk
 * @date 2023/4/10
 */
@Slf4j
public class HiveSQLTest {
    @Test
    public void testCreateSql() {
        String createSql = "CREATE TABLE `fin_dws_mid.dws_mid_a_loan_no_loan_static_ow_a`( \n" +
                "  `loan_no` string COMMENT '借据号'\n" +
                ", `third_code_ext` string COMMENT '账务code'\n" +
                ", `user_no` string COMMENT '用户号'\n" +
                ", `cust_no` string COMMENT '客户号'\n" +
                ", `contract_no` string COMMENT '合同号'\n" +
                ", `loan_req_no` string COMMENT '动支申请号'\n" +
                ", `third_code` string COMMENT '资方code'\n" +
                ", `product_code` string COMMENT '产品代码'\n" +
                ", `time_succ` string COMMENT '放款成功时间，格式：yyyy-MM-dd HH:mm:ss'\n" +
                ", `loan_channel_id` string COMMENT '资金动用渠道'\n" +
                ", `term` bigint COMMENT '期数'\n" +
                ", `rpy_type` string COMMENT '还款方式:00-等额本金'\n" +
                ", `date_inst` string COMMENT '起息日格式:yyyy-MM-dd'\n" +
                ", `date_end` string COMMENT '最后一期还款时间'\n" +
                ", `loan_amt` decimal(17, 2) COMMENT '借款金额'\n" +
                ", `time_third` string COMMENT '推送资方时间'\n" +
                ", `after_cap_nominal_apr` decimal(12, 8) COMMENT 'cap后名义apr'\n" +
                ", `after_cap_irr` decimal(12, 8) COMMENT 'cap后irr'\n" +
                ", `after_cap_realistic_apr` decimal(12, 8) COMMENT 'cap后实际apr'\n" +
                ", `loan_duration` string COMMENT '理论久期'\n" +
                ", `distribute_discount_rate` decimal(17, 10) COMMENT '资产分发打折率，业务系统试算时候确定'\n" +
                ", `before_fpd_refund_amt` decimal(17, 2) COMMENT '首期账单日之前退款金额（仅商城）'\n" +
                ", `before_fpd_bef_refund_amt` decimal(17, 2) COMMENT '首期账单日之前退款金额（仅商城）_截止到上月底'\n" +
                ", `is_union_third` string COMMENT '是否联合贷'\n" +
                ", `req_funder_type` string COMMENT '分润、兜底、分润转置信'\n" +
                ", `split_rate` string COMMENT '联合联合贷拆分比例，非联合贷资方为1'\n" +
                ", `partner_type` string COMMENT '财务资方类型:分润、助贷、上表、智信平台、重组'\n" +
                ", `loan_vintage` string COMMENT 'new_vintage'\n" +
                ", `third_type_main_name` string COMMENT '资方类型'\n" +
                ", `contract_risk_level` string COMMENT '合同风险等级(低风险，中风险，高风险，其他)'\n" +
                ", `contract_risk_tag_r` string COMMENT '合同风险R标'\n" +
                ", `appoint_tag_draw` string COMMENT '资产分发B标_draw'\n" +
                ", `result_before_b8` string COMMENT '仅分润输出结果'\n" +
                ", `extra_budget_flag` string COMMENT '预算外资产标签(Y是/N否)'\n" +
                ", `p10_product_type` string COMMENT '动支产品（上海大额、北京小额、上海小额、尊享贷、尊享e贷、商城、微零花、大额快贷、小微贷、备用金）'\n" +
                ", `product_code_2` string COMMENT '产品代码_含会员'\n" +
                ", `time_finished_p5` string COMMENT 'p5客户首授信日期'\n" +
                ", `wj_terminal_p5` string COMMENT 'p5首授信端'\n" +
                ") COMMENT '复合放款事实表' ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.orc.OrcSerde' \n" +
                "STORED AS INPUTFORMAT 'org.apache.hadoop.hive.ql.io.orc.OrcInputFormat' \n" +
                "OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.orc.OrcOutputFormat'\n" +
                "LOCATION 'hdfs://360jinronglycc/user/hive/warehouse/fin_dws_mid.db/dws_mid_a_loan_no_loan_static_ow_a'\n" +
                "TBLPROPERTIES ( 'bizOwner' = 'raozaipin-jk'\n" +
                ", 'creater' = 'shaojuanjuan-jk'\n" +
                ", 'last_modified_by' = 'dam'\n" +
                ", 'last_modified_time' = '1702975417'\n" +
                ", 'spark.sql.create.version' = '2.2 or prior'\n" +
                ", 'spark.sql.sources.schema.numParts' = '2'\n" +
                ", 'spark.sql.sources.schema.part.0' = '{\"type\":\"struct\"\n" +
                ",\"fields\":[{\"name\":\"loan_no\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"借据号\"}}\n" +
                ",{\"name\":\"third_code_ext\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"账务code\"}}\n" +
                ",{\"name\":\"user_no\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"用户号\"}}\n" +
                ",{\"name\":\"cust_no\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"客户号\"}}\n" +
                ",{\"name\":\"contract_no\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"合同号\"}}\n" +
                ",{\"name\":\"loan_req_no\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"动支申请号\"}}\n" +
                ",{\"name\":\"third_code\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"资方code\"}}\n" +
                ",{\"name\":\"product_code\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"产品代码\"}}\n" +
                ",{\"name\":\"time_succ\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"放款成功时间，格式：yyyy-MM-dd HH:mm:ss\"}}\n" +
                ",{\"name\":\"loan_channel_id\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"资金动用渠道\"}}\n" +
                ",{\"name\":\"term\"\n" +
                ",\"type\":\"long\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"期数\"}}\n" +
                ",{\"name\":\"rpy_type\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"还款方式:00-等额本金\n" +
                ",01-等额本息\n" +
                ",02-先息后本\n" +
                ",03-等本等息\n" +
                ",04-新等额本金\"}}\n" +
                ",{\"name\":\"date_inst\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"起息日\n" +
                ",格式:yyyy-MM-dd\"}}\n" +
                ",{\"name\":\"date_end\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"最后一期还款时间\"}}\n" +
                ",{\"name\":\"loan_amt\"\n" +
                ",\"type\":\"decimal(17\n" +
                ",2)\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"借款金额\"}}\n" +
                ",{\"name\":\"time_third\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"推送资方时间\"}}\n" +
                ",{\"name\":\"after_cap_nominal_apr\"\n" +
                ",\"type\":\"decimal(12\n" +
                ",8)\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"cap后名义apr\"}}\n" +
                ",{\"name\":\"after_cap_irr\"\n" +
                ",\"type\":\"decimal(12\n" +
                ",8)\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"cap后irr\"}}\n" +
                ",{\"name\":\"after_cap_realistic_apr\"\n" +
                ",\"type\":\"decimal(12\n" +
                ",8)\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"cap后实际apr\"}}\n" +
                ",{\"name\":\"loan_duration\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"理论久期\"}}\n" +
                ",{\"name\":\"distribute_discount_rate\"\n" +
                ",\"type\":\"decimal(17\n" +
                ",10)\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"资产分发打折率，业务系统试算时候确定\"}}\n" +
                ",{\"name\":\"before_fpd_refund_amt\"\n" +
                ",\"type\":\"decimal(17\n" +
                ",2)\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"首期账单日之前退款金额（仅商城）\"}}\n" +
                ",{\"name\":\"before_fpd_bef_refund_amt\"\n" +
                ",\"type\":\"decimal(17\n" +
                ",2)\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"首期账单日之前退款金额（仅商城）_截止到上月底\"}}\n" +
                ",{\"name\":\"is_union_third\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"是否联合贷\"}}\n" +
                ",{\"name\":\"req_funder_type\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"分润、兜底、分润转置信\"}}\n" +
                ",{\"name\":\"split_rate\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"联合联合贷拆分比例，非联合贷资方为1\"}}\n" +
                ",{\"name\":\"partner_type\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"财务资方类型:分润、助贷、上表、智信平台、重组\"}}\n" +
                ",{\"name\":\"loan_vintage\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"new_vintage\"}}\n" +
                ",{\"name\":\"third_type_main_name\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"资方类型\"}}\n" +
                ",{\"name\":\"contract_risk_level\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"合同风险等级(低风险，中风险，高风险，其他)\"}}\n" +
                ",{\"name\":\"contract_risk_tag_r\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"合同风险R标\"}}\n" +
                ",{\"name\":\"appoint_tag_draw\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"资产分发B标_draw\"}}\n" +
                ",{\"name\":\"result_before_b8\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"仅分润输出结果\"}}\n" +
                ",{\"name\":\"extra_budget_flag\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"预算外资产标签(Y是/N否)\"}}\n" +
                ",{\"name\":\"p10_product_type\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"动支产品（上海大额、北京小额、上海小额、尊享贷、尊享e贷、商城、微零花、大额快贷、小微贷、备用金）\"}}\n" +
                ",{\"name\":\"product_code_2\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"产品代码_含会员\"}}\n" +
                ",{\"name\":\"time_finished_p5\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"p5客户首授信日期\"}}\n" +
                ",{\"name\":\"wj_terminal_p5\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"p5首授信端\"}}\n" +
                ",{\"name\":\"if_48_test\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"4.8测试标签\"}}\n" +
                ",{\"name\":\"sme_team_gr\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"小微贷作业团队分类\"}}\n" +
                ",{\"name\":\"risk_price_group_credit_p5\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"客户风险分层_p5首授信-拉齐的风险分层（P18、P24、P36、新P3'\n" +
                ", 'spark.sql.sources.schema.part.1' = '6、分发客群）\"}}\n" +
                ",{\"name\":\"new_kpi_category_wj_1_4\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"1.4归因大类\"}}\n" +
                ",{\"name\":\"new_kpi_1st_category_wj_1_4\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"1.4归因一级分类\"}}\n" +
                ",{\"name\":\"new_kpi_2nd_category_wj_1_4\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"1.4归因二级分类\"}}\n" +
                ",{\"name\":\"new_3rd_category_wj_1_4\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"1.4归因三级分类\"}}\n" +
                ",{\"name\":\"time_finished_profit\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"首授信时间\"}}\n" +
                ",{\"name\":\"is_share_profit_to_ice\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"是否分润转智信:Y是N否\"}}\n" +
                ",{\"name\":\"user_type\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"业务线细类_用户盈利性:借条APP&H5\n" +
                ",借条API\n" +
                ",商城\n" +
                ",借条-地推\n" +
                ",微零花\n" +
                ",小微贷-渠道合作\n" +
                ",线下周转灵\n" +
                ",大额快贷\n" +
                ",小微贷-地推\n" +
                ",null\"}}\n" +
                ",{\"name\":\"assets_class\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"业务线大类_资产盈利性分类：借条APP&H5&微零花、借条API&商城、小微贷-渠道合作、借条-地推、大额快贷、小微贷-地推、线下周转灵\"}}\n" +
                ",{\"name\":\"zone_name_p5_first_credit\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"p5首授信申请大区名称\"}}\n" +
                ",{\"name\":\"emt_week_range\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"emt周范围（非自然周，上周四到本周三）\"}}\n" +
                ",{\"name\":\"emt_week_en_name\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"emt周名称_英文(非自然周 上周四至本周三)\"}}\n" +
                ",{\"name\":\"product_type_gr\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"动支产品分类\"}}\n" +
                ",{\"name\":\"mx3_flag\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"马消标签\"}}\n" +
                ",{\"name\":\"cust_type_adjust\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"合同风险等级_低中高马消\"}}\n" +
                ",{\"name\":\"gc_mob\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"观测月\"}}\n" +
                ",{\"name\":\"partner_type_two\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"财务资方类型_分润兜底\"}}\n" +
                ",{\"name\":\"time_succ_credit_mob\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"授信mob_用户盈利性业务线大类\"}}\n" +
                ",{\"name\":\"vintage_not_null_flag\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"分发vintage不为空标签\"}}\n" +
                ",{\"name\":\"b8fr_flag\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"B8仅分润标签\"}}\n" +
                ",{\"name\":\"user_type_loan_succ\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"动支mob_业务线大类\"}}\n" +
                ",{\"name\":\"ditui_gr\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"地推团队分类\"}}\n" +
                ",{\"name\":\"is_loan_no_ice\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"分润转智信标签(收入模式为分润转智信即Y，否则为N)\"}}\n" +
                ",{\"name\":\"new_old_type_p5\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"新老户\"}}\n" +
                ",{\"name\":\"loan_risk_level_cp\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"合同风险等级-高中低(产品用)\"}}\n" +
                ",{\"name\":\"product_type_gr_byj\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"动支产品分类_备用金拆出\"}}\n" +
                ",{\"name\":\"new_kpi_1st_category_wj_1_4_48\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"1.4归因一级分类_48_test\"}}\n" +
                ",{\"name\":\"new_kpi_2nd_category_wj_1_4_48\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"1.4归因二级分类_48_test\"}}\n" +
                ",{\"name\":\"new_3rd_category_wj_1_4_48\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"1.4归因三级分类_48_test\"}}\n" +
                ",{\"name\":\"profit_user_type\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"业务线大类_用户盈利性:借条APP&H5&微零花\n" +
                ",借条API&商城\n" +
                ",小微贷-渠道合作\n" +
                ",借条-地推\n" +
                ",大额快贷\n" +
                ",小微贷-地推\n" +
                ",线下周转灵\"}}\n" +
                ",{\"name\":\"risk_group\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"资产分发G组\"}}\n" +
                ",{\"name\":\"product_type_p5\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"p5首授信产品(上海大额\n" +
                ",北京小额\n" +
                ",上海小额\n" +
                ",尊享e贷\n" +
                ",尊享贷)\"}}\n" +
                ",{\"name\":\"api_channel_flag_p0\"\n" +
                ",\"type\":\"string\"\n" +
                ",\"nullable\":true\n" +
                ",\"metadata\":{\"comment\":\"API渠道标识_p0动支(Y-是\n" +
                ",N-否)\"}}]}'\n" +
                ", 'transient_lastDdlTime' = '1714948151')";
        parse(createSql.replaceAll("`",""));
    }

    @Test
    public void testViewSql() {
        String createViewSql = "CREATE VIEW `ods.prd_cis_u_user_view` AS\n" +
                "select\n" +
                "    `cis_u_user_decrypt`.`id`,\n" +
                "    `cis_u_user_decrypt`.`user_no`,\n" +
                "    `cis_u_user_decrypt`.`cust_no`,\n" +
                "    `cis_u_user_decrypt`.`device_no`,\n" +
                "    `cis_u_user_decrypt`.`geo_no`,\n" +
                "    `cis_u_user_decrypt`.`user_name_encryptx`,\n" +
                "    `cis_u_user_decrypt`.`user_name_md5x`,\n" +
                "    `cis_u_user_decrypt`.`user_pwd`,\n" +
                "    `cis_u_user_decrypt`.`trans_pwd`,\n" +
                "    `cis_u_user_decrypt`.`trans_pwd_flag`,\n" +
                "    `cis_u_user_decrypt`.`salt`,\n" +
                "    `cis_u_user_decrypt`.`mobile_no_encryptx`,\n" +
                "    `cis_u_user_decrypt`.`mobile_no_md5x`,\n" +
                "    `cis_u_user_decrypt`.`division_code`,\n" +
                "    `cis_u_user_decrypt`.`channel_id`,\n" +
                "    `cis_u_user_decrypt`.`sub_channel`,\n" +
                "    `cis_u_user_decrypt`.`activity_info`,\n" +
                "    `cis_u_user_decrypt`.`register_source`,\n" +
                "    `cis_u_user_decrypt`.`host_app`,\n" +
                "    `cis_u_user_decrypt`.`invitation_code`,\n" +
                "    `cis_u_user_decrypt`.`partner_id`,\n" +
                "    `cis_u_user_decrypt`.`register_time`,\n" +
                "    `cis_u_user_decrypt`.`user_state`,\n" +
                "    `cis_u_user_decrypt`.`expire_time`,\n" +
                "    `cis_u_user_decrypt`.`created_by`,\n" +
                "    `cis_u_user_decrypt`.`date_created`,\n" +
                "    `cis_u_user_decrypt`.`updated_by`,\n" +
                "    `cis_u_user_decrypt`.`date_updated`,\n" +
                "    `cis_u_user_decrypt`.`partitions`,\n" +
                "    substr(`cis_u_user_decrypt`.`mobile_no`, 6, 1) as `mobile_the_six`,\n" +
                "    md5x(substr(`cis_u_user_decrypt`.`mobile_no`, 1, 7)) as `mobile_no_pre_7`,\n" +
                "    substr(`cis_u_user_decrypt`.`mobile_no`, 1, 3) as `phone_prefix_3`,\n" +
                "    substr(`cis_u_user_decrypt`.`mobile_no`, -4, 4) as `phone_last_4`,\n" +
                "    md5(`cis_u_user_decrypt`.`mobile_no`) as `mobile_no_md5`,\n" +
                "    replace(`cis_u_user_decrypt`.`partitions`, '-', '') as `pday`\n" +
                "from\n" +
                "    `ods_safe`.`cis_u_user_decrypt`";
        parse(createViewSql.replaceAll("`",""));
    }


    @Test
    public void drop1() {
        log.info("1=========");
        String sql = "DROP TABLE fin_dw.dwd_trade_loan_pda;";
        String correctSql = "DROP TABLE IF EXISTS fin_dw.dwd_trade_loan_pda;";
        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void drop2() {
        String sql = "INSERT OVERWRITE TABLE dwd_tmp.test " +
                " SELECT * FROM (" +
                " select distinct loan_no,user_name FROM A where id = 1 AND date(time_submit)>'11'" +
                " UNION ALL " +
                " select distinct loan_no,user_name FROM A where id = 2 AND time>'22'" +
                " )b WHERE b.id = 3;";
        String correctSql = "INSERT OVERWRITE TABLE dwd_tmp.test" +
                "SELECT  * FROM (\n" +
                " SELECT  loan_no,user_name FROM A WHERE id = 1 AND date(time_submit) > '11'\n" +
                " GROUP BY  loan_no,user_name\n" +
                " UNION ALL\n" +
                " SELECT  loan_no,user_name FROM A WHERE id = 2 AND time > '22'\n" +
                " GROUP BY  loan_no,user_name\n" +
                ") AS b WHERE b.id = 3;";
        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void create() {
        String sql =
                "CREATE TABLE fin_dw.dwd_trade_loan_pda ( \n" +
                        "     user_no                 string         COMMENT  '用户号' \n" +
                        "    ,cust_no                 string         COMMENT  '' \n" +
                        "    ,term                    INT            COMMENT  '期数' \n" +
                        "    ,bank_code               string         COMMENT  '银行卡编号' \n" +
                        "    ,loan_channel_id         string         COMMENT  '资金动用渠道' \n" +
                        "    ,rpy_type                string         COMMENT  '还款方式:00-等额本金,01-等额本息,02-先息后本,03等本等息(商城固定)' \n" +
                        "    ,date_compensate         string         COMMENT  '代偿日期yyyy-MM-dd' \n" +
                        "    ,compensate_type         string         COMMENT  '代偿类型,NCP未代偿,ECP提前代偿,OCP逾期代偿' \n" +
                        "    ,over_due_days           INT            COMMENT  '系统逾期天数(对应over_due_status)' \n" +
                        "    ,calc_over_due_days      INT            COMMENT  '逻辑逾期天数(昨日未还over_due_days+1天)'\n" +
                        "    ,over_due_status         string         COMMENT  '拖欠周期状态码(对应over_due_days)' \n" +
                        ") \n" +
                        "COMMENT '' \n" +
                        "PARTITIONED BY ( \n" +
                        "      pday        string COMMENT  '' \n" +
                        "     ,source_type string COMMENT  '数据源类型:JT借条,WLH微零花,MALL商城' \n" +
                        ") USING TEXT \n";
        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void create2() {
        String sql = "CREATE TABLE IF NOT EXISTS dwd_tmp.test USING TEXT AS \n"
                + "SELECT  loan_no"
                + "       ,IF(t0.settle_days IS NOT NULL, t1.settle_term, NULL) AS settle_term\n"
                + "       ,t1.date_settle_days_last\n"
                + "       ,COUNT(distinct loan_no) AS loan_cnt\n"
                + "       ,COALESCE(t2.over_due_days_head_3terms_max ,0)\n"
                + "       ,t3.date_tran_last\n"
                + "       ,GET_JSON_OBJECT(outputjson, '$.score') AS score\n"
                + "       ,get_json_object(outputjson, '$.val') AS val\n"
                + "       ,'${last2Days_p}'                                   AS pday\n"
                + "       ,t0.source_type   AS source_type\n"
                + "FROM dwd_tmp.tmp00_his AS t0\n"
                + "LEFT JOIN dwd_tmp.tmp01_his AS t1\n"
                + "ON t0.loan_no = t1.loan_no \n"
                + "LEFT JOIN dwd_tmp.tmp02_his AS t2\n"
                + "ON t0.loan_no = t2.loan_no AND 1=1 \n"  // 出现1=1
                + "LEFT JOIN dwd_tmp.tmp03_his AS t3\n"
                + "ON t2.loan_no = t3.loan_no\n"
                + "LEFT JOIN dwd_tmp.tmp04_his AS t4\n"
                + "ON t2.loan_no = t4.loan_no\n"
                + "RIGHT JOIN dwd_tmp.tmp05_his AS t5\n"
                + "ON t5.loan_no = t4.loan_no;\n";
        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void create3() {
        // 禁止 1=1 ，禁止使用 select * FROM 表
        // right join改为使用left join 更符合阅读及使用习惯
        String sql = "CREATE TABLE IF NOT EXISTS dwd_tmp.test USING TEXT AS \n"
                + "SELECT  *,loan_no AS loan_no1,t1.*,count(distinct *)\n"
                + ",IF(COUNT(distinct *) OVER(PARTITION BY cust_no ORDER BY date_created) = loan_no,'Y'," +
                "'N') AS first_loan\n"
                + "FROM dwd_tmp.tmp00_his\n"
                + "LEFT JOIN dwd_tmp.tmp01_his AS t1\n"
                + "ON t0.loan_no = t1.loan_no AND '1'='1'\n";
        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void create4() {
        String sql = "CREATE TABLE IF NOT EXISTS dwd_tmp.test USING TEXT AS \n"
                + "SELECT  *\n"
                + "FROM dwd_tmp.tmp00_his AS t0 where t0.id in (select id FROM dwd_tmp.tmp01_his)\n";
        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void create5() {
        String sql = "CREATE TABLE IF NOT EXISTS dwd_tmp.test USING TEXT AS \n"
                + "SELECT  t.id AS id2\n"
                + "FROM (select * FROM (select * FROM dwd_tmp.tmp01_his where 1=1) AS a1  where 2=2) AS a2 where a2.id=1\n";
        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void alter() {
        String sql = "ALTER TABLE fin_dw.dwd_trade_loan_ice_static_pdi CHANGE COLUMN year_rate year_rate DECIMAL(17,9)" +
                " COMMENT '';";
        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void alter1() {
        String sql = "ALTER TABLE fin_dw.dwd_trade_loan_pda ADD COLUMNS(" +
                "        is_due_mob37_01         string         COMMENT  '达mob37且1天观察日'" +
                "        ,over_due_days_mob37_01 string         COMMENT  ''" +
                "        ,act_prin_amt_mob37_01  decimal(17,2)  COMMENT  '达mob37且1天观察日实还本金'" +
                "        ,is_due_mob37_04        string         COMMENT  '达mob37且4天观察日'" +
                "    );";

        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void alter2() {
        String sql = "ALTER TABLE fin_dw.dwd_trade_loan_pda REPLACE COLUMNS(" +
                "        is_due_mob37_01        string         COMMENT  '达mob37且1天观察日'" +
                "        ,over_due_days_mob37_01 string         COMMENT  '达mob37且1天观察日逾期天数'" +
                "        ,act_prin_amt_mob37_01  decimal(17,2) COMMENT  ''" +
                "        ,is_due_mob37_04        string         COMMENT  '达mob37且4天观察日'" +
                "    );";
        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void insert1() {
        // 没有别名，join几次，就有几个on条件，主表出现的次数不等于join个数，可能出现倾斜
        // 多次使用get_json_object，改用json_purple
        // join次数大于10，join条件疑似数据倾斜(非主表条件)
        String sql = "INSERT OVERWRITE TABLE dwd_tmp.test partition(pday,source_type)\n"
                + "SELECT  loan_no,IF(t0.settle_days IS NOT NULL, t1.settle_term, NULL) AS settle_term\n"
                + "       ,t1.date_settle_days_last\n"
                + "       ,COUNT(distinct loan_no) AS loan_cnt\n"
                + "       ,COALESCE(t2.over_due_days_head_3terms_max ,0)\n"
                + "       ,t3.date_tran_last\n"
                + "       ,GET_JSON_OBJECT(outputjson, '$.score') AS score\n"
                + "       ,get_json_object(outputjson, '$.val') AS val\n"
                + "       ,'${last2Days_p}'                                   AS pday\n"
                + "       ,t0.source_type   AS source_type\n"
                + "FROM dwd_tmp.tmp00_his AS t0\n"
                + "LEFT JOIN dwd_tmp.tmp01_his AS t1\n"
                + "ON t0.loan_no = t1.loan_no\n"
                + "LEFT JOIN dwd_tmp.tmp02_his AS t2\n"
                + "ON t0.loan_no = t2.loan_no AND 1=1\n"
                + "LEFT JOIN dwd_tmp.tmp03_his AS t3\n"
                + "ON t2.loan_no = t3.loan_no\n"
                + "LEFT JOIN dwd_tmp.tmp04_his AS t4\n"
                + "ON t2.loan_no = t4.loan_no;\n";
        HiveStatementParser parser = new HiveStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        HiveRuleCheckVisitor visitor = new HiveRuleCheckVisitor();
        List<SQLExprTableSource> tableList = new ArrayList<>();
        stmtList.forEach(sqlStatement -> {
            sqlStatement.accept(visitor);
            tableList.addAll(visitor.getTableList());
        });
    }

    @Test
    public void crossJoin() {
        // cross join / full join
    }

    // 嵌套式子查询join
    // WITH{
    //  ****
    // }

    private void parse(String sql) {
        // 新建 Parser
        // 解析 SQL 语句
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, "hive");

        // 遍历解析结果，根据不同的语句类型做相应的处理
        for (SQLStatement stmt : stmtList) {
            if (stmt instanceof SQLSelectStatement) {
                // 处理 SELECT 语句
                SQLSelectStatement selectStmt = (SQLSelectStatement) stmt;
                HiveSchemaStatVisitor visitor = new HiveSchemaStatVisitor();
                selectStmt.accept(visitor);
                visitor.getTables();
                System.out.println("The select table name is: " + selectStmt.getSelect().getQueryBlock().getFrom().findTableSource(0));
            } else if (stmt instanceof SQLInsertStatement) {
                // 处理 INSERT 语句
                SQLInsertStatement insertStmt = (SQLInsertStatement) stmt;
                System.out.println("The insert table name is: " + insertStmt.getTableSource().getName());
                List<SQLObject> children = insertStmt.getChildren();
                System.out.println(children);
                SQLSelect query = insertStmt.getQuery();
                System.out.println(query);
            } else if (stmt instanceof SQLUpdateStatement) {
                // 处理 UPDATE 语句
                SQLUpdateStatement updateStmt = (SQLUpdateStatement) stmt;
                System.out.println("The update table name is: " + updateStmt.getFrom().findTableSource(0));
            } else if (stmt instanceof SQLDeleteStatement) {
                // 处理 DELETE 语句
                SQLDeleteStatement deleteStmt = (SQLDeleteStatement) stmt;
                System.out.println("The delete table name is: " + deleteStmt.getTableName().getSimpleName());
            } else if (stmt instanceof HiveCreateTableStatement) {
                // 处理 CREATE 语句
                HiveCreateTableStatement createStmt = (HiveCreateTableStatement) stmt;
                System.out.println("The table schema is: " + createStmt.getSchema());
                System.out.println("The table name is: " + createStmt.getTableName());
                for (SQLTableElement sqlTableElement : createStmt.getTableElementList()) {

                    SQLColumnDefinition definition = (SQLColumnDefinition) sqlTableElement;
                    definition.getName();
                    definition.getDataType();
                    definition.getComment();
                }
            } else if (stmt instanceof SQLCreateViewStatement) {
                // 处理 CREATE 语句
                SQLCreateViewStatement viewStmt = (SQLCreateViewStatement) stmt;
                System.out.println("The view schema is: " + viewStmt.getSchema());
                System.out.println("The view name is: " + viewStmt.getTableSource().getTableName());
                viewStmt.getSubQuery();
                /*for (SQLTableElement sqlTableElement : viewStmt) {

                    SQLColumnDefinition definition  = (SQLColumnDefinition)sqlTableElement;
                    definition.getName();
                    definition.getDataType();
                    definition.getComment();
                }*/
            }
        }
    }
}
