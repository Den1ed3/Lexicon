
import java.util.*;
import java.io.*;

class LexiconTester {

    String file1 = "in1.txt", file2 = "in2.txt";
    ArrayList<String> lines;
    ArrayList<String> sorted_words;
    ArrayList<Word> words;
    ArrayList<Neighbour> neighbours;

    LexiconTester() {
        lines = new ArrayList<>();
        sorted_words = new ArrayList<>();

        words = new ArrayList<>();
        neighbours = new ArrayList<>();
        try {

            readFile(this.file1);
            readFile(this.file2);

        } catch (Exception e) {
            System.out.println("An error occured in reading files...");
        }
    }

    private void readFile(String file_name) throws Exception {

        File f = new File(file_name);
        BufferedReader br = new BufferedReader(new FileReader(f));

        String tmp;

        while ((tmp = br.readLine()) != null) {
            lines.add(tmp.replaceAll("\\p{Punct}", ""));
        }

    }

    public void writeFile() {
        String op = "out.txt";

        try {

            FileWriter fw = new FileWriter(op);
            for (String w : sorted_words) {
                int ws = 0;
                ArrayList<String> strs = new ArrayList<>();
                for (Word word : words) {
                    if (word.getStr().equalsIgnoreCase(w)) {
                        ws = word.getInteger();
                        break;
                    }
                }
                for (Neighbour n : neighbours) {
                    if (n.getStr().equalsIgnoreCase(w)) {
                        strs = n.getStrs();
                        break;
                    }
                }
                fw.write(String.format("%s %d %s\n", w, ws, strs.toString()));
            }

            fw.close();
            System.out.println("Successfully wrote to the file.");

        } catch (Exception e) {

            System.out.println("An error occurred.");
            e.printStackTrace();

        }
    }

    public void ConstructMap() {
        for (String l : lines) {
            String[] w = splitLine(l);
            for (String word : w) {
                word = word.toLowerCase();
                if (!checkIfNum(word)) {
                    boolean val = false;

                    int i = 0;
                    for (Word wrd : words) {
                        if (wrd.getStr().equalsIgnoreCase(word)) {
                            val = true;
                            break;
                        }
                        i++;
                    }

                    if (val) {
                        words.get(i).setInteger((words.get(i).getInteger() + 1));
                    } else {
                        sorted_words.add(word);
                        words.add(new Word(word, 1));
                        neighbours.add(new Neighbour(word, new ArrayList<>()));
                    }
                }
            }
        }
    }

    private String[] splitLine(String line) {
        return line.trim().split("\\s+");
    }

    private boolean checkIfNum(String s) {
        try {
            int d = Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void printLines() {
        for (String l : lines) {
            System.out.println(l);
        }
    }

    public void printWords() {
        for (Word word : words) {
            System.out.printf("Word:%s\tFrequency:%d\n", word.getStr(), word.getInteger());
        }
    }

    public void printSortedWords() {
        for (String word : sorted_words) {
            System.out.println(word);
        }
    }

    public void printNeighbours() {
        for (Neighbour n : neighbours) {
            System.out.println("Word:" + n.getStr() + "\tNeighbours:" + n.getStrs());
        }
    }

    public void bubbleSort() {
        int total_words = sorted_words.size();

        String tmp;

        for (int i = 0; i < total_words - 1; ++i) {
            for (int j = i + 1; j < total_words; ++j) {
                if (sorted_words.get(i).compareTo(sorted_words.get(j)) > 0) {
                    tmp = sorted_words.get(i);
                    sorted_words.set(i, sorted_words.get(j));
                    sorted_words.set(j, tmp);
                }
            }
        }
    }

    public void findNeighbours() {
        //iterate through all the words
        for (int i = 0; i < sorted_words.size(); ++i) {
            for (int j = 0; j < sorted_words.size(); ++j) {
                if (i == j) {
                    continue;
                }
                //check if they are neighbours
                if (areNeighbours(sorted_words.get(i), sorted_words.get(j))) {
                    addNeighbours(sorted_words.get(i), sorted_words.get(j));
                }
            }
        }
    }

    private boolean areNeighbours(String a, String b) {
        char[] array_A = new char[a.length()];
        char[] array_B = new char[b.length()];

        for (int i = 0; i < a.length(); ++i) {
            array_A[i] = a.charAt(i);
        }

        for (int i = 0; i < b.length(); ++i) {
            array_B[i] = b.charAt(i);
        }

        if (a.length() != b.length()) {
            return false;
        } else {
            int diff = 0;
            for (int i = 0; i < a.length(); ++i) {
                if (Character.compare(array_A[i], array_B[i]) != 0) {
                    diff = 1 + diff;
                    if (diff > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void addNeighbours(String a, String b) {
        ArrayList<String> s = new ArrayList<>();
        int i = 0;
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getStr().equals(a)) {
                s = neighbour.getStrs();
                neighbours.get(i).setStrs(s);
                break;
            }
            i++;
        }
        s.add(b);

    }

    public static void main(String[] args) {
        LexiconTester lx = new LexiconTester();
        lx.ConstructMap();
        lx.bubbleSort();
        lx.findNeighbours();
        lx.writeFile();

    }

}
