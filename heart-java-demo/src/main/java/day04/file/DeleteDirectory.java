package day04.file;

import java.io.File;

public class DeleteDirectory {
	public void delete(String path){
		File file=new File(path);
		File file1[]=file.listFiles();
		for (File file2 : file1) {
			//file2.isFile()
			if("*\\.doc".matches(file2.getPath())) file2.delete();//文件删除
			else delete(file2.getPath());//如果是目录，递归回去，进行文件删除		
			
			//file2.delete();//最后把自己也删除
		}
		
	}
	public static void main(String[] args) {
		DeleteDirectory del=new DeleteDirectory();
		del.delete("D:/test/DDD");

	}
}
