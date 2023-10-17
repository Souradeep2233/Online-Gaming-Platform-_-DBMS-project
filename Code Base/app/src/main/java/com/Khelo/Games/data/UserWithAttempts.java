package com.sagarkhurana.quizforfun.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Relation;

public class QuizUserWithAttempts implements Parcelable {

    @Embedded
    private QuizUser quizUser;
    @Relation(
            parentColumn = "email",
            entityColumn = "email",
            entity = QuizAttempt.class
    )
    private QuizAttempt quizAttempt;

    public QuizUserWithAttempts(QuizUser quizUser, QuizAttempt quizAttempt) {
        this.quizUser = quizUser;
        this.quizAttempt = quizAttempt;
    }

    protected QuizUserWithAttempts(Parcel in) {
        quizUser = in.readParcelable(QuizUser.class.getClassLoader());
        quizAttempt = in.readParcelable(QuizAttempt.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(quizUser, flags);
        dest.writeParcelable(quizAttempt, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuizUserWithAttempts> CREATOR = new Creator<QuizUserWithAttempts>() {
        @Override
        public QuizUserWithAttempts createFromParcel(Parcel in) {
            return new QuizUserWithAttempts(in);
        }

        @Override
        public QuizUserWithAttempts[] newArray(int size) {
            return new QuizUserWithAttempts[size];
        }
    };

    public QuizUser getQuizUser() {
        return quizUser;
    }

    public QuizAttempt getQuizAttempt() {
        return quizAttempt;
    }
}
