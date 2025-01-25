/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;
    private int ratio;

    public RandomizedQueue() {
        array = (Item[]) new Object[2];
        size = 0;
        ratio = 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        array[size++] = item;
        if (size >= array.length) {
            resize(2 * array.length);
        }
    }

    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniformInt(size);
        Item item = array[index];
        array[index] = array[--size];
        if (size <= array.length * 0.25) {
            resize(array.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return array[StdRandom.uniformInt(size)];
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] indexs;
        private int remain;

        public RandomizedQueueIterator() {
            remain = size;
            indexs = new int[RandomizedQueue.this.size];
            for (int i = 0; i < indexs.length; i++) {
                indexs[i] = i;
            }
        }

        public boolean hasNext() {
            return remain > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = StdRandom.uniformInt(remain);
            int next = indexs[i];
            indexs[i] = indexs[--remain];
            return array[next];
        }
    }


    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 18; i++) {
            rq.enqueue("A" + i);
        }
        System.out.println("first iterator");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("second iterator ");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (int i = 0; i < 18; i++) {
            System.out.print("deque ");
            System.out.print(rq.dequeue());
            System.out.println(". remain " + rq.size() + " elements. now capacity ");
        }

    }
}
