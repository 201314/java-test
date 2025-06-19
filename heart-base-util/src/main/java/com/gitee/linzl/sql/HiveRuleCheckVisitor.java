package com.gitee.linzl.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.expr.SQLAggregateExpr;
import com.alibaba.druid.sql.ast.expr.SQLAllColumnExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumericLiteralExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.expr.SQLTextLiteralExpr;
import com.alibaba.druid.sql.ast.statement.SQLAlterTableItem;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateStatement;
import com.alibaba.druid.sql.ast.statement.SQLDropTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLLateralViewTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.ast.statement.SQLUnionQuery;
import com.alibaba.druid.sql.ast.statement.SQLUnionQueryTableSource;
import com.alibaba.druid.sql.ast.statement.SQLValuesTableSource;
import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause;
import com.alibaba.druid.sql.dialect.hive.ast.HiveInsertStatement;
import com.alibaba.druid.sql.dialect.hive.stmt.HiveCreateTableStatement;
import com.alibaba.druid.sql.dialect.hive.visitor.HiveSchemaStatVisitor;
import com.alibaba.druid.sql.parser.Token;
import com.alibaba.druid.stat.TableStat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 合并 SQLTableAliasCollectVisitor 、 SQLSubQueryGroupVisitor
 * 非禁用节点检验语法规范、只校验SQL节点、通知内容：强调SLA链路上工作流的重要性、语法问题会导致终端关键报表延迟产出，因此扫描出以下语法规范问题指引大家修正
 * 1. drop
 * IF EXISTS 避免删除不存在的表,导致报错
 * <p>
 * 2. create 单独出现时(如drop/create相同表，只校验drop)
 * IF NOT EXISTS 可以避免重复创建
 * ORC 可以指定存储格式
 * COMMENT 可以指定表注释、字段注释
 * etl_time 建表第一个字段使用,用于查数据运行时间
 * <p>
 * 3. insert
 * 建议指定 overwrite 模式,避免数据重复
 * <p>
 * 4. select * 容易发生插入数据字段不一致、出现同名字段报错
 * 禁止最外层查询使用select * 或 select a.* 模式
 * UNION 表 不允许使用select *  或 select a.* 模式  易发生字段数量不一致
 * JOIN 多表,不允许使用 select *  或 select a.*,b,c,d 模式  易发生插入数据字段不一致、出现同名字段报错
 * <p>
 * 例子
 * create/insert XXX
 * select t1.*,t2.* from ( -- t1和t2表有同名ID
 * select * from A_tb  -- 易出现字段个数不一致
 * UNION
 * select * from B_tb  -- 易出现字段个数不一致
 * ) AS t1 left join C_tb AS t2
 * ON t1.id = t2.id
 * <p>
 * 5. select 结果不允许出现同名字段
 * <p>
 * 6. SQL解析异常 explain解析不通过
 * <p>
 * 7. 禁止出现多个distinct,改用group by查询
 * 禁止5个以上JOIN,性能下降
 *
 * 8. 不允许使用current_date/current_timestamp函数，改用占位符，便于维护时可重跑数据
 *
 * @author linzhenlie-jk
 * @date 2023/4/3
 */
@Slf4j
public class HiveRuleCheckVisitor extends HiveSchemaStatVisitor {
    protected Map<SQLTableSource, List<String>> tableSource2AliasMap = new LinkedHashMap<>();

    List<SQLExprTableSource> tableList = new LinkedList<>();

    List<SQLJoinTableSource.JoinType> joinTypeList = new LinkedList<>();

    private Map<String, AtomicInteger> ownerCnt = new HashMap<>(20);

    private Map<String, AtomicInteger> methodCnt = new HashMap<>(20);

    private Map<String, AtomicInteger> distinctCnt = new HashMap<>(20);

    public void endVisit(HiveCreateTableStatement create) {
        TableStat tableStat = getTableStat(create.getName());
        // 存在drop/create相同表，只校验drop
        if (!StringUtils.startsWith(tableStat.toString().toUpperCase(), "DROP") && !create.isIfNotExists()) {
            log.error("表信息:create操作必须指定IF NOT EXISTS");
        }

        SQLExpr orcStored = ObjectUtils.firstNonNull(create.getStoredAs(), create.getUsing());
        if (Objects.isNull(orcStored)) {
            log.error("表信息:create操作必须指定存储格式STORED AS ORC 或 USING ORC");
        }

        SQLCharExpr comment = (SQLCharExpr) create.getComment();
        if (Objects.isNull(comment) || StringUtils.isBlank(comment.getText())) {
            log.error("表信息:create操作必须指定comment注释");
        }
    }

    public void endVisit(SQLDropTableStatement drop) {
        if (!drop.isIfExists()) {
            log.error("drop操作必须指定IF EXISTS");
        }
    }


    public void endVisit(HiveInsertStatement insert) {
        if (!insert.isOverwrite()) {
            log.error("表信息:insert操作必须指定overwrite");
        }
    }

    public void endVisit(SQLLateralViewTableSource x) {
        if (StringUtils.isNotBlank(x.getAlias())) {
            tableSource2AliasMap.computeIfAbsent(x, key -> new ArrayList<>()).add(x.getAlias());
        }
    }


    public void endVisit(SQLSubqueryTableSource subQuery) {
        String alias = subQuery.getAlias();
        if (StringUtils.isBlank(alias)) {
            return;
        }
        tableSource2AliasMap.computeIfAbsent(subQuery, key -> new ArrayList<>()).add(alias);
    }


    public void endVisit(SQLWithSubqueryClause.Entry x) {
        if (StringUtils.isNotBlank(x.getAlias())) {
            tableSource2AliasMap.computeIfAbsent(x, key -> new ArrayList<>()).add(x.getAlias());
        }
    }


    public void endVisit(SQLSelectQueryBlock selectQuery) {
        if (selectQuery.isDistinct()) {
            // select distinct loan_no FROM A
            log.error("预估数据未来会超千万,并且select字段超过1个且存在distinct操作时,将distinct对应字段下沉到group by");
        }

        if (selectQuery.getSelectList().size() > 1 && distinctCnt.size() >= 1) {
            log.error("select字段超过1个且存在distinct操作时,使用group by替代distinct");
        }
    }

    public void endVisit(SQLUnionQueryTableSource unionTable) {
        if (StringUtils.isNotBlank(unionTable.getAlias())) {
            tableSource2AliasMap.computeIfAbsent(unionTable, key -> new ArrayList<>()).add(unionTable.getAlias());
        }
    }

    public void endVisit(SQLSelectStatement stmt) {
        SQLSelectQuery query = stmt.getSelect().getQuery();
        // TODO 最外层查询 不允许出现 * 取所有字段
        checkSelect(query);
    }

    public void endVisit(SQLUnionQuery unionQuery) {
        // TODO UNION 不允许出现 * 取所有字段
        for (SQLSelectQuery relation : unionQuery.getRelations()) {
            checkSelect(relation);
        }
    }

    public void endVisit(SQLJoinTableSource x) {
        joinTypeList.add(x.getJoinType());
        String alias = x.getAlias();
        int joinSize = CollectionUtils.size(joinTypeList);
        if (joinSize > 5) {
            log.error("禁止5个以上JOIN查询过多，请优化查询语句");
        }

        if (StringUtils.isNotBlank(alias)) {
            tableSource2AliasMap.computeIfAbsent(x, key -> new ArrayList<>()).add(alias);
        }
    }

    public void endVisit(SQLExprTableSource x) {
        tableList.add(x);
        if (StringUtils.isBlank(x.getAlias()) && x.getParent() instanceof SQLJoinTableSource) {
            log.error("表:{},未指定别名", x);
        }

        SQLExpr expr = x.getExpr();
        if (expr instanceof SQLName) {
            tableSource2AliasMap.computeIfAbsent(x, key -> new ArrayList<>()).add(((SQLName) expr).getSimpleName());
        }

        if (StringUtils.isNotBlank(x.getAlias())) {
            tableSource2AliasMap.computeIfAbsent(x, key -> new ArrayList<>()).add(x.getAlias());
        }
    }

    public void endVisit(SQLValuesTableSource x) {
        if (StringUtils.isNotBlank(x.getAlias())) {
            tableSource2AliasMap.computeIfAbsent(x, key -> new ArrayList<>()).add(x.getAlias());
        }
    }


    public void endVisit(SQLColumnDefinition columnDefinition) {
        String logPrefix = "";
        if (columnDefinition.getParent() instanceof SQLAlterTableItem) {
            logPrefix = "表变更信息:";
        }
        if (columnDefinition.getParent() instanceof SQLCreateStatement) {
            logPrefix = "表字段信息:";
        }

        if (Objects.isNull(columnDefinition.getDataType())) {
            log.error(logPrefix + columnDefinition.getName() + "必须指定数据类型");
        }
        SQLCharExpr columnComment = (SQLCharExpr) columnDefinition.getComment();
        if (Objects.isNull(columnComment) || StringUtils.isBlank(columnComment.getText())) {
            log.error(logPrefix + columnDefinition.getName() + "必须填写COMMENT注释");
        }

        //  create 不允许出现同名字段
    }

    public boolean visit(SQLAllColumnExpr x) {
        // 如果是要做字段血缘,此处可以将字段捞回填充,进行各字段accept
        super.visit(x);
        return false;
    }

    public void endVisit(SQLPropertyExpr propertyExpr) {
        // *, t.*
        if (StringUtils.endsWith(propertyExpr.getName(), Token.STAR.name) && propertyExpr.getParent() instanceof SQLSelectItem) {
            log.error("SQLPropertyExpr禁止使用*获取所有字段:{}", propertyExpr.getName());
        }
    }

    public void endVisit(SQLIdentifierExpr idExpr) {
        // 只有在建表时校验
        if (idExpr.getParent() instanceof HiveCreateTableStatement && !idExpr.nameEquals(Token.USING.name) && !idExpr.nameEquals("ORC")) {
            log.error("create操作必须指定STORED AS ORC 或 USING ORC");
        }

        if (idExpr.nameEquals(Token.STAR.name)) {
            log.error("禁止使用*获取所有字段");
        }

        // where 条件未指定引用表名
        if (idExpr.getParent() instanceof SQLBinaryOpExpr) {
            log.error(idExpr.getParent() + ":未指定引用表别名");
        } else {
            //ownerCnt.computeIfAbsent(idExpr.getOwner().toString(), key -> new AtomicInteger(0)).incrementAndGet();
        }
    }

    public void endVisit(SQLMethodInvokeExpr methodInvokeExpr) {
        // where条件中 date(a.time_submit) >'2021-01-01' 不会使用到别名
        if (methodInvokeExpr.getParent() instanceof SQLSelectItem && StringUtils.isBlank(((SQLSelectItem) methodInvokeExpr.getParent()).getAlias())) {
            log.error("未使用别名:{}", SQLUtils.toSQLString(methodInvokeExpr));
        }
        methodCnt.computeIfAbsent(methodInvokeExpr.getMethodName().toLowerCase(), key -> new AtomicInteger(0)).incrementAndGet();
    }

    public void endVisit(SQLAggregateExpr aggregateExpr) {
        if (aggregateExpr.isDistinct()) {
            log.error("聚合函数出现distinct部分:{}，请优化为group by", aggregateExpr);
        }

        /*List<SQLExpr> argList = aggregateExpr.getArguments();
        for (SQLExpr expr : argList) {
            // 重得除了方法外的列循环
            sqlExpr(expr, alias, false, cntVO);
        }*/
    }

    public void endVisit(SQLBinaryOpExpr opExpr) {
        if (!(opExpr.getParent() instanceof SQLJoinTableSource)) {
            return;
        }

        SQLExpr left = opExpr.getLeft();
        SQLExpr right = opExpr.getRight();
        if (left instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr leftBin = (SQLBinaryOpExpr) left;
            SQLExpr leftExpr = leftBin.getLeft();
            SQLExpr rightExpr = leftBin.getRight();
            if (validateConstant(leftExpr) && validateConstant((rightExpr))) {
                log.error("禁止使用常量条件等式:{}", opExpr);
            }
        }

        if (left instanceof SQLBinaryOpExpr) {
            SQLBinaryOpExpr leftBin = (SQLBinaryOpExpr) left;
            SQLExpr leftExpr = leftBin.getLeft();
            SQLExpr rightExpr = leftBin.getRight();
            if (validateConstant(leftExpr) && validateConstant((rightExpr))) {
                log.error("禁止使用常量条件等式:{}", opExpr);
            }
        }
    }

    private boolean validateConstant(SQLExpr sqlExpr) {
        if (sqlExpr instanceof SQLNumericLiteralExpr || sqlExpr instanceof SQLTextLiteralExpr) {
            return true;
        }
        return false;
    }

    public List<SQLExprTableSource> getTableList() {
        return tableList;
    }

    public List<String> getTableNameList() {
        return tableList.stream().map(tableSource -> tableSource.getSchema() + "." + tableSource.getTableName()).collect(Collectors.toList());
    }

    private void checkSelect(SQLSelectQuery sqlSelect) {
        List<SQLSelectItem> selectItemList = null;
        if (sqlSelect instanceof SQLSelectQueryBlock) {
            selectItemList = ((SQLSelectQueryBlock) sqlSelect).getSelectList();
        }

        if (CollectionUtils.isEmpty(selectItemList)) {
            return;
        }

        /**
         * select 是否存在相同字段名
         */
        Map<String, AtomicInteger> itemMap = new HashMap<>();
        for (SQLSelectItem selectItem : selectItemList) {
            if (selectItem.getClass() != SQLSelectItem.class) {
                continue;
            }
            String alias = selectItem.getAlias();
            if (selectItem.getExpr() instanceof SQLIdentifierExpr) {
                SQLIdentifierExpr expr = (SQLIdentifierExpr) selectItem.getExpr();
                if (StringUtils.endsWith(expr.getName(), Token.STAR.name)) {
                    log.error("SQLIdentifierExpr禁止使用*获取所有字段:{}", expr);
                } else if (StringUtils.isNotBlank(expr.getName())) {
                    itemMap.computeIfAbsent(StringUtils.defaultString(alias, expr.getName()),
                            key -> new AtomicInteger(0)).incrementAndGet();
                }
            } else if (selectItem.getExpr() instanceof SQLPropertyExpr) {
                SQLPropertyExpr expr = (SQLPropertyExpr) selectItem.getExpr();
                if (StringUtils.endsWith(expr.getName(), Token.STAR.name)) {
                    log.error("SQLPropertyExpr禁止使用*获取所有字段:{}", expr);
                } else if (StringUtils.isNotBlank(expr.getName())) {
                    itemMap.computeIfAbsent(StringUtils.defaultString(alias, expr.getName()),
                            key -> new AtomicInteger(0)).incrementAndGet();
                }
            } else if (selectItem.getExpr() instanceof SQLAllColumnExpr) {
                // *, t.*
                itemMap.computeIfAbsent(Token.STAR.name, key -> new AtomicInteger(0)).incrementAndGet();
            }
        }
        List<String> collect = itemMap.entrySet().stream().filter(entry -> entry.getValue().intValue() > 1).map(entry -> entry.getKey()).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(collect)) {
            log.error("出现同名字段:{}", String.join(",", collect));
        }
        AtomicInteger atomicInteger = itemMap.get(Token.STAR.name);
        if (atomicInteger.get() >= 1) {
            log.error("输出：select * 禁止使用");
        }
    }
}
