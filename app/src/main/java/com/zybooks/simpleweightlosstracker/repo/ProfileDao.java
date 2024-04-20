package com.zybooks.simpleweightlosstracker.repo;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.simpleweightlosstracker.model.Profile;

import java.util.List;

@Dao
public interface ProfileDao {

    @Query("SELECT * FROM Profile WHERE username = :username")
    LiveData<Profile> getProfile(String username);

    @Query("SELECT * FROM Profile ORDER BY username COLLATE NOCASE")
    LiveData<List<Profile>> getProfiles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addProfile(Profile profile);

    @Update
    void updateProfile(Profile profile);

    @Delete
    void deleteProfile(Profile profile);
}