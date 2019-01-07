package spring;

public class AlreadyExistingMemberException extends RuntimeException {
	
	public AlreadyExistingMemberException(String message) {
		super(message); //super 상위클래스의 생성자를 불러옴.
	}

}
