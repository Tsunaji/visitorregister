package com.digitopolis.visitorregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bank on 9/17/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

        private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, SurveyData.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e("pro", "create");

        String CREATE_TEST_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT," +
                        " %s EMAIL,%s AGE,%s SEX,%s FAV_GAME_TYPE," +
                        " %s QUESTION_1,%s QUESTION_2,%s QUESTION_3," +
                        " %s QUESTION_4,%s QUESTION_5,%s QUESTION_6,%s QUESTION_7,%s QUESTION_8," +
                        " %s PAID,%s BUBBLE_CLICK,%s LIKE_GAME,%s EVER_PAID,%s ADVICE,%s DATE,%s TIME, %s UNIQUE_ID)",
                SurveyData.TABLE,
                SurveyData.Column.ID,
                SurveyData.Column.EMAIL,
                SurveyData.Column.AGE,
                SurveyData.Column.SEX,
                SurveyData.Column.FAV_GAME_TYPE,
                SurveyData.Column.QUESTION_1,
                SurveyData.Column.QUESTION_2,
                SurveyData.Column.QUESTION_3,
                SurveyData.Column.QUESTION_4,
                SurveyData.Column.QUESTION_5,
                SurveyData.Column.QUESTION_6,
                SurveyData.Column.QUESTION_7,
                SurveyData.Column.QUESTION_8,
                SurveyData.Column.PAID,
                SurveyData.Column.BUBBLE_CLICK,
                SurveyData.Column.LIKE_GAME,
                SurveyData.Column.EVER_PAID,
                SurveyData.Column.ADVICE,
                SurveyData.Column.DATE,
                SurveyData.Column.TIME,
                SurveyData.Column.UNIQUE_ID
        );

        Log.e("dfghujik", CREATE_TEST_TABLE);

        db.execSQL(CREATE_TEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + SurveyData.TABLE;

        db.execSQL(DROP_FRIEND_TABLE);

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);
    }

   /* public List<String> getSurveyDataList() {
        List<String> surveyStr = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (SurveyData.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {

            surveyStr.add(cursor.getLong(0) + " " +
                    cursor.getString(1) + " ");

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return surveyStr;
    }*/

    public void deleteSurveyData(String id){
        Log.e("ID INDEX DELETE", id);
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("surveydata", SurveyData.Column.ID+" = "+id, null);
        sqLiteDatabase.close();
    }

    public void deleteAllSurveyData(){
        sqLiteDatabase = this.getWritableDatabase();
        String sql = String.format("DELETE FROM surveydata");
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void addSurveyData(SurveyData surveyData) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Friend.Column.ID, friend.getId());
        values.put(SurveyData.Column.EMAIL, surveyData.getEmail());
        values.put(SurveyData.Column.AGE, surveyData.getAge());
        values.put(SurveyData.Column.SEX, surveyData.getSex());
        values.put(SurveyData.Column.FAV_GAME_TYPE, surveyData.getFavGameType());
        values.put(SurveyData.Column.QUESTION_1, surveyData.getQuestion1());
        values.put(SurveyData.Column.QUESTION_2, surveyData.getQuestion2());
        values.put(SurveyData.Column.QUESTION_3, surveyData.getQuestion3());
        values.put(SurveyData.Column.QUESTION_4, surveyData.getQuestion4());
        values.put(SurveyData.Column.QUESTION_5, surveyData.getQuestion5());
        values.put(SurveyData.Column.QUESTION_6, surveyData.getQuestion6());
        values.put(SurveyData.Column.QUESTION_7, surveyData.getQuestion7());
        values.put(SurveyData.Column.QUESTION_8, surveyData.getQuestion8());
        values.put(SurveyData.Column.PAID, surveyData.getPaid());
        values.put(SurveyData.Column.BUBBLE_CLICK, surveyData.getBubbleClick());
        values.put(SurveyData.Column.LIKE_GAME, surveyData.getLikeGame());
        values.put(SurveyData.Column.EVER_PAID, surveyData.getEverPaid());
        values.put(SurveyData.Column.ADVICE, surveyData.getAdvice());
        values.put(SurveyData.Column.DATE, surveyData.getDate());
        values.put(SurveyData.Column.TIME, surveyData.getTime());
        values.put(SurveyData.Column.UNIQUE_ID, surveyData.getUnique_id());
        Log.e("Client insert db status", "Success !");
        sqLiteDatabase.insert(SurveyData.TABLE, null, values);

        sqLiteDatabase.close();
    }

}
