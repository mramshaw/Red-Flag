import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Driver {

    public static void main(String[] args) {

        List<String> words = KeywordManager.getKeywords("Keywords");
        if (words == null) {
            System.err.println("Could not parse 'Keywords' file, aborting ...");
            System.exit(-1);
        }

        System.out.println();
        System.out.println("Keywords to be scanned for: " + words);
        System.out.println();
   
        List<Email> emails = EmailManager.getEmails("Emails");
        if (emails == null) {
            System.err.println("Could not parse any emails in the 'Emails' directory, aborting ...");
            System.exit(-1);
        }

        System.out.println("Emails to be scanned (" + emails.size() + ")");
        System.out.println(emails);
        System.out.println();

        // Aggregate authors into a list
        List<String> list = emails.stream()
                                  .map(Email::getAuthor)
                                  .collect(Collectors.toList());
        System.out.println("Email Authors: " + list);

        /*

        List<Email> emails = Arrays.asList(email1, email2, email3, email4);
        int total = emails.stream()
                .collect(Collectors.summingInt(Email::getRecipientCount));
        System.out.println("Recipients count = " + total);
        
        */
    }
}
