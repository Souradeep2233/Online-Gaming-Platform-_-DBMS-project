package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.other.Constants;
import com.example.quizapp.other.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MathQuizActivity extends AppCompatActivity {

    private int currentQuestionIndex = 0;
    private TextView questionTextView, questionNumberTextView;
    private Button nextButton;
    private List<String> questions;
    private int correctQuestions = 0;
    private EditText answerEditText;
    private HashMap<String, String> questionsAnswerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_quiz);

        Intent intent = getIntent();
        questionsAnswerMap = (HashMap<String, String>) Utils.getRandomMathQuestions(Constants.QUESTION_SHOWING);
        questions = new ArrayList<>(questionsAnswerMap.keySet());

        questionTextView = findViewById(R.id.textViewQuestion);
        questionNumberTextView = findViewById(R.id.textViewQuestionNumberMath);
        nextButton = findViewById(R.id.buttonNextQuestionMath);
        answerEditText = findViewById(R.id.editTextAnswerMath);

        findViewById(R.id.imageViewStartQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = answerEditText.getText().toString();

                if (answer.isEmpty()) {
                    Toast.makeText(MathQuizActivity.this, "Answer cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (questionsAnswerMap.get(questions.get(currentQuestionIndex)).equals(answer)) {
                    correctQuestions++;
                }
                currentQuestionIndex++;

                if (nextButton.getText().equals(getString(R.string.next))) {
                    displayNextQuestion();
                } else {
                    Intent resultIntent = new Intent(MathQuizActivity.this, FinalResultActivity.class);
                    resultIntent.putExtra(Constants.SUBJECT, getString(R.string.math));
                    resultIntent.putExtra(Constants.CORRECT, correctQuestions);
                    resultIntent.putExtra(Constants.INCORRECT, Constants.QUESTION_SHOWING - correctQuestions);
                    resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(resultIntent);
                    finish();
                }
            }
        });

        displayQuestionData();
    }

    private void displayNextQuestion() {
        answerEditText.setText("");
        questionTextView.setText(questions.get(currentQuestionIndex) + " = ?");
        questionNumberTextView.setText("Current Question: " + (currentQuestionIndex + 1));

        if (currentQuestionIndex == Constants.QUESTION_SHOWING - 1) {
            nextButton.setText(getText(R.string.finish));
        }
    }

    private void displayQuestionData() {
        questionTextView.setText(questions.get(currentQuestionIndex) + " = ?");
        questionNumberTextView.setText("Current Question: " + (currentQuestionIndex + 1));
    }
}
