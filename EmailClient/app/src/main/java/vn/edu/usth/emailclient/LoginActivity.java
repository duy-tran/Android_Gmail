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
    public String userEmail;
    public String userPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View v) throws Exception{
        EditText email = (EditText)findViewById(R.id.emailaddr);
        String email_str     =  email.getText().toString();
        EditText password = (EditText)findViewById(R.id.password);
        String password_str     =  password.getText().toString();
        if (email_str.equals("") || password_str.equals("")) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.missing_info), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.logging_in), Toast.LENGTH_LONG).show();
            if (login_server(email_str,password_str)){
                Log.i("login check","LOGGED IN!!!!");
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_info), Toast.LENGTH_LONG).show();
            }
        }
    }



    private boolean login_server(final String email, final String password) throws Exception{
        AsyncTask<String, Integer, Boolean> task = new AsyncTask<String, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
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
                    store.connect("imap.gmail.com",email,password);
                    userEmail = email;
                    userPassword = password;
                    return true;
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                return false;
            }
        };
        return task.execute(email,password).get();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
