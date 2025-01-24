import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        double num = 1;
        String out = StdIn.readString();
        while (!StdIn.isEmpty()) {
            String newWord = StdIn.readString();
            num = num + 1;
            if (StdRandom.bernoulli(1 / num)) {
                out = newWord;
            }
        }
        System.out.println(out);
    }
}