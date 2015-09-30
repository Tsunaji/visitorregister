package com.digitopolis.visitorregister;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by Bank on 9/18/2015.
 */
public class MenuActivity  extends AppCompatActivity {

    ArrayList<SurveyData> surveyDataArrayList;
    SurveyData surveyData;
    private static final String MY_PREFS = "my_prefs";
    SharedPreferences shared;
    String reponseString = "";
    RadioGroup radioGroup;
    Dialog dialog;
    String ipServer = "http://61.91.14.115";
    String path = "swappysurvey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        // Get SharedPreferences
        shared = getSharedPreferences(MY_PREFS,Context.MODE_PRIVATE);
    }

    public void eventSurvey(View view){
        Intent intent = new Intent(MenuActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void manageData(View view){
        Intent intent = new Intent(MenuActivity.this,ViewData.class);
        startActivity(intent);
    }

    public void exportCSV(){
        saveCSV();
    }

    public void exportCSVFromServer(){
        new RetrieveAsyncTask().execute();
    }

    public void selectExportMode(View view){
        dialog = new Dialog(MenuActivity.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Export(.CSV)จากอะไร");
        radioGroup = (RadioGroup) dialog.findViewById(R.id.radio_group);

        Button cancelButton = (Button) dialog.findViewById(R.id.button2);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        Button okButton = (Button) dialog.findViewById(R.id.button3);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radiobutton = (RadioButton) dialog.findViewById(radioGroup.getCheckedRadioButtonId());
                if(radiobutton.getText().toString().equals("Server")){
                    exportCSVFromServer();
                }
                else if(radiobutton.getText().toString().equals("Device")){
                    exportCSV();
                }
                else{

                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void saveCSV(){
//        File dbFile = getDatabasePath("visitorregister.sqlite");
        DBHelper dbhelper = new DBHelper(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "Survey_data_client.csv");

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
                Log.e("SSS",email);

                bytes = curCSV.getString(2).getBytes();
                String age= new String(bytes, "UTF-8");

                bytes = curCSV.getString(3).getBytes();
                String sex= new String(bytes, "UTF-8");

                bytes = curCSV.getString(4).getBytes();
                String favGameType = new String(bytes, "UTF-8");

                bytes = curCSV.getString(5).getBytes();
                String question1 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(6).getBytes();
                String question2 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(7).getBytes();
                String question3 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(8).getBytes();
                String question4 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(9).getBytes();
                String question5 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(10).getBytes();
                String question6 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(11).getBytes();
                String question7 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(12).getBytes();
                String question8 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(13).getBytes();
                String paid = new String(bytes, "UTF-8");

                bytes = curCSV.getString(14).getBytes();
                String bubble = new String(bytes, "UTF-8");

                bytes = curCSV.getString(15).getBytes();
                String likeGame = new String(bytes, "UTF-8");

                bytes = curCSV.getString(16).getBytes();
                String everPaid = new String(bytes, "UTF-8");

                bytes = curCSV.getString(17).getBytes();
                String advice = new String(bytes, "UTF-8");

                bytes = curCSV.getString(18).getBytes();
                String date = new String(bytes, "UTF-8");

                bytes = curCSV.getString(19).getBytes();
                String time = new String(bytes, "UTF-8");

                bytes = curCSV.getString(20).getBytes();
                String unique_id = new String(bytes, "UTF-8");

                String arrStr[] ={curCSV.getString(0),email,age,sex,favGameType,question1,question2,
                        question3,question4,question5,question6,question7,question8,paid,bubble,likeGame,
                        everPaid,advice,date,time,unique_id};

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

    private void saveCSVFormServer(){

        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "Servey_data_server.csv");

        try
        {
            file.createNewFile();
            JSONArray jsonArray = new JSONArray(reponseString);
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

            for(int i =0;i<jsonArray.length();i++)
            {
                //Which column you want to exprort
                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                byte[] bytes = null;
                bytes =jsonObject.getString("email").getBytes();
                String email= new String(bytes, "UTF-8");

                Log.e("ddddd",email);

                bytes =jsonObject.getString("age").getBytes();
                String age= new String(bytes, "UTF-8");

                bytes =jsonObject.getString("sex").getBytes();
                String sex=  new String(bytes, "UTF-8");

                bytes =jsonObject.getString("fav_game_type").getBytes();
                String favGameType = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("question1").getBytes();
                String question1 = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("question2").getBytes();
                String question2 = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("question3").getBytes();
                String question3 = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("question4").getBytes();
                String question4 = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("question5").getBytes();
                String question5 = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("question6").getBytes();
                String question6 = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("question7").getBytes();
                String question7 = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("question8").getBytes();
                String question8 = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("paid").getBytes();
                String paid = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("bubble_click").getBytes();
                String bubble = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("like_game").getBytes();
                String likeGame = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("ever_paid").getBytes();
                String everPaid = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("advice").getBytes();
                String advice = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("date").getBytes();
                String date = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("time").getBytes();
                String time = new String(bytes, "UTF-8");

                bytes =jsonObject.getString("unique_id").getBytes();
                String unique_id = new String(bytes, "UTF-8");
                String arrStr[] ={jsonObject.getString(SurveyData.Column.ID),email,age,sex,favGameType,question1,question2,
                        question3,question4,question5,question6,question7,question8,paid,bubble,likeGame,
                        everPaid,advice,date,time,unique_id};
                Log.e("String Json", arrStr[3]);
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("derror",sqlEx.getMessage());
        }

    }


    public void eventUpload(View view){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("Confirm");
        builder.setMessage("คุณต้องการจะ upload ข้อมูลใช่หรือไม่ ?");
        builder.setPositiveButton(getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new HttpAsyncTask().execute();
                        AlertDialog.Builder builder2 =
                                new AlertDialog.Builder(MenuActivity.this);
                        builder2.setTitle("Complete");
                        builder2.setMessage("upload ข้อมูลเรียบร้อย");
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

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String url = ipServer+"/"+path+"/"+"insert_json.php";
            Log.e("URL", url);
            try {

                //get all db data
                RetrieveAllSurveyData();

               // build json
                JSONObject jsonObject;
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < surveyDataArrayList.size(); i++) {
                    jsonObject = new JSONObject();
                    jsonObject.put("id", surveyDataArrayList.get(i).getId() + "");
                    jsonObject.put("email", surveyDataArrayList.get(i).getEmail());
                    jsonObject.put("age", surveyDataArrayList.get(i).getAge());
                    jsonObject.put("sex", surveyDataArrayList.get(i).getSex());
                    jsonObject.put("favGameType", surveyDataArrayList.get(i).getFavGameType());
                    jsonObject.put("question1", surveyDataArrayList.get(i).getQuestion1());
                    jsonObject.put("question2", surveyDataArrayList.get(i).getQuestion2());
                    jsonObject.put("question3", surveyDataArrayList.get(i).getQuestion3());
                    jsonObject.put("question4", surveyDataArrayList.get(i).getQuestion4());
                    jsonObject.put("question5", surveyDataArrayList.get(i).getQuestion5());
                    jsonObject.put("question6", surveyDataArrayList.get(i).getQuestion6());
                    jsonObject.put("question7", surveyDataArrayList.get(i).getQuestion7());
                    jsonObject.put("question8", surveyDataArrayList.get(i).getQuestion8());
                    jsonObject.put("paid", surveyDataArrayList.get(i).getPaid());
                    jsonObject.put("bubble", surveyDataArrayList.get(i).getBubbleClick());
                    jsonObject.put("likeGame", surveyDataArrayList.get(i).getLikeGame());
                    jsonObject.put("everPaid", surveyDataArrayList.get(i).getEverPaid());
                    jsonObject.put("advice", surveyDataArrayList.get(i).getAdvice());
                    jsonObject.put("date", surveyDataArrayList.get(i).getDate());
                    jsonObject.put("time", surveyDataArrayList.get(i).getTime());
                    jsonObject.put("unique_id", surveyDataArrayList.get(i).getUnique_id());
                    jsonArray.put(jsonObject);
                }

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("survey_data", jsonArray);

//                JSONObject jsonObject2 = new JSONObject();
//                jsonObject2.put("Test", "TEST");

                URL object = new URL(url);
                HttpURLConnection con = (HttpURLConnection) object.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                con.setUseCaches(false);

                //make some HTTP header nicety
//                con.setFixedLengthStreamingMode(jsonObject2.toString().getBytes().length);
                con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                con.setRequestProperty("Accept", "application/json;charset=utf-8");

                //open
                con.connect();

                //Send request
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                String data = jsonArray.toString();
                writer.write(data);
                writer.flush();
                writer.close();

                //Get Response
                InputStream is = con.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();

                Log.e("Response ", response.toString() + ", Connect status : "+con.getResponseCode());

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error send json", e.getMessage().toString());
//                Toast.makeText(getBaseContext(), "Error send json", Toast.LENGTH_LONG).show();
            }
            return "";
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data sent!", Toast.LENGTH_LONG).show();
        }
    }

    private class RetrieveAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String url = ipServer+"/"+path+"/"+"retrieve_json.php";
            Log.e("URLLLL",url);
            try {

                //get all db data
                URL object = new URL(url);
                HttpURLConnection con = (HttpURLConnection) object.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                con.setUseCaches(false);

                //make some HTTP header nicety
                con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                con.setRequestProperty("Accept", "application/json;charset=utf-8");
                //open
                con.connect();
                //Get Response
                InputStream is = con.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();

                Log.e("Response ", response.toString() + ", Connect status : "+con.getResponseCode());
//                reponseString = response.substring(1,response.length()-1);
                reponseString = response.toString();

                saveCSVFormServer();

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error send json", e.getMessage().toString());
            }
            return "";
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data sent!", Toast.LENGTH_LONG).show();
        }
    }

//    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
//        String line = "";
//        String result = "";
//        while((line = bufferedReader.readLine()) != null)
//            result += line;
//
//        inputStream.close();
//        return result;
//
//    }

//    private void RetrieveAllSurveyData(){
//        DBHelper dbhelper = new DBHelper(getApplicationContext());
//        try
//        {
////            SharedPreferences.Editor editor = shared.edit();
////            editor.clear();
////            editor.commit();
//            int number = shared.getInt("query_position", -1);
//            String query_position = (number)+"";
//            Log.e("Before position", query_position);
//            SQLiteDatabase db = dbhelper.getReadableDatabase();
//            Cursor curCSV = db.rawQuery("SELECT * FROM surveydata WHERE " + SurveyData.Column.ID + ">" + query_position + "", null);
////            Log.e("QUERY DATA ", "SELECT * FROM surveydata WHERE " + SurveyData.Column.ID + "=" + query_position + "");
//            surveyDataArrayList = new ArrayList<>();
//            String lastId= number+"";
//            while(curCSV.moveToNext())
//            {
//                //Which column you want to exprort
//                surveyData = new SurveyData();
//
//                byte[] bytes = null;
//                bytes = curCSV.getString(0).getBytes();
//                lastId = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(1).getBytes();
//                String email= new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(2).getBytes();
//                String age= new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(3).getBytes();
//                String sex= new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(4).getBytes();
//                String favGameType = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(5).getBytes();
//                String question1 = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(6).getBytes();
//                String question2 = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(7).getBytes();
//                String question3 = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(8).getBytes();
//                String question4 = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(9).getBytes();
//                String question5 = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(10).getBytes();
//                String question6 = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(11).getBytes();
//                String question7 = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(12).getBytes();
//                String question8 = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(13).getBytes();
//                String paid = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(14).getBytes();
//                String bubble = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(15).getBytes();
//                String likeGame = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(16).getBytes();
//                String everPaid = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(17).getBytes();
//                String advice = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(18).getBytes();
//                String date = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(19).getBytes();
//                String time = new String(bytes, "UTF-8");
//
//                bytes = curCSV.getString(20).getBytes();
//                String unique_id = new String(bytes, "UTF-8");
//
//                surveyData.setId(Integer.parseInt(curCSV.getString(0)));
//                surveyData.setEmail(email);
//                surveyData.setAge(age);
//                surveyData.setSex(sex);
//                surveyData.setFavGameType(favGameType);
//                surveyData.setQuestion1(question1);
//                surveyData.setQuestion2(question2);
//                surveyData.setQuestion3(question3);
//                surveyData.setQuestion4(question4);
//                surveyData.setQuestion5(question5);
//                surveyData.setQuestion6(question6);
//                surveyData.setQuestion7(question7);
//                surveyData.setQuestion8(question8);
//                surveyData.setPaid(paid);
//                surveyData.setBubbleClick(bubble);
//                surveyData.setLikeGame(likeGame);
//                surveyData.setEverPaid(everPaid);
//                surveyData.setAdvice(advice);
//                surveyData.setDate(date);
//                surveyData.setTime(time);
//                surveyData.setUnique_id(unique_id);
//
//                surveyDataArrayList.add(surveyData);
//            }
//            curCSV.close();
//            SharedPreferences.Editor editor = shared.edit();
////            editor = shared.edit();
//            editor.putInt("query_position", Integer.parseInt(lastId));
//            editor.commit();
//            Log.e("After position", lastId);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            Log.e("query error !", e.getMessage().toString());
//        }
//    }

    private void RetrieveAllSurveyData(){
        DBHelper dbhelper = new DBHelper(getApplicationContext());
        try
        {

            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM surveydata ", null);
            surveyDataArrayList = new ArrayList<>();
//            String lastId= number+"";
            while(curCSV.moveToNext())
            {
                //Which column you want to exprort
                surveyData = new SurveyData();

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
                String question1 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(6).getBytes();
                String question2 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(7).getBytes();
                String question3 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(8).getBytes();
                String question4 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(9).getBytes();
                String question5 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(10).getBytes();
                String question6 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(11).getBytes();
                String question7 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(12).getBytes();
                String question8 = new String(bytes, "UTF-8");

                bytes = curCSV.getString(13).getBytes();
                String paid = new String(bytes, "UTF-8");

                bytes = curCSV.getString(14).getBytes();
                String bubble = new String(bytes, "UTF-8");

                bytes = curCSV.getString(15).getBytes();
                String likeGame = new String(bytes, "UTF-8");

                bytes = curCSV.getString(16).getBytes();
                String everPaid = new String(bytes, "UTF-8");

                bytes = curCSV.getString(17).getBytes();
                String advice = new String(bytes, "UTF-8");

                bytes = curCSV.getString(18).getBytes();
                String date = new String(bytes, "UTF-8");

                bytes = curCSV.getString(19).getBytes();
                String time = new String(bytes, "UTF-8");

                bytes = curCSV.getString(20).getBytes();
                String unique_id = new String(bytes, "UTF-8");

                surveyData.setId(Integer.parseInt(curCSV.getString(0)));
                surveyData.setEmail(email);
                surveyData.setAge(age);
                surveyData.setSex(sex);
                surveyData.setFavGameType(favGameType);
                surveyData.setQuestion1(question1);
                surveyData.setQuestion2(question2);
                surveyData.setQuestion3(question3);
                surveyData.setQuestion4(question4);
                surveyData.setQuestion5(question5);
                surveyData.setQuestion6(question6);
                surveyData.setQuestion7(question7);
                surveyData.setQuestion8(question8);
                surveyData.setPaid(paid);
                surveyData.setBubbleClick(bubble);
                surveyData.setLikeGame(likeGame);
                surveyData.setEverPaid(everPaid);
                surveyData.setAdvice(advice);
                surveyData.setDate(date);
                surveyData.setTime(time);
                surveyData.setUnique_id(unique_id);

                surveyDataArrayList.add(surveyData);
            }
            curCSV.close();
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("query error !", e.getMessage().toString());
        }
    }
}
