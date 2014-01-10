package com.example.ExerciseMe;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.lang.reflect.Array;
import java.util.List;

public class Homescreen extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        setListAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1,
                R.id.workout_name, getResources().getStringArray(R.array.workouts)));

        TextView tv = (TextView) findViewById(R.id.newsletter);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homescreen.this, EnterEmail.class);
                startActivity(intent);
            }
        });
    }

    private class MyAdapter extends ArrayAdapter<String> {

        private MyAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflator.inflate(R.layout.list_item, parent, false);
            String[] workouts = getResources().getStringArray(R.array.workouts);
            ImageView iv =  (ImageView) row.findViewById(R.id.workout_image);
            TextView tv = (TextView) row.findViewById(R.id.workout_name);

            tv.setText(workouts[position]);
            if (workouts[position].equals("Ab Roller"))
                iv.setImageResource(R.drawable.abroller);
            else if (workouts[position].equals("Full Body"))
                iv.setImageResource(R.drawable.fullbody);

            return row;
        }
    }
}