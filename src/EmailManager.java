import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collection;
import java.util.HashSet;

public class EmailManager {

    private static Collection<Email> emails;

    public static Collection<Email> getEmails(String dirName) {

        if (emails != null)
            return emails;

        emails = new HashSet<Email>();

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
            //  System.out.println("hashCode '" + em.hashCode() + "'");
                if (em != null) {
                    emails.add(em);
                }
            }
        }
        System.out.println();
        System.out.println("All Emails parsed (" + fileList.length + ")");

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
        String text    = "";

        int    sIndx, fIndx, tIndx, dIndx;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while((line = br.readLine()) != null) {

                String uc = line.toUpperCase();

                sIndx = uc.indexOf(SUBJECT);
                fIndx = uc.indexOf(FROM);
                tIndx = uc.indexOf(TO);
                dIndx = uc.indexOf(DATE);
                if (sIndx == 0) {
                    subject = line.substring(SUBJECT.length()).trim();
                //    System.out.println("SUBJECT = " + subject);
                }
                else if (fIndx == 0) {
                    from = line.substring(FROM.length()).trim();
                //    System.out.println("FROM = " + from);
                }
                else if (tIndx == 0) {
                    to = line.substring(TO.length()).trim();
                //    System.out.println("TO = " + to);
                }
                else if (dIndx == 0) {
                    date = line.substring(DATE.length()).trim();
                //    System.out.println("DATE = " + date);
                }
                else {
                    text = text + line + "\n";
                //    System.out.println("TEXT = " + text);
                }
            }   
            br.close();

            return new Email(subject, from, to, date, text);
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
        Collection<Email> emails = getEmails("Emails");
        if (emails == null) {
            System.err.println("Could not parse any emails in the 'Emails' directory, aborting ...");
            System.exit(-1);
        }

        System.out.println();
        System.out.println("Emails to be scanned, duplicates removed (" + emails.size() + ")");
        System.out.println("=============================================");
        System.out.println(emails);
        System.out.println();
    }
}
