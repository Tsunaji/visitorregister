
package com.digitopolis.visitorregister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import au.com.bytecode.opencsv.CSVWriter;

public class MainActivity extends AppCompatActivity {

    EditText email, favGameType, likeGame, everPaid, advice;
//    Spinner age;
    TextView eventNameShow;
    RadioGroup age, sex, q1, q2, q3, q4, q5, q6, q7, q8, paid, bubbleClick;
    Button submit;
    DBHelper mHelper;
    static String eventName = "";
    int ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        eventName = intent.getStringExtra("event_name");
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
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
        eventNameShow = (TextView) findViewById(R.id.survey_textview_eventname);
        eventNameShow.setText(eventName);

//        age = (Spinner) findViewById(R.id.spinner);
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.age_spiner_array, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        age.setAdapter(adapter);

        favGameType = (EditText) findViewById(R.id.editText4);
        likeGame = (EditText) findViewById(R.id.editText7);
        everPaid = (EditText) findViewById(R.id.editText8);
        advice = (EditText) findViewById(R.id.editText9);

        age = (RadioGroup) findViewById(R.id.radio_group12);
        sex = (RadioGroup) findViewById(R.id.radio_group);
        q1 = (RadioGroup) findViewById(R.id.radio_group2);
        q2 = (RadioGroup) findViewById(R.id.radio_group3);
        q3 = (RadioGroup) findViewById(R.id.radio_group4);
        q4 = (RadioGroup) findViewById(R.id.radio_group5);
        q5 = (RadioGroup) findViewById(R.id.radio_group6);
        q6 = (RadioGroup) findViewById(R.id.radio_group7);
        q7 = (RadioGroup) findViewById(R.id.radio_group8);
        q8 = (RadioGroup) findViewById(R.id.radio_group9);
        paid = (RadioGroup) findViewById(R.id.radio_group10);
        bubbleClick = (RadioGroup) findViewById(R.id.radio_group11);

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
            RadioButton radiobutton10 = (RadioButton) findViewById(paid.getCheckedRadioButtonId());
            RadioButton radiobutton11 = (RadioButton) findViewById(bubbleClick.getCheckedRadioButtonId());
            RadioButton radiobutton12 = (RadioButton) findViewById(age.getCheckedRadioButtonId());

            String Data[]={email.getText().toString(),
                    radiobutton12.getText().toString(),
                    radiobutton.getText().toString(),
                    favGameType.getText().toString(),
                    radiobutton2.getText().toString(),
                    radiobutton3.getText().toString(),
                    radiobutton4.getText().toString(),
                    radiobutton5.getText().toString(),
                    radiobutton6.getText().toString(),
                    radiobutton7.getText().toString(),
                    radiobutton8.getText().toString(),
                    radiobutton9.getText().toString(),
                    radiobutton10.getText().toString(),
                    radiobutton11.getText().toString(),
                    likeGame.getText().toString(),
                    everPaid.getText().toString(),
                    advice.getText().toString()};


            Boolean check = true;
            for(int i=0;i<Data.length;i++){
                if((Data[i]== "" || Data[i].isEmpty() || Data[i]== null)&& i!=Data.length-1){
                    Log.e("Input error !", "blank data");
                    check = false;
                }
            }

            if(check){
                saveData(Data);
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Complete");
                builder.setMessage("บันทึกข้อมูลเรียบร้อย");
                builder.setNegativeButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                intent.putExtra("event_name",eventName);
                                startActivity(intent);
                            }
                        });
                builder.show();
            }
            else{
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Error");
                builder.setMessage("กรอกข้อมูลไม่ครบ กรุณาตรวจสอบใหม่อีกครั้ง");
                builder.setNegativeButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        }
        catch (Exception e){
            Log.e("Get data error !", "data problem");
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error");
            builder.setMessage("กรอกข้อมูลไม่ครบ กรุณาตรวจสอบใหม่อีกครั้ง");
            builder.setNegativeButton(getString(android.R.string.ok),
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = dateFormat.format(c.getTime());
        String formattedTime = timeFormat.format(c.getTime());
        String android_id = Settings.Secure.getString(getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        surveyData.setEmail(data[0]);
        surveyData.setAge(data[1]);
        surveyData.setSex(data[2]);
        surveyData.setFavGameType(data[3]);
        surveyData.setQuestion1(data[4]);
        surveyData.setQuestion2(data[5]);
        surveyData.setQuestion3(data[6]);
        surveyData.setQuestion4(data[7]);
        surveyData.setQuestion5(data[8]);
        surveyData.setQuestion6(data[9]);
        surveyData.setQuestion7(data[10]);
        surveyData.setQuestion8(data[11]);
        surveyData.setPaid(data[12]);
        surveyData.setBubbleClick(data[13]);
        surveyData.setLikeGame(data[14]);
        surveyData.setEverPaid(data[15]);
        surveyData.setAdvice(data[16]);
        surveyData.setDate(formattedDate);
        surveyData.setTime(formattedTime);
        surveyData.setUnique_id(android_id);
        surveyData.setEvent_name(eventName);

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
