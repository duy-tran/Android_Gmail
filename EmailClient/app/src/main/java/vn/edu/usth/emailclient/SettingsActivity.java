package vn.edu.usth.emailclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.security.AccessController.getContext;
import static vn.edu.usth.emailclient.Shared.getInstance;

/**
 * Created by duong on 05/12/2016.
 */

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);
        setTitle(getResources().getString(R.string.action_settings));
        EditText editText = (EditText) findViewById(R.id.changeName);
        editText.setText(Shared.getInstance().getUserName());
    }


    @Override
    protected void onStart() {
        super.onStart();
        Button submit= (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view)
            {
                EditText edit =  (EditText) findViewById(R.id.changeName);
                String newName = edit.getText().toString();
                Shared.getInstance().setUserName(newName);
//                TextView textView = (TextView) findViewById(R.id.textView5);
//                textView.setText("Current name: " + getInstance().getUserName());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.changed_name), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }






}
