package vn.edu.usth.emailclient;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

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
    private int move;
    private String s = null;
    private String s1 = null;
    private String s2 = null;
    private String s3 = null;
    private String s4 = null;

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
            case R.id.move:
                move = 1;
                read();
                finish();
                return true;
            case R.id.delete:
                key = 1;
                read();
                finish();
                return true;
            case R.id.forward:
                Intent intent = new Intent(ReadMailActivity.this, WriteMailActivity.class);
                Bundle b = new Bundle();
                b.putString("content", s2.toString());
                b.putString("subject", s.toString());
                intent.putExtras(b);
                startActivity(intent);
                finish();
                return true;
            case R.id.reply:
                Intent intent1 = new Intent(ReadMailActivity.this, WriteMailActivity.class);
                Bundle b1 = new Bundle();
                b1.putString("receiver", s1.toString() );
                b1.putString("content", s2.toString());
                b1.putString("subject", s.toString());
                b1.putString("date", s4.toString());
                intent1.putExtras(b1);
                startActivity(intent1);
                finish();
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

                Intent mIntent  = getIntent();
                String folder = mIntent.getStringExtra("Folder");
                int index = mIntent.getIntExtra("Index",0);

                Folder inbox = null;
                try {
//                    Session session = Session.getInstance(props, null);
//                    Store store = session.getStore();
//                    store.connect("imap.gmail.com", Shared.getInstance().getUserEmail(), Shared.getInstance().getUserPassword());
                    inbox = Shared.getInstance().getStore().getFolder(folder);
                    inbox.open(Folder.READ_WRITE);

                    Message msg  = Shared.getInstance().getMessagesFolder(folder)[index];
                    MailItem mailItem = Shared.getInstance().getMailItems(folder)[index];

                    //Message msg = inbox.getMessage(inbox.getMessageCount());
                    Address[] in = msg.getFrom();


                    Address[] out = msg.getAllRecipients();
                    for (Address address : in) {
//                                System.out.println("FROM:" + address.toString());
                        s1 = address.toString();

                    }
                    for (Address address : out) {
//
                        s3 = address.toString();

                    }
//                    Multipart mp = (Multipart) msg.getContent();
                    s4 = mailItem.getDate().toString();
//                    BodyPart bp = mp.getBodyPart(0);

//                            System.out.println("SENT DATE:" + msg.getSentDate());

//                    s = msg.getSubject();
                    s = mailItem.getSubject();
//                            subject.setText(msg.getSubject());
//                            System.out.println("SUBJECT:" + msg.getSubject());
//                    s2 = (String) bp.getContent();
                    s2 = mailItem.getContent();
//                            System.out.println("CONTENT:" + bp.getContent());
                    if (key == 1) {
                        msg.setFlag(Flags.Flag.DELETED, true);
//                        inbox.close(true);
                    }
                    if (move ==1){
                        Folder dfolder = Shared.getInstance().getStore().getFolder("[Gmail]/Spam");
                        if (!dfolder.exists())
                            dfolder.create(Folder.HOLDS_MESSAGES);
                        inbox.copyMessages(new Message[]{msg}, dfolder);
                        msg.setFlag(Flags.Flag.DELETED, true);
                    }
                    move = 0;
                    key = 0;
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
