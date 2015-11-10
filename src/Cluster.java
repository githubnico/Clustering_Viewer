import java.util.ArrayList;

/**
 * Created by Deviltech on 10.11.2015.
 */
public class Cluster {

    private ArrayList<ClusterEntry> myEntries;

    // standard constructor
    public Cluster(ArrayList<ClusterEntry> myEntries) {
        this.myEntries = myEntries;
    }

    //empty constructor
    public Cluster() {
        this.myEntries = new ArrayList<ClusterEntry>();
    }

    public ArrayList<ClusterEntry> getMyEntries() {
        return myEntries;
    }

    public void setMyEntries(ArrayList<ClusterEntry> myEntries) {
        this.myEntries = myEntries;
    }

    // Add a single Entry
    public void addEntry(ClusterEntry c){
        myEntries.add(c);
    }

    public String toString(){
        String result = "";
        for(ClusterEntry c: myEntries){
            result += "NEW CLUSTER: \n";
            result += c.getSequenceId() + "\n";
            result += c.getLength() + "\n";
            result += c.getStrain() + "\n";
            result += c.getSequenceSimilarity() + "\n";
        }
        return result;
    }
}
