package pens.lab.app.belajaractivity.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import pens.lab.app.belajaractivity.base.BaseModel;

public class Task extends BaseModel implements Serializable {
    private int id;
    private String title;
    private String description;
    private String date;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Task(int id, String title, String description, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
