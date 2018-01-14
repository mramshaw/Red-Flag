import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeywordManager {

    private static List<String> words;

    public static List<String> getKeywords(String fileName) {

        if (words != null)
            return words;

        words = new ArrayList<String>();

        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // Ignore first three lines
            br.readLine();
            br.readLine();
            br.readLine();

            while((line = br.readLine()) != null) {
                words.add(line.trim());
            }   
            br.close();

            Collections.sort(words, (a, b) -> a.compareTo(b));
            return words;
        }
        catch(FileNotFoundException fnfe) {
            System.err.println("Unable to open file '" + fileName + "'");                
            return null;
        }
        catch(IOException ioe) {
            System.err.println("Error reading file '" + fileName + "'");                  
            return null;
        }
    }

    public static void main(String[] args) {

        System.out.println();
        System.out.println("Keywords to be scanned for:");
        System.out.println();
        System.out.println(getKeywords("Keywords"));
    }
}
