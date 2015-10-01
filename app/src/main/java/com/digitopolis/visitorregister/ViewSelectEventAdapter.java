package com.digitopolis.visitorregister;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by User on 9/28/2015.
 */
public class ViewSelectEventAdapter extends ArrayAdapter<ListViewItemSection> {
    private Context context;
    private LinkedList<ListViewItemSection> items;
    private LayoutInflater vi;
    ViewItemEvent viewItemEvent;
    DBHelper mHelper;
    ViewEventData viewEventData;
    int positionDel;

    public ViewSelectEventAdapter(Context context, LinkedList<ListViewItemSection> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        mHelper = new DBHelper(context);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        positionDel = position;
        final ListViewItemSection i = items.get(position);

        if (i != null) {

            viewItemEvent = (ViewItemEvent) i;
            v = vi.inflate(R.layout.view_item_event, null);
            final TextView eventName = (TextView) v
                    .findViewById(R.id.event_name);
            if (eventName != null)
                eventName.setText(viewItemEvent.getEventName());
            eventName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("event_name",eventName.getText().toString());
                    context.startActivity(intent);
                }
            });
            ImageView delete = (ImageView)v.findViewById(R.id.imageView4);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
//                        Intent intent = new Intent(getContext(), ViewEventData.class);
//                        viewEventData = new ViewEventData();
//                        ViewEventData.list_item.remove(position);
                        mHelper.deleteSurveyEvent(eventName.getText().toString());
//                        getContext().startActivity(intent);
                    }catch(Exception e){
                       Log.e("Delete Error", e.getMessage().toString());
//                        if( position<ViewEventData.list_item.size()) {
//                            Log.e("Delete Error", "asdsadsad");
//                            ViewEventData.list_item.remove(position);
//                            Intent intent = new Intent(getContext(), ViewEventData.class);
//                            getContext().startActivity(intent);
//                        }
                    }
                }
            });

        }
        return v;
    }
}
