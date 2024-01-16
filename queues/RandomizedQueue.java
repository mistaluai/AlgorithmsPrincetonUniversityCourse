/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
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
        private Item[] items;
        private int movingIndex = 0;

        ItemIterator() {
            if (size > 0) {
                items = (Item[]) new Object[size];
                Node movingNode = head;
                while (movingNode != null) {
                    int index = StdRandom.uniformInt(0, size);
                    if (items[index] == null) {
                        items[index] = movingNode.item;
                        movingNode = movingNode.next;
                    }
                }
            }
        }


        public boolean hasNext() {
            if (size < 0)
                return false;

            return movingIndex != (size);
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return items[movingIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            tail = head;
        }
        else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    private Node randomNode() {
        int index = StdRandom.uniformInt(0, size);
        Node random = head;
        for (int i = 0; i < index; i++)
            random = random.next;

        return random;
    }

    // remove and return a random item
    public Item dequeue() {
        Node random = randomNode();
        if (random == head) {
            head = head.next;
        }
        else if (random == tail) {
            tail = tail.prev;
            tail.next = null;
        }
        else {
            random.prev.next = random.next;
        }
        size--;
        return random.item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return randomNode().item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ItemIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++)
            rq.enqueue(i);
        rq.sample();
        rq.dequeue();
        Iterator<Integer> rqIterator = rq.iterator();
        while (rqIterator.hasNext())
            StdOut.print(rqIterator.next() + " ");


    }

}
