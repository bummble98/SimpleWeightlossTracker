package com.zybooks.simpleweightlosstracker.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Profile.class, parentColumns = "username",
        childColumns = "profile_username", onDelete = CASCADE),
        tableName = "weight",
        indices = {@Index(value = {"profile_username"})})
public class Weight {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;
    @ColumnInfo(name = "date")
    private String mDate;
    @ColumnInfo(name = "weight")
    private Integer mWeight;
    @ColumnInfo(name = "profile_username")
    private String mProfileUsername;

    public Weight(){}

    public Weight(String date, Integer weight, String username){
        mDate = date;
        mWeight = weight;
        mProfileUsername = username;

    }

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

    public void setWeight(Integer weight) { mWeight=weight;}

    public String getProfileUsername() {
        return mProfileUsername;
    }

    public void setProfileUsername(String profileId) {
        mProfileUsername = profileId;
    }
}