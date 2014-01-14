package com.example.ExerciseMe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class BadgeDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("New Badge Awarded!");
        builder.setCancelable(true);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.newbadge, null));
        builder.setMessage("New Badge Awarded!")
                .setPositiveButton("Share to Facebook", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences prefs = getActivity().getSharedPreferences("fbShare", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putBoolean("share", true);
                        edit.commit();
                        BadgeDialogFragment.this.getDialog().cancel();

                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        BadgeDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}