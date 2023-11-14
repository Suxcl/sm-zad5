package com.example.zadanie3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;




public class TaskStorage {
    private static TaskStorage instance = new TaskStorage();
    private List<Task> tasks;


    private TaskStorage() {
        final int tasksCount = 50;
        tasks = new ArrayList<Task>(tasksCount);

        for (int i = 1; i <= tasksCount; ++i) {
            Task task = new Task();
            task.setName("nrnrnrnrnrnrnrnrnrnrnrnrnrnrnrnrnr" + i);
            task.setDone(i % 3 == 0);

            if(i % 3 == 0){
                task.setCategory(Category.STUDIA);
            } else {
                task.setCategory(Category.DOM);
            }
            tasks.add(task);
        }

    }


    public static TaskStorage getInstance() {
        return instance;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTask(UUID id) {
        for (Task task : tasks) {
            if (task.getId().equals(id))
                return task;
        }
        return null;
    }
}

