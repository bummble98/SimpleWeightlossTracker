package com.zybooks.simpleweightlosstracker.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.zybooks.simpleweightlosstracker.R;
import com.zybooks.simpleweightlosstracker.databinding.ActivityMainBinding;
import com.zybooks.simpleweightlosstracker.model.Profile;
import com.zybooks.simpleweightlosstracker.repo.WeightLogDatabase;
import com.zybooks.simpleweightlosstracker.repo.WeightLogRepository;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "Main activity opened");
        com.zybooks.simpleweightlosstracker.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WeightLogDatabase db = Room.databaseBuilder(getApplicationContext(),
                WeightLogDatabase.class, "WeightLogDatabase").build();
        String username = getIntent().getStringExtra("username");
        Profile profile = WeightLogRepository.getInstance(this).getProfile(username);
        setSupportActionBar(binding.toolbar);
        Log.d("TAG", "Actionbar set");

        if (navHostFragment == null) {
            Log.e("MainActivity", "NavHostFragment not found");
        } else {
            // NavHostFragment found, initialize the NavController
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            if (navController == null) {
                // NavController not found
                Log.e("MainActivity", "NavController not found");
            }
            Log.d("TAG", "Nav controller found and set");
            appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
