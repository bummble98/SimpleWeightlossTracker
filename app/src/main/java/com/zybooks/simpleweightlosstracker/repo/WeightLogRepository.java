package com.zybooks.simpleweightlosstracker.repo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.zybooks.simpleweightlosstracker.model.Weight;
import com.zybooks.simpleweightlosstracker.model.Profile;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class WeightLogRepository {
    public MutableLiveData<String> importedProfile = new MutableLiveData<>();
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService mDatabaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static WeightLogRepository mStudyRepo;
    private final ProfileDao mProfileDao;
    private final WeightDao mWeightDao;


    public static WeightLogRepository getInstance(Context context) {
        if (mStudyRepo == null) {
            mStudyRepo = new WeightLogRepository(context);
        }
        return mStudyRepo;
    }

    private WeightLogRepository(Context context) {
        RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                mDatabaseExecutor.execute(() -> addStarterData());
            }
        };

        WeightLogDatabase database = Room.databaseBuilder(context, WeightLogDatabase.class, "study.db")
                .addCallback(databaseCallback)
                .build();

        mProfileDao = database.profileDao();
        mWeightDao = database.weightDao();

        if (mProfileDao.getProfiles().isInitialized()) {
            addStarterData();
        }
    }

    private void addStarterData() {
        Profile profile = new Profile("Math");
        long profileId = mProfileDao.addProfile(profile);

        Weight weight = new Weight();
        weight.setText("What is 2 + 3?");
        weight.setAnswer("2 + 3 = 5");
        weight.setProfileId(profileId);
        mWeightDao.addWeight(weight);

        weight = new Weight();
        weight.setText("What is pi?");
        weight.setAnswer("The ratio of a circle's circumference to its diameter.");
        weight.setProfileId(profileId);
        mWeightDao.addWeight(weight);

        profile = new Profile("History");
        profileId = mProfileDao.addProfile(profile);

        weight = new Weight();
        weight.setText("On what date was the U.S. Declaration of Independence adopted?");
        weight.setAnswer("July 4, 1776");
        weight.setProfileId(profileId);
        mWeightDao.addWeight(weight);

        profile = new Profile("Computing");
        mProfileDao.addProfile(profile);
    }

    public Profile getSubject(long subjectId) {
        return mProfileDao.getProfile(subjectId).getValue();
    }

    public List<Profile> getSubjects() {
        return mProfileDao.getProfiles().getValue();
    }

    public void addSubject(Profile profile) {
        mDatabaseExecutor.execute(() -> {
            long subjectId = mProfileDao.addProfile(profile);
            profile.setId(subjectId);
        });
    }

    public void deleteSubject(Profile profile) {
        mDatabaseExecutor.execute(() -> {
            mProfileDao.deleteProfile(profile);
        });
    }

    public LiveData<Weight> getQuestion(long weightId) {
        return mWeightDao.getWeight(weightId);
    }

    public LiveData<List<Weight>> getQuestions(long subjectId) {
        return mWeightDao.getQuestions(subjectId);
    }

    public void addWeight(Weight weight) {
        mDatabaseExecutor.execute(() -> {
            long questionId = mWeightDao.addWeight(weight);
            weight.setId(questionId);
        });
    }

    public void updateQuestion(Weight weight) {
        mDatabaseExecutor.execute(() -> {
            mWeightDao.updateQuestion(weight);
        });
    }

    public void deleteQuestion(Weight weight) {
        mDatabaseExecutor.execute(() -> {
            mWeightDao.deleteQuestion(weight);
        });
    }

}