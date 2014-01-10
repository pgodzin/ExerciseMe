package com.example.ExerciseMe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        boolean cb1Checked, cb2Checked, cb3Checked, cb4Checked;
        TextView doneTV = (TextView) findViewById(R.id.done);
        final CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox);
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               // if (cb1.isChecked()) cb1Checked = true;   TODO: Figure out!
            }
        });
        final CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox cb4 = (CheckBox) findViewById(R.id.checkBox4);

        if (cb1.isChecked() && cb2.isChecked() && cb3.isChecked() && cb4.isChecked())
            doneTV.setVisibility(View.VISIBLE);

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


}