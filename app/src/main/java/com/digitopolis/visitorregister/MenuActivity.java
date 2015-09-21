package com.digitopolis.visitorregister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileWriter;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by Bank on 9/18/2015.
 */
public class MenuActivity  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
    }

    public void eventSurvey(View view){
        Intent intent = new Intent(MenuActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public  void exportCSV(View view){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("Confirm");
        builder.setMessage("คุณต้องการจะ export ข้อมูลใช่หรือไม่ ?");
        builder.setPositiveButton(getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveCSV();
                        AlertDialog.Builder builder2 =
                                new AlertDialog.Builder(MenuActivity.this);
                        builder2.setTitle("Complete");
                        builder2.setMessage("export ข้อมูลเรียบร้อย");
                        builder2.setNegativeButton(getString(android.R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
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

    private void saveCSV(){
        File dbFile = getDatabasePath("visitorregister.sqlite");
        DBHelper dbhelper = new DBHelper(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "surveydata.csv");


        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM surveydata",null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext())
            {
                //Which column you want to exprort
                byte[] bytes = null;
                bytes = curCSV.getString(1).getBytes();
                String email= new String(bytes, "UTF-8");

                bytes = curCSV.getString(2).getBytes();
                String age= new String(bytes, "UTF-8");

                bytes = curCSV.getString(3).getBytes();
                String sex= new String(bytes, "UTF-8");

                bytes = curCSV.getString(4).getBytes();
                String favGameType = new String(bytes, "UTF-8");

                bytes = curCSV.getString(5).getBytes();
                String favGameEx= new String(bytes, "UTF-8");

                bytes = curCSV.getString(6).getBytes();
                String question1 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(7).getBytes();
                String question2 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(8).getBytes();
                String question3 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(9).getBytes();
                String question4 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(10).getBytes();
                String question5 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(11).getBytes();
                String question6 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(12).getBytes();
                String question7 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(13).getBytes();
                String question8 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(14).getBytes();
                String paid = new String(bytes, "UTF-8");

                bytes = curCSV.getString(15).getBytes();
                String bubble = new String(bytes, "UTF-8");

                bytes = curCSV.getString(16).getBytes();
                String likeGame = new String(bytes, "UTF-8");

                bytes = curCSV.getString(17).getBytes();
                String everPaid = new String(bytes, "UTF-8");

                bytes = curCSV.getString(18).getBytes();
                String advice = new String(bytes, "UTF-8");

                bytes = curCSV.getString(19).getBytes();
                String date = new String(bytes, "UTF-8");

                bytes = curCSV.getString(20).getBytes();
                String time = new String(bytes, "UTF-8");

                String arrStr[] ={curCSV.getString(0),email,age,sex,favGameType,favGameEx,question1,question2,
                        question3,question4,question5,question6,question7,question8,paid,bubble,likeGame,
                        everPaid,advice,date,time};

                Log.e("TagString",arrStr[0]+" "+arrStr[1]+" "+arrStr[2]+" "+arrStr[3]+" "+arrStr[4]+" "+arrStr[5]
                        +" "+arrStr[6]+" "+arrStr[7]+" "+arrStr[8]+" "+arrStr[9]+" "+arrStr[10]+" "+arrStr[11]
                        +" "+arrStr[12]+" "+arrStr[13]+" "+arrStr[14]+" "+arrStr[15]+" "+arrStr[16]+" "+arrStr[17]
                        +" "+arrStr[18]+" "+arrStr[19]);

                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }


    public void eventUpload(View view){

    }
}
