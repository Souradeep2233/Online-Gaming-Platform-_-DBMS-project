package com.sagarkhurana.quizforfun.data;

import android.content.Context;

import androidx.room.Room;

public class QuizDatabaseClient {

    private static final String DB_NAME = "quiz_db";
    private static QuizDatabase instance;

    public static synchronized QuizDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(), QuizDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public QuizDatabase getQuizDatabase() {
        return instance;
    }
}
