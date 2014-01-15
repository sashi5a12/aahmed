package stack;

public class ArrayStack<T> implements StackInterface<T> {

	private static final int DEFAULT_CAPACITY=25;
	private int size;
	private T[] s;
	private int top = -1;
	
	public ArrayStack(){
		this(DEFAULT_CAPACITY);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayStack(int cap){
		s=(T[])new Object[cap];
		size=cap;
	}
	
	public int size() {
		return top+1;
	}

	public boolean isEmpty() {
		return top<0;
	}

	public void push(T e) throws FullStackException {
		if(s.length == size){
			throw new FullStackException("Stack is full.");
		}		
		s[++top]=e;		
	}

	public T pop() throws EmptyStackException {
		if(isEmpty())
			throw new EmptyStackException("Stack is empty.");
		T e = s[top];
		s[top--]=null;
		return e;
	}

	public T top() throws EmptyStackException {
		if(isEmpty())
			throw new EmptyStackException("Stack is empty.");
		return s[top];
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if(s.length>0) sb.append(s[0]);
		if(s.length>1){
			for (int i = 1; i < s.length; i++) {
				sb.append(",").append(s[i]);
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
