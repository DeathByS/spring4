package chap2;

public class Greeter {
	
	private String format;
	

	
	public void setFormat(String format)
	{
		this.format = format;
	}

	public String greet(String guest) {
		// TODO Auto-generated method stub
		return String.format(format, guest);
	}


	
}
