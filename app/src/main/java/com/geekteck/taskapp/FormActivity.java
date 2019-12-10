package com.geekteck.taskapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.geekteck.taskapp.ui.home.HomeFragment;

public class FormActivity extends AppCompatActivity {
     private EditText editTitle;
     private EditText editdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle= findViewById(R.id.editTitle);
        editdescription= findViewById(R.id.editDescription);

    }
    public void OnClick(View view) {
        String title = editTitle.getText().toString().trim();
        String description= editdescription.getText().toString().trim();


        Intent intent  = new Intent();
        intent.putExtra("title",title);
        intent.putExtra("description",description);
        setResult(RESULT_OK,intent);
        finish();
    }
}
