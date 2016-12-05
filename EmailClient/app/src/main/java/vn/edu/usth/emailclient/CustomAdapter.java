package vn.edu.usth.emailclient;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Created by duy on 12/4/16.
 */

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private MailItem[] mailItems;

    private static LayoutInflater inflater = null;

    public CustomAdapter(Context context, MailItem[] mailItems) {
        // Strings is temporary, should be replaced by array of mails
        this.context = context;
        this.mailItems = mailItems;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mailItems.length;
    }

    @Override
    public Object getItem(int i) {
        return mailItems[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder {
        TextView sender;
        TextView time;
        TextView content;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int index = mailItems.length-1 - i;
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.mail_item, null);
        holder.sender = (TextView) rowView.findViewById(R.id.sender);
        holder.time = (TextView) rowView.findViewById(R.id.time_sent);
        holder.content = (TextView) rowView.findViewById(R.id.content);
        try {
            holder.sender.setText(mailItems[index].getSender());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowView;
    }
}
