package day06.file;

import java.io.File;
import java.io.IOException;

public class FileCreate {
	public static void main(String[] args) throws Exception {
		File file1=new File("D:\\javaTest\\fileDelete.txt");  //文件路径
		System.out.println(file1.exists());
		
		//创建文件
		if(!file1.exists()){
			file1.createNewFile();
		}else{
			file1.delete();//文件存在就删除
		}
		
		//创建文件夹
		File file2=new File("D:\\javaTest\\mkdir");//创建单级目录
		
		File file3=new File("D:\\javaTest\\mkdirs\\directory");//创建多级目录(和创建单级目录一样)
		
		if(!file2.exists()){
			System.out.println(file2.mkdir());//开始创建
			if(file2.isDirectory()){
				System.out.println("证明创建成功");
			}
		}
		
	}

}
