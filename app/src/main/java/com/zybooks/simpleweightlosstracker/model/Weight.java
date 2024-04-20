package com.zybooks.simpleweightlosstracker.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Profile.class, parentColumns = "id",
        childColumns = "profile_id", onDelete = CASCADE))
public class Weight {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;
    @ColumnInfo(name = "date")
    private String mDate;
    @ColumnInfo(name = "weight")
    private Integer mWeight;
    @ColumnInfo(name = "profile_id")
    private long mProfileId;

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String text) {
        mDate = text;
    }

    public Integer getWeight() {
        return mWeight;
    }

    public void setAnswer(String answer) {
    }
    public void setWeight(int weight) { mWeight=weight;}

    public long getProfileId() {
        return mProfileId;
    }

    public void setProfileId(long profileId) {
        mProfileId = profileId;
    }
}