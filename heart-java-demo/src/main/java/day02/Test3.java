package day02;

public class Test3 {
	int count;

	public void test(int array[]) {
		// for(int i=0,j=a.length-1;i<j;i++,j--){
		// if(a[i]==a[j]){
		// count++;
		// }
		// }
		// if(count==a.length/2){
		// System.out.println("对称");
		// }else{
		// System.out.println("不对称");
		// }
		for (int i = 0; i < array.length / 2; i++) {
			if (array[i] != array[array.length - 1 - i]) {
				String s = "不对称";
			}
		}
	}

	public static void main(String args[]) {
		Test3 tt = new Test3();
		int a[] = { 1, 2, 0, 3, 1 };
		tt.test(a);
	}
}
