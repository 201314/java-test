package day06.file;

import java.io.File;

public class FileSearch {
	public void ergodic(File file){//遍历路径,并打印出路径名称
		
		File filePath[]=file.listFiles();
		
		for (File file2 : filePath) {
			if(file2.isFile())//是文件就打印出来
				System.out.println(file2);
			else {  //如果是文件夹，递归继续遍历
				System.out.println(file2+"------------");	
				ergodic(file2);
			}
		}
	}
	public static void main(String[] args) {
		File file=new File("D:\\javaTest");  //文件路径
		
		System.out.println(file.isFile());
		System.out.println(file.isDirectory());
		
		System.out.println(file.delete());//文件夹为空时才能被删除
		file.deleteOnExit();
		System.out.println(file.exists());
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getClass());
		System.out.println(file.getName());
		System.out.println(file.getParent());
		
		FileSearch operate=new FileSearch();
		operate.ergodic(file);
				
	}

}
