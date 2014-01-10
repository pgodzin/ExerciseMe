package com.example.ExerciseMe;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class FullBodyWorkout extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullbody);

        Button b1 = (Button) findViewById(R.id.shirtButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.amazon.com/Under-Armour-Short-Sleeve-T-Shirt/dp/B0073GO64U/ref=sr_1_13?ie=UTF8&qid=1389349267&sr=8-13&keywords=workout+clothes"));
                startActivity(intent);
            }


        });

        Button b2 = (Button) findViewById(R.id.shortsButton);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.amazon.com/Under-Armour-Mens-Shorts-Black/dp/B001U5PJT6/ref=sr_1_4?ie=UTF8&qid=1389349318&sr=8-4&keywords=workout+shorts"));
                startActivity(intent);
            }
        });

        Button b3 = (Button) findViewById(R.id.suppButton);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.amazon.com/Optimum-Nutrition-Opti-Men-Multivitamins-180-Count/dp/B0015R3AAO/ref=sr_1_2?ie=UTF8&qid=1389349340&sr=8-2&keywords=supplements"));
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