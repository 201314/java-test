package day02;

public class Test4 {
	int temp, j;

	public void sort(int array[]) {
		for (int i = 0; i < array.length; i++) {
			for (j = i + 1; j < array.length; j++) {
				if (array[i] > array[j]) {
					temp = array[j];
					array[j] = array[i];
					array[i] = temp;
				}
			}
		}

		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}

		// for(int i=1;i<array.length;i++){
		// temp=array[i];
		// j=i-1;
		// while(j>=0&&temp<array[j]){
		// array[j+1]=array[j];
		// j--;
		// }
		// array[j+1]=temp;
		// }
	}

	public static void main(String args[]) {
		Test4 tt = new Test4();
		int a[] = { 10, 31, 9, 900, 100 };
		tt.sort(a);
	}

}
