/**
 * Created by Deviltech on 10.11.2015.
 */
public class ClusterEntry {

    private String sequenceId;
    private String strain;
    private String length;
    private String sequenceSimilarity;

    public ClusterEntry(String sequenceId, String strain, String length, String sequenceSimilarity) {
        this.sequenceId = sequenceId;
        this.strain = strain;
        this.length = length;
        this.sequenceSimilarity = sequenceSimilarity;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }


    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getSequenceSimilarity() {
        return sequenceSimilarity;
    }

    public void setSequenceSimilarity(String sequenceSimilarity) {
        this.sequenceSimilarity = sequenceSimilarity;
    }
}
