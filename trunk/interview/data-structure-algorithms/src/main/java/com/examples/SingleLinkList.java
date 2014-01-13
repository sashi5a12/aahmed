package com.examples;

public class SingleLinkList {
	private Node head;
//	private Node tail;
	private int count;
	
	public SingleLinkList(){
		head = null;
//		tail = null;
		count = 0;
	}
	
	public void addFirst(Node n){
		n.setNext(this.head);
		head = n;
		count++;
	}
	
	/*public void addLast(Node n){
		n.setNext(null);
		if (tail==null){
			tail = n;
		}
		else {
			tail.setNext(n);
			tail = n;
		}
		count++;
	}*/
	
	public void remove(){
		if(head == null){
			throw new RuntimeException();
		}
		Node tmp = head;
		head = head.getNext();
		tmp.setNext(null);
		count--;
	}
	
	public int getCount() {
		return count;
	}

	public void printList(){
		Node itr = head;
		while (itr != null){
			System.out.print(itr.getElment()+", ");
			itr = itr.getNext();
		}
	}
}
