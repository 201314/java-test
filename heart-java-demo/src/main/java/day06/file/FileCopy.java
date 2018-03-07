package day06.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class FileCopy {
	public static void main(String[] args) throws Exception {
		//先读取，再写入
		InputStream is=new FileInputStream("src\\a.txt");
		InputStreamReader isr=new InputStreamReader(is);
		BufferedReader buf=new BufferedReader(isr);
		String str=buf.readLine();//高效率读取文件
		StringBuffer sb=new StringBuffer();
		
		OutputStream os=new FileOutputStream("src\\b.txt");
		PrintWriter pw=new PrintWriter(os);//写入数据，使用PrintWriter
		
		while(str!=null){
			sb.append(str).append("\r\n");//读取每行，并添加换行符，放入StringBuffer缓冲流中
			str=buf.readLine();
		}
		
		pw.write(sb.toString());	
		pw.flush();
		
		is.close();
		isr.close();
		buf.close();
		os.close();
		pw.close();
		
	}

}
