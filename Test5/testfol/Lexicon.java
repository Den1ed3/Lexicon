import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Lexicon {

    private final ArrayList<LexiconNode> lexicon = new ArrayList<>();

    /**
     * Reads the text from the specified file, filters out the all the
     * non-alphabetic characters and adds all the words to the lexicon.
     *
     * @param fileName the name of the file to read from.
     * @throws IOException if some error occurs whilst reading from to the file.
     */
    public void readFromFile(String fileName) throws IOException {
        Scanner sc = new Scanner(new File(fileName));

        String text = "";
        while (sc.hasNextLine())
            text += sc.nextLine() + "\n";

        text = text.replaceAll("[^A-Za-z ]", "");
        text = text.toLowerCase();

        String[] words = text.split(" ");
        for (String word : words) {
            LexiconNode node = findNode(word);
            if (node == null) lexicon.add(new LexiconNode(word));
            else node.incrFreq();
        }
        setNeighborsForAll();
    }

    /**
     * Writes contents of the lexicon to the specified file.
     *
     * @param filename the name of the file to write to.
     * @throws IOException if some error occurs whilst writing to the file.
     */
    public void writeToFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        for (LexiconNode lexiconNode : lexicon)
            fw.write(lexiconNode + "\n");
        fw.close();
    }

    /**
     * Sorts the words in the lexicon in alphabetical order. There are two
     * sorting algorithms that can be used. The default one is merge-sort and
     * the alternative one is bubble-sort.
     *
     * @param alternative whether the alternative sorting algorithm should be
     *                    used or not.
     * @return the time it takes for the sorting to be finished.
     */
    public long sort(boolean alternative) {
        long t1 = System.currentTimeMillis();
        if (alternative) Utilities.bubbleSort(lexicon);
        else Utilities.mergeSort(lexicon);
        long t2 = System.currentTimeMillis();
        return t2 - t1;
    }

    /**
     * Searches the lexicon for a node that contains the specified word. If
     * one is found it is returned otherwise null is returned instead.
     *
     * @param word the word to search for.
     * @return the found node of the word or null.
     */
    private LexiconNode findNode(String word) {
        for (LexiconNode node : lexicon)
            if (node.getWord().equals(word)) return node;
        return null;
    }

    /**
     * Finds and sets the neighbors for every word in the lexicon. Two words
     * are considered neighbors when they have the same character length and
     * differ in exactly one letter.
     */
    private void setNeighborsForAll() {
        for (LexiconNode node : lexicon) {
            ArrayList<String> neighbors = lexicon.stream()
                    .map(LexiconNode::getWord)
                    .filter(word -> Utilities.diffByOneLetter(word, node.getWord()))
                    .distinct()
                    .collect(Collectors.toCollection(ArrayList::new));

            node.setNeighbors(neighbors);
        }
    }
}
