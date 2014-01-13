package com.examples;

public class Node {
	private String elment;
	private Node next;
	
	
	public Node() {
	}
	
	public Node(String elment, Node next) {
		this.elment = elment;
		this.next = next;
	}
	
	public String getElment() {
		return elment;
	}
	public void setElment(String elment) {
		this.elment = elment;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	
}
