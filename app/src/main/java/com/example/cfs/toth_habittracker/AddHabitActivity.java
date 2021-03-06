package com.example.cfs.toth_habittracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddHabitActivity extends AppCompatActivity {

    private Habit newHabit = new Habit("New task.");
    private static final String FILENAME = "file.sav";

    private HabitData hData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        hData = new HabitData();
        loadFromFile();
        Intent intent = getIntent();
        String message = intent.getStringExtra(HabitMainActivity.HABIT_MESSAGE);


        try {
            newHabit.editTitle(message);

        } catch (InvalidHabitInputException e) {
            e.printStackTrace();
        }

        TextView textView = new TextView(this);
        textView.setTextSize(25);
        textView.setTextColor(Color.BLUE);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_add_habit);

        layout.addView(textView);
        saveInFile();
    }

    protected void setWeekDaysActive(View view) {

//        Date date = new Date();
        ArrayList<String> dayList = new ArrayList();
        // Is the button now checked?

        if(((CheckBox) findViewById(R.id.monButton)).isChecked()){
            dayList.add("Monday");
        }
        if(((CheckBox) findViewById(R.id.tuesButton)).isChecked()){
            dayList.add("Tuesday");
        }
        if(((CheckBox) findViewById(R.id.wedButton)).isChecked()){
            dayList.add("Wednesday");
        }
        if(((CheckBox) findViewById(R.id.thurButton)).isChecked()){
            dayList.add("Thursday");
        }
        if(((CheckBox) findViewById(R.id.friButton)).isChecked()){
            dayList.add("Friday");
        }
        if(((CheckBox) findViewById(R.id.satButton)).isChecked()){
            dayList.add("Saturday");
        }
        if(((CheckBox) findViewById(R.id.sunButton)).isChecked()){
            dayList.add("Sunday");
        }

        //todo:setResult(give intent with habit obj added in);
        //pass back name and array of strings
        Intent intent = getIntent();
        String message = intent.getStringExtra(HabitMainActivity.HABIT_MESSAGE);
        intent.putStringArrayListExtra("dayList",dayList);
        intent.putExtra(HabitMainActivity.HABIT_MESSAGE,message);
        //todo: add cancel button where RESULT_CANCELED
        setResult(RESULT_OK,intent);

        finish();
    }

    /**Code from 301 Lab https://github.com/joshua2ua/lonelyTwitter**/
    public void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<HabitData>(){}.getType();
            hData = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
			/* Create a brand new list if we can't find the file. */
            hData = new HabitData();
        }

    }


    /**Code from 301 Lab https://github.com/joshua2ua/lonelyTwitter**/
    public void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(hData, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
			/* Rethrow. */
            throw new RuntimeException(e);
        } catch (IOException e) {
			/* Rethrow. */
            throw new RuntimeException(e);
        }
    }
}
