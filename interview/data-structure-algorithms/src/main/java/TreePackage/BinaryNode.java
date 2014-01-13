package TreePackage;

public class BinaryNode<T> implements BinaryNodeInterface<T> {

	private T data;
	private BinaryNode<T> left;
	private BinaryNode<T> right;
	
	public BinaryNode(){
		this(null);
	}
	public BinaryNode(T data){
		this(data,null, null);
	}
	public BinaryNode(T data, BinaryNode<T> leftChild, BinaryNode<T> rightChild){
		this.data = data;
		this.left = leftChild;
		this.right = rightChild;
	}
	
	public T getData() {
		return this.data;
	}

	public void setData(T newData) {
		this.data=newData;
	}

	public BinaryNodeInterface<T> getLeftChild() {
		return left;
	}

	public void setLeftChild(BinaryNodeInterface<T> leftChild) {
		this.left = (BinaryNode<T>)leftChild;
	}

	public BinaryNodeInterface<T> getRightChild() {
		return this.right;
	}

	public void setRightChild(BinaryNodeInterface<T> rightChild) {
		this.right = (BinaryNode<T>)rightChild;
	}

	public boolean hasLeftChild() {
		return this.left != null;
	}

	public boolean hasRightChild() {
		return this.right != null;
	}

	public boolean isLeaf() {
		return (this.left == null) && (this.right == null);
	}

	public int getNumberOfNodes() {
		int leftNumber = 0;
		int rightNumber = 0;
		
		if (left != null)
			leftNumber = left.getNumberOfNodes();
		
		if(right != null)
			rightNumber = right.getNumberOfNodes();
		
		return 1 + leftNumber + rightNumber;
	}

	public int getHeight() {
		return getHeight(this);
	}

	private int getHeight (BinaryNode<T> node){
		int height =0 ;
		if(node != null)
			height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
		return height;
	}
	public BinaryNodeInterface<T> copy() {
		BinaryNode<T> newNode = new BinaryNode<T>(data);
		
		if(this.left!=null)
			newNode.left = (BinaryNode<T>)this.left.copy();
		if(this.right!=null)
			newNode.right = (BinaryNode<T>)this.right.copy();
		
		return newNode;
	}

}
