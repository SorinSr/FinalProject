package com.example.finalproject.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;

import java.util.List;

public class AdapterTrips extends RecyclerView.Adapter<ViewHolderTrips> {

    private List<Trips> mTrips;

    public AdapterTrips(List<Trips> mTrips) {
        this.mTrips = mTrips;
    }

    @NonNull
    @Override
    public ViewHolderTrips onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.trips_item,
                viewGroup,
                false);
        return new ViewHolderTrips(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTrips viewHolderTrips, int i) {
        Trips currentTrip = mTrips.get(i);
        if (currentTrip != null) {
            if (currentTrip.getmName()!= null) {
                viewHolderTrips.getmTextViewNume().setText(currentTrip.getmName());
            }
            if (currentTrip.getmLocatie() != null) {
                viewHolderTrips.getmTextViewDestinatie().setText(currentTrip.getmLocatie());
            }
        }

    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }
}
