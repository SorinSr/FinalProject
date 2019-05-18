package com.example.finalproject.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.R;

public class ViewHolderTrips extends RecyclerView.ViewHolder {

    private TextView mTextViewNume, mTextViewLocatie;

    public TextView getmTextViewNume() {
        return mTextViewNume;
    }

    public TextView getmTextViewDestinatie() {
        return mTextViewLocatie;
    }

    public ViewHolderTrips(@NonNull View itemView) {
        super(itemView);

        mTextViewLocatie = itemView.findViewById(R.id.text_view_destinatie);
        mTextViewNume = itemView.findViewById(R.id.text_view_nume);
    }
}
