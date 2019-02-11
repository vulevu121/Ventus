package com.vle.ventus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
//    private String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLogin;
    protected Button mCreate;
    private ProgressBar progress;

//    private FirebaseUser currentUser;

//    @Override
//    public void onStop() {
//        super.onStop();
//        signOut();
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progress = (ProgressBar) findViewById(R.id.loginProgressBar);
        mAuth = FirebaseAuth.getInstance();
        mUsername = (EditText) findViewById(R.id.editUsername);
        mPassword = (EditText) findViewById(R.id.editPassword);
        mLogin = (Button) findViewById(R.id.buttonLogin);
        mCreate = (Button) findViewById(R.id.buttonCreateLogin);

        mCreate.setVisibility(View.INVISIBLE);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(MainActivity.this, TabActivity.class));
        }

    }

    public void onCreateNewAccount(View v) {
        createAccount(mUsername.getText().toString(), mPassword.getText().toString());
//        sendEmailVerification();
    }

    public void onLoginButtonTap(View v) {
//        Toast.makeText(getApplicationContext(), "Logged in successful!", Toast.LENGTH_LONG).show();
        signIn(mUsername.getText().toString(), mPassword.getText().toString());
//        startActivity(new Intent(MainActivity.this, TabActivity.class));
    }

    public void onVerifyEmail(View v) {
        sendEmailVerification();
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mUsername.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mUsername.setError("Required.");
            valid = false;
        } else {
            mUsername.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }

    private void signIn(String email, String password) {
//        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgress();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            Toast.makeText(MainActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, TabActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        hideProgress();

                    }
                });
        // [END sign_in_with_email]
    }

    private void createAccount(String email, String password) {
//        Log.d(TAG, "createAccount:" + email);
//        if (!validateForm()) {
//            return;
//        }

//        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Account created.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

//                            updateUI(null);
                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]Toast.makeText(MainActivity.this, "Account not created.",
                        //                                    Toast.LENGTH_SHORT).show();
                    }
                });
        // [END create_user_with_email]
    }

    private void sendEmailVerification() {
        // Disable button
//        findViewById(R.id.verifyEmailButton).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            final String userEmail = user.getEmail();

            if (!user.isEmailVerified()) {
                user.sendEmailVerification()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // [START_EXCLUDE]
                                // Re-enable button
//                        findViewById(R.id.verifyEmailButton).setEnabled(true);

                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this,
                                            "Verification email sent to " + userEmail,
                                            Toast.LENGTH_SHORT).show();
                                } else {
//                                    Log.e(TAG, "sendEmailVerification", task.getException());
                                    Toast.makeText(MainActivity.this,
                                            "Failed to send verification email.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                // [END_EXCLUDE]
                            }
                        });
                // [END send_email_verification]
            } else {
                Toast.makeText(MainActivity.this, "Already Verified.",
                        Toast.LENGTH_SHORT).show();
            }

        }




    }

    private void signOut() {
        mAuth.signOut();
//        updateUI(null);
    }

    private void showProgress() {
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgress() {
        if (progress != null) {
            progress.setVisibility(View.GONE);
        }
    }
}
