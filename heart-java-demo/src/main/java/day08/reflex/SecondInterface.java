package day08.reflex;

public interface SecondInterface extends FirstInterface {
	String FIRST_NAME = "SecondInterface默认为public final类型，且必须赋值,方法都为public";

	public void secondOneMethod(String name);

	public String secondTwoMethod();

	public String secondThreeMethod(String name, String height);

}
