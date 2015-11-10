import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Deviltech on 10.11.2015.
 */
public class ReadIn {

    // Read in a file
    public ArrayList<String> readInFile(File file) throws FileNotFoundException {
        StringBuilder stringBuffer = new StringBuilder();
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
        ArrayList<String> myArray = readInFile(file);
        for (int i = 0; i < myArray.size(); i++) {
            String current = myArray.get(i);
            if(current.startsWith(">")){
                String key = current.substring(1, current.indexOf('.'));
                String value = current.substring(current.lastIndexOf(';')+1, current.length());
                myMap.put(key, value);
            }
        }
        return myMap;
    }


    // Reads in clsr file, finds fasta file and generates all clusters
    public ArrayList<Cluster> ClusterFileToCluster(File clsrFile) throws FileNotFoundException {
        ArrayList<Cluster> myClusters = new ArrayList<Cluster>();
        File fastaFile = new Filter().finder(clsrFile).get(0);
        HashMap<String, String> myHashMapFamilies = fastaToHashMap(fastaFile);

        ArrayList<String> myClusterReads = readInFile(clsrFile);
        for (int i = 0; i < myClusterReads.size(); i++) {
            String currentClusterString = myClusterReads.get(i);
            if(currentClusterString.startsWith(">")){
                Cluster currentCluster = new Cluster();
                for(int j = i+1; j <myClusterReads.size(); j++){
                    String currentString = myClusterReads.get(j);
                    // new ClusterEntry
                    if(!currentString.startsWith(">")){
                        String length = currentString.substring(currentString.indexOf("\t")+2, currentString.indexOf("nt, >"));
                        String sequenceId = currentString.substring(currentString.indexOf(">")+1, currentString.indexOf("."));
                        String sequenceSimilarity = "";
                        if(currentString.charAt(currentString.length()-1) == '*'){
                            sequenceSimilarity = "100.00%";
                        }else{
                            sequenceSimilarity = currentString.substring(currentString.indexOf("at +/")+5, currentString.length());
                        }
                        String strain = myHashMapFamilies.get(sequenceId);
                        ClusterEntry myClusterEntry =  new ClusterEntry(sequenceId, strain, length, sequenceSimilarity);
                        currentCluster.addEntry(myClusterEntry);
                    } else{
                        // end of ClusterEntry
                        myClusters.add(currentCluster);


                        break;
                    }
                }

            }
        }

        //TODO

        return myClusters;
    }




}
