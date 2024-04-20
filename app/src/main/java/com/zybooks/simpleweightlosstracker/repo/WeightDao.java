package com.zybooks.simpleweightlosstracker.repo;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.zybooks.simpleweightlosstracker.model.Weight;

import java.util.List;

@Dao
public interface WeightDao {
    @Query("SELECT * FROM Weight WHERE id = :id")
    LiveData<Weight> getWeight(long id);

    @Query("SELECT * FROM Weight WHERE profile_username = :profile_username ORDER BY id")
    LiveData<List<Weight>> getWeights(String profile_username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addWeight(Weight weight);

    @Update
    void updateWeight(Weight weight);

    @Delete
    void deleteWeight(Weight weight);
}