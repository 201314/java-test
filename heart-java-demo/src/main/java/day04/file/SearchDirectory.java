package day04.file;

import java.io.File;

public class SearchDirectory {
	public void search(String path){
		File file=new File(path);
		File []name=file.listFiles();
		for (File f : name) {
			if(f.isFile()){
				System.out.println(f.getPath());
				if(f.getName().matches(".*\\.doc"))
					f.delete();
			}else{
				System.out.println(f.getPath()+":");
				search(f.getPath());
			}
		}
	}
	public static void main(String[] args) {
		SearchDirectory sd=new SearchDirectory();
		sd.search("D:/test/DDD");
	}
}
