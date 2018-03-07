package day06;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class FileInputStreamTest {
	public static void main(String[] args) throws Exception {
		File file=new File("src\\day06\\name.txt");
		InputStream is=new FileInputStream(file);
		InputStreamReader isr=new InputStreamReader(is,"UTF-8");
		System.out.println(System.getProperty("file.encoding"));
				
		char[] b=new char[(int) (file.length())/2];
		while(true){
			if(isr.read(b)!=-1){
				String str=new String(b);
				System.out.println(str);
			}else{
				break;
			}
		}
		is.close();
		
	}

}
