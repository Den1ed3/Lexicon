import java.io.IOException;

public class LexiconTester {

    public static void main(String[] args) throws IOException {

        Lexicon lexicon = new Lexicon();
        lexicon.readFromFile("in1.txt");
        lexicon.readFromFile("in2.txt");
        boolean alternative = args.length == 1 && args[0].equals("Y");

        long execTime = lexicon.sort(alternative);
        lexicon.writeToFile("out.txt");

        String sortAlgUsed = alternative ? "Bubble-Sort" : "Merge-Sort";
        System.out.println(sortAlgUsed + ": " + execTime + " ms");
    }
}




