package TreePackage;

interface BinaryNodeInterface<T> {
	
	public T getData();
	public void setData(T newData);
	public BinaryNodeInterface<T> getLeftChild();
	public void setLeftChild(BinaryNodeInterface<T> leftChild);
	public BinaryNodeInterface<T> getRightChild();
	public void setRightChild(BinaryNodeInterface<T> rightChild);
	public boolean hasLeftChild();
	public boolean hasRightChild();
	public boolean isLeaf();
	public int getNumberOfNodes();
	public int getHeight();
	public BinaryNodeInterface<T> copy();
}
