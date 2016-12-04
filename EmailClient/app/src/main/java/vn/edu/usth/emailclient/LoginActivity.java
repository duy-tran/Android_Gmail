package vn.edu.usth.emailclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Created by duy on 11/22/16.
 */

public class LoginActivity extends AppCompatActivity{
    private String userEmail;
    private String userPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View v) throws Exception{
        userEmail =  ((EditText)findViewById(R.id.emailaddr)).getText().toString();
        userPassword = ((EditText)findViewById(R.id.password)).getText().toString();
        Shared.getInstance().setUserName(((EditText)findViewById(R.id.user_name)).getText().toString());
        if (userEmail.equals("") || userPassword.equals("")) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.missing_info), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.logging_in), Toast.LENGTH_LONG).show();
            if (login()){
                Shared.getInstance().setUserEmail(userEmail);
                Shared.getInstance().setUserPassword(userPassword);
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                getApplicationContext().startActivity(myIntent);
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_info), Toast.LENGTH_LONG).show();
            }
        }
    }


    private boolean login() throws Exception{
        AsyncTask<Void, Integer, Boolean> task = new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                Properties props = new Properties();
                props.setProperty("mail.store.protocol", "imaps");
                Session session = Session.getInstance(props,null);
                Store store = null;
                try {
                    store = session.getStore();
                }
                catch (NoSuchProviderException e) {
                    e.printStackTrace();
                }
                try {
                    store.connect("imap.gmail.com",userEmail,userPassword);
                    Shared.getInstance().setStore(store);
                    return true;
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                return false;
            }
        };
        return task.execute().get();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
