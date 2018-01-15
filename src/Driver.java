import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
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
        System.out.println("Keywords to be scanned for:");
        System.out.println(words);
        System.out.println();
   
        Collection<Email> emails = EmailManager.getEmails("Emails");
        if (emails == null) {
            System.err.println("Could not parse any emails in the 'Emails' directory, aborting ...");
            System.exit(-1);
        }

        System.out.println();
        System.out.println("Emails to be scanned (duplicates removed): " + emails.size());
        System.out.println();

        // Now convert the keywords to upper case
        List<String> keywords = new ArrayList<String>(words.size());
        for (String word : words) {
             keywords.add(word.toUpperCase());
        }

        List<Email> flagged;

        Predicate<Email> matchKeywords = (email) -> {
            // Only flagging email subject for now
            String text = email.getSubject().toUpperCase();
            for (String keyword : keywords) {
            //  System.out.println("text: " + text + " word: " + keyword);
                if (text.contains(keyword)) {
                //  System.out.println("Matched: " + keyword);
                    return true;
                }
            }
            return false;
        };

        if (args.length > 1 && args[1] == "-parallel") {
            flagged = emails.parallelStream()
                            .filter(email -> matchKeywords.test(email))
                            .collect(Collectors.toList());
        } else {
            flagged = emails.stream()
                            .filter(email -> matchKeywords.test(email))
                            .collect(Collectors.toList());
        }

        System.out.println("Red-Flagged Email:");
        System.out.println(flagged);
        System.out.println();

        // Aggregate flagged authors into a list
        List<String> list = flagged.stream()
                                   .map(Email::getAuthor)
                                   .collect(Collectors.toList());
        System.out.println("Red-Flagged Email Authors: " + list);
    }
}
