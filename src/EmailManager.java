import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//import java.util.Arrays;
import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashSet;
import java.util.List;
//import java.util.stream.Collectors;

public class EmailManager {

    private static List<Email> emails;

    public static List<Email> getEmails(String dirName) {

        if (emails != null)
            return emails;

        emails = new ArrayList<Email>();

        File curDir = new File(dirName);

        File[] fileList = curDir.listFiles();

        if (fileList == null) {
            System.err.println("Directory '" + dirName + "' has no emails, returning 'null'");
            return null;
        }

        for (File f : fileList) {
            if (f.isFile()) {
                String e = dirName + "/" + f.getName();
                System.out.println("Parsing email '" + e + "'");
                Email em = parseFile(e);
                if (em != null) {
                    emails.add(em);
                }
            }
        }
        System.out.println();
        System.out.println("All Emails parsed");
        System.out.println();

/*
        List<Email> emails = Arrays.asList(email1, email2, email3, email4);
        int total = emails.stream()
                .collect(Collectors.summingInt(Email::getRecipientCount));
        System.out.println("Recipients count = " + total);
        
        //create a list with duplicates
        List<Email> dupEmails = Arrays.asList(email1, email2, email3, email4, email1, email2);
        System.out.println("Before removing duplicates: ");
        System.out.println("===========================");
        System.out.println(dupEmails.toString());
        
        Collection<Email> noDups = new HashSet<>(dupEmails);
        System.out.println("After removing duplicates: ");
        System.out.println("===========================");
        System.out.println(noDups.toString());
*/
        return emails;
    }

    private static Email parseFile(String fileName) {

        String line;

        String SUBJECT = "Subject: ".toUpperCase();
        String FROM    = "From: "   .toUpperCase();
        String TO      = "To: "     .toUpperCase();
        String DATE    = "Date: "   .toUpperCase();

        String subject = "";
        String from    = "";
        String to      = "";
        String date    = "";

        int    sIndx, fIndx, tIndx, dIndx;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while((line = br.readLine()) != null) {

                line  = line.toUpperCase();

                sIndx = line.indexOf(SUBJECT);
                fIndx = line.indexOf(FROM);
                tIndx = line.indexOf(TO);
                dIndx = line.indexOf(DATE);
                if (sIndx == 0) {
                    subject = line.substring(SUBJECT.length()).trim();
                //    System.out.println("SUBJECT = " + subject);
                }
                if (fIndx == 0) {
                    from = line.substring(FROM.length()).trim();
                //    System.out.println("FROM = " + from);
                }
                if (tIndx == 0) {
                    to = line.substring(TO.length()).trim();
                //    System.out.println("TO = " + to);
                }
                if (dIndx == 0) {
                    date = line.substring(DATE.length()).trim();
                //    System.out.println("DATE = " + date);
                }
            }   
            br.close();

            return new Email(subject, from, to, date);
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
        List<Email> emails = getEmails("Emails");
        if (emails == null) {
            System.err.println("Could not parse any emails in the 'Emails' directory, aborting ...");
            System.exit(-1);
        }

        System.out.println("Emails to be scanned (" + emails.size() + ")");
        System.out.println(emails);
        System.out.println();
    }
}
