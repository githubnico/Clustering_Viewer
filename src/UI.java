import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Deviltech on 10.11.2015.
 */
public class UI extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("clsr files (*.clsr)", "*.clsr");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setTitle("Open .clsr File");
        File selectedDirectory = fileChooser.showOpenDialog(primaryStage);
        ArrayList<File> myFastas = new Filter().finder(selectedDirectory);
        System.out.println(new ReadIn().fastaToHashMap(myFastas.get(0)).values());
        System.out.println(new ReadIn().ClusterFileToCluster(selectedDirectory).toString());



    }
}
