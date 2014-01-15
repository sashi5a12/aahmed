package stack;

public class LinkedStack<T> implements StackInterface<T> {
	private int size;
	private Node header;

	public LinkedStack(){
		this.size=0;
		this.header=null;
	}
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void push(T e) {
		Node n = new Node(e);
		n.next = header;
		header = n;
		size++;
	}

	public T pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException("Stack is empty.");
		}
		T e = header.data;
		header = header.next;
		size--;
		return e;
	}

	public T top() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException("Stack is empty.");
		}
		return header.data;
	}

	private class Node {
		private T data;
		private Node next;

		public Node(T e) {
			this.data = e;
			this.next = null;
		}
	}

}
