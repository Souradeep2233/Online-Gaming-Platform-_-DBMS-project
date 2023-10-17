package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizapp.other.Constants;

public class QuizOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_option);

        CardView mathCardView = findViewById(R.id.cardViewMath);
        CardView geographyCardView = findViewById(R.id.cardViewGeography);
        CardView literatureCardView = findViewById(R.id.cardViewLiterature);

        findViewById(R.id.imageViewQuizOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mathCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz(getString(R.string.math));
            }
        });

        geographyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz(getString(R.string.geography));
            }
        });

        literatureCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz(getString(R.string.literature));
            }
        });
    }

    private void startQuiz(String subject) {
        Intent intent = new Intent(QuizOptionActivity.this, QuizActivity.class);
        intent.putExtra(Constants.SUBJECT, subject);
        startActivity(intent);
    }
}
