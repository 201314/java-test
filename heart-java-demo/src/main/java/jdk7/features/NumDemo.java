package jdk7.features;

/**
 * 数字可以用下划线表示,这样看起来更人性化，直观
 * </p>
 * <strong>但是在以下情况下不允许添加下划线</strong>
 * </p>
 * At the beginning or end of a number:在第一个后或最后一数字后不能添加下划线
 * </p>
 * Adjacent to a decimal point in a floating point literal:浮点数中的小数点相邻位置不能使用下划线
 * </p>
 * Prior to an F or L suffix:在F或L后缀之前不能使用下划线
 * </p>
 * In positions where a string of digits is expected:
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月24日
 */
public class NumDemo {

	public static void main(String[] args) {
		long creditCardNumber = 1234_5678_9012_3456L;
		System.out.println(creditCardNumber);
		long socialSecurityNumber = 999_99_9999L;
		float pi = 3.14_15F;
		long hexBytes = 0xFF_EC_DE_5E;
		long hexWords = 0xCAFE_BABE;
		long maxLong = 0x7fff_ffff_ffff_ffffL;
		byte nybbles = 0b0010_0101;
		long bytes = 0b11010010_01101001_10010100_10010010;
	}
}
