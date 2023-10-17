package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quizapp.adapter.HistoryAdapter;
import com.example.quizapp.data.Attempt;
import com.example.quizapp.data.UserDatabase;
import com.example.quizapp.data.UserDatabaseClient;
import com.example.quizapp.data.UserAttempts;
import com.example.quizapp.util.SharedPrefs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;
    private List<UserAttempts> userAttemptsList;
    private TextView totalPointsTextView, totalQuestionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        totalQuestionsTextView = findViewById(R.id.totalQuestionsTextView);
        totalPointsTextView = findViewById(R.id.totalPointsTextView);

        findViewById(R.id.imageViewHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String email = SharedPrefs.getInstance().getUser(this).getEmail();
        GetAllUserAttemptsTask getAllUserAttemptsTask = new GetAllUserAttemptsTask(email);
        getAllUserAttemptsTask.execute();
    }

    class GetAllUserAttemptsTask extends AsyncTask<Void, Void, Void> {

        private final String email;
        ArrayList<Attempt> attempts = new ArrayList<>();

        public GetAllUserAttemptsTask(String email) {
            this.email = email;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());
            attempts = (ArrayList<Attempt>) databaseClient.userDao().getUserAndAttemptsBySameEmail(email);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            int overallPoints = 0;

            for (Attempt attempt : attempts) {
                overallPoints += attempt.getEarnedPoints();
            }

            totalQuestionsTextView.setText(String.valueOf(attempts.size()));
            totalPointsTextView.setText(String.valueOf(overallPoints));

            Collections.sort(attempts, new AttemptCreatedTimeComparator());

            HistoryAdapter adapter = new HistoryAdapter(attempts);
            historyRecyclerView.setAdapter(adapter);
        }
    }

    public class AttemptCreatedTimeComparator implements Comparator<Attempt> {

        @Override
        public int compare(Attempt attempt1, Attempt attempt2) {
            return Long.compare(attempt2.getCreatedTime(), attempt1.getCreatedTime());
        }
    }
}
