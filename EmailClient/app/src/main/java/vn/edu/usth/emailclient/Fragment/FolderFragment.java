package vn.edu.usth.emailclient.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.usth.emailclient.R;

/**
 * Created by duy on 12/4/16.
 */

public class FolderFragment extends Fragment {
    public FolderFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.folder_fragment, container, false);
        return v;
    }
}
