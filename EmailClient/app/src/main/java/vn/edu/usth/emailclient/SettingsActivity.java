package vn.edu.usth.emailclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by duong on 05/12/2016.
 */

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);
    }


    @Override
    protected void onStart() {
        super.onStart();
        TextView textView = (TextView) findViewById(R.id.textView5);
        textView.setText(Shared.getInstance().getUserEmail());
        TextView textView2 = (TextView) findViewById(R.id.textView7);
        textView2.setText(Shared.getInstance().getUserName());
    }



}
