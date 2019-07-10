package com.gitee.linzl.nio.buffer;

import java.nio.IntBuffer;

public class BufferTest {
	public static void main(String[] args) {
		// 1 基本操作
		// 创建指定长度的缓冲区
		IntBuffer buf = IntBuffer.allocate(10);
		System.out.println("创建指定长度的缓冲区: " + buf);
		buf.put(13);// position位置：0 - > 1
		System.out.println("13当前位置: " + buf.position());
		buf.put(21);// position位置：1 - > 2
		System.out.println("21当前位置: " + buf.position());
		buf.put(35);// position位置：2 - > 3
		System.out.println("35当前位置: " + buf.position());
		// 通常用在写转换为读模式时，重置limit=当前position,然后position=0,也就是limit=3,position=0
		// 重置limit是为了防止读到根本没有操作过的位置
		buf.flip();
		System.out.println("使用flip复位: " + buf);
		// 容量一旦初始化后不允许改变(warp方法包裹数组除外)
		// 由于只装载了三个元素,所以可读取或者操作的元素为3 则limit=3
		System.out.println("容量为: " + buf.capacity() + ",上限为: " + buf.limit());

		System.out.println("获取下标为1的元素: " + buf.get(1));
		System.out.println("get(index)方法，position位置不改变: " + buf);
		buf.put(1, 4);// put的index不能超过limit
		System.out.println("put(index, change)方法，position位置不变: " + buf);

		for (int i = 0; i < 2; i++) {
			// 调用get方法会使其缓冲区位置（position）向后递增一位
			System.out.println("位置: " + buf.position() + ",元素: " + buf.get());
		}
		System.out.println("buf对象遍历之后为: " + buf);
		buf.flip();// 再重置一次
		System.out.println("再次重置后: " + buf);

		buf.rewind();// 重新读取buffer中的有效数据：limit不变，并将position置为零。
		buf.clear();// 重新写入buffer：limit=容量，并将position置为零。
		// 2 wrap方法使用
		/**
		 * wrap方法会包裹一个数组: 一般这种用法不会先初始化缓存对象的长度，因为没有意义，最后还会被wrap所包裹的数组覆盖掉。
		 * 并且wrap方法修改缓冲区对象的时候，数组本身也会跟着发生变化。
		 * 
		 * int[] arr = new int[]{1,2,5};
		 * 
		 * IntBuffer buf1 = IntBuffer.wrap(arr);
		 * 
		 * System.out.println(buf1);
		 * 
		 * IntBuffer buf2 = IntBuffer.wrap(arr, 0 , 2);
		 * 
		 * System.out.println(buf2);
		 * 
		 * 这样使用表示容量为数组arr的长度，但是可操作的元素只有实际进入缓存区的元素长度
		 */

		// 3 其他方法
		/**
		 * IntBuffer buf1 = IntBuffer.allocate(10);
		 * 
		 * int[] arr = new int[]{1,2,5};
		 * 
		 * buf1.put(arr); System.out.println(buf1);
		 * 
		 * //一种复制方法
		 * 
		 * IntBuffer buf3 = buf1.duplicate();
		 * 
		 * System.out.println(buf3);
		 * 
		 * //设置buf1的位置属性
		 * 
		 * //buf1.position(0);
		 * 
		 * buf1.flip();
		 * 
		 * System.out.println(buf1);
		 * 
		 * System.out.println("可读数据为：" + buf1.remaining());
		 * 
		 * int[] arr2 = new int[buf1.remaining()];
		 * 
		 * //将缓冲区数据放入arr2数组中去
		 * 
		 * buf1.get(arr2);
		 * 
		 * for(int i : arr2){ System.out.print(Integer.toString(i) + ","); }
		 */
	}
}
