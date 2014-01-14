package bag;

import java.util.Arrays;

public class ArrayBag<T> implements BagInterface<T> {

	private int noOfEntries;
	private static int CAPACITY = 25;
	private T[] bag;

	public ArrayBag() {
		this(CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public ArrayBag(int size) {
		if (size <= 0) {
			bag = (T[]) new Object[CAPACITY];
		} else {
			bag = (T[]) new Object[size];
		}
	}

	public int getCurrentSize() {
		return noOfEntries;
	}

	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[noOfEntries];
		int index = 0;
		for (T e : bag) {
			result[index++] = e;
		}
		return result;
	}

	public boolean add(T e) {
		if (isFull()) {
			bag=(T[])Arrays.copyOf(bag, CAPACITY*2);
			CAPACITY *= 2;
		}
		bag[noOfEntries++] = e;

		return true;
	}

	private boolean isFull() {
		return bag.length == noOfEntries;
	}

	public boolean isEmpty() {
		return noOfEntries == 0;
	}

	public void clear() {
		bag = null;
	}

	public T remove() {
		T e = null;
		if (!isEmpty()) {
			e = bag[noOfEntries - 1];
			bag[noOfEntries--] = null;
		}
		return e;
	}

	public boolean remove(T entry) {
		boolean result = false;
		int indexOf = getIndexOf(entry);
		if(!isEmpty() && indexOf >=0 ){
			bag[indexOf] = bag[noOfEntries - 1];
			bag[noOfEntries--] = null;
			result = true;
		}
		return result;
	}

	public boolean contains(T entry) {	
		return (getIndexOf(entry) > -1);
	}

	private int getIndexOf(T anEntry) {
		int where = -1;
		boolean stillLooking = true;
		for (int index = 0; stillLooking && (index < noOfEntries); index++) {
			if (anEntry.equals(bag[index])) {
				stillLooking = false;
				where = index;
			} 
		} 
		return where;
	}

	public int getFrequencyOf(T entry) {
		int index = 0;
		if (!isEmpty()) {
			for (T e : bag) {
				if (e.equals(entry)) {
					index++;
				}
			}
		}

		return index;
	}
}
