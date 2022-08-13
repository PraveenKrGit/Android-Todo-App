package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todo.Adaptor.TodoAdaptor;
import com.example.todo.Model.ToDoModel;
import com.example.todo.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView taskRecyclerView;
    private TodoAdaptor taskAdaptor;
    private FloatingActionButton fab;

    private List<ToDoModel> taskList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //from here hide action bar
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        //set recycler view as linear layout
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //setting adaptor to recycler view
        taskAdaptor = new TodoAdaptor(db, this);
        taskRecyclerView.setAdapter(taskAdaptor);

        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdaptor));
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);

        //model - define structure of each task
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdaptor.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdaptor.setTasks(taskList);
        taskAdaptor.notifyDataSetChanged();
    }
}