package day07.ObjectIOputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
	private static final long serialVersionUID = 2152047803017544443L;
	private String userName;
	private String password;
	private Date date;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void register(List<User> list) {
		try (OutputStream os = new FileOutputStream("D:\\hello.txt");
				ObjectOutputStream oos = new ObjectOutputStream(os);) {
			oos.writeObject(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login() {
		try (InputStream is = new FileInputStream("D:\\hello.txt");
				ObjectInputStream ois = new ObjectInputStream(is);) {
			List<User> list = (List) ois.readObject();
			for (User per : list) {
				System.out.println(per.getUserName());
				System.out.println(per.getPassword());
				System.out.println(per.getDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
