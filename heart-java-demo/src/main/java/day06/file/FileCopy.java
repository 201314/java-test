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
		//�ȶ�ȡ����д��
		InputStream is=new FileInputStream("src\\a.txt");
		InputStreamReader isr=new InputStreamReader(is);
		BufferedReader buf=new BufferedReader(isr);
		String str=buf.readLine();//��Ч�ʶ�ȡ�ļ�
		StringBuffer sb=new StringBuffer();
		
		OutputStream os=new FileOutputStream("src\\b.txt");
		PrintWriter pw=new PrintWriter(os);//д�����ݣ�ʹ��PrintWriter
		
		while(str!=null){
			sb.append(str).append("\r\n");//��ȡÿ�У�����ӻ��з�������StringBuffer��������
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
