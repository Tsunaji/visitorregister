package com.digitopolis.visitorregister;

/**
 * Created by User on 9/28/2015.
 */
public class ViewDataItemModel implements ListViewItemSection{
    private final String id;
    private final String email;
    private final String date;
    private final String time;

    public ViewDataItemModel(String id, String email, String date, String time){
        super();
        this.id = id;
        this.email = email;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public boolean isSection() {
        return true;
    }
}
