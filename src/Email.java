/**
 * This is a quick and dirty representation of an Email.
 *
 * Its body and attachment(s) are ignored.
 */
public class Email {

    private String subject;
    private String author;
    private String recipients;
    private String dateTime;
  
    public Email(String subject, String from, String to, String sent) {
        this.subject    = subject;
        this.author     = from;
        this.recipients = to;
        this.dateTime   = sent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRecipient() {
        return recipients;
    }

    public String[] getRecipients() {
        // The following line needs a better Regex
        return recipients.split(", ");
    }

    public int getRecipientCount() {
        // The following line needs a better Regex
        return recipients.split(", ").length;
    }

    public void setRecipient(String to) {
        this.recipients = to;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String sent) {
        this.dateTime = sent;
    }

    public String toString() {
        return "Subject: " + subject    + "\n" +
               "From:    " + author     + "\n" + 
               "To:      " + recipients + "\n" +
               "Sent:    " + dateTime;
    }
}
