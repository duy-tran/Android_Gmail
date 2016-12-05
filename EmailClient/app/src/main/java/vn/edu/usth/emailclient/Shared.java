package vn.edu.usth.emailclient;

import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Store;

/**
 * Created by duy on 12/4/16.
 */

public class Shared {
    private static Shared instance;
    private String userName = "";
    private String userEmail;
    private String userPassword;
    private Store store;
    /*
        HashMap messages
        String: Folder name
        Message[]: Array of messages in that folder
     */
    private HashMap<String, Message[]> messagesFolder = new HashMap<>();

    private HashMap<String, MailItem[]> mailItems = new HashMap<>();

    public static final int MAX_MESSAGES = 10;

    public static final String folderInbox = "Inbox";
    public static final String folderSent = "[Gmail]/Sent Mail";
    public static final String folderDraft = "[Gmail]/Drafts";
    public static final String folderSpam = "[Gmail]/Spam";
    public static final String folderTrash = "[Gmail]/Trash";

    public static HashMap<String, String> titles;
    static
    {
        titles = new HashMap<>();
        titles.put(folderInbox,"Inbox");
        titles.put(folderSent,"Sent");
        titles.put(folderDraft,"Drafts");
        titles.put(folderSpam,"Spam");
        titles.put(folderTrash,"Trash");
    }

    public static final String[] folderNames = {folderInbox,folderSent,folderDraft,folderSpam,folderTrash};

    public static Shared getInstance(){
        if (instance == null) {
            instance = new Shared();
        }
        return instance;
    }

    public MailItem[] getMailItems(String folderName) {
        return mailItems.get(folderName);
    }

    public void setMailItems(String folderName, MailItem[] mailList) {
        mailItems.put(folderName,mailList);
    }

    public Message[] getMessagesFolder(String folderName){
        if (messagesFolder.get(folderName) != null) {
            return messagesFolder.get(folderName);
        }
        return null;
    }

    public void setMessagesFolder(String folderName, Message[] messages){
        messagesFolder.put(folderName,messages);
    }

    public Authenticator getAuthenticator(){
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userEmail,userPassword);
            }
        };
    }

    public Properties sendProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        return props;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
