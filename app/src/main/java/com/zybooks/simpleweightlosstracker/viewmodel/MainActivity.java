package com.zybooks.simpleweightlosstracker.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.zybooks.simpleweightlosstracker.R;
import com.zybooks.simpleweightlosstracker.databinding.ActivityMainBinding;
import com.zybooks.simpleweightlosstracker.model.Profile;
import com.zybooks.simpleweightlosstracker.model.Weight;
import com.zybooks.simpleweightlosstracker.repo.WeightLogDatabase;
import com.zybooks.simpleweightlosstracker.repo.WeightLogRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Thread chartSetupBackgroundThread;
    private LiveData<Profile> profileLiveData;
    private String username;
    private String password;
    private MutableLiveData<List<Weight>> weightsLiveData = new MutableLiveData<>();
    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "Main activity opened");
        com.zybooks.simpleweightlosstracker.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Log.d("MainActivity", "Actionbar set");


        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        Log.d("MainActivity", "NavHostFragment set");

        if (navHostFragment instanceof NavHostFragment) {
            NavController navController = ((NavHostFragment) navHostFragment).getNavController();
            Log.d("TAG", "Nav controller found and set");
            appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            Log.d("TAG", "AppBar set");
        } else {
            Log.e("MainActivity", "NavHostFragment not found");
        }


        WeightLogRepository repository = WeightLogRepository.getInstance(this);
        String passedUsername = getIntent().getStringExtra("profile");
        profileLiveData = repository.getProfile(passedUsername);
        profileLiveData.observe(this, profile -> {
            if (profile != null) {
                Log.d("Login onCreate", "Profile live data changed");
                username = profile.getUsername();
                password = profile.getPassword();
            }
        });



        weightsLiveData.observe(MainActivity.this, weights -> {
            // TODO: 4/19/2024 set up chart functionality

            TextView lbsTillGoalTextView = findViewById(R.id.lbs_till_goal_details);
            lbsTillGoalTextView.setText(username);
            TextView currentGoalTextView = findViewById(R.id.current_goal_details);
            currentGoalTextView.setText(password);


        });
        profileLiveData = WeightLogRepository.getInstance(this).getProfile(username);
        //chartSetupTask(username);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d("TAG", "On-create options finished");
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_weights_action) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void chartSetupTask(String username) {
        chartSetupBackgroundThread = new Thread(() -> {

            WeightLogDatabase db = Room.databaseBuilder(getApplicationContext(),
                    WeightLogDatabase.class, "WeightLogDatabase").build();
            weightsLiveData = (MutableLiveData<List<Weight>>) db.weightDao().getWeights(username);

        });
        chartSetupBackgroundThread.start();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (chartSetupBackgroundThread != null) {
            chartSetupBackgroundThread.interrupt();
        }
    }

}
