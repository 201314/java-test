package day03;

public class SonInLaw extends MotherInLaw {
	public int introduction() {
		return 3 * super.introduce();
	}

	public static void main(String args[]) {
		MotherInLaw mother = new SonInLaw();
		System.out.println("老干妈的辣的级别是:" + mother.introduce());
		SonInLaw son = new SonInLaw();
		System.out.println("干儿子辣的级别是:" + son.introduction());
	}
}
