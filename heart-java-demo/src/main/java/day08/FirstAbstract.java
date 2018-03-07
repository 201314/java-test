package day08;

public abstract class FirstAbstract extends SecondAbstract {
	private String firstName;

	public void FirstAbstractMethod() {
	};

	public String FirstAbstractMethod(String name) {
		return name;
	}

	public class FirstAbstractClassPublicInner {
		public void print() {

		}
	}

	private class FirstAbstractClassPrivateInner {
		public void print() {

		}
	}
}
