import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Created by Deviltech on 10.11.2015.
 */
public class UI extends Application {


    @Override
    public void start(Stage primaryStage)  {

        // define file chooser
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for .clsr files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(myLabels.FILECHOOSER_FILE_DESCRIPTION, myLabels.FILECHOOSER_FILETYPE);
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setTitle(myLabels.FILECHOOSER_TITLE);
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        // handle null filepath
        if(selectedFile == null){
            Platform.exit();
        }

        // get fasta filepaths from current directory
        ArrayList<File> myFastas = new Filter().finder(selectedFile);

        boolean useFasta = true;

        // don't read in fasta if there is no file
        if(myFastas.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(myLabels.ALERT_INF_TITLE);
            alert.setHeaderText(null);
            alert.setContentText(myLabels.ALERT_INF_TEXT);
            useFasta = false;
            alert.showAndWait();
        }


        ArrayList<Cluster> myClusters = null;

        try {
            // read in cluster file and generate clusters with cluster entries
            myClusters = new ReadIn().ClusterFileToCluster(selectedFile, useFasta);
        } catch (FileNotFoundException e) {
            // handle read in error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(myLabels.ALERT_ERR_TITLE);
            alert.setHeaderText(null);
            alert.setContentText(myLabels.ALERT_ERR_TEXT + selectedFile.toString());

            alert.showAndWait();
            e.printStackTrace();
        }


        // Prepare user interface
        primaryStage.setTitle(myLabels.MAIN_TITLE);
        final Scene scene = new Scene(new Group(), 660, 410);
        Group sceneRoot = (Group) scene.getRoot();

        // invisible root for all clusters
        final TreeItem<ClusterEntry> ultraRoot = new TreeItem<>(new ClusterEntry(myLabels.SEQUENCE_ID,myLabels.STRAIN, myLabels.SEQUENCE_LENGTH, myLabels.SEQUENCE_SIMILARITY));

        // Column for sequence ID
        TreeTableColumn<ClusterEntry, String> sequenceIdColumn =
                new TreeTableColumn<>(myLabels.SEQUENCE_ID);
        sequenceIdColumn.setPrefWidth(150);
        sequenceIdColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterEntry, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getSequenceId())
        );

        // Column for strain
        TreeTableColumn<ClusterEntry, String> strainColumn =
                new TreeTableColumn<>(myLabels.STRAIN);
        strainColumn.setPrefWidth(240);
        strainColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterEntry, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getStrain())
        );

        // Column for sequence length
        TreeTableColumn<ClusterEntry, String> lengthColumn =
                new TreeTableColumn<>(myLabels.SEQUENCE_LENGTH);
        lengthColumn.setPrefWidth(130);
        lengthColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterEntry, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getLength())
        );

        // Column for similarity
        TreeTableColumn<ClusterEntry, String> similarityColumn =
                new TreeTableColumn<>(myLabels.SEQUENCE_SIMILARITY);
        similarityColumn.setPrefWidth(130);
        similarityColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterEntry, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getSequenceSimilarity())
        );

        // iterate over every Cluster and generate root with ClusterEntry children
        for (Cluster currentCluster : myClusters) {

            ArrayList<ClusterEntry> entries = currentCluster.getMyEntries();

            // generate root, which is also representative
            ClusterEntry representative = currentCluster.getRepresentative();
            final TreeItem<ClusterEntry> root =
                    new TreeItem<>(new ClusterEntry(representative.getSequenceId(), representative.getStrain(), representative.getLength(), representative.getSequenceSimilarity()));

            root.setExpanded(false);

            // fill root with ClusterEntry children
            entries.stream().forEach((entry) -> {
                root.getChildren().add(new TreeItem<>(entry));
            });

            // add Cluster root to invisible main root
            ultraRoot.getChildren().addAll(root);
        }

        // Prepace TreeTableView
        ultraRoot.setExpanded(true);
        TreeTableView<ClusterEntry> treeTableView = new TreeTableView<>(ultraRoot);
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().setAll(sequenceIdColumn, strainColumn, lengthColumn, similarityColumn);
        sceneRoot.getChildren().add(treeTableView);

        // Prepare and show scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
