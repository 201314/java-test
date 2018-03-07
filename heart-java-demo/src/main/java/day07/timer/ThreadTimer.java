package day07.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 不建议使用这种，会出现时间上的问题
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年1月10日
 */
public class ThreadTimer {
	public static void main(String[] args) {
		Timer timer = new Timer();
		// 启动MyTask,延迟1000毫秒后启动，间隔2000毫秒
		timer.schedule(new MyTask(), 1000, 2000);
	}
}

class MyTask extends TimerTask {

	@Override
	public void run() {
		System.out.println("业务逻辑");
	}

}