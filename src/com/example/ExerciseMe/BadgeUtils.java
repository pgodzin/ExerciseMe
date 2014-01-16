package com.example.ExerciseMe;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public class BadgeUtils {

    public static void showBadge(Context context, FragmentManager fm, String badgeName) {
        SharedPreferences badgePrefs = context.getSharedPreferences("Badges", Context.MODE_PRIVATE);
        SharedPreferences.Editor badgeEdit = badgePrefs.edit();

        badgeEdit.putBoolean("ShownBadge_" + badgeName, true);
        badgeEdit.commit();

        DialogFragment frag = new BadgeDialogFragment(context, badgeName);
        frag.show(fm, "New Badge:" + badgeName);
    }
}
