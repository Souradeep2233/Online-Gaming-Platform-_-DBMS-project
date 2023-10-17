package com.example.quizapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quiz_user")
public class QuizUser implements Parcelable {

    @ColumnInfo(name = "user_name")
    private final String userName;
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_email")
    private final String userEmail;
    @ColumnInfo(name = "user_password")
    private String userPassword;

    public QuizUser(String userName, @NonNull String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public static Creator<QuizUser> getCREATOR() {
        return CREATOR;
    }

    protected QuizUser(Parcel in) {
        userName = in.readString();
        userEmail = in.readString();
        userPassword = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userEmail);
        dest.writeString(userPassword);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuizUser> CREATOR = new Creator<QuizUser>() {
        @Override
        public QuizUser createFromParcel(Parcel in) {
            return new QuizUser(in);
        }

        @Override
        public QuizUser[] newArray(int size) {
            return new QuizUser[size];
        }
    };
}
