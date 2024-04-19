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
    @ColumnInfo(name = "text")
    private String mText;
    @ColumnInfo(name = "answer")
    private String mAnswer;
    @ColumnInfo(name = "profile_id")
    private long mProfileId;

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public long getProfileId() {
        return mProfileId;
    }

    public void setProfileId(long profileId) {
        mProfileId = profileId;
    }
}