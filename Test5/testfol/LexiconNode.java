import java.util.ArrayList;

public class LexiconNode implements Comparable<LexiconNode> {

    private final String word;
    private int freq;
    private ArrayList<String> neighbors;

    /**
     * Constructor of the {@link LexiconNode} class.
     *
     * @param word the word contained in the node.
     */
    public LexiconNode(String word) {
        this.word = word;
        freq = 1;
    }

    /**
     * Increments the frequency of the word in the lexicon by one.
     */
    public void incrFreq() {
        freq++;
    }

    /**
     * @return the word contained in the node.
     */
    public String getWord() {
        return word;
    }

    /**
     * @return the frequency in which the word appears in the lexicon.
     */
    public int getFreq() {
        return freq;
    }

    /**
     * @return the neighbors of the word.
     */
    public ArrayList<String> getNeighbors() {
        return neighbors;
    }

    /**
     * @param neighbors the list with the neighbors.
     */
    public void setNeighbors(ArrayList<String> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public int compareTo(LexiconNode other) {
        return this.word.compareTo(other.word);
    }

    @Override
    public String toString() {
        return word + " " + freq + " " + neighbors;
    }
}