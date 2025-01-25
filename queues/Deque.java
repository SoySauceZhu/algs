/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

// TODO: Add sentinel node!!! Make it a loop.

public class Deque<Item> implements Iterable<Item> {

    private Node firstNode;
    private Node lastNode;
    private int size;

    private class Node {
        Item item;
        Node last;
        Node next;

        public Node(Item item) {
            this.item = item;
        }
    }

    public Deque() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node(item);

        if (isEmpty()) {
            firstNode = node;
            lastNode = node;
        }
        else {
            node.next = this.firstNode;
            this.firstNode.last = node;
            this.firstNode = node;
        }
        size += 1;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node(item);

        if (isEmpty()) {
            lastNode = node;
            firstNode = node;
        }
        else {
            node.last = this.lastNode;
            this.lastNode.next = node;
            this.lastNode = node;
        }
        size += 1;
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        if (size == 1) {
            Item item = firstNode.item;
            firstNode = null;
            lastNode = null;
            size -= 1;
            return item;
        }
        else {
            Item item = this.firstNode.item;
            this.firstNode = this.firstNode.next;
            this.firstNode.last = null;
            size -= 1;
            return item;
        }
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            Item item = lastNode.item;
            firstNode = null;
            lastNode = null;
            size -= 1;
            return item;
        }
        else {
            Item item = this.lastNode.item;
            this.lastNode = this.lastNode.last;
            this.lastNode.next = null;
            size -= 1;
            return item;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;
        private int remains;

        public DequeIterator() {
            current = Deque.this.firstNode;
            remains = Deque.this.size;
        }

        @Override
        public boolean hasNext() {
            return remains > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            remains -= 1;
            return item;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            dq.addFirst("A" + i);
        }
        for (int i = 0; i < 5; i++) {
            dq.addLast("B" + i);
        }
        for (String s : dq) {
            System.out.println(s);
        }
        System.out.println("dq has " + dq.size() + " elements in total");
        for (int i = 0; i < 10; i++) {
            System.out.println(dq.removeFirst());
            System.out.println(dq.removeLast());
            System.out.println(dq.size());
        }
    }
}
