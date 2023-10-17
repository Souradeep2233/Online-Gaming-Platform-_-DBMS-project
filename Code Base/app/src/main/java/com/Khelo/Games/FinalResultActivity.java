package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.quizapp.data.Attempt;
import com.example.quizapp.data.UserDatabase;
import com.example.quizapp.data.UserDatabaseClient;
import com.example.quizapp.util.Constants;
import com.example.quizapp.util.SharedPrefs;
import com.example.quizapp.util.Utils;

import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {

    private TextView subjectTextView, correctTextView, incorrectTextView, earnedTextView, overallStatusTextView, dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        int correctAnswers = intent.getIntExtra(Constants.CORRECT, 0);
        int incorrectAnswers = intent.getIntExtra(Constants.INCORRECT, 0);
        String subject = intent.getStringExtra(Constants.SUBJECT);
        String email = SharedPrefs.getInstance().getUser(this).getEmail();
        int earnedPoints = (correctAnswers * Constants.CORRECT_POINT) - (incorrectAnswers * Constants.INCORRECT_POINT);

        subjectTextView = findViewById(R.id.textViewSubject);
        correctTextView = findViewById(R.id.textViewCorrect);
        incorrectTextView = findViewById(R.id.textViewIncorrect);
        earnedTextView = findViewById(R.id.textViewEarned);
        overallStatusTextView = findViewById(R.id.textViewOverallStatus);
        dateTextView = findViewById(R.id.textViewDate);

        findViewById(R.id.imageViewResultQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMainActivity();
            }
        });

        findViewById(R.id.btnFinishQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMainActivity();
            }
        });

        Attempt attempt = new Attempt(
            Calendar.getInstance().getTimeInMillis(),
            subject,
            correctAnswers,
            incorrectAnswers,
            earnedPoints,
            email
        );

        calculateOverallPoints(attempt);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToMainActivity();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void calculateOverallPoints(Attempt attempt) {
        GetOverallPointsTask getOverallPointsTask = new GetOverallPointsTask(attempt);
        getOverallPointsTask.execute();
    }

    class GetOverallPointsTask extends AsyncTask<Void, Void, Void> {

        private final Attempt attempt;
        private int overallPoints = 0;

        public GetOverallPointsTask(Attempt attempt) {
            this.attempt = attempt;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());
            overallPoints = databaseClient.userDao().getOverallPoints(attempt.getEmail());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            attempt.setOverallPoints(overallPoints + attempt.getEarned());
            displayData(attempt);
            SaveUserAttemptTask saveUserAttemptTask = new SaveUserAttemptTask(attempt);
            saveUserAttemptTask.execute();

            Log.d("OVERALL POINTS", String.valueOf(overallPoints));
        }
    }

    private void displayData(Attempt attempt) {
        subjectTextView.setText(attempt.getSubject());
        correctTextView.setText(String.valueOf(attempt.getCorrect()));
        incorrectTextView.setText(String.valueOf(attempt.getIncorrect()));
        earnedTextView.setText(String.valueOf(attempt.getEarned()));
        overallStatusTextView.setText(String.valueOf(attempt.getOverallPoints()));
        dateTextView.setText(Utils.formatDate(attempt.getCreatedTime()));
    }

    class SaveUserAttemptTask extends AsyncTask<Void, Void, Void> {

        private Attempt attempt;

        public SaveUserAttemptTask(Attempt attempt) {
            this.attempt = attempt;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());
            databaseClient.userDao().insertAttempt(attempt);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Attempt Saved", attempt.toString());
        }
    }
}
