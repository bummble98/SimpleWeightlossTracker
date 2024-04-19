package com.zybooks.simpleweightlosstracker;

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

import com.zybooks.simpleweightlosstracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "Main activity opened");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        Log.d("TAG", "Action bar set");


        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment == null) {
            // NavHostFragment not found, handle the error
            Log.e("MainActivity", "NavHostFragment not found");
            // Handle the error, maybe show an error message or finish the activity
        } else {
            // NavHostFragment found, initialize the NavController
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

            if (navController == null) {
                // NavController not found, handle the error
                Log.e("MainActivity", "NavController not found");
                // Handle the error, maybe show an error message or finish the activity
            } else {
                // NavController found, continue with your navigation setup
                // For example, setup action bar with NavController
                // NavigationUI.setupActionBarWithNavController(this, navController);
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
