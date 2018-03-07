package day08;

public interface FirstInterface extends SecondInterface {
	String firstName = "FirstInterface默认为public final类型，且必须赋值,方法都为public";

	public void firstOneMethod(String name);

	public String firstTwoMethod();

	public String firstThreeMethod(String name, String height);

}
