package exception;

public class UserExistException extends RuntimeException {
	public UserExistException(String error){
		super(error);
	}
	public void printStackTrace(){
		System.out.println("自定义异常");
	}
}
