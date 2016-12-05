package vn.edu.usth.emailclient;

import android.app.Fragment;
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

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import vn.edu.usth.emailclient.Fragment.FolderFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int MAX_MESSAGES = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadAllFolder();

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

    private void loadAllFolder(){
        System.out.println("Loading all folder");
        AsyncTask<Void, Integer, Folder> task = new AsyncTask<Void, Integer, Folder>() {
            @Override
            protected Folder doInBackground(Void... voids) {
                Store store = Shared.getInstance().getStore();
                try {
                    return store.getFolder(Shared.folderInbox);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Folder folder) {
                Shared.getInstance().setMessagesFolder(Shared.folderInbox,folder);
                addFragment(Shared.folderInbox);
            }
        };
        task.execute();
    }

    private void addFragment(String label){
        Fragment folderFragment = FolderFragment.newInstance(label);
        getFragmentManager().beginTransaction().add(R.id.mail_list, folderFragment).commit();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
            addFragment("Inbox");
        } else if (id == R.id.nav_sent) {
            addFragment("Sent");
        } else if (id == R.id.nav_draft) {
            addFragment("Draft");
        } else if (id == R.id.nav_spam) {

        } else if (id == R.id.nav_trash) {

        } else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(this)
                    .setMessage(getResources().getString(R.string.confirmLogout))
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.logging_out), Toast.LENGTH_LONG).show();
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
