package day07.ObjectIOputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		User user=new User();
		user.setUserName("lzl");
		user.setPassword("asd");
		user.setDate(new Date());
		
		User user1=new User();
		user1.setUserName("lzl01");
		user1.setPassword("asd01");
		user1.setDate(new Date());
		
		List<User> list=new ArrayList<User>();
		list.add(user);
		list.add(user1);
		
		DateDemo dd=new DateDemo();
		dd.setList(list);
		
		user.register(list);
		user.login();
		
	}
}
