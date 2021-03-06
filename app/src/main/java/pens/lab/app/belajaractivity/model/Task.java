package pens.lab.app.belajaractivity.model;

public class Task {
    private int task_id;
    private int user_id;
    private String title;
    private String description;
    String due_date;
    private int checked;
    private String created_at;
    private String updated_at;
    private String alarm_time;

    public Task(int task_id, int user_id, String title, String description, String due_date, int checked, String created_at, String updated_at, String alarm_time) {
        this.task_id = task_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.checked = checked;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.alarm_time = alarm_time;
    }

    public Task(String title, String description, String due_date) {
        this.title = title;
        this.description = description;
        this.due_date = due_date;
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

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
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

    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }
}
