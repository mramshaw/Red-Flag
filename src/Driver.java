import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Driver {

    public static void main(String[] args) {

        final boolean parallel = (args.length > 0 && args[0].equals("-parallel"));

        String ps = PostScriptManager.getPostScript("PostScript");
        if (ps == null) {
            System.err.println("Could not parse 'PostScript' file, aborting ...");
            System.exit(-1);
        }

        List<String> words = KeywordManager.getKeywords("Keywords");
        if (words == null) {
            System.err.println("Could not parse 'Keywords' file, aborting ...");
            System.exit(-1);
        }

        System.out.println();
        System.out.println("Keywords to be scanned for:");
        System.out.println(words);
        System.out.println();
   
        // Now convert the keywords to upper case & remove duplicates
        System.out.println("Keywords to be scanned for (upper-case, duplicates removed):");
        HashSet<String> keywords = new HashSet<String>(words.size());
        for (String word : words) {
             keywords.add(word.toUpperCase());
        }
        System.out.println(keywords);
        System.out.println();

        Collection<Email> emails = EmailManager.getEmails("Emails");
        if (emails == null) {
            System.err.println("Could not parse any emails in the 'Emails' directory, aborting ...");
            System.exit(-1);
        }

        System.out.println();
        System.out.println("Emails to be scanned (duplicates removed): " + emails.size());
        System.out.println();

        List<Email> flagged;

        Predicate<Email> matchKeywords = (email) -> {
            String text = email.getSubject().toUpperCase();
            for (String keyword : keywords) {
            //  System.out.println("text: " + text + " word: " + keyword);
                if (text.contains(keyword)) {
                //  System.out.println("Matched: " + keyword);
                    return true;
                }
            }
            text = email.getText().toUpperCase();
            for (String keyword : keywords) {
            //  System.out.println("text: " + text + " word: " + keyword);
                if (text.contains(keyword)) {
                //  System.out.println("Matched: " + keyword);
                    return true;
                }
            }
            return false;
        };

        long startTime = System.nanoTime();
        if (parallel) {
            flagged = emails.parallelStream()
                            .filter(email -> matchKeywords.test(email))
                            .collect(Collectors.toList());
        } else {
            flagged = emails.stream()
                            .filter(email -> matchKeywords.test(email))
                            .collect(Collectors.toList());
        }
        long streamTime = System.nanoTime() - startTime;
        // 4186615 vs. 6065363 (parallel) so parallel processing seems considerably slower for this small sample (6 emails)
        // 4065363 vs. 5863130 - another set of runs, numbers slightly different but difference similiar
        //     [It looks like the parallel process is created by forking, which is a considerable overhead.]
        System.out.println("Parsing E-Mails - Streaming method parallel: " + parallel + ", time (nanos): " + streamTime);
        System.out.println();

        System.out.println("Red-Flagged Email:");
        System.out.println("==================");
        flagged.stream()
               .forEach(email -> System.out.println(email + "\n"));
        System.out.println();

        // Aggregate flagged authors into a list
        List<String> list = flagged.stream()
                                   .map(Email::getAuthor)
                                   .collect(Collectors.toList());
        System.out.println("Red-Flagged Email Authors:");
        System.out.println("==========================");
        System.out.println(list);
        System.out.println();

/*
        System.out.println();
        System.out.println("Emails (before red-flagged emails removed):");
        System.out.println(emails);
        System.out.println();
*/

        emails.removeAll(flagged);
        System.out.println();

        startTime = System.nanoTime();
        if (parallel) {
            emails.parallelStream()
                  .forEach(email -> email.appendText(ps));
        } else {
            emails.stream()
                  .forEach(email -> email.appendText(ps));
        }
        streamTime = System.nanoTime() - startTime;
        // 206702 vs. 419706 (parallel) so parallel processing seems considerably slower for this small sample (6 emails)
        // 204046 vs. 422650 - another set of runs, numbers slightly different but difference similiar
        //     [It looks like the parallel process is created by forking, which is a considerable overhead.]
        System.out.println("Attaching PostScripts - Streaming method parallel: " + parallel + ", time (nanos): " + streamTime);
        System.out.println();

        System.out.println("Emails (after red-flagged emails removed):");
        System.out.println("==========================================");
        emails.stream()
              .forEach(email -> System.out.println(email));
        System.out.println();
    }
}
