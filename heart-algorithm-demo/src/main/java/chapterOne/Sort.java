package chapterOne;

public class Sort {
	public static void permute(String str){
		char[]ch=str.toCharArray();
		permute(ch,0,ch.length-1);
	}
	private static void permute(char[]str,int low,int high){
		String st=null;
		char temp=0;
		if(low==high-1) {
			temp=str[low];
			str[low]=str[high-1];
			str[high-1]=temp;
		}else{
			for (int i = 0; i < str.length; i++) {
				st=new String(str);
				st.substring(i,str.length-1);
			}
		}
	}
	public static void main(String[] args) {
		String str="abc";
		permute(str);
	}

}
