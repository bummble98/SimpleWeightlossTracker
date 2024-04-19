package com.zybooks.simpleweightlosstracker.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profile {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;
    @NonNull
    @ColumnInfo(name = "text")
    private String mText;
    @ColumnInfo(name = "updated")
    private long mUpdateTime;

    public Profile(@NonNull String text) {
        mText = text;
        mUpdateTime = System.currentTimeMillis();
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getText() {
        return mText;
    }

    public void setText(String profile) {
        mText = profile;
    }

    public long getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(long updateTime) {
        mUpdateTime = updateTime;
    }
}