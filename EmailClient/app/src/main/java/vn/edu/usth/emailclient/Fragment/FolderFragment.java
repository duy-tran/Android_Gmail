package vn.edu.usth.emailclient.Fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import javax.mail.Message;

import vn.edu.usth.emailclient.CustomAdapter;
import vn.edu.usth.emailclient.MailItem;
import vn.edu.usth.emailclient.R;
import vn.edu.usth.emailclient.ReadMailActivity;
import vn.edu.usth.emailclient.Shared;

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

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (label.equals("")){
            label = Shared.folderInbox;
        }
        System.out.println("Label = "+label);
        View v = inflater.inflate(R.layout.folder_fragment, container, false);
        final MailItem[] mailItems = Shared.getInstance().getMailItems(label);
        ListView lv = (ListView) v.findViewById(R.id.list_mail);
        lv.setAdapter(new CustomAdapter(getContext(), mailItems));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("You clicked item "+i);
                Intent mIntent = new Intent(getContext(),ReadMailActivity.class);
                mIntent.putExtra("Folder",label);
                mIntent.putExtra("Index",mailItems.length-1-i);
                startActivity(mIntent);
            }
        });
        return v;
    }
}
