package com.zybooks.simpleweightlosstracker.repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.zybooks.simpleweightlosstracker.model.Weight;
import com.zybooks.simpleweightlosstracker.model.Profile;

@Database(entities = {Weight.class, Profile.class}, version = 1)
public abstract class WeightLogDatabase extends RoomDatabase {

    public abstract WeightDao weightDao();
    public abstract ProfileDao profileDao();
}