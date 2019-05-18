package com.example.finalproject.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.finalproject.AboutUsDialog.AboutUsFragment;
import com.example.finalproject.ManagerTrip.ManageTrip;
import com.example.finalproject.R;
import com.example.finalproject.RecyclerView.AdapterTrips;
import com.example.finalproject.RecyclerView.RecyclerViewActivity;
import com.example.finalproject.RecyclerView.Trips;
import com.example.finalproject.SwitchColor.Settings;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerTrips extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerViewTripsNavi;
    RecyclerView.LayoutManager layoutManager;
    private List<Trips> mTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_trips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add your gorgeous trip", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(NavigationDrawerTrips.this, ManageTrip.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();
        setTrips();
        setLayoutManager();
        setAdapter();
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
        getMenuInflater().inflate(R.menu.navigation_drawer_trips, menu);
        return true;
    }

    @Override   //Three dots Setting menu
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(NavigationDrawerTrips.this, Settings.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.allTrips) {
            startActivity(new Intent(NavigationDrawerTrips.this, RecyclerViewActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "link carte aplicatie cand o sa fie lansata");
            sendIntent.setType("text/plain");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }
        }else if (id == R.id.about_us) {
            aboutUs();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setAdapter() {
        AdapterTrips tripsAdapter = new AdapterTrips(mTrips);
        mRecyclerViewTripsNavi.setAdapter(tripsAdapter);
    }

    public void aboutUs() {
        DialogFragment newFragment = new AboutUsFragment();
        newFragment.show(getSupportFragmentManager(), "info");
    }

    private void setLayoutManager() {
        layoutManager = new LinearLayoutManager(this);
        mRecyclerViewTripsNavi.setLayoutManager(layoutManager);
    }

    private void initView() {
        mRecyclerViewTripsNavi = findViewById(R.id.recycler_view_trips_navigation);
    }

    private void setTrips() {
        mTrips = new ArrayList<>();

        Trips trip1 = new Trips("Vacanta de vara", "Maldive");
        Trips trip2 = new Trips("Vacanta de iarna", "Bran");
        Trips trip3 = new Trips("Vacanta de schi", "Austria");
        Trips trip4 = new Trips("Vacanta de paste", "Veentia");
        mTrips.add(trip1);
        mTrips.add(trip2);
        mTrips.add(trip3);
        mTrips.add(trip4);
    }
}
