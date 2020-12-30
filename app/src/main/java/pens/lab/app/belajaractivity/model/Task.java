package pens.lab.app.belajaractivity.model;

import java.io.Serializable;

import pens.lab.app.belajaractivity.base.BaseModel;

public class Task extends BaseModel implements Serializable {
    private int task_id;
    private int user_id;
    private String title;
    private String description;
    private int checked;
    private String created_at;
    private String updated_at;

    public Task(int task_id, int user_id, String title, String description, int checked, String created_at, String updated_at) {
        this.task_id = task_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.checked = checked;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(int task_id, String title, String description) {
        this.task_id = task_id;
        this.title = title;
        this.description = description;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
