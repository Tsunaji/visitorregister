package com.digitopolis.visitorregister;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.LinkedList;

/**
 * Created by User on 9/28/2015.
 */
public class ViewEventData extends AppCompatActivity implements  View.OnClickListener {
    public LinkedList<ListViewItemSection> list_item = new LinkedList<>();
    public android.widget.ListView listView = null;
    private ProgressDialog pDialog;
    EditText editText;
    DBHelper mHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHelper = new DBHelper(this);
        Log.e("asdfdfas click", " " + list_item.size());
        setContentView(R.layout.select_event_dialog);

        editText = (EditText)findViewById(R.id.editText5);
        listView = (android.widget.ListView) findViewById(R.id.listViewEvent);
        listView.setItemsCanFocus(true);
        ImageView button = (ImageView) findViewById(R.id.imageView3);
        button.setOnClickListener(this);
        new ListViewBuild().execute();

    }

    @Override
    public void onClick(View v) {
        if (!editText.getText().toString().equals("")) {
            list_item.add(new ViewItemEvent("new", editText.getText().toString()));
            ViewSelectEventAdapter adapter = new ViewSelectEventAdapter(ViewEventData.this, list_item);
            listView.setAdapter(adapter);
        }
    }

    public class ListViewBuild extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ViewEventData.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (isCancelled()) {
                return null;
            }
            try
            {
                DBHelper dbhelper = new DBHelper(getApplicationContext());
                SQLiteDatabase db = dbhelper.getReadableDatabase();
                Cursor cur = db.rawQuery("SELECT event_name FROM surveydata GROUP BY event_name", null);

                while(cur.moveToNext())
                {
                    byte[] bytes = null;

                    bytes = cur.getString(0).getBytes();
                    String name = new String(bytes, "UTF-8");

                    Log.e("DATA"," "+name);

                    list_item.add(new ViewItemEvent(name,name));
                }
                cur.close();
            }
            catch(Exception e){
                e.printStackTrace();
                Log.e("query error !", e.getMessage().toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (pDialog.isShowing())
                pDialog.dismiss();
            ViewSelectEventAdapter adapter = new ViewSelectEventAdapter(ViewEventData.this, list_item);
            listView.setAdapter(adapter);

//            list_item.add(new ViewItemEvent("new","test"));
            super.onPostExecute(result);

        }
    }

    public void UpdateAdapter(int i){
        list_item.remove(i);
        ViewSelectEventAdapter adapter = new ViewSelectEventAdapter(ViewEventData.this, list_item);
        listView.setAdapter(adapter);
    }
}
