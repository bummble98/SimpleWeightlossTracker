package com.zybooks.simpleweightlosstracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "Login Screen Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void LoginAttempt(View view) {
        Log.d("TAG", "Login Pushed");
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void SignUpAttempt(View view) {
        System.exit(0);

    }
}
