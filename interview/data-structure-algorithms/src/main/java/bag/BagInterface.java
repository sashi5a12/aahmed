package bag;

public interface BagInterface<T> {
	
	//Gets the current number of entries in this bag.
	public int getCurrentSize();
	
	//Sees whether this bag is full.
	//public boolean isFull();
	
	//Sees whether this bag is empty.
	public boolean isEmpty();
	
	//Adds a new entry to this bag.
	public boolean add(T entry);
	
	//Removes one unspecified entry from this bag, if possible.
	public boolean remove(T entry);
	
	//Removes one occurrence of a given entry from this bag,
	public T remove();
	
	//Removes all entries from this bag.
	public void clear();
	
	//Counts the number of times a given entry appears in this bag.
	public int getFrequencyOf(T entry);
	
	//Tests whether this bag contains a given entry.
	public boolean contains(T entry);
	
	//Creates an array of all entries that are in this bag.
	public T[] toArray();
}
