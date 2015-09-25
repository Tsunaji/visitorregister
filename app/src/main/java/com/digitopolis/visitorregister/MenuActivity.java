package com.digitopolis.visitorregister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
//        File dbFile = getDatabasePath("visitorregister.sqlite");
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
        new HttpAsyncTask().execute();
    }

//    public static String POST(String url, ArrayList<SurveyData> surveyDataArrayList){
//        InputStream inputStream = null;
//        String result = "";
//        try {
//
//            // 1. create HttpClient
//            HttpClient httpclient = new DefaultHttpClient();
//            URLConnection urlConnection = ;
//            // 2. make POST request to the given URL
//            HttpPost httpPost = new HttpPost(url);
//
//            String json = "";
//
//            // 3. build jsonObject
//            JSONObject jsonObject;
//            JSONArray jsonArray = new JSONArray();
//            for(int i=0;i<surveyDataArrayList.size();i++){
//                jsonObject = new JSONObject();
//                jsonObject.put("id", surveyDataArrayList.get(i).getId()+"");
//                jsonObject.put("email", surveyDataArrayList.get(i).getEmail());
//                jsonObject.put("age", surveyDataArrayList.get(i).getAge());
//                jsonObject.put("sex", surveyDataArrayList.get(i).getSex());
//                jsonObject.put("favGameType", surveyDataArrayList.get(i).getFavGameType());
//                jsonObject.put("favGameEx", surveyDataArrayList.get(i).getFavGameEx());
//                jsonObject.put("question1", surveyDataArrayList.get(i).getQuestion1());
//                jsonObject.put("question2", surveyDataArrayList.get(i).getQuestion2());
//                jsonObject.put("question3", surveyDataArrayList.get(i).getQuestion3());
//                jsonObject.put("question4", surveyDataArrayList.get(i).getQuestion4());
//                jsonObject.put("question5", surveyDataArrayList.get(i).getQuestion5());
//                jsonObject.put("question6", surveyDataArrayList.get(i).getQuestion6());
//                jsonObject.put("question7", surveyDataArrayList.get(i).getQuestion7());
//                jsonObject.put("question8", surveyDataArrayList.get(i).getQuestion8());
//                jsonObject.put("paid", surveyDataArrayList.get(i).getPaid());
//                jsonObject.put("bubble", surveyDataArrayList.get(i).getBubbleClick());
//                jsonObject.put("likeGame", surveyDataArrayList.get(i).getLikeGame());
//                jsonObject.put("everPaid", surveyDataArrayList.get(i).getEverPaid());
//                jsonObject.put("advice", surveyDataArrayList.get(i).getAdvice());
//                jsonObject.put("date", surveyDataArrayList.get(i).getDate());
//                jsonObject.put("time", surveyDataArrayList.get(i).getTime());
//
//                jsonArray.put(jsonObject);
//            }
//
//            // 4. convert JSONObject to JSON to String
//            JSONObject jsonObject1 = new JSONObject();
//            jsonObject1.put("survey_data", jsonArray);
//
//
//            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
//            // ObjectMapper mapper = new ObjectMapper();
//            // json = mapper.writeValueAsString(person);
//
//            // 5. set json to StringEntity
//            StringEntity se = new StringEntity(jsonObject1);
//
//            // 6. set httpPost Entity
//            httpPost.setEntity(se);
//
//            // 7. Set some headers to inform server about the type of the content
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
//
//            // 8. Execute POST request to the given URL
//            HttpResponse httpResponse = httpclient.execute(httpPost);
//
//            // 9. receive response as inputStream
//            inputStream = httpResponse.getEntity().getContent();
//
//            // 10. convert inputstream to string
//            if(inputStream != null)
//                result = convertInputStreamToString(inputStream);
//            else
//                result = "Did not work!";
//
//        } catch (Exception e) {
//            Log.d("InputStream", e.getLocalizedMessage());
//        }
//
//        // 11. return result
//        return result;
//    }

//    public boolean isConnected(){
//        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected())
//            return true;
//        else
//            return false;
//    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        StringBuilder sb = new StringBuilder();
        DataOutputStream printout;
        @Override
        protected String doInBackground(String... urls) {
            String url = "http://192.168.1.2/insert_json.php";
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
                    jsonObject.put("favGameEx", surveyDataArrayList.get(i).getFavGameEx());
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
                    jsonArray.put(jsonObject);
                }

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("survey_data", jsonArray);

                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("Test", "TEST");

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
                String data = jsonObject1.toString();
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

    private void RetrieveAllSurveyData(){
        DBHelper dbhelper = new DBHelper(getApplicationContext());
        try
        {
            String id = SurveyData.Column.ID;
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM surveydata WHERE "+id+">3",null);
            surveyDataArrayList = new ArrayList<>();
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

                surveyData.setId(Integer.parseInt(curCSV.getString(0)));
                surveyData.setEmail(email);
                surveyData.setAge(age);
                surveyData.setSex(sex);
                surveyData.setFavGameType(favGameType);
                surveyData.setFavGameEx(favGameEx);
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
