package com.gitee.linzl.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLIndexDefinition;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.alibaba.druid.sql.visitor.VisitorFeature;
import org.apache.commons.lang3.StringUtils;

/**
 * @author linzhenlie-jk
 * @date 2023/6/12
 */
public class MySqlToHiveOutputVisitor extends MySqlASTVisitorAdapter {
    private StringBuilder createColumnContent = null;

    private StringBuilder selectColumnContent = null;

    private Map<String, StringBuilder> priKeyNum = null;
    private Integer priKeyIdx = 1;

    private Map<String, StringBuilder> uniqueNum = null;

    private Integer uniqueIdx = 1;

    private List<String> columnIdx = null;

    private final String formatePrex = "%";

    private StringBuilder fullContent = null;
    private String tableType = null;
    private Boolean ifView = false;
    private static final String SPACE_PAD =" ";
    private static final String SINGLE_QUOTATION ="'";
    private static final String COMMA =",";

    public MySqlToHiveOutputVisitor() {
        this("", Boolean.FALSE);
    }

    public MySqlToHiveOutputVisitor(String tableType) {
        this(tableType, Boolean.FALSE);
    }

    public MySqlToHiveOutputVisitor(String tableType,Boolean ifView) {
        this.tableType = tableType;
        this.ifView = ifView;
        this.fullContent = new StringBuilder();
        this.createColumnContent = new StringBuilder();
        this.selectColumnContent = new StringBuilder();
        this.priKeyNum = new LinkedHashMap<>();
        this. uniqueNum = new LinkedHashMap<>();
        this.columnIdx = new ArrayList<>();
        this.priKeyIdx = 1;
        this.uniqueIdx = 1;
    }

    private String replaceChar(String str) {
        return str.trim().replaceAll("`", "")
            .replaceAll(" ", "")
            .replaceAll("：", ":")
            .replaceAll(";", "；")
            .replaceAll("（", "(")
            .replaceAll("）", ")")
            .replaceAll("\r\n", "")
            .replaceAll("\n", "");
    }

    public void endVisit(SQLColumnDefinition columnDefinition) {
        String unionType = "STRING";
        String hiveDataType = "STRING";
        String columnName = replaceChar(columnDefinition.getColumnName());
        String columnNameNew = columnName;

        switch (columnDefinition.getDataType().getName().toUpperCase()) {
            case "DATETIME":
            case SQLDataType.Constants.TIMESTAMP:
                unionType = SQLDataType.Constants.TIMESTAMP;
                if (StringUtils.equalsAny(columnName, "date_created", "date_updated")) {
                    break;
                }
                if (columnName.endsWith("_time") || columnName.endsWith("_date")) {
                    columnNameNew = "time_" + columnName.substring(0, columnName.length() - 5);
                } else if (columnName.startsWith("time_") || columnName.startsWith("date_")) {
                    columnNameNew = "time_" + columnName.substring(5, columnName.length());
                } else {
                    columnNameNew = "time_" + columnName;
                }
                break;
            case SQLDataType.Constants.DATE:
                unionType = SQLDataType.Constants.DATE;
                if (StringUtils.equalsAny(columnName, "date_created", "date_updated")) {
                    break;
                }
                if (columnName.endsWith("_time") || columnName.endsWith("_date")) {
                    columnNameNew = "date_" + columnName.substring(0, columnName.length() - 5);
                } else if (columnName.startsWith("time_") || columnName.startsWith("date_")) {
                    columnNameNew = "date_" + columnName.substring(5, columnName.length());
                } else {
                    columnNameNew = "date_" + columnName;
                }
                break;
            case SQLDataType.Constants.TINYINT:
            case SQLDataType.Constants.SMALLINT:
            case SQLDataType.Constants.INT:
            case SQLDataType.Constants.BIGINT:
                unionType = SQLDataType.Constants.BIGINT;
                hiveDataType = SQLDataType.Constants.BIGINT;
                break;
            case SQLDataType.Constants.DOUBLE:
            case SQLDataType.Constants.FLOAT:
            case SQLDataType.Constants.DOUBLE_PRECISION:
            case SQLDataType.Constants.REAL:
            case SQLDataType.Constants.DECIMAL:
                unionType = SQLDataType.Constants.DECIMAL;
                if (columnName.contains("_amt") || columnName.contains("_amount")) {
                    columnNameNew = columnName.replaceAll("_amount", "_amt");
                    hiveDataType = "DECIMAL(17,2)";
                } else {
                    hiveDataType = "DECIMAL(12,8)";
                }
                break;
        }

        createColumnContent.append(COMMA).append(columnNameNew).append(SPACE_PAD);
        if (Boolean.FALSE.equals(ifView)) {
            createColumnContent.append(hiveDataType).append(SPACE_PAD);
        }

        SQLCharExpr commentExpr = (SQLCharExpr) columnDefinition.getComment();
        String comment = replaceChar(commentExpr == null ? StringUtils.EMPTY : commentExpr.getText());
        createColumnContent.append("COMMENT").append(SPACE_PAD)
                .append(SINGLE_QUOTATION).append(comment);

        if (unionType == SQLDataType.Constants.DATE) {
            createColumnContent.append("yyyy-MM-dd");
        } else if (unionType == SQLDataType.Constants.TIMESTAMP) {
            createColumnContent.append("yyyy-MM-dd HH:mm:ss");
        }
        createColumnContent.append(formatePrex).append(columnName).append(formatePrex).append(SINGLE_QUOTATION);
        createColumnContent.append(System.lineSeparator());

        selectColumnContent.append(COMMA);
        String kuhaoAS = " AS ";
        if (SQLDataType.Constants.TIMESTAMP.equals(unionType)) {
            selectColumnContent.append("SUBSTR(").append(columnName).append(",1,19)").append(kuhaoAS).append(columnNameNew);
        } else if (SQLDataType.Constants.DATE.equals(unionType)) {
            selectColumnContent.append("SUBSTR(").append(columnName).append(",1,10)").append(kuhaoAS).append(columnNameNew);
        } else if (SQLDataType.Constants.BIGINT.equals(unionType) || SQLDataType.Constants.DECIMAL.equals(unionType)) {
            selectColumnContent.append("COALESCE(").append(columnName).append(",0)").append(kuhaoAS).append(columnNameNew);
        } else {
            selectColumnContent.append("IF(trim(").append(columnName).append(") IN ('','null','NULL'),NULL,trim(").append(columnName).append("))").append(kuhaoAS).append(columnNameNew);
        }

        selectColumnContent.append(System.lineSeparator());
        columnIdx.add(columnName);
    }


    public void endVisit(MySqlCreateTableStatement createTable) {
        String tableName = replaceChar(createTable.getTableName());
        if(StringUtils.isNotBlank(tableType)){
            tableName += "_" + tableType;
        }

        // Create表语句
        StringBuilder createContent = null;
        if (Boolean.FALSE.equals(ifView)) {
            createContent = new StringBuilder("CREATE TABLE IF NOT EXISTS").append(SPACE_PAD);
        } else {
            createContent = new StringBuilder("CREATE OR REPLACE VIEW").append(SPACE_PAD);
        }
        createContent.append(tableName).append(SPACE_PAD)
                .append("(").append(SPACE_PAD)
                .append(createColumnContent.deleteCharAt(0));
        if (ifView){
            createContent.append(",etl_time COMMENT  'etl处理时间(格式:yyyy-MM-dd HH:mm:ss)'")
                    .append(SPACE_PAD)
                    .append(",etl_key COMMENT   'etl处理key(质量唯一键)'")
                    .append(SPACE_PAD);
        } else {
            createContent.append(",etl_time STRING COMMENT  'etl处理时间(格式:yyyy-MM-dd HH:mm:ss)'")
                    .append(SPACE_PAD)
                    .append(",etl_key STRING COMMENT   'etl处理key(质量唯一键)'")
                    .append(SPACE_PAD);
        }

        String createPartition = "";
        String selectPartition = "";
        if (StringUtils.equalsAny(tableType, "pdi", "pda")) {
            if (Boolean.TRUE.equals(ifView)){
                createContent.append(",pday COMMENT '按日分区'").append(SPACE_PAD);
                selectPartition = ",pday ";
            }else {
                createPartition = "PARTITIONED BY (pday STRING COMMENT '按日分区')";
                selectPartition = ",'${yesterday_p}' AS pday";
            }
        } else if (StringUtils.equalsAny(tableType, "pmi")) {
            if (Boolean.TRUE.equals(ifView)){
                createContent.append(",pmonth COMMENT '按月分区'").append(SPACE_PAD);
                selectPartition = ",pmonth ";
            }else {
                createPartition = "PARTITIONED BY (pmonth STRING COMMENT '按月分区')";
                selectPartition = ",substr('${last_month_start_p}' ,1,6) AS pmonth";
            }
        } else if (StringUtils.equalsAny(tableType, "pyi")) {
            if (Boolean.TRUE.equals(ifView)){
                createContent.append(",pyear COMMENT '按年分区'").append(SPACE_PAD);
                selectPartition = ",pyear ";
            }else {
                createPartition = "PARTITIONED BY (pyear STRING COMMENT '按年分区')";
                selectPartition = ",substr('${yesterday_p}' ,1,4) AS pyear";
            }
        }

        SQLCharExpr sqlCharExpr = (SQLCharExpr) createTable.getComment();
        createContent.append(")").append(SPACE_PAD)
                .append("COMMENT").append(SPACE_PAD)
            .append(SINGLE_QUOTATION).append(replaceChar(Objects.isNull(sqlCharExpr)?
                        StringUtils.EMPTY:sqlCharExpr.getText())).append(SINGLE_QUOTATION)
            .append(System.lineSeparator())
            .append(createPartition)
            .append(System.lineSeparator());
        if (Boolean.FALSE.equals(ifView)) {
            createContent.append("STORED AS ORC;");
        } else {
            createContent.append(SPACE_PAD).append("AS").append(SPACE_PAD);
        }

        String createColumn = createContent.toString();
        boolean uniqueFlag = uniqueNum.size() > 0 ? Boolean.TRUE : Boolean.FALSE;
        for (int index = 0, length = columnIdx.size(); index < length; index++) {
            String columnName = columnIdx.get(index);
            StringBuilder columIdxBuilder = null;
            // 有唯一索引先用唯一索引
            if (uniqueFlag == Boolean.TRUE) {
                columIdxBuilder = uniqueNum.get(columnName);
            } else {
                columIdxBuilder = priKeyNum.get(columnName);
            }
            String comment = "";
            if (Objects.nonNull(columIdxBuilder)) {
                comment = columIdxBuilder.reverse().deleteCharAt(0).reverse().insert(0, "(").append(")").toString();
            }
            createColumn = createColumn.replaceAll(formatePrex + columnName + formatePrex, comment);
        }

        // insert select 语句
        StringBuilder selectContent = new StringBuilder();
        if (Boolean.FALSE.equals(ifView)) {
            selectContent.append("INSERT OVERWRITE TABLE").append(SPACE_PAD).append(tableName).append(System.lineSeparator());
        }

        selectContent.append("SELECT").append(SPACE_PAD)
            .append(selectColumnContent.deleteCharAt(0)).append(System.lineSeparator())
            .append(",SUBSTR(CURRENT_TIMESTAMP(),1,19)  AS etl_time");

        if (uniqueFlag){
            Map<String, String> groupedMap = uniqueNum.entrySet().stream().sequential()
                    .collect(Collectors.groupingBy(entry -> entry.getValue().toString(),
                            Collectors.mapping(Map.Entry::getKey, Collectors.joining(","))));
            String md5Key =  groupedMap.values().stream().sequential().findFirst().get();
            selectContent.append(",MD5(CONCAT_WS('|',").append(md5Key).append(")) AS etl_key");
        } else {
            Map<String, String> groupedMap = priKeyNum.entrySet().stream().sequential()
                    .collect(Collectors.groupingBy(entry -> entry.getValue().toString(),
                            Collectors.mapping(Map.Entry::getKey, Collectors.joining(","))));
            String md5Key =  groupedMap.values().stream().sequential().findFirst().get();
            selectContent.append(",MD5(CONCAT_WS('|',").append(md5Key).append(")) AS etl_key");
        }

        selectContent.append(selectPartition).append(System.lineSeparator())
            .append("FROM").append(SPACE_PAD).append(tableName).append(";");

        fullContent.append(createColumn);
        fullContent.append(System.lineSeparator());
        fullContent.append(selectContent);
    }

    public void endVisit(MySqlUnique mySqlUnique) {
        SQLIndexDefinition idxDefinition = mySqlUnique.getIndexDefinition();
        idxDefinition.getColumns().forEach(item -> {
            SQLExpr sqlExpr = item.getExpr();
            String name = null;
            if(sqlExpr instanceof SQLMethodInvokeExpr){
                SQLMethodInvokeExpr invokeExpr = (SQLMethodInvokeExpr) sqlExpr;
                name = invokeExpr.getMethodName();
            }

            if(sqlExpr instanceof SQLIdentifierExpr){
                SQLIdentifierExpr invokeExpr = (SQLIdentifierExpr) sqlExpr;
                name = invokeExpr.getName();
            }

            uniqueNum.computeIfAbsent(replaceChar(name),
                    s -> new StringBuilder("业务主键")).append(uniqueIdx).append(COMMA);
        });

        uniqueIdx++;
    }

    public void endVisit(MySqlPrimaryKey primaryKey) {
        SQLIndexDefinition idxDefinition = primaryKey.getIndexDefinition();
        idxDefinition.getColumns().forEach(item -> {
            SQLExpr sqlExpr = item.getExpr();
            String name = null;
            if(sqlExpr instanceof SQLMethodInvokeExpr){
                SQLMethodInvokeExpr invokeExpr = (SQLMethodInvokeExpr) sqlExpr;
                name = invokeExpr.getMethodName();
            }

            if(sqlExpr instanceof SQLIdentifierExpr){
                SQLIdentifierExpr invokeExpr = (SQLIdentifierExpr) sqlExpr;
                name = invokeExpr.getName();
            }

            priKeyNum.computeIfAbsent(replaceChar(name),
                    s -> new StringBuilder("主键")).append(priKeyIdx).append(COMMA);
        });

        priKeyIdx++;
    }

    public String getContent() {
        return SQLUtils.formatHive(fullContent.toString(), new SQLUtils.FormatOption());
    }
}
