import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Deviltech on 10.11.2015.
 */
public class ClusterEntry {

    private SimpleStringProperty sequenceId;
    private SimpleStringProperty strain;
    private SimpleStringProperty length;
    private SimpleStringProperty sequenceSimilarity;


    public SimpleStringProperty sequenceIdProperty() {
        if (sequenceId == null) {
            sequenceId = new SimpleStringProperty(this, "sequenceId");
        }
        return sequenceId;
    }

    public SimpleStringProperty strainProperty() {
        if (strain == null) {
            strain = new SimpleStringProperty(this, "strain");
        }
        return strain;
    }

    public SimpleStringProperty length() {
        if (length == null) {
            length = new SimpleStringProperty(this, "length");
        }
        return length;
    }

    public SimpleStringProperty sequenceSimilarity() {
        if (sequenceSimilarity == null) {
            sequenceSimilarity = new SimpleStringProperty(this, "sequenceSimilarity");
        }
        return sequenceSimilarity;
    }


    public ClusterEntry(String sequenceId, String strain, String length, String sequenceSimilarity) {
        this.sequenceId = new SimpleStringProperty(sequenceId);
        this.strain = new SimpleStringProperty(strain);
        this.length = new SimpleStringProperty(length);
        this.sequenceSimilarity = new SimpleStringProperty(sequenceSimilarity);
    }



    public String getSequenceId() {
        return sequenceId.get();
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId.set(sequenceId);
    }

    public String getStrain() {
        return strain.get();
    }

    public void setStrain(String strain) {
        this.strain.set(strain);
    }

    public String getLength() {
        return length.get();
    }

    public SimpleStringProperty lengthProperty() {
        return length;
    }

    public void setLength(String length) {
        this.length.set(length);
    }

    public String getSequenceSimilarity() {
        return sequenceSimilarity.get();
    }

    public SimpleStringProperty sequenceSimilarityProperty() {
        return sequenceSimilarity;
    }

    public void setSequenceSimilarity(String sequenceSimilarity) {
        this.sequenceSimilarity.set(sequenceSimilarity);
    }
}
