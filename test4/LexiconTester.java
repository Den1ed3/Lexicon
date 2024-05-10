import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LexiconTester {

    public static void main(String[] args) {
        Lexicon lexicon = new Lexicon();
        lexicon.readFromFile("in1.txt");
        lexicon.readFromFile("in2.txt");
        boolean alternative = args.length == 1 && args[0].equals("Y");
        lexicon.sort(alternative);
        lexicon.writeToFile("out.txt");
    }
}

class Lexicon {

    private final ArrayList<WordNode> lexicon = new ArrayList<>();

    public void readFromFile(String fileName) {
        File file = new File(fileName);
        String text = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
                text += scanner.nextLine() + "\n";

            text = text.replaceAll("[^A-Za-z ]", "");
            text = text.toLowerCase();

            String[] words = text.split(" ");
            for (String word : words) {
                WordNode node = findNode(word);
                if (node == null) lexicon.add(new WordNode(word));
                else node.incrFreq();
            }
            setNeighbors();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String filename) {
        try {
            FileWriter fr = new FileWriter(filename);
            for (WordNode wordNode : lexicon)
                fr.write(wordNode + "\n");
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sort(boolean alternative) {
        if (alternative) {
            Utilities.bubbleSort(lexicon);
        } else {
            Utilities.mergeSort(lexicon);
        }
    }

    private WordNode findNode(String word) {
        return lexicon.stream()
                .filter(node -> node.getWord().equals(word))
                .findFirst()
                .orElse(null);
    }

    private void setNeighbors() {
        for (WordNode wordNode : lexicon) {
            List<String> neighbors = lexicon.stream()
                    .map(WordNode::getWord)
                    .filter(word -> Utilities.diffByOneLetter(wordNode.getWord(), word))
                    .toList();
            wordNode.getNeighbors().addAll(neighbors);
        }
    }

    static class WordNode implements Comparable<WordNode> {

        private final String word;
        private int freq;
        private final ArrayList<String> neighbors;

        public WordNode(String word) {
            this.word = word;
            freq = 1;
            neighbors = new ArrayList<>();
        }

        public void incrFreq() {
            freq++;
        }

        public String getWord() {
            return word;
        }

        public int getFreq() {
            return freq;
        }

        public ArrayList<String> getNeighbors() {
            return neighbors;
        }

        @Override
        public int compareTo(WordNode other) {
            return this.word.compareTo(other.word);
        }

        @Override
        public String toString() {
            return word + " " + freq + " " + neighbors;
        }
    }
}

class Utilities {

    public static boolean diffByOneLetter(String word1, String word2) {
        if (word1.length() != word2.length()) return false;
        int diffCount = 0;
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();
        for (int i = 0; i < chars1.length; i++)
            if (chars1[i] != chars2[i]) diffCount++;
        return diffCount == 1;
    }

    public static <T extends Comparable<T>> void bubbleSort(ArrayList<T> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (list.get(j).compareTo(list.get(j + 1)) > 0)
                    Collections.swap(list, j, j + 1);
    }

    public static <T extends Comparable<T>> void mergeSort(ArrayList<T> list) {
        ArrayList<T> left = new ArrayList<>();
        ArrayList<T> right = new ArrayList<>();
        int center;

        if (list.size() > 1) {
            center = list.size() / 2;

            for (int i = 0; i < center; i++)
                left.add(list.get(i));
            for (int i = center; i < list.size(); i++)
                right.add(list.get(i));

            mergeSort(left);
            mergeSort(right);
            merge(left, right, list);
        }
    }

    private static <T extends Comparable<T>> void merge(ArrayList<T> left, ArrayList<T> right, ArrayList<T> whole) {
        int leftIndex = 0;
        int rightIndex = 0;
        int wholeIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if ((left.get(leftIndex).compareTo(right.get(rightIndex))) < 0) {
                whole.set(wholeIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                whole.set(wholeIndex, right.get(rightIndex));
                rightIndex++;
            }
            wholeIndex++;
        }

        ArrayList<T> rest;
        int restIndex;
        if (leftIndex >= left.size()) {
            rest = right;
            restIndex = rightIndex;
        } else {
            rest = left;
            restIndex = leftIndex;
        }

        for (int i = restIndex; i < rest.size(); i++) {
            whole.set(wholeIndex, rest.get(i));
            wholeIndex++;
        }
    }
}
