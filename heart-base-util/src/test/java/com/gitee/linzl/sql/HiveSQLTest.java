package com.gitee.linzl.sql;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.hive.parser.HiveStatementParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author linzhenlie-jk
 * @date 2023/4/10
 */
@Slf4j
public class HiveSQLTest {
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
            ") AS b WHERE b.id = 3;"
            ;
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
    public  void create5() {
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
}
