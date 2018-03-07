package day03;

public class Robber extends Prisoner {
	public void pray() {
		System.out.println("悔啊，早知道不抢油条吃了……");
	}

	public static void main(String[] args) {
		Prisoner rob=new Robber();
		rob.pray();
		Prisoner pri=new Prisoner();
		pri.pray();
		
	}

}
