package com.example.finalproject.RecyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.finalproject.ManagerTrip.ManageTrip;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView mRecyclerViewTrips;
    RecyclerView.LayoutManager layoutManager;
    private List<Trips> mTrips= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initView();
        setTrips();
        setLayoutManager();
        setAdapter();
    }

    private void setAdapter() {
        AdapterTrips tripsAdapter = new AdapterTrips(mTrips);
        mRecyclerViewTrips.setAdapter(tripsAdapter);
    }

    private void setLayoutManager() {
        layoutManager = new LinearLayoutManager(this);
        mRecyclerViewTrips.setLayoutManager(layoutManager);
    }

    private void initView() {
        mRecyclerViewTrips = findViewById(R.id.recycler_view_trips);
    }

    private void setTrips() {
        /*
        Trips trip1 = new Trips("Vacanta de vara", "Maldive");
        Trips trip2 = new Trips("Vacanta de iarna", "Bran");
        Trips trip3 = new Trips("Vacanta de schi", "Austria");
        Trips trip4 = new Trips("Vacanta de paste", "Veentia");
        Trips trip5 = new Trips("City Break", "Los Angeles");
        Trips trip6 = new Trips("Vacanta de odihna", "Amsterdam");
        Trips trip7 = new Trips("O fuga pana la mare", "Costinesti");
        Trips trip11 = new Trips("Vacanta de vara", "Maldive");
        Trips trip12 = new Trips("Vacanta de iarna", "Bran");
        Trips trip13 = new Trips("Vacanta de schi", "Austria");
        Trips trip14 = new Trips("Vacanta de paste", "Veentia");
        Trips trip15 = new Trips("City Break", "Los Angeles");
        Trips trip16 = new Trips("Vacanta de odihna", "Amsterdam");
        mTrips.add(trip1);
        mTrips.add(trip2);
        mTrips.add(trip3);
        mTrips.add(trip4);
        mTrips.add(trip5);
        mTrips.add(trip6);
        mTrips.add(trip7);
        mTrips.add(trip11);
        mTrips.add(trip12);
        mTrips.add(trip13);
        mTrips.add(trip14);
        mTrips.add(trip15);
        mTrips.add(trip16);
        */
        Bundle dataReceived = getIntent().getExtras();
        if (dataReceived != null) {
            String tripName = dataReceived.getString(ManageTrip.TITLE);
            String destination = dataReceived.getString(ManageTrip.DESTIANTION);
            Trips trip = new Trips(tripName, destination);
            mTrips.add(trip);
        }
    }
}
