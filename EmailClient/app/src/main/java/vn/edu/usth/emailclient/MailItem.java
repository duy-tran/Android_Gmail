package vn.edu.usth.emailclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by duy on 12/5/16.
 */

public class MailItem {
    private String sender;
    private String subject;
    private Date time;
    private String content;
    private Boolean ifRead;

    private static final int maxLengthSender = 25;
    private static final int maxLengthSubject = 40;
    private static final int maxLengthContent = 40;

    public MailItem(String sender, String subject, Date time, String content, Boolean ifRead) {
        this.sender = sender;
        this.subject = subject;
        this.time = time;
        this.content = content;
        this.ifRead = ifRead;
    }

    public Boolean getIfRead() {
        return ifRead;
    }

    public void setIfRead(Boolean ifRead) {
        this.ifRead = ifRead;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getSenderShort() {
        String result;
        int index = sender.indexOf("<");
        result = sender.substring(0,index);
        if (result.length()>maxLengthSender)
            result = result.substring(0,maxLengthSender)+"...";
        return result;
    }

    public String getSubjectShort() {
        if (subject == null)
            return "(no subject)";
        String result = subject;
        if (subject.length()>maxLengthSubject) {
            result = subject.substring(0,maxLengthSubject)+"...";
        }
        return result;
    }

    public String getTime() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date today = Calendar.getInstance().getTime();
        if (df.format(today).equals(df.format(time))) {
            DateFormat hourFormat = new SimpleDateFormat("HH:mm");
            return hourFormat.format(time);
        }
        DateFormat dayFormat = new SimpleDateFormat("dd/MM");
        return dayFormat.format(time);
    }

    public String getContentShort() {
        String result = content;
        if (content.length()>maxLengthContent) {
            result = content.substring(0, maxLengthContent) + "...";
        }
        int index = result.indexOf("\n");
        if (index>0)
            result = result.substring(0,index);
        return result;
    }
}
