import java.util.Objects;

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
    private String body;
  
    public Email(String subject, String from, String to, String sent, String text) {
        this.subject    = subject;
        this.author     = from;
        this.recipients = to;
        this.dateTime   = sent;
        this.body       = text;
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

    public String getText() {
        return body;
    }

    public void setText(String text) {
        this.body = text;
    }

    public void appendText(String text) {
        this.body = body + text;
    }

    @Override
    public boolean equals(Object other) {

        if (other == this)             return true;
        if (other == null)             return false;

        if (other.getClass() != this.getClass())
            return false;

        Email otherEmail = (Email) other;

        if (!otherEmail.getSubject()  .equals(subject   ))
            return false;
        if (!otherEmail.getAuthor()   .equals(author    ))
            return false;
        if (!otherEmail.getRecipient().equals(recipients))
            return false;
        if (!otherEmail.getDateTime() .equals(dateTime  ))
            return false;
        if (!otherEmail.getText()     .equals(body      ))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject + author + recipients + dateTime + body);
    }

    @Override
    public String toString() {
        return "Subject: " + subject    + "\n" +
               "From:    " + author     + "\n" + 
               "To:      " + recipients + "\n" +
               "Sent:    " + dateTime   + "\n" +
               body;
    }
}
