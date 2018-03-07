package day03;

public class User {
	private int ensure;//保存信用度
	public int pay(int money){
		this.ensure=2*money/1;
		return this.ensure;
	}

}
