package com.digitopolis.visitorregister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText email, age, favGameType, favGameEx, paid, bubbleClick, likeGame, whatPaidFor, advice;
    RadioGroup sex, q1, q2, q3, q4, q5, q6, q7, q8;
    Button submit;
    ArrayList<String> dataes = new ArrayList<>();

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

    private void InitInitial(){
        email = (EditText) findViewById(R.id.editText);
        age = (EditText) findViewById(R.id.editText2);
        favGameType = (EditText) findViewById(R.id.editText3);
        favGameEx = (EditText) findViewById(R.id.editText4);
        paid = (EditText) findViewById(R.id.editText5);
        bubbleClick = (EditText) findViewById(R.id.editText6);
        likeGame = (EditText) findViewById(R.id.editText7);
        whatPaidFor = (EditText) findViewById(R.id.editText8);
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
                    whatPaidFor.getText().toString(),
                    advice.getText().toString()};

            for(int i=0;i<Data.length;i++){
                if(Data[i]== "" && i!=Data.length-1){
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
        }
        catch (Exception e){
            Log.e("Input error !", "data null");
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
