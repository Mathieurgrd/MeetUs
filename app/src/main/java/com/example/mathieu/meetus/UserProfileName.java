package com.example.mathieu.meetus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mathieu on 29/03/17.
 */

public class UserProfileName implements Parcelable {

    private String mName;

    private UserProfileName() {
    }

    public UserProfileName(String Name){
        this.mName=Name;
    }


    protected UserProfileName(Parcel in) {
        mName=in.readString();
    }

    public static final Creator<UserProfileName> CREATOR = new Creator<UserProfileName>() {
        @Override
        public UserProfileName createFromParcel(Parcel in) {
            return new UserProfileName(in);
        }

        @Override
        public UserProfileName[] newArray(int size) {
            return new UserProfileName[size];
        }
    };

    public String getmName(){return mName;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
    }
}