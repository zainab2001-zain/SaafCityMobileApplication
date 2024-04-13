package com.buiseness.saafcitybuiseness;

import com.buiseness.saafcitybuiseness.databinding.ActivityDashboardScreenBinding;
import com.buiseness.saafcitybuiseness.SignInActivity;

import com.buiseness.saafcitybuiseness.ui.RejectedComplaints.RejectedComplaintsFragment;
import android.content.Intent;
import android.media.metrics.LogSessionId;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;

import com.buiseness.saafcitybuiseness.ui.slideshow.SlideshowFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;



public class Dashboard_Screen extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardScreenBinding binding;
//    LogInResponse logInResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSupportActionBar(binding.appBarDashboardScreen.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_newcomplaints)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard_screen);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        NavigationView navigationView1 = findViewById(R.id.nav_view);
        navigationView1.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(menuItem -> {
            logout();
            return true;
        });

        NavigationView navigationView2 = findViewById(R.id.nav_view);
        navigationView2.getMenu().findItem(R.id.nav_newcomplaints).setOnMenuItemClickListener(menuItem -> {
            newcomplaints();
            return true;
        });


        Intent intent= getIntent();

    }

    private void rejectedcomplaints() {
        Intent intent=new Intent(Dashboard_Screen.this, SlideshowFragment.class);
        startActivity(intent);
    }

    private void newcomplaints() {
        Intent intent=new Intent(Dashboard_Screen.this, LocationScreen.class);
        startActivity(intent);
    }

    private void logout() {
        Intent intent=new Intent(Dashboard_Screen.this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
   /* public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);

        // Add click listener for Rejected Complaints menu item
        menu.findItem(R.id.nav_rejectedcomplaints).setOnMenuItemClickListener(menuItem -> {
            // Handle click event for Rejected Complaints menu item
            // Open the RejectedComplaintsFragment or perform desired actions
            Fragment fragment = new RejectedComplaintsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_home, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        });
        // Add click listener for Rejected Complaints menu item
        menu.findItem(R.id.nav_newcomplaints).setOnMenuItemClickListener(menuItem -> {
            // Handle click event for Rejected Complaints menu item
            // Open the RejectedComplaintsFragment or perform desired actions
            Fragment fragment = new RejectedComplaintsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_home, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        });
        return true;



    }
*/
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard_screen);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}