import java.util.ArrayList;

/**
 * Created by Deviltech on 10.11.2015.
 */
public class Cluster {

    private ClusterEntry representative;
    private ArrayList<ClusterEntry> myEntries;

    // standard constructor
    public Cluster(ClusterEntry representative, ArrayList<ClusterEntry> myEntries) {
        this.myEntries = myEntries;
        this.representative = representative;
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

    public ClusterEntry getRepresentative() {
        return representative;
    }

    public void setRepresentative(ClusterEntry representative) {
        this.representative = representative;
    }

    // Add a single Entry
    public void addEntry(ClusterEntry c){
        myEntries.add(c);
    }


}
