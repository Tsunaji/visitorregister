
package com.digitopolis.visitorregister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import au.com.bytecode.opencsv.CSVWriter;

public class MainActivity extends AppCompatActivity {

    EditText email, age, favGameType, favGameEx, paid, bubbleClick, likeGame, everPaid, advice;
    RadioGroup sex, q1, q2, q3, q4, q5, q6, q7, q8;
    Button submit;
    DBHelper mHelper;
    int ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitInitial();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("โปรดตรวจสอบข้อมูลให้ครบถ้วน");
                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GetData();
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
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Log.e("Test", "Back button pressed!");
        }
        else if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            Log.e("Test", "Home button pressed!");
        }
        return super.onKeyDown(keyCode, event);
    }

    private void InitInitial(){

        mHelper = new DBHelper(this);

        email = (EditText) findViewById(R.id.editText);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);

        age = (EditText) findViewById(R.id.editText2);
        favGameType = (EditText) findViewById(R.id.editText3);
        favGameEx = (EditText) findViewById(R.id.editText4);
        paid = (EditText) findViewById(R.id.editText5);
        bubbleClick = (EditText) findViewById(R.id.editText6);
        likeGame = (EditText) findViewById(R.id.editText7);
        everPaid = (EditText) findViewById(R.id.editText8);
        advice = (EditText) findViewById(R.id.editText9);

        sex = (RadioGroup) findViewById(R.id.radio_group);
        q1 = (RadioGroup) findViewById(R.id.radio_group2);
        q2 = (RadioGroup) findViewById(R.id.radio_group3);
        q3 = (RadioGroup) findViewById(R.id.radio_group4);
        q4 = (RadioGroup) findViewById(R.id.radio_group5);
        q5 = (RadioGroup) findViewById(R.id.radio_group6);
        q6 = (RadioGroup) findViewById(R.id.radio_group7);
        q7 = (RadioGroup) findViewById(R.id.radio_group8);
        q8 = (RadioGroup) findViewById(R.id.radio_group9);

        submit = (Button) findViewById(R.id.button);
    }

    private void GetData() {
        try{
            RadioButton radiobutton = (RadioButton) findViewById(sex.getCheckedRadioButtonId());
            RadioButton radiobutton2 = (RadioButton) findViewById(q1.getCheckedRadioButtonId());
            RadioButton radiobutton3 = (RadioButton) findViewById(q2.getCheckedRadioButtonId());
            RadioButton radiobutton4 = (RadioButton) findViewById(q3.getCheckedRadioButtonId());
            RadioButton radiobutton5 = (RadioButton) findViewById(q4.getCheckedRadioButtonId());
            RadioButton radiobutton6 = (RadioButton) findViewById(q5.getCheckedRadioButtonId());
            RadioButton radiobutton7 = (RadioButton) findViewById(q6.getCheckedRadioButtonId());
            RadioButton radiobutton8 = (RadioButton) findViewById(q7.getCheckedRadioButtonId());
            RadioButton radiobutton9 = (RadioButton) findViewById(q8.getCheckedRadioButtonId());

            String Data[]={email.getText().toString(),
                    age.getText().toString(),
                    radiobutton.getText().toString(),
                    favGameType.getText().toString(),
                    favGameEx.getText().toString(),
                    radiobutton2.getText().toString(),
                    radiobutton3.getText().toString(),
                    radiobutton4.getText().toString(),
                    radiobutton5.getText().toString(),
                    radiobutton6.getText().toString(),
                    radiobutton7.getText().toString(),
                    radiobutton8.getText().toString(),
                    radiobutton9.getText().toString(),
                    paid.getText().toString(),
                    bubbleClick.getText().toString(),
                    likeGame.getText().toString(),
                    everPaid.getText().toString(),
                    advice.getText().toString()};


            Boolean check = true;
            for(int i=0;i<Data.length;i++){
                if(Data[i]== "" && i!=Data.length-1){
                    Log.e("Input error !", "blank data");
                    check = false;
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("กรอกข้อมูลไม่ครบ กรุณาตรวจสอบใหม่อีกครั้ง");
                    builder.setNegativeButton(getString(android.R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }
            }

            if(check){
                saveData(Data);
            }
        }
        catch (Exception e){
            Log.e("Get data error !", "data problem");
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error");
            builder.setMessage("กรอกข้อมูลไม่ครบ กรุณาตรวจสอบใหม่อีกครั้ง");
            builder.setNegativeButton(getString(android.R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.show();
        }
    }

    private void saveData(String[] data) {

        SurveyData surveyData = new SurveyData();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = dateFormat.format(c.getTime());
        String formattedTime = timeFormat.format(c.getTime());

        surveyData.setEmail(data[0]);
        surveyData.setAge(data[1]);
        surveyData.setSex(data[2]);
        surveyData.setFacvGameType(data[3]);
        surveyData.setFavGameEx(data[4]);
        surveyData.setQuestion1(data[5]);
        surveyData.setQuestion2(data[6]);
        surveyData.setQuestion3(data[7]);
        surveyData.setQuestion4(data[8]);
        surveyData.setQuestion5(data[9]);
        surveyData.setQuestion6(data[10]);
        surveyData.setQuestion7(data[11]);
        surveyData.setQuestion8(data[12]);
        surveyData.setPaid(data[13]);
        surveyData.setBubbleClick(data[14]);
        surveyData.setLikeGame(data[15]);
        surveyData.setEverPaid(data[16]);
        surveyData.setAdvice(data[17]);
        surveyData.setDate(formattedDate);
        surveyData.setTime(formattedTime);
        if (ID == -1) {
            mHelper.addSurveyData(surveyData);
        } else {
            surveyData.setId(ID);
            //mHelper.updateFriend(friend);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
