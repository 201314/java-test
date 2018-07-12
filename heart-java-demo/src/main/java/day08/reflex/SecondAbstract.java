package day08.reflex;

public abstract class SecondAbstract extends FirstAbstract {
	private String secondName;

	public void secondAbstractMethod() {
	}

	public String secondAbstractMethod(String name) {
		return name;
	}

	public class SecondAbstractClassPublicInner {
		public void print() {

		}
	}

	private class SecondAbstractClassPrivateInner {
		public void print() {

		}
	}
}
