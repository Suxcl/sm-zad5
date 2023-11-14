package com.example.zadanie3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.media.RouteListingPreference;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class TaskListActivity extends SingleFragmentActivity {




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("TaskListActivity", "onCreateSTART");
        super.onCreate(savedInstanceState);
        Log.d("TaskListActivity", "onCreateEND");
    }

    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }
}