package vn.edu.usth.emailclient.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import vn.edu.usth.emailclient.CustomAdapter;
import vn.edu.usth.emailclient.R;

/**
 * Created by duy on 12/4/16.
 */

public class FolderFragment extends Fragment {
    String label = "";

    public FolderFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (label.equals("")) {
            String[] data = {"4", "2", "0"};
            View v = inflater.inflate(R.layout.folder_fragment, container, false);
            ListView lv = (ListView) v.findViewById(R.id.list_mail);
            lv.setAdapter(new CustomAdapter(getContext(), data));
            return v;
        }
        return null;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
