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
                //mDatabaseExecutor.execute(() -> addStarterData());
            }
        };

        WeightLogDatabase database = Room.databaseBuilder(context, WeightLogDatabase.class, "study.db")
                .addCallback(databaseCallback)
                .build();

        mProfileDao = database.profileDao();
        mWeightDao = database.weightDao();

        if (mProfileDao.getProfiles().isInitialized()) {
            //addStarterData();
        }
    }


    public LiveData<Profile> getProfile(String username) {
        return mProfileDao.getProfile(username);
    }

    public List<Profile> getProfiles() {
        return mProfileDao.getProfiles().getValue();
    }

    public boolean doesProfileExist(String username) {
        LiveData<Profile> profile = mProfileDao.getProfile(username);
        return profile != null;
    }
    public void addProfile(Profile profile) {
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

    public LiveData<Weight> getWeight(long weightId) {
        return mWeightDao.getWeight(weightId);
    }

    public LiveData<List<Weight>> getWeights(String profile_username) {
        return mWeightDao.getWeights(profile_username);
    }

    public void addWeight(Weight weight) {
        mDatabaseExecutor.execute(() -> {
            long weightId = mWeightDao.addWeight(weight);
            weight.setId(weightId);
        });
    }

    public void updateWeight(Weight weight) {
        mDatabaseExecutor.execute(() -> {
            mWeightDao.updateWeight(weight);
        });
    }

    public void deleteQuestion(Weight weight) {
        mDatabaseExecutor.execute(() -> {
            mWeightDao.deleteWeight(weight);
        });
    }

}