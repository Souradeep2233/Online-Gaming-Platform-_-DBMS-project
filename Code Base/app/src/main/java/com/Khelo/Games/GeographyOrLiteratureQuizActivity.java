package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quizapp.util.Constants;
import com.example.quizapp.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private int currentQuestionIndex = 0;
    private TextView questionTextView, questionNumberTextView;
    private Button nextButton;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private List<String> questions;
    private int correctQuestions = 0;
    private Map<String, Map<String, Boolean>> questionsAnswerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        String subject = intent.getStringExtra(Constants.SUBJECT);

        TextView titleTextView = findViewById(R.id.textViewTitle);

        if (subject.equals(getString(R.string.literature))) {
            questionsAnswerMap = Utils.getRandomLiteratureAndGeographyQuestions(this, getString(R.string.literature), Constants.QUESTION_SHOWING);
            titleTextView.setText(getString(R.string.literature_quiz));
        } else {
            questionsAnswerMap = Utils.getRandomLiteratureAndGeographyQuestions(this, getString(R.string.geography), Constants.QUESTION_SHOWING);
            titleTextView.setText(getString(R.string.geography_quiz));
        }
        questions = new ArrayList<>(questionsAnswerMap.keySet());

        questionTextView = findViewById(R.id.textViewQuestion);
        questionNumberTextView = findViewById(R.id.textViewQuestionNumber);
        nextButton = findViewById(R.id.btnNextQuestion);
        radioGroup = findViewById(R.id.radioGroup);

        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);

        findViewById(R.id.btnNextQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                boolean answer = questionsAnswerMap.get(questions.get(currentQuestionIndex)).get(selectedRadioButton.getText());

                if (answer) {
                    correctQuestions++;
                }

                currentQuestionIndex++;

                if (nextButton.getText().equals(getString(R.string.next))) {
                    displayNextQuestion();
                } else {
                    Intent resultIntent = new Intent(QuizActivity.this, ResultActivity.class);
                    resultIntent.putExtra(Constants.SUBJECT, subject);
                    resultIntent.putExtra(Constants.CORRECT, correctQuestions);
                    resultIntent.putExtra(Constants.INCORRECT, Constants.QUESTION_SHOWING - correctQuestions);
                    resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(resultIntent);
                    finish();
                }
            }
        });

        findViewById(R.id.imageViewStartQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        displayQuestion();
    }

    private void displayNextQuestion() {
        setAnswersToRadioButton();
        questionTextView.setText(questions.get(currentQuestionIndex));
        questionNumberTextView.setText(getString(R.string.current_question_number, currentQuestionIndex + 1));

        if (currentQuestionIndex == Constants.QUESTION_SHOWING - 1) {
            nextButton.setText(getText(R.string.finish));
        }
    }

    private void displayQuestion() {
        questionTextView.setText(questions.get(currentQuestionIndex));
        questionNumberTextView.setText(getString(R.string.current_question_number, currentQuestionIndex + 1));

        setAnswersToRadioButton();
    }

    private void setAnswersToRadioButton() {
        ArrayList<String> questionOptions = new ArrayList(questionsAnswerMap.get(questions.get(currentQuestionIndex)).keySet());

        radioButton1.setText(questionOptions.get(0));
        radioButton2.setText(questionOptions.get(1));
        radioButton3.setText(questionOptions.get(2));
        radioButton4.setText(questionOptions.get(3));
    }
}
