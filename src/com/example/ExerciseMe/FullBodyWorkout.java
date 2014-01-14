package com.example.ExerciseMe;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.amazon.device.associates.AssociatesAPI;
import com.amazon.device.associates.LinkService;
import com.amazon.device.associates.NotInitializedException;
import com.amazon.device.associates.OpenProductPageRequest;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.widget.FacebookDialog;
import org.joda.time.DateTime;

public class FullBodyWorkout extends FragmentActivity {
    final int MAXDAYS = 2;
    private UiLifecycleHelper uiHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.fullbody);
        //AssociatesAPI.initialize(new AssociatesAPI.Config(APPLICATION_KEY, this)); TODO: Get key

        Button b1 = (Button) findViewById(R.id.shirtButton);
        final String asin1 = "B001U5PJT6";
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenProductPageRequest request = new OpenProductPageRequest(asin1);
                try {
                    LinkService linkService = AssociatesAPI.getLinkService();
                    linkService.openRetailPage(request);
                } catch (NotInitializedException e) {
                    e.printStackTrace();
                }
            }


        });

        final String asin2 = "B001U5PJT6";
        Button b2 = (Button) findViewById(R.id.shortsButton);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenProductPageRequest request = new OpenProductPageRequest(asin2);
                try {
                    LinkService linkService = AssociatesAPI.getLinkService();
                    linkService.openRetailPage(request);
                } catch (NotInitializedException e) {
                    e.printStackTrace();
                }
            }


        });

        final String asin3 = "B0015R3AAO";
        Button b3 = (Button) findViewById(R.id.suppButton);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenProductPageRequest request = new OpenProductPageRequest(asin3);
                try {
                    LinkService linkService = AssociatesAPI.getLinkService();
                    linkService.openRetailPage(request);
                } catch (NotInitializedException e) {
                    e.printStackTrace();
                }
            }


        });

        Button play = (Button) findViewById(R.id.play_button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullBodyWorkout.this, com.example.ExerciseMe.MediaPlayer.class);
                startActivity(intent);
            }
        });

        SharedPreferences prefs = FullBodyWorkout.this.getSharedPreferences("TimeCompleted", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();          //TODO: Don't delete on restart
        edit.clear();
        edit.commit();


    }

    public void onCheckboxClicked(View view) {

        TextView doneTV = (TextView) findViewById(R.id.done);
        final CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox cb4 = (CheckBox) findViewById(R.id.checkBox4);

        if (cb1.isChecked() && cb2.isChecked() && cb3.isChecked() && cb4.isChecked()) {
            doneTV.setVisibility(View.VISIBLE);
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder mBuilder =
                    new Notification.Builder(this)
                            .setSmallIcon(android.R.drawable.stat_sys_warning)
                            .setContentTitle("Sore after workout?")
                            .setContentText("Try supplements now!");
            nm.notify(0, mBuilder.build());

            DateTime dt = new DateTime();
            saveCompletionTime(dt);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
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

    private void saveCompletionTime(DateTime dt) {
        //int currentDay = dt.getDayOfYear();
        int currentDay = dt.getMinuteOfDay();
        int[] days = getFromPrefs();
        int[] newArray;

        SharedPreferences prefs = FullBodyWorkout.this.getSharedPreferences("TimeCompleted", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        if (prefs.getBoolean("Done", false)) {  // Goal has already been met
//            Toast toast = Toast.makeText(FullBodyWorkout.this, "Already completed a workout for " + MAXDAYS + " in a row!", 3000);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//            Dialog d = new Dialog(FullBodyWorkout.this);
            DialogFragment frag = new BadgeDialogFragment();
            frag.show(getSupportFragmentManager(), "New Badge");
            SharedPreferences fbPrefs = this.getSharedPreferences("fbShare", Context.MODE_PRIVATE);
            SharedPreferences.Editor fbEdit = fbPrefs.edit();
            if (fbPrefs.getBoolean("share", false)) {
                shareToFB();
            }

        } else if (days.length > 0 && currentDay != days[days.length - 1]) {
            int daysDiff = currentDay - days[days.length - 1];
            if (daysDiff == 1) { // completes on following day TODO: end of year?
                if (days.length < MAXDAYS - 1) {   // hasn't reached goal yet
                    newArray = new int[days.length + 1];
                    for (int j = 0; j < days.length; j++)
                        newArray[j] = days[j];
                    newArray[days.length] = currentDay;
                    storeIntArray(newArray);
                } else if (days.length == MAXDAYS - 1) { // Hit the goal
                    edit.remove("TimeCompleted");
                    edit.putBoolean("Done", true);
                    edit.commit();
                }
            } else if (daysDiff != 0) {  // not a consecutive day
                edit.remove("TimeCompleted");
                edit.commit();
                newArray = new int[1];
                newArray[0] = currentDay;
                storeIntArray(newArray);
            }
        } else if (days.length == 0) {   // no consecutive days (first time completing)
            newArray = new int[1];
            newArray[0] = currentDay;
            storeIntArray(newArray);
        }

    }

    private void storeIntArray(int[] array) {
        SharedPreferences.Editor edit = FullBodyWorkout.this.getSharedPreferences("TimeCompleted", Context.MODE_PRIVATE).edit();
        edit.putInt("Count", array.length);
        int count = 0;
        for (int i : array) {
            edit.putInt("Day_" + count++, i);
        }
        edit.commit();
    }

    private int[] getFromPrefs() {
        int[] ret;
        SharedPreferences prefs = this.getSharedPreferences("TimeCompleted", Context.MODE_PRIVATE);
        int count = prefs.getInt("Count", 0);
        Toast toast = Toast.makeText(FullBodyWorkout.this, "Consecutive days: " + count, 3000);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        ret = new int[count];
        for (int i = 0; i < count; i++) {
            ret[i] = prefs.getInt("Day_" + i, i);
        }
        return ret;
    }

    private void shareToFB() {
        if (FacebookDialog.canPresentOpenGraphActionDialog(getApplicationContext(),
                FacebookDialog.OpenGraphActionDialogFeature.OG_ACTION_DIALOG)) {
            OpenGraphObject badge = OpenGraphObject.Factory.createForPost("exercisemeapp:badge");
            badge.setProperty("title", "I achieved a new badge!");
            badge.setProperty("image", "http://4sqday16.files.wordpress.com/2011/11/foursquare-gym-rat-badge.png");
            //badge.setProperty("url", "https://example.com/cooking-app/badge/Buffalo-Tacos.html");  TODO: link to appstore
            badge.setProperty("description", "I completed a full body workout for 7 straight days!");
            OpenGraphAction action = GraphObject.Factory.create(OpenGraphAction.class);
            action.setProperty("badge", badge);

            FacebookDialog shareDialog = new FacebookDialog.OpenGraphActionDialogBuilder(this, action, "exercisemeapp:earn", "badge")
                    .build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } else {
            Toast.makeText(this, "Facebook not available", Toast.LENGTH_SHORT).show();
        }
    }
}