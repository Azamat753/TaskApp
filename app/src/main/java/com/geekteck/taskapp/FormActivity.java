package com.geekteck.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class FormActivity extends AppCompatActivity {
    Task task;

    private EditText editTitle;
    private EditText editdescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.editTitle);
        editdescription = findViewById(R.id.editDescription);
        getting();
    }
    public void OnClick(View view) {
        String title = editTitle.getText().toString().trim();
        String description = editdescription.getText().toString().trim();

        if (task != null) {
            task.setTitle(title);
            task.setDesc(description);
            App.getDatabase().taskDao().update(task);
        } else {
            task = new Task(title, description);
            App.getDatabase().taskDao().insert(task);


        }
        finish();

    }

    public void getting() {
        task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            editTitle.setText(task.getTitle());
            editdescription.setText(task.getDesc());

        }


    }



}


// Intent intent = new Intent();
//        intent.putExtra("title", title);
//        intent.putExtra("description", description);
//        setResult(RESULT_OK, intent);