package com.example.planaday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private EditText etName;
    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Sign up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUp(name, email, username, password);
            }
        });
    }

    /**
     * Verifies credentials and creates a new account
     * @param name
     * @param email
     * @param username
     * @param password
     */
    private void signUp(String name, String email, String username, String password) {
        ParseUser user = new ParseUser();
        user.put("name", name);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Something went wrong");
                    if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(SignUpActivity.this, "Failed to create new account. Check again.", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                Toast.makeText(SignUpActivity.this, "New account created successfully!", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Success signing up!");
                launchMainActivity();
            }
        });
    }

    /**
     * Takes user to main activity
     */
    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }

}