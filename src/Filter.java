/**
 * Created by Deviltech on 10.11.2015.
 */
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class Filter {

    public static ArrayList<File> finder(File clusterFile){
        String dirName = clusterFile.getParent().toString();
        String clusterName = clusterFile.getName();
        File dir = new File(dirName);

        // Get all .fa or .fasta files of directory
        File[] myFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.endsWith(".fasta"); }
        } );

        ArrayList<File> results = new ArrayList<File>();
        // Filter for files containing the cluster file name
        for (File file: myFiles){
            String clusterSub = clusterName.substring(0, clusterName.indexOf('.'));
            String fileSub = file.getName().substring(0, file.getName().indexOf('.'));
            if(clusterSub.contains(fileSub)){
               results.add(file);
            }
        }

        return results;

    }

}
