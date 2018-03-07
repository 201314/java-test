package day06.reflect;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

/**
 * 常见数据库字段对应Java类型
 * 
 * @author linzl
 * 
 */
public class TypeMatch {

	public static Object sqlType2JavaType(ResultSet rs, ResultSetMetaData data,
			int column) throws SQLException {

		Object obj = null;
		//判断该列是否不存在，且该列的值不等于null
		if(rs.getObject(column)!=null&&(!rs.wasNull())){
			switch (data.getColumnType(column)) {
			case Types.BIT:
			case Types.BOOLEAN:
				obj = new Boolean(rs.getBoolean(column));
				break;

			case Types.DATE:
				obj = rs.getDate(column);
				break;

			case Types.BIGINT:
				obj = new Long(rs.getLong(column));
				break;

			case Types.DOUBLE:
				obj = new Double(rs.getDouble(column));
				break;

			case Types.FLOAT:
				obj = new Float(rs.getFloat(column));
				break;

			case Types.INTEGER:
				obj = new Integer(rs.getInt(column));
				break;

			case Types.SMALLINT:
				obj = new Short(rs.getShort(column));
				break;

			case Types.TIME:
				obj = rs.getTime(column);
				break;

			case Types.TIMESTAMP:
				obj = rs.getTimestamp(column);
				break;

			case Types.TINYINT:
				obj = new Byte(rs.getByte(column));
				break;

			case Types.REAL:
			case Types.DECIMAL:
			case Types.NUMERIC: // 对应Number(0,0)，有可能会有小数
				//某列类型的精确度(类型的长度),不包含小数后
				int length  = data.getPrecision(column);
				//小数点后的位数
				int scale = data.getScale(column);
				System.out.println(length+"-->"+scale);
				if(scale==0){
					if(length<=String.valueOf(Short.MAX_VALUE).length()){
						System.out.println("Short-->"+data.getColumnName(column));
						obj = new Short(rs.getShort(column));
						break;
					}else if(length<=String.valueOf(Integer.MAX_VALUE).length()){
						System.out.println("Integer-->"+data.getColumnName(column));
						obj = new Integer(rs.getInt(column));
						break;
					}else if(length<=String.valueOf(Long.MAX_VALUE).length()){
						System.out.println("Long-->"+data.getColumnName(column));
						obj = new Long(rs.getLong(column));
						break;
					}
					obj = new Integer(rs.getInt(column));
					break;
				}else if(scale<0){
					//当字段类型为Number(0,0)时，scale = -127
					obj = new Integer(rs.getInt(column));
					break;
				}
				//此处应该不做任何判断，根据自己的需要将BigDecimal的转换成自己想要的Short,Integer,Long等类型
				obj = new BigDecimal(rs.getString(column));
				break;

			case Types.CHAR:
			case Types.NCHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
			case Types.NVARCHAR:
			case Types.LONGNVARCHAR:
				obj = rs.getString(column);
				break;
				
			case Types.CLOB:
			case Types.NCLOB:
			case Types.BLOB:
			default:
				obj = rs.getObject(column);
				break;
			}
		}
		
		return obj;
	}
	 
}
