package com.example.zadanie3;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        UUID taskId = (UUID)getIntent().getSerializableExtra(TaskListFragment.KEY_EXTRA_TASK_ID);
        Log.d("MainActivity", "creating taskID");
        return TaskFragment.newInstance(taskId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreateSTART");
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreateEND");
    }
}