
package com.gitee.linzl.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

/**
 * 适用场景：整数，无重复
 * 
 * 常见的应用是那些需要对海量数据进行一些统计工作的时候，比如日志分析等。
 * 
 * 面试题中也常出现，比如：统计40亿个数据中没有出现的数据，将40亿个不同数据进行排序等。
 * 
 * 
 * BitSet是位操作的对象，值只有0或1即false和true，内部维护了一个long数组，初始只有一个long，所以BitSet最小的size是64，当随着存储的元素越来越多，BitSet内部会动态扩充，最终内部是由N个long来存储，这些针对操作都是透明的。
 * 
 * 用1位来表示一个数据是否出现过，0为没有出现过，1表示出现过。使用用的时候既可根据某一个是否为0表示，此数是否出现过。
 * 
 * 一个1G的空间，有 8*1024*1024*1024=8.58*10^9bit，也就是可以表示85亿个不同的数
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2019年7月12日
 */
public class BitSetDemo {
	/**
	 * 求一个字符串包含的char
	 * 
	 */
	public static void containChars(String str) {
		BitSet used = new BitSet();
		for (int i = 0; i < str.length(); i++)
			used.set(str.charAt(i)); // set bit for char

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int size = used.size();
		System.out.println(size);
		for (int i = 0; i < size; i++) {
			if (used.get(i)) {
				sb.append((char) i);
			}
		}
		sb.append("]");
		System.out.println(sb.toString());
	}

	/**
	 * 求素数 有无限个。
	 * 
	 * 一个大于1的自然数,如果除了1和它本身外,不能被其他自然数整除(除0以外)的数称之为素数(质数),否则称为合数
	 */
	public static void computePrime() {
		BitSet sieve = new BitSet(1024);
		int size = sieve.size();
		for (int i = 2; i < size; i++) {
			sieve.set(i);
		}
		int finalBit = (int) Math.sqrt(sieve.size());

		for (int i = 2; i < finalBit; i++) {
			if (sieve.get(i)) {
				for (int j = 2 * i; j < size; j += i) {
					sieve.clear(j);
				}
			}
		}
		int counter = 0;
		for (int i = 1; i < size; i++) {
			if (sieve.get(i)) {
				System.out.printf("%5d", i);
				if (++counter % 15 == 0) {
					System.out.println();
				}
			}
		}
		System.out.println();
	}

	/**
	 * 进行数字排序
	 */
	public static void sortArray() {
		int[] array = new int[] { 423, 700, 9999, 2323, 356, 6400, 1, 2, 3, 2, 2, 2, 2 };
		BitSet bitSet = new BitSet(2 << 13);
		// 虽然可以自动扩容，但尽量在构造时指定估算大小,默认为64
		System.out.println("BitSet size: " + bitSet.size());

		for (int i = 0; i < array.length; i++) {
			bitSet.set(array[i]);
		}
		// 剔除重复数字后的元素个数
		int bitLen = bitSet.cardinality();

		// 进行排序，即把bit为true的元素复制到另一个数组
		int[] orderedArray = new int[bitLen];
		int k = 0;
		for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
			orderedArray[k++] = i;
		}

		System.out.println("After ordering: ");
		for (int i = 0; i < bitLen; i++) {
			System.out.print(orderedArray[i] + "\t");
		}

		System.out.println("iterate over the true bits in a BitSet");
		// 或直接迭代BitSet中bit为true的元素iterate over the true bits in a BitSet
		for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
			System.out.print(i + "\t");
		}
		System.out.println("---------------------------");
	}

	/**
	 * 将BitSet对象转化为ByteArray
	 * 
	 * @param bitSet
	 * @return
	 */
	public static byte[] bitSet2ByteArray(BitSet bitSet) {
		byte[] bytes = new byte[bitSet.size() / 8];
		for (int i = 0; i < bitSet.size(); i++) {
			int index = i / 8;
			int offset = 7 - i % 8;
			bytes[index] |= (bitSet.get(i) ? 1 : 0) << offset;
		}
		return bytes;
	}

	/**
	 * 将ByteArray对象转化为BitSet
	 * 
	 * @param bytes
	 * @return
	 */
	public static BitSet byteArray2BitSet(byte[] bytes) {
		BitSet bitSet = new BitSet(bytes.length * 8);
		int index = 0;
		for (int i = 0; i < bytes.length; i++) {
			for (int j = 7; j >= 0; j--) {
				bitSet.set(index++, (bytes[i] & (1 << j)) >> j == 1 ? true : false);
			}
		}
		return bitSet;
	}

	/**
	 * 简单使用示例
	 */
	public static void simpleExample() {
		String names[] = { "Java", "Source", "and", "Support" };
		BitSet bits = new BitSet();
		for (int i = 0, n = names.length; i < n; i++) {
			if ((names[i].length() % 2) == 0) {
				bits.set(i);
			}
		}

		System.out.println(bits);
		System.out.println("Size : " + bits.size());
		System.out.println("Length: " + bits.length());
		for (int i = 0, n = names.length; i < n; i++) {
			if (!bits.get(i)) {
				System.out.println(names[i] + " is odd");
			}
		}
		BitSet bites = new BitSet();
		bites.set(0);
		bites.set(1);
		bites.set(2);
		bites.set(3);
		bites.andNot(bits);
		System.out.println(bites);
	}

	// 有1千万个随机数，随机数的范围在1到1亿之间。现在要求写出一种算法，将1到1亿之间没有在随机数中的数求出来
	public void notFound() {
		Random random = new Random();
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10000000; i++) {
			int randomResult = random.nextInt(100000000);
			list.add(randomResult);
		}
		System.out.println("产生的随机数有");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		BitSet bitSet = new BitSet(100000000);
		for (int i = 0; i < 10000000; i++) {
			bitSet.set(list.get(i));
		}

		System.out.println("0~1亿不在上述随机数中有" + bitSet.size());
		for (int i = 0; i < 100000000; i++) {
			if (!bitSet.get(i)) {
				System.out.println(i);
			}
		}
	}

	public static void main(String args[]) {
		String s1 = new String("kvill");
		s1.intern();
		String s2 = "kvill";
		System.out.println(s1 == s2);

		String s3 = new String("1") + new String("1");
		s3.intern();
		String s4 = "11";
		System.out.println(s3 == s4);
		// BitSet使用示例,从左向右数
//		BitSetDemo.containChars("How do you do? 你好呀");
//		BitSetDemo.computePrime();
//		BitSetDemo.sortArray();
//		BitSetDemo.simpleExample();
		System.out.println(2 >> 6);

		// 使用long[]存储，默认是64位。如果指定的长度不等于(2^6)*N,则程序会自动进行补齐，使长度等于最接近的值，如127，最终是128位长度
		BitSet bitSet = new BitSet();
		System.out.println("bitSet.size()==>" + bitSet.size());
		bitSet.set(9);
		bitSet.set(10, false);
		System.out.println(bitSet.size() + "," + bitSet.cardinality());
		// 将BitSet对象转成byte数组
//		byte[] bytes = BitSetDemo.bitSet2ByteArray(bitSet);
//		System.out.println(Arrays.toString(bytes));
//
//		// 在将byte数组转回来
//		bitSet = BitSetDemo.byteArray2BitSet(bytes);
//		System.out.println(bitSet.size() + "," + bitSet.cardinality());
//		System.out.println(bitSet.get(3));
//		System.out.println(bitSet.get(98));
		// 打印位置是true的值
		for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
			System.out.print(i + ",");
		}

		for (int i = 0, size = bitSet.size() - 1; i <= size; i++) {
			System.out.print(bitSet.get(i) + "\t");
		}
	}
}