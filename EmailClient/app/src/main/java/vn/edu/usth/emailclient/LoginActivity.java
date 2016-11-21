package vn.edu.usth.emailclient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by duy on 11/22/16.
 */

public class LoginActivity extends AppCompatActivity{
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View v){
        EditText email = (EditText)findViewById(R.id.emailaddr);
        String email_str     =  email.getText().toString();
        EditText password = (EditText)findViewById(R.id.password);
        String password_str     =  password.getText().toString();
        if (email_str.equals("") || password_str.equals("")) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.missing_info), Toast.LENGTH_LONG).show();
        } else {
            if (login_server(email_str,password_str)){
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.logging_in), Toast.LENGTH_LONG).show();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                        getApplicationContext().startActivity(myIntent);
                    }
                }, 3000);
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_info), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean login_server(String email, String password){
        return true;
    }
}
