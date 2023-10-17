package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quizapp.data.User;
import com.example.quizapp.data.UserDatabase;
import com.example.quizapp.data.UserDatabaseClient;
import com.example.quizapp.other.SharedPref;
import com.example.quizapp.other.Utils;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView backButton = findViewById(R.id.imageView);
        EditText usernameEditText = findViewById(R.id.tietUsername);
        EditText emailEditText = findViewById(R.id.tietEmail);
        EditText passwordEditText = findViewById(R.id.tietPassword);
        Button registerButton = findViewById(R.id.btnRegister);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!validateInputs(username, email, password)) return;

                RegisterUserTask registerUserTask = new RegisterUserTask(username, email, password);
                registerUserTask.execute();
            }
        });
    }

    private boolean validateInputs(String username, String email, String password) {
        if (username.isEmpty()) {
            Toast.makeText(this, getString(R.string.username_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, getString(R.string.email_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Utils.isValidEmail(email)) {
            Toast.makeText(this, getString(R.string.email_not_valid), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    class RegisterUserTask extends AsyncTask<Void, Void, Void> {
        private final String username;
        private final String email;
        private final String password;
        private User user;
        private boolean isRegistrationSuccessful = true;

        public RegisterUserTask(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());

            user = new User(username, email, password);

            try {
                databaseClient.userDao().insertUser(user);
            } catch (Exception e) {
                isRegistrationSuccessful = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (isRegistrationSuccessful) {
                Toast.makeText(RegisterActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
                SharedPref sharedPref = SharedPref.getInstance();
                sharedPref.setUser(RegisterActivity.this, user);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "This email is already in use by someone else", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
