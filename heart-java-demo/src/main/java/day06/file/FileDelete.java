package day06.file;

import java.io.File;

public class FileDelete {
	public void delete(File file){

		File filePath[]=file.listFiles();
		
		for (File file2 : filePath) {
			if(file2.isFile()){//���ļ��ʹ�ӡ����
				file2.delete();
			}else {  //������ļ��У��ݹ��������
				delete(file2);
			}
		}
		file.delete();//�����Լ�Ҳɾ��
	}
	public static void main(String[] args) {
		File file=new File("D:\\javaTest");  //�ļ�·��
		FileDelete fd=new FileDelete();
		fd.delete(file);
	}

}
