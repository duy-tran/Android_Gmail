package vn.edu.usth.emailclient;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import vn.edu.usth.emailclient.Fragment.FolderFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean addedFragment = false;
    boolean loaded = false;
    String currentFolder = Shared.folderInbox;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!loaded) {
            loadFolder(Shared.folderInbox);
            loaded = true;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), WriteMailActivity.class);
                getApplicationContext().startActivity(myIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_email);
        email.setText(Shared.getInstance().getUserEmail());
    }

    private void loadFolder(final String label){
        final ProgressDialog progressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.loading), true);
        progressDialog.show();
        AsyncTask<Void, Integer, Void> task = new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Store store = Shared.getInstance().getStore();
                Folder folder = null;
                try {
                    folder = store.getFolder(label);
                    if (!folder.isOpen()) {
                        folder.open(Folder.READ_WRITE);
                    }
                    int totalMessage = folder.getMessageCount();
                    int lastMessageIndex = totalMessage - Shared.MAX_MESSAGES + 1;
                    if (lastMessageIndex < 1)
                        lastMessageIndex = 1;
                    Message[] messages = folder.getMessages(lastMessageIndex, totalMessage);
                    MailItem[] items = new MailItem[messages.length];
                    for (int i = 0; i < messages.length; i++) {
                        boolean ifRead = messages[i].isSet(Flags.Flag.SEEN);
                        Object obj = messages[i].getContent();
                        String content;
                        if (obj instanceof Multipart) {
                            Multipart mp = (Multipart) messages[i].getContent();
                            BodyPart bp = mp.getBodyPart(0);
                            content = (String) bp.getContent();
                        } else {
                            content = (String) messages[i].getContent();
                        }
                        //String content = (String) ((Multipart) messages[i].getContent()).getBodyPart(0).getContent();
                        items[i] = new MailItem(messages[i].getFrom()[0].toString(),messages[i].getSubject(),
                                messages[i].getSentDate(),content,ifRead);
                    }
                    Shared.getInstance().setMailItems(label,items);
                    Shared.getInstance().setMessagesFolder(label,messages);
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void voids) {
                progressDialog.dismiss();
                addFragment(label);
            }
        };
        task.execute();
    }

    private void addFragment(String label){
        Fragment folderFragment = FolderFragment.newInstance(label);
        if (!addedFragment) {
            getFragmentManager().beginTransaction().replace(R.id.mail_list,folderFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.mail_list, folderFragment).commit();
            addedFragment = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView userName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
        userName.setText(Shared.getInstance().getUserName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
             Intent i = new Intent(MainActivity.this, SettingsActivity.class);
             startActivity(i);
        } else if (id == R.id.refresh) {
            loadFolder(currentFolder);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchTo(String label) {
        loadFolder(label);
        currentFolder = label;
        setTitle(Shared.titles.get(label));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
            switchTo(Shared.folderInbox);
        } else if (id == R.id.nav_sent) {
            switchTo(Shared.folderSent);
        } else if (id == R.id.nav_draft) {
            switchTo(Shared.folderDraft);
        } else if (id == R.id.nav_spam) {
            switchTo(Shared.folderSpam);
        } else if (id == R.id.nav_trash) {
            switchTo(Shared.folderTrash);
        } else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(this)
                    .setMessage(getResources().getString(R.string.confirmLogout))
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            getApplicationContext().startActivity(myIntent);
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel), null)
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
