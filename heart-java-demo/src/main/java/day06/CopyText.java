package day06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class CopyText {
	public static void main(String[] args) throws Exception {
		InputStream is=new FileInputStream("src\\day06\\name.txt");
		InputStreamReader isr=new InputStreamReader(is,"UTF-8");
		BufferedReader buf=new BufferedReader(isr);
		String str=buf.readLine();
		StringBuffer sb = new StringBuffer();
		
		File file=new File("D:\\hello.txt");
		OutputStream os=new FileOutputStream(file);
		PrintWriter pw=new PrintWriter(os);
		
		while(str!=null){
			sb.append(str).append("\r\n");
			str=buf.readLine();
		}
		pw.write(sb.toString());
		pw.flush();
		os.close();
	}
}
