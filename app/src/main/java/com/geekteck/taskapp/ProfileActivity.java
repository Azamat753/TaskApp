package com.geekteck.taskapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editEmail;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        userId = FirebaseAuth.getInstance().getUid();

        getInfo2();
    }

    public void getInfo2() {
        FirebaseFirestore.getInstance().collection("users").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    String name = documentSnapshot.getString("name");
                    String email = documentSnapshot.getString("email");
                }
            }
        });

    }

//    private void getInfo() {
//        FirebaseFirestore.getInstance()
//                .collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    String name = task.getResult().getString("name");
//                    editName.setText(name);
//                }
//            }
//        });
//    }

    public void OnSaveProfile(View view) {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        final Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);

        SharedPreferences.Editor editor = getSharedPreferences("settings",MODE_PRIVATE).edit();
        editor.putString("name",name);
        editor.putString("email",email);
        editor.apply();

        Intent intent = new Intent();
        intent.putExtra("name",name);
        intent.putExtra("email",email);
        setResult(RESULT_OK,intent);
        finish();



        FirebaseFirestore.getInstance().collection
                ("users").document(userId).
                set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toaster.show("Успешно");
                } else {
                    Toaster.show("Ошибка");

                }
            }
        });

    }
}
