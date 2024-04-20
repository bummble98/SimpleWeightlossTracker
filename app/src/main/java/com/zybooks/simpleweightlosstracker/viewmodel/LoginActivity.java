package com.zybooks.simpleweightlosstracker.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.zybooks.simpleweightlosstracker.R;
import com.zybooks.simpleweightlosstracker.model.Profile;
import com.zybooks.simpleweightlosstracker.repo.WeightLogDatabase;
import com.zybooks.simpleweightlosstracker.repo.WeightLogRepository;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "Login Screen Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WeightLogDatabase db = Room.databaseBuilder(getApplicationContext(),
                WeightLogDatabase.class, "WeightLogDatabase").build();

    }
    public void LoginAttempt(View view) {
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);

        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(username.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Username and password required", Toast.LENGTH_SHORT).show();
        }
        else {
            Profile loggedProfile = WeightLogRepository.getInstance(this).getProfile(username);
            if (loggedProfile == null) {
                Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
            }
            else {

                String loginVerificationUsername = loggedProfile.getUsername();
                String loginVerificationPassword = loggedProfile.getPassword();
                if (!Objects.equals(loginVerificationUsername, username) || !Objects.equals(loginVerificationPassword, password)) {
                    Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("profile", loginVerificationUsername);
                    startActivity(intent);
                }
            }
        }
    }
    public void SignUpAttempt(View view) {
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(username.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Username and password required", Toast.LENGTH_SHORT).show();
        }
        else {
            Profile existingProfile = WeightLogRepository.getInstance(this).getProfile(username);
            if (existingProfile != null) {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            } else {
                Profile profile = new Profile(username, password);

                WeightLogRepository.getInstance(this).addProfile(profile);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("profile", profile.getId());
                startActivity(intent);
            }
        }

    }
}
