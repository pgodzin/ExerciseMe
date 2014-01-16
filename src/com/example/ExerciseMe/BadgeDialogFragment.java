package com.example.ExerciseMe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.widget.FacebookDialog;

public class BadgeDialogFragment extends DialogFragment {

    private UiLifecycleHelper uiHelper;
    private int layout = R.layout.newbadge;
    private String badgeName;
    private Context context;

    public BadgeDialogFragment(Context c, String badge) {
        super();
        badgeName = badge;
        context = c;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        uiHelper = new UiLifecycleHelper(getActivity(), null);
        uiHelper.onCreate(savedInstanceState);

        SharedPreferences badgePrefs = getActivity().getSharedPreferences("Badges", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = badgePrefs.edit();
        edit.putBoolean("Shown_" + badgeName, true);
        edit.commit();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("New Badge Awarded!");
        builder.setCancelable(true);
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(layout, null);
        ImageView badgeImage = (ImageView) view.findViewById(R.id.badge);
        TextView badgeText = (TextView) view.findViewById(R.id.badgeText);

        badgeText.setText(context.getResources().getIdentifier(badgeName + "Text", "string", context.getPackageName()));
        badgeImage.setImageResource(context.getResources().getIdentifier(badgeName, "drawable", context.getPackageName()));

        builder.setView(view);
        builder
                .setPositiveButton("Share to Facebook", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        BadgeDialogFragment.this.getDialog().cancel();
                        // shareToFB();    //TODO: Fix

                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        BadgeDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }


        });
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }


    private void shareToFB() {
        if (FacebookDialog.canPresentOpenGraphActionDialog(getActivity().getApplicationContext(),
                FacebookDialog.OpenGraphActionDialogFeature.OG_ACTION_DIALOG)) {
            OpenGraphObject badge = OpenGraphObject.Factory.createForPost("exercisemeapp:badge");
            badge.setProperty("title", "I achieved a new badge!");
            badge.setProperty("image", "http://4sqday16.files.wordpress.com/2011/11/foursquare-gym-rat-badge.png");
            //badge.setProperty("url", "https://example.com/cooking-app/badge/Buffalo-Tacos.html");  TODO: link to appstore
            badge.setProperty("description", "I completed a full body workout for 7 straight days!");
            OpenGraphAction action = GraphObject.Factory.create(OpenGraphAction.class);
            action.setProperty("badge", badge);

            FacebookDialog shareDialog = new FacebookDialog.OpenGraphActionDialogBuilder(getActivity(), action, "exercisemeapp:earn", "badge")
                    .build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } else {
            Toast.makeText(getActivity(), "Facebook not available", Toast.LENGTH_SHORT).show();
        }
    }

}