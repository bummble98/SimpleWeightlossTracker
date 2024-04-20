package com.zybooks.simpleweightlosstracker.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "profile",
        indices = {@Index(value = {"username"}, unique = true)}
)
public class Profile {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;
    @NonNull
    @ColumnInfo(name = "username")
    private String mUsername;
    @NonNull
    @ColumnInfo(name = "password")
    private String mPassword;

    public Profile( @NonNull String username, @NonNull String password) {
        mUsername = username;
        mPassword = password;
    }


    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }
    public String getPassword() {
        return mPassword;
    }

    public void setUsername(String profile) {
        mUsername = profile;
    }
}