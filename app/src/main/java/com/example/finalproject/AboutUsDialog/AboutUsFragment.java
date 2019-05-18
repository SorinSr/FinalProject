package com.example.finalproject.AboutUsDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.example.finalproject.R;

public class AboutUsFragment extends DialogFragment {
       @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
           LayoutInflater inflater = requireActivity().getLayoutInflater();
           AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
           builder.setView(inflater.inflate(R.layout.fragment_about_us,null))
                   .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           // User cancelled the dialog
                       }
                   });

           // Create the AlertDialog object and return it
           return builder.create();
       }
}


