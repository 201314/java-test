package day02;

public class Test2 {
	public void test(int a[]){
		int count=0;
	s:	for(int i=0;i<a.length;i++){
			int temp=a[i];
			for(int j=i+1;j<a.length;j++){
				if(temp==a[j]){
					count++;
					break s;
				}
			}
		}
		if(count!=0){
			System.out.print("重复");
		}else{
			System.out.print("不重复");
		}
	}
	public static void main(String args[]){
		int a[]={10,95,10,93,94,951,96,97,98,99};
		Test2 tt=new Test2();
		tt.test(a);
	}
}
