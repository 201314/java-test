package day06;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class FileOutputStreamDemo {
	public static void main(String[] args) throws Exception {
		File file=new File("src\\day06\\name.txt");
		OutputStream os=new FileOutputStream(file,true);
		PrintWriter pw=new PrintWriter(os);
		String str="who are you?";
		pw.write(str);
		
		pw.close();
		os.close();
	}

}
