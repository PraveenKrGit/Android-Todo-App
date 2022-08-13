package com.example.todo.Model;

//defines stucture of each individual tasks
public class ToDoModel {
    private int id, status;
    private String task;

    //id = execute query based on id of the ask
    //status = boolean variable 0 for not done
    //1 for done
    //task = actual text of task

    //defining getter and setter > generate> getteer and setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
