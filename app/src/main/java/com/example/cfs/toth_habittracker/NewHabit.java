package com.example.cfs.toth_habittracker;

import android.provider.CalendarContract;

import java.util.ArrayList;

/**
 * Created by tothd on 9/26/2016.
 */
public class NewHabit extends Habit {

    public NewHabit(String givenTitle) {
        super(givenTitle);
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public ArrayList<String> weekDaysActive() {

        return null;
    }
}
