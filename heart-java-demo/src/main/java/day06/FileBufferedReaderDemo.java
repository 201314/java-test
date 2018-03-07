package day06;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileBufferedReaderDemo {
	public static void main(String[] args) throws Exception {
		InputStream is=new FileInputStream("src\\day06\\name.txt");
		InputStreamReader isr=new InputStreamReader(is,"UTF-8");
		BufferedReader buf=new BufferedReader(isr);
		String str=buf.readLine();
		
		while(str!=null){
			System.out.println(str);
			str=buf.readLine();
		}
	}

}
