package com.geekteck.taskapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {
    Task task;

    private EditText editTitle;
    private EditText editDescription;
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        taskId=FirebaseAuth.getInstance().getUid();
        getting();
        getInfo();
    }

    public void getInfo() {
        FirebaseFirestore.getInstance().collection("task").document(taskId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    String title = documentSnapshot.getString("title");
                    String description = documentSnapshot.getString("description");
                }
            }
        });
    }

    public void OnClick(View view) {
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();

        final Map<String, Object> map = new HashMap<>();

        map.put("title", title);
        map.put("description", description);
        FirebaseFirestore.getInstance().collection
                ("task").document(taskId).
                set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                if (task.isSuccessful()) {
                    Toaster.show("Успешно");
                } else {
                    Toaster.show("Ошибка");
                }
            }
        });

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
            editDescription.setText(task.getDesc());

        }
    }

}
// Intent intent = new Intent();
//        intent.putExtra("title", title);
//        intent.putExtra("description", description);
//        setResult(RESULT_OK, intent);