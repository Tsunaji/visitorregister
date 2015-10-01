package com.digitopolis.visitorregister;

/**
 * Created by User on 9/28/2015.
 */
public class ViewItemEvent implements ListViewItemSection{
    private String _id;
    private String eventName;

    public ViewItemEvent(String id,String eventName){
        super();
        this.eventName = eventName;
        this._id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public boolean isSection() {
        return true;
    }
}
