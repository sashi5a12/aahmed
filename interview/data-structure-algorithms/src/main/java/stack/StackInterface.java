package stack;

public interface StackInterface<T> {
	public int size();
	public boolean isEmpty();
	public void push(T e) throws FullStackException;
	public T pop() throws EmptyStackException;
	public T top() throws EmptyStackException;
}
