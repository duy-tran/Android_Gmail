package vn.edu.usth.emailclient;

import java.util.Date;

/**
 * Created by duy on 12/5/16.
 */

public class MailItem {
    private String sender;
    private String subject;
    private String time;
    private String content;

    public MailItem(String sender, String subject, Date time, String content) {
        this.sender = sender;
        this.subject = subject;
        this.time = time.toString();
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
