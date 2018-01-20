import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PostScriptManager {

    private static String ps;

    public static String getPostScript(String fileName) {

        if (ps != null)
            return ps;

        ps = "";

        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while((line = br.readLine()) != null) {
                ps = ps + line + "\n";
            }   
            br.close();

            return ps;
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
        System.out.println("PostScript to be appended:");
        System.out.println();
        System.out.println(getPostScript("PostScript"));
    }
}
