package com.example.zadanie3;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.logging.Logger;

public class TaskListFragment extends Fragment {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    public static final String KEY_EXTRA_TASK_ID = "tasklistfragment.task_id";
    private static final String TAG = "TaskListFragment";
    public TaskListFragment() {}

    private boolean subtitleVisible = false;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateViewStart");
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        //setHasOptionsMenu(true);
        this.setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d(TAG, "onCreateViewEnd");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateStart");
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        Log.d(TAG, "onCreateEND");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOMstart");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_menu2, menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if(subtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
        Log.d(TAG, "onCreateOMEND");
    }

    public void updateSubtitle(){
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();
        int todoTaskCount = 0;
        for(Task task:tasks){
            if(!task.isDone()){
                todoTaskCount++;
            }
        }


        String subtitle = (String.valueOf(todoTaskCount)+" "+getString(R.string.subtitle_format));
        if(!subtitleVisible){
            subtitle = null;
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setSubtitle(subtitle);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, String.valueOf(item.getItemId()));
        if(item.getItemId() == R.id.new_task){
            Task task = new Task();
            TaskStorage.getInstance().addTask(task);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(TaskListFragment.KEY_EXTRA_TASK_ID, task.getId());
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.show_subtitle){
            subtitleVisible = !subtitleVisible;
            getActivity().invalidateOptionsMenu();
            updateSubtitle();
            return true;
        }

        else{
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }



    private void updateView() {
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();

        if (adapter == null) {
            adapter = new TaskAdapter(tasks);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
            updateSubtitle();
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTextView, dateTextView;
        private ImageView catImageView;
        private CheckBox checkBoxDone;
        private Task task;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);

            nameTextView = itemView.findViewById(R.id.task_item_name);
            dateTextView = itemView.findViewById(R.id.task_item_date);
            catImageView = itemView.findViewById(R.id.catImageView);
            checkBoxDone = itemView.findViewById(R.id.checkBoxDone);


            // przekreślanie na bieżąco tekstu
            checkBoxDone.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(!nameTextView.getPaint().isStrikeThruText()){
                        nameTextView.setPaintFlags(nameTextView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                        dateTextView.setPaintFlags(dateTextView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    }else{
                        nameTextView.setPaintFlags(nameTextView.getPaintFlags() &~ Paint.STRIKE_THRU_TEXT_FLAG);
                        dateTextView.setPaintFlags(dateTextView.getPaintFlags() &~ Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            });

        }

        public void bind(Task task) {
            this.task = task;

            if(task.isDone()){
                checkBoxDone.setChecked(true);
                nameTextView.setPaintFlags(nameTextView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                dateTextView.setPaintFlags(dateTextView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else{
                checkBoxDone.setChecked(false);
                nameTextView.setPaintFlags(nameTextView.getPaintFlags() &~ Paint.STRIKE_THRU_TEXT_FLAG);
                dateTextView.setPaintFlags(dateTextView.getPaintFlags() &~ Paint.STRIKE_THRU_TEXT_FLAG);
            }
            nameTextView.setText(task.getName());
            dateTextView.setText(task.getDate().toString());

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(KEY_EXTRA_TASK_ID, task.getId());
            startActivity(intent);
        }

        public CheckBox getCheckBox() {
            return checkBoxDone;
        }



    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            CheckBox checkBox = holder.getCheckBox();
            checkBox.setChecked(tasks.get(position).isDone());
            checkBox.setOnCheckedChangeListener(((buttonView, isChecked) ->
                    tasks.get(holder.getBindingAdapterPosition()).setDone(isChecked)));

            Task task = tasks.get(position);
            holder.bind(task);
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }
}
