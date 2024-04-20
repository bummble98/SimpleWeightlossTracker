package com.zybooks.simpleweightlosstracker.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zybooks.simpleweightlosstracker.R;
import com.zybooks.simpleweightlosstracker.model.Profile;
import com.zybooks.simpleweightlosstracker.repo.WeightLogRepository;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private WeightLogRepository repository;

    private LiveData<Profile> profileLiveData = new MutableLiveData<>(new Profile("", ""));
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Login onCreate", "Login Screen Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        repository = WeightLogRepository.getInstance(this);
        Log.d("Login onCreate", "Repository instance created");

        profileLiveData.observe(this, profile -> {
            if (profile != null) {
                Log.d("Login onCreate", "Profile live data changed");
                username = profile.getUsername();
                password = profile.getPassword();
            }
        });
    }


    public void LoginAttempt(View view) {
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        String inputUsername = editTextUsername.getText().toString().trim();
        String inputPassword = editTextPassword.getText().toString().trim();
        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            Toast.makeText(this, "Username and password required", Toast.LENGTH_SHORT).show();
        } else {
            profileLiveData = repository.getProfile(inputUsername);
            profileLiveData.observe(this, profile -> {
                if (profile != null) {
                    Log.d("LoginAttempt", "Profile live data changed");
                    username = profile.getUsername();
                    password = profile.getPassword();
                    Log.d("LogInAttempt", "Search for profile complete");
                    Log.d("LogInAttempt", "inputUsername = " + inputUsername);
                    Log.d("LogInAttempt", "inputPassword = " + inputPassword);
                    Log.d("LogInAttempt", "Username = " + username);
                    Log.d("LogInAttempt", "Password = " + password);
                    if (!Objects.equals(username, inputUsername) || !Objects.equals(password, inputPassword)) {
                        Log.d("LogInAttempt", "Incorrect Credentials: Credentials don't match");
                        Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("profile", username);
                        startActivity(intent);
                    }
                }
                else {
                    Log.d("LogInAttempt", "Incorrect Credentials: Username null");
                    Log.d("LogInAttempt", "Search for profile complete");
                    Log.d("LogInAttempt", "inputUsername = " + inputUsername);
                    Log.d("LogInAttempt", "inputPassword = " + inputPassword);
                    Log.d("LogInAttempt", "Username = " + username);
                    Log.d("LogInAttempt", "Password = " + password);
                    username = null;
                    password = null;
                    Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void SignUpAttemptDebug(View view) {
        Log.d("SignUpAttempt", "Signup Debug attempt");


        String usernameDebug = "dom";
        String passwordDebug = "pass";
        Log.d("SignUpAttempt", "Database instance set");
        if(repository.doesProfileExist(usernameDebug)){
            Log.d("SignUpAttempt", "Profile does not exist");
        }
        else {
            Log.d("SignUpAttempt", "Profile does exist");
        }
        profileLiveData = repository.getProfile(usernameDebug);
        Log.d("SignUpAttempt", "Search for profile complete");
        Log.d("SignUpAttempt", "Username = " + username);
        Log.d("SignUpAttempt", "Password = " + password);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("profile", "username");
        Log.d("SignUpAttempt", "Starting main activity");
        startActivity(intent);

    }
    public void SignUpAttempt(View view) {
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(username.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Username and password required", Toast.LENGTH_SHORT).show();
            Log.d("SignUpAttempt", "Login Fields empty");

        }
        else {
            Log.d("SignUpAttempt", "Login Fields not empty");

            profileLiveData = repository.getProfile(username);
            Log.d("SignUpAttempt", "Search for profile complete");
            Log.d("SignUpAttempt", "Username = " + username);
            Log.d("SignUpAttempt", "Password = " + password);

            if (password != null) {
                Log.d("SignUpAttempt", "Username already exists");
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("SignUpAttempt", "Profile does not exist");

                Profile profile = new Profile(username, password);

                repository.addProfile(profile);
                Log.d("SignUpAttempt", "Profile added");

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("profile", username);
                Log.d("SignUpAttempt", "Starting main activity");
                startActivity(intent);
            }
        }

    }
}
