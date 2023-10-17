package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myquizapp.data.User;
import com.example.myquizapp.data.UserDatabase;
import com.example.myquizapp.data.UserDatabaseClient;
import com.example.myquizapp.utilities.SharedPrefs;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText oldPasswordEditText, newPasswordEditText, confirmNewPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPasswordEditText = findViewById(R.id.editTextOldPassword);
        newPasswordEditText = findViewById(R.id.editTextNewPassword);
        confirmNewPasswordEditText = findViewById(R.id.editTextConfirmNewPassword);
        Button saveButton = findViewById(R.id.btnSavePassword);

        findViewById(R.id.imageViewBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmNewPassword = confirmNewPasswordEditText.getText().toString();

                if (!isValidInput(oldPassword, newPassword, confirmNewPassword)) {
                    return;
                }

                updatePassword(oldPassword, newPassword);
            }
        });
    }

    private void updatePassword(String oldPassword, String newPassword) {
        User user = SharedPrefs.getInstance().getUser(this);

        if (!user.getPassword().equals(oldPassword)) {
            Toast.makeText(this, "Please enter the correct old password", Toast.LENGTH_SHORT).show();
            return;
        }

        user.setPassword(newPassword);
        UpdatePasswordTask updatePasswordTask = new UpdatePasswordTask(user);
        updatePasswordTask.execute();
    }

    private boolean isValidInput(String oldPassword, String newPassword, String confirmNewPassword) {
        if (oldPassword.isEmpty()) {
            Toast.makeText(this, "Old password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (newPassword.isEmpty()) {
            Toast.makeText(this, "New password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (confirmNewPassword.isEmpty()) {
            Toast.makeText(this, "Confirm new password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!confirmNewPassword.equals(newPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (oldPassword.equals(newPassword)) {
            Toast.makeText(this, "New password must be different from the old password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    class UpdatePasswordTask extends AsyncTask<Void, Void, Void> {
        private final User user;

        public UpdatePasswordTask(User user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());
            databaseClient.userDao().updateUser(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(ChangePasswordActivity.this, "Password Updated Successfully!", Toast.LENGTH_SHORT).show();
            SharedPrefs.getInstance().setUser(ChangePasswordActivity.this, user);
            finish();
        }
    }
}
