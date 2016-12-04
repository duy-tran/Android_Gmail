package vn.edu.usth.emailclient;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by duy on 12/4/16.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    String[] strings;

    private static LayoutInflater inflater = null;

    public CustomAdapter(Context context, String[] strings) {
        // Strings is temporary, should be replaced by array of mails
        this.context = context;
        this.strings = strings;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class Holder {
        TextView sender;
        TextView time;
        TextView content;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.mail_item, null);
        holder.sender = (TextView) rowView.findViewById(R.id.sender);
        holder.time = (TextView) rowView.findViewById(R.id.time_sent);
        holder.content = (TextView) rowView.findViewById(R.id.content);
        holder.sender.setText(strings[i]);
        return rowView;
    }
}
