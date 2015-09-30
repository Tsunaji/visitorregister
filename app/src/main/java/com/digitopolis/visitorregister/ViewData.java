package com.digitopolis.visitorregister;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.LinkedList;

/**
 * Created by User on 9/28/2015.
 */
public class ViewData extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    LinkedList<ListViewItemSection> list_item = new LinkedList<>();
    android.widget.ListView listView = null;
    private ProgressDialog pDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data_listview_activity);

        listView = (android.widget.ListView) findViewById(R.id.view_data_listview);
        Button button = (Button) findViewById(R.id.button2);
        new ListViewBuild().execute();
        listView.setOnItemClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final ViewDataItemModel viewDataItemModel = (ViewDataItemModel) list_item.get(position);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(ViewData.this);
        builder.setTitle("Confirm");
        builder.setMessage("คุณต้องการจะ ลบ ข้อมูลใช่หรือไม่ ?");
        builder.setPositiveButton(getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder2 =
                                new AlertDialog.Builder(ViewData.this);
                        builder2.setTitle("Complete");
                        builder2.setMessage("ลบ ข้อมูลเรียบร้อย");
                        builder2.setNegativeButton(getString(android.R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        DBHelper dbHelper = new DBHelper(ViewData.this);
                                        dbHelper.deleteSurveyData(viewDataItemModel.getId());
//                                        dbHelper.deleteSurveyData("8");
                                        finish();
                                        Intent intent = new Intent(ViewData.this, ViewData.class);
                                        startActivity(intent);
                                    }
                                });
                        builder2.show();
                    }
                });
        builder.setNegativeButton(getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(ViewData.this);
        builder.setTitle("Confirm");
        builder.setMessage("คุณต้องการจะ ลบ ข้อมูลทั้งหมดใช่หรือไม่ ?");
        builder.setPositiveButton(getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder2 =
                                new AlertDialog.Builder(ViewData.this);
                        builder2.setTitle("Complete");
                        builder2.setMessage("ลบ ข้อมูลทั้งหมดเรียบร้อย");
                        builder2.setNegativeButton(getString(android.R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        DBHelper dbHelper = new DBHelper(ViewData.this);
                                        dbHelper.deleteAllSurveyData();
                                        finish();
                                        Intent intent = new Intent(ViewData.this, ViewData.class);
                                        startActivity(intent);
                                    }
                                });
                        builder2.show();
                    }
                });
        builder.setNegativeButton(getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    public class ListViewBuild extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ViewData.this);
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
                Cursor curCSV = db.rawQuery("SELECT * FROM surveydata ", null);
//                surveyDataArrayList = new ArrayList<>();
                while(curCSV.moveToNext())
                {
                    //Which column you want to exprort

                    byte[] bytes = null;

                    bytes = curCSV.getString(0).getBytes();
                    String id = new String(bytes, "UTF-8");

                    bytes = curCSV.getString(1).getBytes();
                    String email= new String(bytes, "UTF-8");

                    bytes = curCSV.getString(18).getBytes();
                    String date = new String(bytes, "UTF-8");

                    bytes = curCSV.getString(19).getBytes();
                    String time = new String(bytes, "UTF-8");

                    Log.e("DATA",id+" "+email+" "+date+" "+time);
                    list_item.add(new ViewDataItemModel(id, email, date, time));
                }
                curCSV.close();
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
            ViewDataAdapter adapter = new ViewDataAdapter(ViewData.this,
                    list_item);
//            android.widget.ListView listView1 = (android.widget.ListView) findViewById(R.id.view_data_listview);
            listView.setAdapter(adapter);
            super.onPostExecute(result);

        }

    }
}
