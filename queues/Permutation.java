/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> deque = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            deque.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            String str = deque.dequeue();
            System.out.println(str);
        }

    }
}
