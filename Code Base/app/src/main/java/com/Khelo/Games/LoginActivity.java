package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.data.User;
import com.example.quizapp.data.UserDatabase;
import com.example.quizapp.data.UserDatabaseClient;
import com.example.quizapp.util.SharedPrefs;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onStart() {
        super.onStart();

        SharedPrefs sharedPrefs = SharedPrefs.getInstance();
        if (sharedPrefs.getUser(this) != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_QuizForFun);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        TextView signUpTextView = findViewById(R.id.textViewSignUp);
        Button loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!validateInputs(username, password)) return;

                LoginUserTask loginUserTask = new LoginUserTask(username, password);
                loginUserTask.execute();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private boolean validateInputs(String username, String password) {

        if (username.isEmpty()) {
            Toast.makeText(this, getString(R.string.username_cannot_be_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    class LoginUserTask extends AsyncTask<Void, Void, Void> {

        private final String username;
        private final String password;
        private ArrayList<User> users = new ArrayList<>();

        public LoginUserTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());
            users = (ArrayList<User>) databaseClient.userDao().getAllUsers();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (User user : users) {
                if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                    SharedPrefs sharedPrefs = SharedPrefs.getInstance();
                    sharedPrefs.setUser(LoginActivity.this, user);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    return;
                }
            }
            Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
        }
    }
}
