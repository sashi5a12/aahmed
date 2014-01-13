package com.examples;

import java.util.Arrays;

public class InsertionSort {

	private static void insertionSort(int[] array){
		for(int i=1; i<array.length; i++){
			int curr = array[i];
			int j = i - 1;
			
			System.out.println("i="+i);
			System.out.println("curr="+curr);
			System.out.println("j="+j);
			while (j>=0 && array[j]>curr){
				array[j+1] = array[j--];
//				System.out.println(array[j]+">"+curr);
				System.out.println("j="+j);
				System.out.println(Arrays.toString(array));
			}
			array[j+1]=curr;
			System.out.println(Arrays.toString(array));
			System.out.println("-------------------");
		}
		
		System.out.println(Arrays.toString(array));
	}
	public static void main(String[] args) {
		insertionSort(new int[]{5,1,6,10,9,2,4});
	}

}
