package com.examples;

public class TestSingleLinkList {

	public static void main(String[] args) {
		Node a = new Node("A",null);
		Node b = new Node("B",null);
		Node c = new Node("C",null);
		Node d = new Node("D",null);
		SingleLinkList sl = new SingleLinkList();
		sl.addFirst(a);
		sl.addFirst(b);
		sl.addFirst(c);
		sl.addFirst(d);
		sl.printList();
		System.out.println("\ncount="+sl.getCount());
	}

}
