package day06.file;

import java.io.File;
import java.io.IOException;

public class FileCreate {
	public static void main(String[] args) throws Exception {
		File file1=new File("D:\\javaTest\\fileDelete.txt");  //�ļ�·��
		System.out.println(file1.exists());
		
		//�����ļ�
		if(!file1.exists()){
			file1.createNewFile();
		}else{
			file1.delete();//�ļ����ھ�ɾ��
		}
		
		//�����ļ���
		File file2=new File("D:\\javaTest\\mkdir");//��������Ŀ¼
		
		File file3=new File("D:\\javaTest\\mkdirs\\directory");//�����༶Ŀ¼(�ʹ�������Ŀ¼һ��)
		
		if(!file2.exists()){
			System.out.println(file2.mkdir());//��ʼ����
			if(file2.isDirectory()){
				System.out.println("֤�������ɹ�");
			}
		}
		
	}

}
