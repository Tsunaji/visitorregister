package com.digitopolis.visitorregister;

import android.provider.BaseColumns;

/**
 * Created by Bank on 9/18/2015.
 */
public class SurveyData {

    public static final String DATABASE_NAME = "visitorregister.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "surveydata";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String EMAIL = "'อีเมล'";
        public static final String AGE = "'อายุ'";
        public static final String SEX = "'เพศ'";
        public static final String FAV_GAME_TYPE = "'ประเภทเกมที่ชอบเล่นบน mobile'";
        public static final String QUESTION_1 = "'ความสนุก'";
        public static final String QUESTION_2 = "'ความยากในการเล่น'";
        public static final String QUESTION_3 = "'ความสวยงามของกราฟฟิคและ animation'";
        public static final String QUESTION_4 = "'ความเข้าใจในการใช้ UI'";
        public static final String QUESTION_5 = "'Sound BGM เข้ากับเกม'";
        public static final String QUESTION_6 = "'Sound SFX เข้ากับเกม'";
        public static final String QUESTION_7 = "'Item พิเศษช่วยในเกม สามารถช่วยได้'";
        public static final String QUESTION_8 = "'คุณอยากกลับมาเล่นเกมนี้อีกหรือไม่'";
        public static final String PAID = "'คุณจะจ่ายเงินซื้อ Item ในเกมนี้หรือไม่'";
        public static final String BUBBLE_CLICK = "'คุณกด Bubble ขณะเล่นหรือไม่ (Bubble ที่อยู่ตรงกลางจอ)'";
        public static final String LIKE_GAME = "'คุณชอบอะไรในเกมนี้มากที่สุด'";
        public static final String EVER_PAID = "'คุณเคยเติมเงินประเภทเกม Casual, Puzzle เกมหรือเปล่า / ถ้าเคย เกมอะไร / และเติมสูงสุดกี่บาท'";
        public static final String ADVICE = "'ข้อเสนอแนะ'";
        public static final String DATE = "'วัน'";
        public static final String TIME = "'เวลา'";
        public static final String UNIQUE_ID = "'unique_id'";
    }

    private int id;
    private String email;
    private String age;
    private String sex;
    private String favGameType;
    private String question1;
    private String question2;
    private String question3;
    private String question4;
    private String question5;
    private String question6;
    private String question7;
    private String question8;
    private String paid;
    private String bubbleClick;
    private String likeGame;
    private String everPaid;
    private String advice;
    private String date;
    private String time;
    private String unique_id;

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFavGameType() {
        return favGameType;
    }

    public void setFavGameType(String favGameType) {
        this.favGameType = favGameType;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }

    public String getQuestion6() {
        return question6;
    }

    public void setQuestion6(String question6) {
        this.question6 = question6;
    }

    public String getQuestion7() {
        return question7;
    }

    public void setQuestion7(String question7) {
        this.question7 = question7;
    }

    public String getQuestion8() {
        return question8;
    }

    public void setQuestion8(String question8) {
        this.question8 = question8;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getBubbleClick() {
        return bubbleClick;
    }

    public void setBubbleClick(String bubbleClick) {
        this.bubbleClick = bubbleClick;
    }

    public String getLikeGame() {
        return likeGame;
    }

    public void setLikeGame(String likeGame) {
        this.likeGame = likeGame;
    }

    public String getEverPaid() {
        return everPaid;
    }

    public void setEverPaid(String everPaid) {
        this.everPaid = everPaid;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
