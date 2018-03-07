package day06.file;

import java.io.File;

public class FileDelete {
	public void delete(File file){

		File filePath[]=file.listFiles();
		
		for (File file2 : filePath) {
			if(file2.isFile()){//是文件就打印出来
				file2.delete();
			}else {  //如果是文件夹，递归继续遍历
				delete(file2);
			}
		}
		file.delete();//最后把自己也删除
	}
	public static void main(String[] args) {
		File file=new File("D:\\javaTest");  //文件路径
		FileDelete fd=new FileDelete();
		fd.delete(file);
	}

}
