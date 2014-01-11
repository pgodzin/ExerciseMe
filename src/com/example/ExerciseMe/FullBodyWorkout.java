package com.example.ExerciseMe;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.amazon.device.associates.AssociatesAPI;
import com.amazon.device.associates.LinkService;
import com.amazon.device.associates.NotInitializedException;
import com.amazon.device.associates.OpenProductPageRequest;

public class FullBodyWorkout extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    }

    public void onCheckboxClicked(View view) {

        TextView doneTV = (TextView) findViewById(R.id.done);
        final CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox cb4 = (CheckBox) findViewById(R.id.checkBox4);

        if (cb1.isChecked() && cb2.isChecked() && cb3.isChecked() && cb4.isChecked()){
            doneTV.setVisibility(View.VISIBLE);
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder mBuilder =
                    new Notification.Builder(this)
                            .setSmallIcon(android.R.drawable.stat_sys_warning)
                            .setContentTitle("Sore after workout?")
                            .setContentText("Try supplements now!");
            nm.notify(0, mBuilder.build());

        }

    }
}