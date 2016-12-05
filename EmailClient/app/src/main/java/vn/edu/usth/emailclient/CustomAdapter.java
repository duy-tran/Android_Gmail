package vn.edu.usth.emailclient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
    private Message[] messages;

    private static LayoutInflater inflater = null;

    public CustomAdapter(Context context, MailItem[] mailItems, Message[] messages) {
        // Strings is temporary, should be replaced by array of mails
        this.context = context;
        this.mailItems = mailItems;
        this.messages = messages;
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
        TextView subject;
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
        holder.subject = (TextView) rowView.findViewById(R.id.subject);
        holder.content = (TextView) rowView.findViewById(R.id.content);

        holder.sender.setText(mailItems[index].getSenderShort());
        holder.time.setText(mailItems[index].getTime());
        holder.subject.setText(mailItems[index].getSubjectShort());
        holder.content.setText(mailItems[index].getContentShort());
        if (!mailItems[index].getIfRead()) {
            holder.sender.setTypeface(holder.sender.getTypeface(), Typeface.BOLD);
            holder.subject.setTypeface(holder.subject.getTypeface(), Typeface.BOLD);
            holder.time.setTypeface(holder.time.getTypeface(), Typeface.BOLD);
            holder.sender.setTextColor(Color.parseColor("#000000"));
            holder.subject.setTextColor(Color.parseColor("#000000"));
            holder.time.setTextColor(Color.parseColor("#0000BB"));
        }

        return rowView;
    }
}
