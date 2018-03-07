package day06.regex;

public class Pattern {
	public static void main(String[] args) {
		String regex="http://\\d{3}\\.\\d{3}\\.\\d{1}\\.\\d{3}";
		System.out.println("http://192.168.9.151".matches(regex));
		String regex1="eee*\\.exe";
		System.out.println(".exe".matches(regex1));
	}

}
