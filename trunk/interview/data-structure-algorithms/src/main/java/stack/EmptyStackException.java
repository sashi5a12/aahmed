package stack;

public class EmptyStackException extends Exception {
	
	private static final long serialVersionUID = 1808349369293098686L;

	public EmptyStackException(String errMsg){
		super(errMsg);
	}
}
