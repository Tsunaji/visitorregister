package com.digitopolis.visitorregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by User on 9/28/2015.
 */
public class ViewDataAdapter extends ArrayAdapter<ListViewItemSection> {
    private Context context;
    private LinkedList<ListViewItemSection> items;
    private LayoutInflater vi;

    public ViewDataAdapter(Context context, LinkedList<ListViewItemSection> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final ListViewItemSection i = items.get(position);
        if (i != null) {

            ViewDataItemModel viewDataItemModel = (ViewDataItemModel) i;
            v = vi.inflate(R.layout.view_data_listview_item_activity, null);
            final TextView id = (TextView) v
                    .findViewById(R.id.view_listview_id);
            final TextView email = (TextView) v
                    .findViewById(R.id.view_listview_email);
            final TextView date = (TextView) v
                    .findViewById(R.id.view_listview_date);
            final TextView time = (TextView) v
                    .findViewById(R.id.view_listview_time);

            if (id != null)
                id.setText(viewDataItemModel.getId());
            if (email != null)
                email.setText(viewDataItemModel.getEmail());
            if (date != null)
                date.setText(viewDataItemModel.getDate());
            if (time != null)
                time.setText(viewDataItemModel.getTime());

        }
        return v;
    }
}
