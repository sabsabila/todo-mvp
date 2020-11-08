package pens.lab.app.belajaractivity.utils;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.model.Task;

public class Database {
    private ArrayList<Task> tasks;
    private static Database instance;
    private static int id = 1;

    public Database(){
        tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task getTaskById(int id){
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).getId() == id)
                return tasks.get(i);
        }
        return null;
    }

    public void editById(int id, String title, String description){
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).getId() == id) {
                tasks.get(i).setTitle(title);
                tasks.get(i).setDescription(description);
            }
        }
    }

    public void addTask(String title, String desc){
        Task newTask = new Task(id, title, desc);
        tasks.add(newTask);
        id++;
    }

    public void deleteTask(int idx){
        tasks.remove(idx);
    }

    public void deleteAll(){
        tasks.clear();
    }

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }
}
