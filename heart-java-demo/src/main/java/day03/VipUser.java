package day03;

public class VipUser extends User{
	public int vipPay(int money){
		return 2*super.pay(money);
	}
	public static void main(String args[]){
		User user=new VipUser();
		System.out.println("普通用户:"+user.pay(20));
		VipUser vip=new VipUser();
		System.out.println("vip用户:"+vip.vipPay(20));
		
	}
}
