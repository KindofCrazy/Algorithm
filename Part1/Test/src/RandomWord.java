import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        int i = 0;
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            i++;
            if (StdRandom.bernoulli((double) 1 / i)) {
                champion = string;
            }
        }
        StdOut.println(champion);
    }
}
