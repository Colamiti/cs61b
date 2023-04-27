package deque;

public class ArrayDeque<T> {

    private T[] items;
    private int back_index;
    private int first_index;
    private int last_index;
    private int size;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[5];
        size = 0;
        first_index = 0;
        last_index = 0;
        back_index = items.length-1;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (first_index == 0) {
            System.arraycopy(items, 0, a, 0, size);
        }else{
            int before_0 = items.length - first_index;
            System.arraycopy(items, first_index, a,0, before_0);
            System.arraycopy(items, 0, a, before_0,  size - before_0);
        }

        items = a;
        back_index = items.length - 1;
        first_index = 0;
        last_index = size;
    }
    
    /** Inserts X into the back of the list. */
    public void addLast(T item) {
        if (size == items.length) {
            resize((int) (size *1.01+.5));
        }
        items[last_index] = item;
        last_index++;
        size = size + 1;
    }
    public void addFirst(T item){
        if (size == items.length) {
            resize((int) Math.round(size *1.01+.5));
        }
        if (first_index == 0) first_index = back_index;
        else first_index--;
        items[first_index] = item;
        size = size + 1;
    }
    public T getFirst() {
        return items[first_index];
    }
    /** Returns the T from the back of the list. */
    public T getLast() {
        return items[last_index];
    }
    /** Gets the ith T in the list (0 is the front). */
    public T get(int i) {
        if (size == 0) return null;
        if (i >= size) return null;
        if (first_index == 0){
            return items[i];
        }
        else if (first_index+i <= back_index){
            return items[i+first_index];
        }
        else {
            return items[i + back_index - first_index];
        }
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    public T removeFirst(){
        T item = getFirst();
        items[first_index] = null;
        first_index++;
        if (first_index == items.length) first_index = 0;
        size--;
        return item;
    }



    /** Deletes T from back of the list and
     * returns deleted T. */
    public T removeLast() {
        T item = getLast();
        items[last_index] = null;
        last_index--;
        size = size - 1;
        return item;
    }

    public boolean isEmpty(){
        return size == 0;
    }



}
