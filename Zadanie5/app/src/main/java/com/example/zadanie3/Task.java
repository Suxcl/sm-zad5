package com.example.zadanie3;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID id;
    private String name;
    private Date date;
    private boolean done;

    private Category category;
    public Task(){
        id = UUID.randomUUID();
        this.category = Category.DOM;
        date=new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String toString) {
        this.name = toString;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDone(boolean isChecked) {
        this.done=isChecked;
    }

    public boolean isDone() {
        return this.done;
    }

    public UUID getId() {
        return this.id;
    }

    public void setCategory(Category val) {
        this.category = val;
    }
    public Category getCategory(){
        return  this.category;
    }

    public void setDate(Date time) {
        this.date = time;
    }

}
