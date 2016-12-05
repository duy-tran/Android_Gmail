package vn.edu.usth.emailclient.Fragment;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import vn.edu.usth.emailclient.CustomAdapter;
import vn.edu.usth.emailclient.MailItem;
import vn.edu.usth.emailclient.R;
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
        Folder data = Shared.getInstance().getMessagesFolder(label);
        final View v = inflater.inflate(R.layout.folder_fragment, container, false);
        AsyncTask<Folder, Integer, MailItem[]> task = new AsyncTask<Folder, Integer, MailItem[]>() {
            @Override
            protected MailItem[] doInBackground(Folder... folders) {
                Folder folder = folders[0];
                try {
                    if (!folder.isOpen()) {
                        folder.open(Folder.READ_WRITE);
                    }
                    int totalMessage = folder.getMessageCount();
                    int lastMessageIndex = totalMessage - Shared.MAX_MESSAGES + 1;
                    if (lastMessageIndex < 1)
                        lastMessageIndex = 1;
                    Message[] messages = folder.getMessages(lastMessageIndex, totalMessage);
                    MailItem[] items = new MailItem[messages.length];
                    for (int i = 0; i < messages.length; i++) {
                        Multipart mp = (Multipart) messages[i].getContent();
                        BodyPart bp = mp.getBodyPart(0);
                        String content = (String) bp.getContent();
                        items[i] = new MailItem(messages[i].getFrom()[0].toString(),messages[i].getSubject(),
                                messages[i].getSentDate(),content);
                    }
                    return items;
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(MailItem[] mailItems) {
                ListView lv = (ListView) v.findViewById(R.id.list_mail);
                lv.setAdapter(new CustomAdapter(getContext(), mailItems));
            }
        };
        task.execute(data);
        return v;
    }
}
