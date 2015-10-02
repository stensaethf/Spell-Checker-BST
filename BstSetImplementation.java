import java.util.Iterator;

/** An ADT that represents a set: a bunch of items that must be distinct from
 * each other, stored with no defined order.
 * @author Frederik Roenn Stensaeth and Javier Ernesto Moran Lemus.
 * @param <T> The type of item to be stored in the set.
 */

/** A class That creates a Binary Search Tree. */ 
public class BstSetImplementation<T extends Comparable<? super T>> 
        implements Set<T> {
    private Node root = null;
    private int size;

    /* A class that creates a Node. */
    private class Node {
        /** T variable val is the value a node holds. */
        public T val;
        /** Node variable next is the next node in the chain. */
        public Node right;
        public Node left;
        /** Creates an instance of the node. */
        private Node(T value) {
            val = value;
            right = null;
            left = null;
        }
    }

    /** Adds the given item to the set, ignoring duplicates. */
    public void add(T item) {
        //Checks if the tree is empty, if it is, creates the root.
        if (isEmpty()){
            root = new Node(item);
            size = 1;
        } else {
            add(root, item);
        }
    }

    /** Helper function for add.
     * Takes a root node and the item as parameters. 
     * This allows a node to be added in a given subtree. */
    private T add(Node root_node, T item) {
        //Initializes a variable that will be given to the main add function.
        T val_ret = null;
        //Makes sure the Root is not null.
        if(root_node != null){
            //Makes sure the item is not already on the list. 
            if(item.compareTo(root_node.val) == 0) {
                val_ret = root_node.val;
            //Adds to the left subtree if the item is less than the root.
            } else if(item.compareTo(root_node.val) < 0){
                if(root_node.left != null) {
                    val_ret = add(root_node.left, item);
                } else {
                    root_node.left = new Node(item);
                    size++;
                }
            //Adds to the right subtree if the items is greater than the root.
            } else if(item.compareTo(root_node.val) > 0) {
               if(root_node.right != null) {
                    val_ret = add(root_node.right, item);
                } else {
                    root_node.right = new Node(item);
                    size++;
                } 
            }
        }
        //Returns a value to the other add function. 
        return val_ret;
    }
    
    /** Removes the given item.
     * @return False if the item wasn't in the set before.
     */
    public boolean remove(T item) {
        //Checks if the item is in the tree.
        //If it is, removes it using a helper function.
        if(item != null && contains(item)) {
            Node root_add = remove(root, item);
            size--;
            return true;
        }
        return false; 
    }

    /** Helper function for remove method.
    * Takes a root node, and the item to be removed as parameters.*/
    private Node remove(Node root_node, T item) {
        if(root_node != null) {
            T value_r = root_node.val;
            //Checks if the item is the root node, if it is, removes it.
            if(item.compareTo(value_r) == 0) {
                root_node = remove_top(root_node);
            //Removes the item from the left subtree if it is less
            //than the current root.
            } else if (item.compareTo(value_r) < 0) { 
                Node left = root_node.left;
                Node root_new = remove(left, item);
                root_node.left = root_new;
            //Removes the item from the right subtree if it is more
            //than the current root. 
            } else {
                Node right = root_node.right;
                Node root_new = remove(right, item);
                root_node.right = root_new;
            }
        }
        //Returns the removed value to remove. 
        return root_node;
    }
    /** Helper function for the helper remove function. */
    private Node remove_top(Node root_node) {
        //If the node has no childre, removes it.
        if(root_node.left != null && root_node.right != null) {
            Node left_root = root_node.left;
            Node largest = largest_remove(left_root);
            root_node.val = largest.val;
            root_node.left = largest;
        //If it has children, assigns the value of the children to this node.
        } else if(root_node.right != null) {
            root_node = root_node.right;
        } else {
            root_node = root_node.left;
        }
        return root_node;
    }

    /** Helper function that removes the largest of the two children. */
    private Node largest_remove(Node root_node) {
        //Checks to see if it has a right child first.
        //Right children are always larger.
        if(root_node.right != null) {
            Node right_kid = root_node.right;
            while(right_kid.right != null){
                right_kid = right_kid.right;
            }
        //Removes the left child if it has right children.
        } else {
            root_node = root_node.left;
        }
        return root_node;
    }
    
    /** Checks whether the given item is in the set. */
    public boolean contains(T item) {
        if(root == null) {
            return false;
        }
        if(item != null) {
            Node node = find(root, item);
            if(node != null){
                return true;
            }
        }
        return false;
    }
    
    /** Returns the number of items in the set.
     * @return the number of items in the set.
     */
    public int size() {
        return size;
    }
    
    /** Returns whether the set is empty. */
    public boolean isEmpty() {
        if(root == null) {
            return true;
        }
        return false;
    }
    
    /** Removes all items from the set. */
    public void clear() {
        root = null;
        size = 0;
    }

    /** Helper function for contains. 
     * Finds out if a certain item exists in the tree. */
    private Node find(Node root_node, T item) {
        if(root_node == null) {
            return null;
        } else if(root_node.val.equals(item)) {
            return root_node;
        }
        if(item.compareTo(root_node.val) < 0) {
            return find(root_node.left, item);
        } else {
            return find(root_node.right, item);
        }
    }
    
    /** Creates a new Set and returns a union of this set and otherSet.
     * @param otherSet A set to combine with this set.
     * @return The union of the two sets.
     */
    @SuppressWarnings("unchecked")
    public Set<T> union(Set<T> otherSet) {
        Set<T> new_set = new BstSetImplementation<T>();
        //Adds all objects from one Array to the union array.
        for(Object item : toArray()) {
            new_set.add((T) item);
        }
        //Adds all objects from the other array to the union array.
        for(Object item : otherSet.toArray()) {
            if(!this.contains((T) item)) {
                new_set.add((T) item);
            }
        }
        return new_set;
    }
    
    /** Creates a new Set and returns an intersections of this set 
     * and otherSet.
     * @param otherSet A set to combine with this set.
     * @return The intersection of the two sets.
     */
    @SuppressWarnings("unchecked")
    public Set<T> intersect(Set<T> otherSet) {
        Set<T> new_set = new BstSetImplementation<T>();
        for(Object item : otherSet.toArray()) {
            if(this.contains((T) item)) {
                new_set.add((T) item);
            }
        }
        return new_set;
    }
    
    /** Returns an iterator over the items in the set (which will access the
     * items in some arbitrary order).
     */
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Unsupported Method.");
    }
    
    /** Returns an array containing the same contents as this set, in an
     * arbitrary order. Note that, for technical reasons, the type of the items
     * contained in the set can't be communicated properly to the caller, so an
     * array of Objects gets returned.
     * @return an array of length size(), with the same items in it as are
     * stored in the set.
     */
    public Object[] toArray() {
        Queue<Node> queue = new MysteryQueueImplementation<Node>();
        @SuppressWarnings("unchecked")
              Object[] array = new Object[this.size()];
        Node current = root;
        queue.enqueue(current);
        int count = 0;
        //Does a depth first traversal using a queue, 
        //to make sure to enqueue all the nodes
        if(current != null) {
            while(!queue.isEmpty()) {
                current = queue.dequeue();
                if(current.left != null) {
                    queue.enqueue(current.left);
                }
                if(current.right != null) {
                    queue.enqueue(current.right);
                }
                array[count] = current.val;
                count++;
            }
        }
        //Returns the union array. 
        return array;
    }

    /** Main function that tests the functionality of BstSetImplementation.
     * Creates a special emphasiz on testing union and intersect. */ 
    public static void main(String[] args) {
        //Test suit for BstSetImplementation. 
        //Creates a set and tests its different functions.
        Set<Integer> test_set = new BstSetImplementation<Integer>();
        if(test_set.isEmpty() == false) {
            System.out.println(
                "*** isEmpty FAILED; Expected true, got false ***");
        }
        for(int i = 0; i < 20; i++) {
            if(test_set.size() != i) {
                System.out.printf(
                    "*** size FAILED; Expected %d, got %d ***\n", i, 
                    test_set.size());
            }
            test_set.add(i);
            if(test_set.size() != i + 1) {
                System.out.printf(
                    "*** add FAILED; Expected %d, got %d ***\n", i + 1, 
                    test_set.size());
            }
        }
        if(test_set.isEmpty() == true) {
            System.out.println(
                "*** isEmpty FAILED; Expected false, got true ***");
        }
        for(int i = 0; i < 20; i++) {
            test_set.add(i);
        }
        if(test_set.size() != 20) {
            System.out.printf(
                "*** add FAILED; Expected 20, got %d ***\n", 
                test_set.size());
        }
        for(int i = 0; i < 20; i++) {
            if(test_set.contains(i) == false) {
                System.out.println(
                    "*** contains FAILED; Expected true, got false ***");
            }
            if(test_set.remove(i) == false) {
                System.out.println(
                    "*** remove FAILED; Expected true, got false ***");
            }
        }
        if(test_set.isEmpty() == true) {
            System.out.println(
                "*** isEmpty FAILED; Expected false, got true ***");
        }
        if(test_set.remove(100) == true) {
            System.out.println(
                "*** remove FAILED; Expected false, got true ***");
        }
        for(int i = 0; i < 10; i++) {
            test_set.add(i);
        }
        test_set.clear();
        if(test_set.isEmpty() == false) {
            System.out.println(
                "*** clear FAILED; Expected empty set, got non-empty set ***");
        }
        test_set.clear();
        if(test_set.isEmpty() == false) {
            System.out.println(
                "*** clear FAILED; Expected empty set, got non-empty set ***");
        }

        Object[] test_array = test_set.toArray();
        if(test_array.length != 0) {
            System.out.print("*** toArray FAILED; Expected empty array, ");
            System.out.println("got array of items ***"); 
        }
        for(int i = 0; i < 10; i++) {
            test_set.add(i);
        }
        test_array = test_set.toArray();
        for(Object item : test_array) {
            if(!test_set.contains((Integer) item)) {
                System.out.print("*** toArray FAILED; Expected array of ");
                System.out.println("items in set, got different ***");
            }
        }
        test_set.clear();

        Set<Integer> test_set2 = new BstSetImplementation<Integer>();
        Set<Integer> intersect_set = test_set.intersect(test_set2);
        Set<Integer> union_set = test_set.union(test_set2);
        if(!intersect_set.isEmpty()) {
            System.out.println("*** intersect FAILED; Expected empty set, ");
            System.out.println("got non-empty set ***");
        }
        if(!union_set.isEmpty()) {
            System.out.println(
                "*** union FAILED; Expected empty set, got non-empty set ***");
        }
        for(int i = 0; i < 10; i++) {
            test_set.add(i);
            test_set2.add(i + 8);
        }
        intersect_set = test_set.intersect(test_set2);
        if(intersect_set.isEmpty()) {
            System.out.println(
                "*** intersect FAILED; Expected non-empty set, ");
            System.out.println("got empty set ***");
        }
        union_set = test_set.union(test_set2);
        if(union_set.isEmpty()) {
            System.out.println(
                "*** union FAILED; Expected non-empty set, got empty set ***");
        }
        if(!intersect_set.contains(9)) {
            System.out.println(
               "*** intersect FAILED; Expected 9 to be in set, it is not ***");
        }
        if(intersect_set.contains(7)) {
            System.out.println(
               "*** intersect FAILED; Expected 7 not to be in set, it is ***");
        }
        if(intersect_set.contains(15)) {
            System.out.println(
              "*** intersect FAILED; Expected 15 not to be in set, it is ***");
        }
        if(!union_set.contains(0)) {
            System.out.println(
                "*** union FAILED; Expected 0 to be in set, it is not ***");
        }
        if(!union_set.contains(9)) {
            System.out.println(
                "*** union FAILED; Expected 9 to be in set, it is not ***");
        }
        if(union_set.contains(50)) {
            System.out.println(
                "*** union FAILED; Expected 50 not to be in set, it is ***");
        }
    }
}