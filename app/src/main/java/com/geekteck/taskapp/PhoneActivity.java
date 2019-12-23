package com.geekteck.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

public class PhoneActivity extends AppCompatActivity {
    EditText editNumber;
    EditText editCode;
    private  String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        editNumber = findViewById(R.id.editNumber);
        editCode = findViewById(R.id.editCode);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("TAG", "onVerificationCompleted: ");
                singIn(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("TAG", "onVerificationCompleted:" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken ) {
                super.onCodeSent(s, forceResendingToken );


                    Log.d("TAG", "onCodeSent:" + s);


                mVerificationId = s;
                mResendToken = forceResendingToken;
            }
        };
    }

    private void singIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                callbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    public void OnClick(View view) {
        LinearLayout numberField = findViewById(R.id.number);
        LinearLayout code = findViewById(R.id.code);

        numberField.setVisibility(View.INVISIBLE);
        code.setVisibility(View.VISIBLE);


        String number = editNumber.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber
                (number, 60,
                TimeUnit.SECONDS, this, callbacks);

    }

    public void OnCode(View view) {

//        String code = editCode.getText().toString();
//        if (TextUtils.isEmpty(code)) {
//            editCode.setError("Cannot be empty.");
//            return;
//        }

        resendVerificationCode(editCode.getText().toString(),mResendToken);

    }
}
