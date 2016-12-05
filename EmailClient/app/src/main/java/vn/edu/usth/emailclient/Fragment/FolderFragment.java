package vn.edu.usth.emailclient.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import vn.edu.usth.emailclient.CustomAdapter;
import vn.edu.usth.emailclient.R;

/**
 * Created by duy on 12/4/16.
 */

public class FolderFragment extends android.app.Fragment {
    String label = "";

    public static FolderFragment newInstance(String label) {
        FolderFragment myFragment = new FolderFragment();
        Bundle args = new Bundle();
        args.putString("label", label);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            label = getArguments().getString("label");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] data = {"4", "2", "0"};
        if (label.equals("")){
            label = "inbox";
        }
        View v = inflater.inflate(R.layout.folder_fragment, container, false);
        ListView lv = (ListView) v.findViewById(R.id.list_mail);
        lv.setAdapter(new CustomAdapter(getContext(), data));
        Log.i("Label",label);
        return v;
    }
}
