package com.sagarkhurana.quizforfun.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuizUserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void createUser(QuizUser user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createAttempt(QuizAttempt attempt);

    @Update
    void updateUser(QuizUser user);

    @Query("SELECT * FROM user")
    List<QuizUser> observeAllUsers();

    @Delete
    void deleteUser(QuizUser user);

    @Transaction
    @Query("SELECT DISTINCT * FROM attempt WHERE email = :email")
    List<QuizAttempt> getUserAndAttemptsWithSameEmailAddress(String email);

    @Transaction
    @Query("SELECT SUM(earned) FROM attempt WHERE email = :email")
    int getOverallPoints(String email);

}
