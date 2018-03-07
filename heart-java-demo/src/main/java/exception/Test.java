package exception;

public class Test {
	public int testException() throws UserExistException {
		try {
			int i = 0;
			throw new UserExistException("user exit");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		} finally {
			System.out.println("hello world");
			return 2;
		}

	}

	public static void main(String[] args) {
		Test test = new Test();
		try {
			int result = test.testException();
			System.out.println("得到finally结果==》" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
