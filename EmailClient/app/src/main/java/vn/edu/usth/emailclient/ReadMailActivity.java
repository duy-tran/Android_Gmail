package vn.edu.usth.emailclient;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Created by THANH on 12/4/2016.
 */

public class ReadMailActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private int key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.readmail));
        setContentView(R.layout.activity_readmail);

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.readmail_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        read();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_readmail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.refresh:

                return true;
            case R.id.delete:
                key = 1;
                read();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void read() {
        AsyncTask<String, Integer, String[]> task = new AsyncTask<String, Integer, String[]>() {
            @Override
            protected void onPreExecute() {
            }

            @Override
            protected String[] doInBackground(String... params) {
                Properties props = new Properties();
                props.setProperty("mail.store.protocol", "imaps");

                String s = null;
                String s1 = null;
                String s2 = null;
                String s3 = null;
                String s4 = null;
                Folder inbox = null;
                try {
                    Session session = Session.getInstance(props, null);
                    Store store = session.getStore();
                    store.connect("imap.gmail.com", "chuyendivote001@gmail.com", "ict12345");
                    inbox = store.getFolder("INBOX");
                    inbox.open(Folder.READ_WRITE);

                    Message msg = inbox.getMessage(inbox.getMessageCount());
                    Address[] in = msg.getFrom();


                    Address[] out = msg.getAllRecipients();
                    for (Address address : in) {
//                                System.out.println("FROM:" + address.toString());
                        s1 = address.toString();

                    }
                    for (Address address : out) {
//                                System.out.println("FROM:" + address.toString());
                        s3 = address.toString();

                    }
                    Multipart mp = (Multipart) msg.getContent();
                    s4 = msg.getSentDate().toString();
                    BodyPart bp = mp.getBodyPart(0);

//                            System.out.println("SENT DATE:" + msg.getSentDate());

                    s = msg.getSubject();
//                            subject.setText(msg.getSubject());
//                            System.out.println("SUBJECT:" + msg.getSubject());
                    s2 = (String) bp.getContent();
//                            System.out.println("CONTENT:" + bp.getContent());
                    if (key == 1) {
                        msg.setFlag(Flags.Flag.DELETED, true);
//                        inbox.close(true);
                    }

                } catch (Exception mex) {
                    mex.printStackTrace();
                }
                try {
                    inbox.close(true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                return new String[]{s, s1, s2, s3, s4};

            }

            @Override
            protected void onProgressUpdate(Integer... values) {

            }

            @Override
            protected void onPostExecute(String[] s) {
                TextView from = (TextView)findViewById(R.id.mail_from_value);
                from.setText(s[1]);

                TextView to = (TextView)findViewById(R.id.mail_to_value);
                to.setText(s[3]);

                TextView subject = (TextView) findViewById(R.id.subject_value);
                subject.setText(s[0]);

                TextView date = (TextView) findViewById(R.id.send_date);
                date.setText(s[4]);


                TextView content = (TextView) findViewById(R.id.content_value);
                content.setText(s[2]);
                content.setMovementMethod(new ScrollingMovementMethod());
                if(key==1){
                    finish();
                }
                key = 0;

            }


        };
        task.execute();

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ReadMail Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
