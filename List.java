import java.util.Iterator;
/** 
 * An interface for the List ADT, adapted from Frank Carrano.
 * @author Jadrian Miles
 */
public interface List<T> extends Iterable<T> {
    /** Adds newItem to the end of the list. */
    public void add(T newItem);
    
    /** Adds newItem at the given index.
     * Adds newItem at index newPosition, and shifts each item at or beyond
     * that index to the next higher index.
     * @post The item at newPosition is newItem.
     * @return false if newPosition is out of bounds.
     */
    public boolean add(int newPosition, T newItem);
    
    /** Removes the item at the given index.
     * Removes the item at the given index, shifts each item beyond that index
     * to the next lower index.
     * @return the removed item, or null if position is out of bounds.
     */
    public T remove(int position);
    
    /** Removes all items from the list. */
    public void clear();
    
    /** Replaces the item at a given index.
     * @return false if newPosition is out of bounds.
     */
    public boolean replace(int position, T newItem);
    
    /** Returns the item at a given index.
     * @return the item, or null if position is out of bounds.
     */
    public T at(int position);
    
    /** Returns true if the list contains the target item. */
    public boolean contains(T targetItem);
    
    /** Returns the length of the list: the number of items stored in it. */
    public int length();
    
    /** Returns true if the list has no items stored in it. */
    public boolean isEmpty();
    
    /** Returns an array version of the list.
     * @return an array of length length(), with the same items in it as are
     *         stored in the list, in the same order.
     */
    public T[] toArray();
    
    /** Returns an iterator that begins at index 0 in this list. */
    public Iterator<T> iterator();
}
