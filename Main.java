import java.util.*;
import java.io.*;

class Lexicon{
    //filenames
    String file1 = "in1.txt", file2 = "in2.txt";
    //stores lines read from files
    ArrayList<String> lines;
    //stores sorted words
    ArrayList<String> sorted_words;
    //frequency hash map
    HashMap<String, Integer> words;
    //neighbour table
    HashMap<String, ArrayList<String>> neighbours;
    
    //default constructor
    Lexicon(){
        //initialize both arraylists
        lines = new ArrayList<>();
        sorted_words = new ArrayList<>();
        
        //initialize both hashmaps
        words = new HashMap<>();
        neighbours = new HashMap<>();
        try {
            
            readFile(this.file1);
            readFile(this.file2);
            
        } catch(Exception e) {
            System.out.println("An error occured in reading files...");
        }
    }
    
    private void readFile(String file_name) throws Exception{
        
        File f = new File(file_name);
        BufferedReader br = new BufferedReader(new FileReader(f));
    
        String tmp;
        
        //read line by line, replace punctuation and add to ArrayList lines
        while ((tmp = br.readLine()) != null){
            lines.add(tmp.replaceAll("\\p{Punct}", ""));
        }
        
    }
    
    public void writeFile(){
        String op = "out.txt";
        
        try {
            
            FileWriter fw = new FileWriter(op);
            //write in format: word frequency [neighbour1, neighbour2]
            for(String w : sorted_words){
                fw.write(String.format("%s %d %s\n", w, words.get(w), neighbours.get(w).toString()));
            }
            
            fw.close();
            System.out.println("Successfully wrote to the file.");
            
        } catch(Exception e) {
            
            System.out.println("An error occurred.");
            e.printStackTrace();
            
        }  
    }
    
    public void ConstructMap(){
        //iterate through all the lines
        for(String l : lines){
            //split lines on spaces, get word array
            String[] w = splitLine(l);
            //for all words in the array
            for(String word : w){
                //convert word to lowercase
                word = word.toLowerCase();
                //check if word is an integer or not
                if(!checkIfNum(word)){
                    //if word present in HashMap words, simply increment it
                    if(words.containsKey(word) == true){
                        words.put(word, words.get(word)+1);
                    }else{
                        //add word to ArrayList sorted_words
                        //sort the list later
                        sorted_words.add(word);
                        //else add new entry to HashMap
                        words.put(word, 1);
                        //initialize neighbour ArrayList in HashMap neighbours
                        neighbours.put(word, new ArrayList<String>());
                    }
                }
            }
        }
    }
    
    private String[] splitLine(String line){
        return line.trim().split("\\s+");
    }
    
    private boolean checkIfNum(String s){
        try {
            //parse string as int
            int d = Integer.parseInt(s);
        } catch(Exception e) {
            //if any errors, return false
            return false;
        }
        //is an integer, so return true
        return true;
    }
    
    public void printLines(){
        for(String l : lines){
            System.out.println(l);
        }
    }
    
    public void printWords(){
        for(Map.Entry word : words.entrySet()){
            System.out.printf("Word:%s\tFrequency:%d\n", word.getKey(), word.getValue());
        }
    }
    
    public void printSortedWords(){
        for(String word : sorted_words){
            System.out.println(word);
        }
    }
    
    public void printNeighbours(){
        for(Map.Entry word : neighbours.entrySet()){
            System.out.println("Word:" + word.getKey() + "\tNeighbours:" + word.getValue());
        }
    }
    
    //normal bubbleSort for strings.
    public void bubbleSort(){
        int total_words = sorted_words.size();
        
        String tmp;
        
        for(int i = 0 ; i < total_words - 1 ; i++){
            for(int j = i + 1 ; j < total_words ; j++){
                if(sorted_words.get(i).compareTo(sorted_words.get(j))>0){
                    tmp = sorted_words.get(i);
                    sorted_words.set(i, sorted_words.get(j));
                    sorted_words.set(j, tmp);
                }
            }
        }
    }
    
    public void findNeighbours(){
        //iterate through all the words
        for(int i = 0 ; i < sorted_words.size(); i++){
            for(int j = 0; j < sorted_words.size(); j++){
                if(i == j){
                    continue;
                }
                //check if they are neighbours
                if(areNeighbours(sorted_words.get(i), sorted_words.get(j))){
                    addNeighbours(sorted_words.get(i), sorted_words.get(j));
                }
            }
        }
    }
    
    private boolean areNeighbours(String a, String b){
        //convert both strings to char array
        char[] array_A = new char[a.length()];
        char[] array_B = new char[b.length()];
        
        //add characters from respective strings to arrays
        
        for(int i = 0 ; i < a.length() ; i++){
            array_A[i] = a.charAt(i);
        }
        
        for(int i = 0 ; i < b.length() ; i++){
            array_B[i] = b.charAt(i);
        }
        
        //if lengths are not equal, they are not neighbours
        if(a.length() != b.length()){
            return false;
        }else{
            int diff = 0;
            //chech each character from both arrays
            for(int i = 0 ; i < a.length() ; i++){
                if(Character.compare(array_A[i], array_B[i]) != 0){
                    //if different, increment diff
                    diff = 1 + diff;
                    if(diff > 1){
                        //if the differ by more than one Character
                        //they are not neighbours, return false
                        //System.out.printf("%s and %s are not neighbours..\n",a,b);
                        return false;
                    }
                }
            }
        }
        //System.out.printf("%s and %s are neighbours..\n",a,b);
        return true;
    }
    
    private void addNeighbours(String a, String b){
        //get neighbour ArrayList of a
        ArrayList<String> s = neighbours.get(a);
        //add b to that ArrayList
        s.add(b);
        //update 
        neighbours.put(a,s);
    }
    
}

public class Main
{
        public static void main(String[] args){
                Lexicon lx = new Lexicon();
                lx.ConstructMap();
                lx.bubbleSort();
                lx.findNeighbours();
                lx.writeFile();
        }
}