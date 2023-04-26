package deque;

public class LinkedListDeque<T> {

    /* Implements a double linked list for a generic E
      datatype.
     */
    private class ElementNode {
        public ElementNode prev;
        public ElementNode next;
        public T item;

        public ElementNode(){
        }

        public ElementNode(T i,ElementNode p,  ElementNode n){
            item = i;
            prev = p;
            next = n;
        }

    }

    private ElementNode sentinel;
    private int size;

    public LinkedListDeque(){
        size = 0;
        sentinel = new ElementNode();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
    public LinkedListDeque(T item){
        size = 1;
        sentinel = new ElementNode();
        ElementNode Node = new ElementNode(item, sentinel, sentinel);
        sentinel.next = Node;
        sentinel.prev = Node;
    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size ==0;
    }

    public void addFirst(T item){
        sentinel.next = sentinel.prev.next = new ElementNode(item, sentinel,sentinel.next);
        if (isEmpty()) sentinel.prev = sentinel.next;
        size++;
    }

    public void addLast(T item){
        sentinel.prev = sentinel.prev.next = new ElementNode(item, sentinel.prev,sentinel);
        if (isEmpty()) sentinel.next = sentinel.prev;
        size++;
    }

    public void printDeque(){
        ElementNode p = sentinel;
        while (p.next.item != null){
            System.out.print(p.next.item);
            p = p.next;
            System.out.print(" ");
        }

    }

    public T removeFirst(){
        if (isEmpty()) return null;
        T item = sentinel.next.item;
        sentinel.next.next.prev= sentinel;
        sentinel.next = sentinel.next.next;
        size --;
        return item;

    }

    public T removeLast(){
        if (isEmpty()) return null;
        T item = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size --;
        return item;
    }

    public T get(int index){
        if (size <= index) return null;
        ElementNode p = sentinel.next;
        for (int i =0; i< index; i++){
            p= p.next;
        }
        return p.item;
    }

    public T getRecursive(int index){
       if (size <= index) return null;
       return getRecursiveHelper(sentinel.next, index);

    }

    private T getRecursiveHelper( ElementNode node, int index){
        if (index == 0) return node.item;
        return getRecursiveHelper(node.next, index-1);
    }



}
