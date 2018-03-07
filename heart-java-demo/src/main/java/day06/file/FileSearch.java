package day06.file;

import java.io.File;

public class FileSearch {
	public void ergodic(File file){//����·��,����ӡ��·������
		
		File filePath[]=file.listFiles();
		
		for (File file2 : filePath) {
			if(file2.isFile())//���ļ��ʹ�ӡ����
				System.out.println(file2);
			else {  //������ļ��У��ݹ��������
				System.out.println(file2+"------------");	
				ergodic(file2);
			}
		}
	}
	public static void main(String[] args) {
		File file=new File("D:\\javaTest");  //�ļ�·��
		
		System.out.println(file.isFile());
		System.out.println(file.isDirectory());
		
		System.out.println(file.delete());//�ļ���Ϊ��ʱ���ܱ�ɾ��
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
