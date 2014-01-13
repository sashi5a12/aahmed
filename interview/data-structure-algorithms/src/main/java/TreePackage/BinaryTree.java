package TreePackage;

import java.util.Iterator;

public abstract class BinaryTree<T> implements BinaryTreeInterface<T> {
	
	private BinaryNodeInterface<T> root;
	
	public BinaryTree(){
		this.root = null;
	}
	
	public BinaryTree(T rootData){
		this.root = new BinaryNode<T>(rootData);
	}
	
	public BinaryTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree){
		privateSetTree(rootData, leftTree, rightTree);
	}
	
	private void privateSetTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree){
		root = new BinaryNode<T>(rootData);
		
		
		if ((leftTree != null) && !leftTree.isEmpty())
			root.setLeftChild(leftTree.root);
		
		if ((rightTree != null) && !rightTree.isEmpty()){
			
			if(rightTree != leftTree){
				root.setRightChild(rightTree.root);
			}
			else {
				root.setRightChild(rightTree.root.copy());
			}
		}
		
		if((leftTree != null) && (leftTree != this))
			leftTree.clear();
		
		if((rightTree != null) && (rightTree != this))
			rightTree.clear();
	}
	
	public void setTree(T rootData){
		root = new BinaryNode<T>(rootData);
	}
	
	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree){
		privateSetTree(rootData, (BinaryTree<T>)leftTree, (BinaryTree<T>)rightTree);
	}
	
	public T getRootData(){
		T rootData = null;
		if(this.root != null)
			rootData = root.getData();
		return rootData;
	}
	protected void setRootData(T rootData){
		root.setData(rootData);
	}
	protected void setRootNode(BinaryNodeInterface<T> rootNode){
		this.root = rootNode;
	}
	protected BinaryNodeInterface<T> getRootNode(){
		return root;
	}
	public boolean isEmpty(){
		return root == null;
	}
	
	public void clear(){
		this.root = null;
	}
	
	public int getHeight(){
		return root.getHeight();
	}
	public int getNumberOfNodes(){
		return root.getNumberOfNodes();
	}
	
	public void inorderTraverse(){
		inorderTraverse(root);
	}
	
	private void inorderTraverse(BinaryNodeInterface<T> node){
		if(node != null){
			inorderTraverse(node.getLeftChild());
			System.out.println(node.getData());
			inorderTraverse(node.getRightChild());
		}
	}
	
	public void preorderTraverse(){
		preorderTraverse(root);
	}
	private void preorderTraverse(BinaryNodeInterface<T> node){
		if(node != null){
			System.out.println(node.getData());
			preorderTraverse(node.getLeftChild());
			preorderTraverse(node.getRightChild());
		}
	}
	
	private void postorderTraverse(BinaryNodeInterface<T> node){
		if(node != null){
			postorderTraverse(node.getLeftChild());
			postorderTraverse(node.getRightChild());
			System.out.println(node.getData());
		}
	}
}
