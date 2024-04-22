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

    private MutableLiveData<Profile> profileLiveData = new MutableLiveData<>(new Profile("", ""));
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Login onCreate", "Login Screen Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        repository = WeightLogRepository.getInstance(this);
        Log.d("Login onCreate", "Repository instance created");
        //profileLiveData.observe(this, this::onChanged);
        repository.deleteProfile(new Profile("db","db"));
        EditText editTextUsernameDebug = findViewById(R.id.editTextUsername);
        editTextUsernameDebug.setText("debug");
        EditText editTextPasswordDebug = findViewById(R.id.editTextPassword);
        editTextPasswordDebug.setText("debug");

    }

    public void LoginAttempt(View view) {
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        String inputUsername = editTextUsername.getText().toString().trim();
        String inputPassword = editTextPassword.getText().toString().trim();
        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            Toast.makeText(this, "Username and password required", Toast.LENGTH_SHORT).show();
        } else {
            repository.doesProfileExist(inputUsername).observe(this, exists -> {
                if (exists) {
                    Log.d("Login attempt", "Profile exists");
                    LiveData<Profile> profileLiveData = repository.checkCredentials(inputUsername, inputPassword);
                    if (profileLiveData == null) {
                        Log.d("Login attempt", "Profile data null");
                    }
                    else {
                        profileLiveData.observe(this, profile -> {
                            if (profile == null) {
                                Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();

                            } else {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("profile", inputUsername);
                                startActivity(intent);
                            }

                        });
                    }
                } else {
                    Log.d("Login attempt", "Profile does not exist");
                    Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void SignUpAttempt(View view) {
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        String inputUsername = editTextUsername.getText().toString().trim();
        String inputPassword = editTextPassword.getText().toString().trim();
        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            Toast.makeText(this, "Username and password required", Toast.LENGTH_SHORT).show();
        } else if(inputPassword.length() < 5 ) {
            Toast.makeText(this, "Password must be longer than five characters", Toast.LENGTH_SHORT).show();
        } else {
            repository.doesProfileExist(inputUsername).observe(this, exists -> {
                if (exists) {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    repository.addProfile(new Profile(inputUsername,inputPassword));
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("profile", inputUsername);
                    startActivity(intent);
                }
            });
        }
    }
    private void onChanged(Profile profile) {
        if (Objects.equals(profile.getUsername(), "") || Objects.equals(profile.getPassword(), "")) {
            Log.d("profileLiveData onChanged", "Profile live data and variables reset");
            username = null;
            password = null;

        } else {
            Log.d("profileLiveData onChanged", "Profile live data and variables set");
            username = profile.getUsername();
            password = profile.getPassword();
        }
    }
}
