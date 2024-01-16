/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node head;
    private Node tail;

    private class Node {
        Item item;
        Node prev;
        Node next;

        Node(Item item) {
            this.item = item;
        }
    }

    private class ItemIterator implements Iterator<Item> {
        Node currentNode;

        ItemIterator() {
            currentNode = new Node(null);
            currentNode.next = head;
        }


        public boolean hasNext() {

            return currentNode.next != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            currentNode = currentNode.next;
            return currentNode.item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty deque
    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node newNode = new Node(item);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        }
        else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }


    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException();

        Node oldItem = head;
        if (head == tail) {
            head = null;
            tail = null;
        }
        else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return oldItem.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException();
        Node oldItem = tail;
        if (tail == head) {
            tail = null;
            head = null;
        }
        else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return oldItem.item;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ItemIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(null);
        for (int i = 0; i < 10; i++)
            d.addFirst(i);
        for (int i = 10; i < 20; i++)
            d.addLast(i);
        d.removeLast();
        d.removeFirst();
        d.removeLast();
        d.removeFirst();
        d.removeLast();
        d.removeFirst();
        d.removeLast();
        d.removeFirst();
        d.removeLast();
        d.removeFirst();
        d.removeLast();
        d.removeFirst();
        d.removeLast();
        d.removeFirst();
        d.removeLast();
        d.removeFirst();
        d.removeLast();
        d.size();
        d.isEmpty();
    }

}
