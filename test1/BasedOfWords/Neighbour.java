
import java.util.ArrayList;

public class Neighbour {

    private String str;
    private ArrayList<String> strs;

    public Neighbour() {
    }

    public Neighbour(String str, ArrayList<String> strs) {
        this.str = str;
        this.strs = strs;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public ArrayList<String> getStrs() {
        return strs;
    }

    public void setStrs(ArrayList<String> strs) {
        this.strs = strs;
    }

}
