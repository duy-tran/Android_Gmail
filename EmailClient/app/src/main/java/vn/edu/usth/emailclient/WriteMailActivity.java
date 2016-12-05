package vn.edu.usth.emailclient;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by duy on 11/20/16.
 */

public class WriteMailActivity extends AppCompatActivity {

    Handler handler;

    private static final int SHOW_TOAST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.writemail_compose));
        setContentView(R.layout.activity_writemail);

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.writemail_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            String subject = "Fwd: " + b.getString("subject");
            String content = " Forwarded message:  " + System.getProperty ("line.separator") + System.getProperty ("line.separator") + b.getString("content");
            EditText fwsubject = (EditText)findViewById(R.id.subject);
            fwsubject.setText(subject);

            EditText fwcontent = (EditText)findViewById(R.id.content);
            fwcontent.setText(content);
        }

        Bundle b1 = getIntent().getExtras();
        if(b1!=null){
            String subject = "RE: " + b1.getString("subject");
            String receiver = b1.getString("receiver");
            String date = b1.getString("date");
            String content = System.getProperty ("line.separator") + System.getProperty ("line.separator") +"------------"+
                    System.getProperty ("line.separator")+ "On "+ date + ", " + receiver + " wrote:"
                    + System.getProperty("line.separator") + b1.getString("content");
            EditText resubject = (EditText)findViewById(R.id.subject);
            resubject.setText(subject);
            EditText re_receiver = (EditText)findViewById(R.id.receiver);
            re_receiver.setText(receiver);

            EditText recontent = (EditText)findViewById(R.id.content);
            recontent.setText(content);
        }



        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == SHOW_TOAST) {
                    String message = (String)msg.obj;
                    Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_writemail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.send) {
            String receiverEmail = ((EditText) findViewById(R.id.receiver)).getText().toString();
            String receiverCCEmail = ((EditText) findViewById(R.id.receiver_cc)).getText().toString();
            String subject = ((EditText) findViewById(R.id.subject)).getText().toString();
            String content = ((EditText) findViewById(R.id.content)).getText().toString();
            sendEmail(receiverEmail,receiverCCEmail,subject,content);
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.mail_sending),Toast.LENGTH_LONG).show();
            finish();
            return true;
        } else if (id == R.id.exit) {
            new AlertDialog.Builder(this)
                    .setMessage(getResources().getString(R.string.confirmDelete))
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel), null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void sendEmail(String receiverEmail, String receiverCCEmail, String subject, String content) {
        AsyncTask<String, Integer, Void> task = new AsyncTask<String, Integer, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                Message msg = new Message();
                msg.what = SHOW_TOAST;
                try {
                    if (send(strings[0],strings[1],strings[2],strings[3])) {
                        msg.obj = getApplicationContext().getResources().getString(R.string.mail_sent);
                    } else {
                        msg.obj = getApplicationContext().getResources().getString(R.string.mail_sent_fail);
                    }
                } catch (Exception e) {
                    msg.obj = getApplicationContext().getResources().getString(R.string.mail_sent_fail);
                }
                handler.sendMessage(msg);
                return null;
            }
        };
        task.execute(receiverEmail,receiverCCEmail,subject,content);
    }

    private boolean send(String receiverEmail, String receiverCCEmail, String subject, String content) throws Exception {
        if (!receiverEmail.equals("") && !content.equals("")) {
            Session session = Session.getInstance(Shared.getInstance().sendProperties(), Shared.getInstance().getAuthenticator());
            MimeMessage msg = new MimeMessage(session);
            InternetAddress fromEmail = new InternetAddress(Shared.getInstance().getUserEmail(), Shared.getInstance().getUserName());
            InternetAddress toEmail = new InternetAddress(receiverEmail);
            msg.setFrom(fromEmail);
            msg.setRecipient(MimeMessage.RecipientType.TO, toEmail);
            if (!receiverCCEmail.equals("")) {
                InternetAddress toCCEmail = new InternetAddress(receiverCCEmail);
                msg.setRecipient(MimeMessage.RecipientType.CC, toCCEmail);
            }
            if (!subject.equals("")) {
                msg.setSubject(subject);
            }

            Multipart multipart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(content);
            multipart.addBodyPart(bodyPart);
            msg.setHeader("X-Priority", "1");
            msg.setContent(multipart);

            Transport.send(msg);
            return true;
        }
        return false;
    }

}
