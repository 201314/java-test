package day07.thread.multithread;

public class Test {
	public static void main(String[] args) {
		Thread odd=new Odd();
		Thread even=new Even();
		
		even.setDaemon(true);//守护线程，只有当所有的线程都结束掉的时候，它才会自动退出，这是一个不安全的行为，有可能其他线程还没结束时就退出了
							 //可以用来记录一些日记信息或者需要时刻记录的消息
		odd.start();
		even.start();
	}
}
