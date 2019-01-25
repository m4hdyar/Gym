package com.example.m4hdyar.gym.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.m4hdyar.gym.R;
import com.example.m4hdyar.gym.utils.SharedPrefManager;
import com.example.m4hdyar.gym.models.User;
import com.example.m4hdyar.gym.fragments.BodyStateFragment;
import com.example.m4hdyar.gym.fragments.MealdietProgramFragment;
import com.example.m4hdyar.gym.fragments.ProfileFragment;
import com.example.m4hdyar.gym.fragments.SportProgramFragment;
import com.example.m4hdyar.gym.fragments.SubscriptionHistoryFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set Athlete name and Subscription name in nav
        View headerView = navigationView.getHeaderView(0);

        TextView drawerAthleteName = (TextView) headerView.findViewById(R.id.athleteNameInHeader);
        TextView drawerSubscriptionName = (TextView) headerView.findViewById(R.id.sunNameINHeader);
        //This part is for setting image in nav
//        ImageView drawerImage = (ImageView) headerView.findViewById(R.id.imageView_nav_bar);
//        Picasso.get().load("http://baamardom.ir/wp-content/uploads/2018/02/%D8%B7%D9%86%D8%A7%D8%B2-%D8%B7%D8%A8%D8%A7%D8%B7%D8%A8%D8%A7%DB%8C%DB%8C-429x400.jpg").into(drawerImage, new Callback() {
//            @Override
//            public void onSuccess() {
//                Log.e("Picasso","Succeed");
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.e("Picasso","Fail");
//                e.printStackTrace();
//
//            }
//        });
        drawerAthleteName.setText(User.athleteName+" "+User.athleteFamily);
        drawerSubscriptionName.setText(User.SubscriptionName);


        //Start program from SportProgramFragment
        Fragment fragment = new SportProgramFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Bundle bundle = new Bundle();

        if (id == R.id.nav_sport_program) {
            fragment = new SportProgramFragment();
        } else if (id == R.id.nav_meal_diet_program) {
            fragment = new MealdietProgramFragment();
        } else if (id == R.id.nav_body_state) {
            fragment = new BodyStateFragment();
        } else if (id == R.id.nav_account) {
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_subscription_history) {
            fragment = new SubscriptionHistoryFragment();
        } else if (id == R.id.nav_exit) {
            SharedPrefManager.getInstance(getApplicationContext()).logout(this);

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
