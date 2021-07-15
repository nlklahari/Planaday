package com.example.planaday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Takes user directly to main activity if already logged in
        if (ParseUser.getCurrentUser() != null) {
            Log.i(TAG, "User already logged in, directly launched main activity");
            launchMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        // Log In Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        // Sign Up through text view
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick signup button");
                launchSignUpActivity();
            }
        });
    }

    /**
     * Verifies login and takes user to main activity
     * @param username
     * @param password
     */
    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Toast.makeText(LoginActivity.this, "Invalid Username and/or Password", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                launchMainActivity();
                Log.d(TAG, "Successful login", e);
                Toast.makeText(LoginActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Takes user to Sign Up Activity
     */
    private void launchSignUpActivity() {
        Log.i(TAG, "Launched sign up activity");
        Intent intent = new Intent(this, SignUpActivity.class);
        this.startActivity(intent);
    }

    /**
     * Takes user to main activity
     */
    private void launchMainActivity() {
        Log.i(TAG, "Launched main activity");
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }
}