import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Deviltech on 10.11.2015.
 */
public class ReadIn {

    // Read in a file
    public ArrayList<String> readInFile(File file) throws FileNotFoundException {

        // read in file
        BufferedReader bufferedReader = null;
        ArrayList<String> result = new ArrayList<String>();

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                result.add(text);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("File not found" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Input/Output Error:" + ex.getMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                System.out.println("Input/Output Error:" + ex.getMessage());
            }
        }

        return result;
    }

    // Generate a HashMap with Sequence Headers
    public HashMap<String, String> fastaToHashMap(File file) throws FileNotFoundException {
        HashMap<String, String> myMap = new HashMap<String, String>();

        //read in fasta file
        ArrayList<String> myArray = readInFile(file);
        for (int i = 0; i < myArray.size(); i++) {
            String current = myArray.get(i);
            if(current.startsWith(">")){
                // Key: Sequence ID
                String key = current.substring(1, current.indexOf('.'));
                // Value: strain
                String value = current.substring(current.lastIndexOf(';')+1, current.length());
                myMap.put(key, value);
            }
        }
        return myMap;
    }


    // Reads in .clsr file, finds fasta file and generates all clusters
    public ArrayList<Cluster> ClusterFileToCluster(File clsrFile, boolean useFasta) throws FileNotFoundException {
        ArrayList<Cluster> myClusters = new ArrayList<Cluster>();

        HashMap<String, String> myHashMapFamilies = new HashMap<>();

        //ignore Fasta file Strain if no file available
        if(useFasta){
            File fastaFile = new Filter().finder(clsrFile).get(0);
            myHashMapFamilies = fastaToHashMap(fastaFile);
        }

        ArrayList<String> myClusterReads = readInFile(clsrFile);
        for (int i = 0; i < myClusterReads.size(); i++) {
            // Current cluster string
            String currentClusterString = myClusterReads.get(i);
            if(currentClusterString.startsWith(">")){
                // prepare empty Cluster for ClusterEntries
                Cluster currentCluster = new Cluster();
                for(int j = i+1; j <myClusterReads.size(); j++){
                    String currentString = myClusterReads.get(j);
                    // new ClusterEntry
                    if(!currentString.startsWith(">")){
                        // initiate sequenceSimilarity
                        String sequenceSimilarity = "";
                        String length = currentString.substring(currentString.indexOf("\t")+2, currentString.indexOf("nt, >"));
                        String sequenceId = currentString.substring(currentString.indexOf(">")+1, currentString.indexOf("."));
                        String strain = "";
                        // fill strain entry with filler, if fasta file not available
                        if(useFasta){
                            strain = myHashMapFamilies.get(sequenceId);
                        } else {
                            strain = myLabels.NOT_AVAILABLE;
                        }
                        // check for representative
                        if(currentString.charAt(currentString.length()-1) == '*'){
                            // representative
                            sequenceSimilarity = myLabels.FULL_SIMILARITY;
                            ClusterEntry myClusterEntry =  new ClusterEntry(sequenceId, strain, length, sequenceSimilarity);
                            currentCluster.setRepresentative(myClusterEntry);
                        }else{
                            // no representative
                            sequenceSimilarity = currentString.substring(currentString.indexOf("at +/")+5, currentString.length());
                            ClusterEntry myClusterEntry =  new ClusterEntry(sequenceId, strain, length, sequenceSimilarity);
                            currentCluster.addEntry(myClusterEntry);
                        }
                    } else{
                        // end of ClusterEntry
                        myClusters.add(currentCluster);

                        break;
                    }
                }

            }
        }

        return myClusters;
    }




}
