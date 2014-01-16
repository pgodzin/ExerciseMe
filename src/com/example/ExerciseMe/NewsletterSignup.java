package com.example.ExerciseMe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

public class NewsletterSignup extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emailentry);

        Button b = (Button) findViewById(R.id.enter_email);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences badgePrefs = NewsletterSignup.this.getSharedPreferences("Badges", Context.MODE_PRIVATE);

                SharedPreferences.Editor edit = badgePrefs.edit();  //
                edit.remove("Shown_newsletter");       //TODO: remove once live
                edit.commit(); //
                if (badgePrefs.getBoolean("Shown_newsletter", false) == false)
                    BadgeUtils.showBadge(NewsletterSignup.this, getSupportFragmentManager(), "newsletter");
            }
        });
    }
}