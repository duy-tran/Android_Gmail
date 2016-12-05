package vn.edu.usth.emailclient;

import android.app.ProgressDialog;
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
import android.widget.Toast;

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
    private int delete;
    private int move;
    private String r_subject = null;
    private String r_adr = null;
    private String r_content = null;
    private String r_recipients = null;
    private String r_date = null;
    private Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_readmail);

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.readmail_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        //ab.setDisplayHomeAsUpEnabled(true);
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
            case R.id.up:
                System.out.println("Press back button");
                ReadMailActivity.this.onBackPressed();
                return true;
            case R.id.move:
                move = 1;
//                read();
                finish();
                return true;
            case R.id.delete:
                delete = 1;
//                read();
                finish();
                return true;
            case R.id.forward:
                Intent intent = new Intent(ReadMailActivity.this, WriteMailActivity.class);
                b.putString("type", "fw");
                b.putString("receiver", r_adr.toString() );
                b.putString("content", r_content.toString());
                b.putString("subject", r_subject.toString());
                b.putString("date", r_date.toString());
                intent.putExtras(b);
                startActivity(intent);
                finish();
                return true;
            case R.id.reply:
                Intent intent1 = new Intent(ReadMailActivity.this, WriteMailActivity.class);
                Bundle b = new Bundle();
                b.putString("type", "re");
                b.putString("receiver", r_adr.toString() );
                b.putString("content", r_content.toString());
                b.putString("subject", r_subject.toString());
                b.putString("date", r_date.toString());
                intent1.putExtras(b);
                startActivity(intent1);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void read() {
        Toast.makeText(getApplicationContext(),getResources().getString(R.string.loading),Toast.LENGTH_SHORT).show();
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
                    inbox = Shared.getInstance().getStore().getFolder(folder);
                    inbox.open(Folder.READ_WRITE);

                    Message msg  = Shared.getInstance().getMessagesFolder(folder)[index];
                    MailItem mailItem = Shared.getInstance().getMailItems(folder)[index];

                    Address[] in = msg.getFrom();

                    Address[] out = msg.getAllRecipients();
                    for (Address address : in) {
                        r_adr = address.toString();

                    }
                    for (Address address : out) {
                        r_recipients = address.toString();
                    }
                    r_subject = mailItem.getSubject();
                    r_content = mailItem.getContent();
                    r_date = mailItem.getDate().toString();
                    if (delete == 1) {
                        msg.setFlag(Flags.Flag.DELETED, true);
                    }
                    Folder dfolder = Shared.getInstance().getStore().getFolder("[Gmail]/Spam");
                    if (move ==1){
                        if (!dfolder.exists())
                            dfolder.create(Folder.HOLDS_MESSAGES);
                        inbox.copyMessages(new Message[]{msg}, dfolder);
                        msg.setFlag(Flags.Flag.DELETED, true);
                    }
                } catch (Exception mex) {
                    mex.printStackTrace();
                }
//                try {
//                    inbox.close(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                return new String[]{r_subject, r_adr, r_content, r_recipients, r_date};

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
                if(delete==1 || move ==1){
                    finish();
                }
                delete = 0;
                move = 0;

            }


        };
        task.execute();

    }

    private void delete(){
        Folder inbox = null;
        Intent mIntent  = getIntent();
        String folder = mIntent.getStringExtra("Folder");
        int index = mIntent.getIntExtra("Index",0);
        try {
            inbox = Shared.getInstance().getStore().getFolder(folder);
            inbox.open(Folder.READ_WRITE);
            Message msg  = Shared.getInstance().getMessagesFolder(folder)[index];
            msg.setFlag(Flags.Flag.DELETED, true);
            inbox.close(true);
        } catch (Exception e){

        }
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
