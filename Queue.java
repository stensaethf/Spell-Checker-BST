/**
 * An interface for the Queue ADT.
 * 
 * @param <T> The type of data that the queue stores.
 * 
 * @author Jadrian Miles
 */
public interface Queue<T> {
  
  /** Adds the given item to the back of the queue.
   * @param item the item to add
   */
  public void enqueue(T item);
  
  /** Removes an item from the front of the queue and returns it.
   * @return the item at the front of the queue, or null if empty
   */
  public T dequeue();
  
  /** Returns the item at the front of the queue, without removing it.
   * @return the item at the front of the queue, or null if empty
   */
  public T peek();
  
  /** Returns true if the queue is empty.
   */
  public boolean isEmpty();
  
  /** Removes all items from the queue. */
  public void clear();
}
