package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quizapp.data.User;
import com.example.quizapp.util.SharedPrefs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView usernameTextView = findViewById(R.id.textViewUsernameHome);
        CardView startQuizCardView = findViewById(R.id.cardViewStartQuiz);
        CardView ruleCardView = findViewById(R.id.cardViewRule);
        CardView historyCardView = findViewById(R.id.cardViewHistory);
        CardView editPasswordCardView = findViewById(R.id.cardViewEditPassword);
        CardView logoutCardView = findViewById(R.id.cardViewLogout);

        SharedPrefs sharedPrefs = SharedPrefs.getInstance();
        User user = sharedPrefs.getUser(this);
        usernameTextView.setText("Hello, " + user.getUsername());

        startQuizCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QuizOptionActivity.class));
            }
        });

        ruleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RuleActivity.class));
            }
        });

        historyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });

        editPasswordCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditPasswordActivity.class));
            }
        });

        logoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefs.clearSharedPrefs(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
