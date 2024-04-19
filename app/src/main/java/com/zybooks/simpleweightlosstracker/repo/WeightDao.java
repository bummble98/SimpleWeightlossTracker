package com.zybooks.simpleweightlosstracker.repo;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.zybooks.simpleweightlosstracker.model.Weight;

import java.util.List;

@Dao
public interface WeightDao {
    @Query("SELECT * FROM Weight WHERE id = :id")
    LiveData<Weight> getWeight(long id);

    @Query("SELECT * FROM Weight WHERE profile_id = :profileId ORDER BY id")
    LiveData<List<Weight>> getQuestions(long profileId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addWeight(Weight weight);

    @Update
    void updateQuestion(Weight weight);

    @Delete
    void deleteQuestion(Weight weight);
}