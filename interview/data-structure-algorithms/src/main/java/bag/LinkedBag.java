package bag;

public class LinkedBag<T> implements BagInterface<T> {

	private Node<T> header;
	private int size;

	public LinkedBag() {
		this.header = null;
		this.size = 0;
	}

	public int getCurrentSize() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean add(T entry) {
		Node<T> n = new Node<T>(entry);
		n.setNextNode(header);
		header = n;
		size++;
		return true;
	}

	public boolean remove(T entry) {
		boolean result = false;
		Node<T> findNode = findNode(entry);
		if (findNode != null) {
			findNode.setData(header.getData());
			remove();
			result = true;
		}
		return result;
	}

	private Node<T> findNode(T entry) {
		boolean found = false;
		Node<T> currentNode = header;
		while (!found && currentNode != null) {
			if (currentNode.getData().equals(entry)) {
				found = true;
			}
			currentNode = currentNode.getNextNode();
		}
		return currentNode;
	}

	public T remove() {
		T result = null;
		if (!isEmpty()) {
			result = header.getData();
			header = header.getNextNode();
			size--;
		}
		return result;
	}

	public void clear() {
		while (!isEmpty())
			remove();
	}

	public int getFrequencyOf(T entry) {
		int count = 0;
		Node<T> currentNode = header;
		while (currentNode != null) {
			if (currentNode.getData().equals(entry)) {
				count++;
			}
			currentNode = currentNode.getNextNode();
		}
		return count;
	}

	public boolean contains(T entry) {
		boolean found = false;
		Node<T> currentNode = header;
		while (!found && currentNode != null) {
			if (currentNode.getData().equals(entry)) {
				found = true;
			}
			currentNode = currentNode.getNextNode();
		}
		return found;
	}

	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] data = (T[]) new Object[size];
		if (!isEmpty()) {
			int index = 0;
			Node<T> currentNode = header;
			while ((index < size) && currentNode != null) {
				data[index++] = currentNode.getData();
				currentNode = currentNode.getNextNode();
			}
		}
		return data;
	}

	private class Node<T> {
		private T data;
		private Node<T> nextNode;

		public Node() {

		}

		public Node(T data) {
			this.data = data;
			this.nextNode = null;
		}

		public Node(T data, Node<T> nextNode) {
			this.data = data;
			this.nextNode = nextNode;
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public Node<T> getNextNode() {
			return nextNode;
		}

		public void setNextNode(Node<T> nextNode) {
			this.nextNode = nextNode;
		}
	}

}
