package com.injecsulin.elonmusk.injecsulin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.app.ProgressDialog;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etUsername;
    private EditText etPassword;
    Button btnLogin;
    TextView btnRegister;
    TextView txtStatusTextView;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.txtEmailLogin);
        etPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnSignUp);
        txtStatusTextView = (TextView) findViewById(R.id.txtStatusTextView);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("443215582397-j1hkq2c3ohhca69ldd59ga8jipmhjcql.apps.googleusercontent.com")
                .requestEmail()
                .build();

        final Intent intent = new Intent(this, RegisterActivity.class);


        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                if(validateForm()) {
                    signIn(username, password);
                }
                else{
                    txtStatusTextView.setText("Email or Password required");
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // [START sign_in_with_email]

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            txtStatusTextView.onVisibilityAggregated(true);
                            txtStatusTextView.setText("Login success");
                            // updateUI(user);
                            startActivity(new Intent(LoginActivity.this, InjectorActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            txtStatusTextView.setText("Incorrect username or password");
                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                        progressDialog.dismiss();
                    }
                });

        // [END sign_in_with_email]
        }
        public void showProgressDialog(){
            //pbrLogin.setVisibility(View.VISIBLE);
        }

        public void hideProgressDialog(){
            //pbrLogin.setVisibility(View.INVISIBLE);
        }

        private boolean validateForm() {
            boolean valid = true;

            String email = etUsername.getText().toString();  //Fixme: When clicked , null pointer exception: Attempt to invoke vistual method... null object reference
            if (TextUtils.isEmpty(email)) {
                etUsername.setError("Required.");
                valid = false;
            } else {
                etUsername.setError(null);
            }

            String password = etPassword.getText().toString();
            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Required.");
                valid = false;
            } else {
                etPassword.setError(null);
            }

            return valid;
        }

        private void updateUI(FirebaseUser user) {
            //hideProgressDialog();
            if (user != null) {
                //  mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                //   user.getEmail(), user.isEmailVerified()));
                //  mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

                //    findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
                //    findViewById(R.id.email_password_fields).setVisibility(View.GONE);
                //    findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

                //    findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
            } else {
                //   mStatusTextView.setText(R.string.signed_out);
                //    mDetailTextView.setText(null);

                //  findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
                //   findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
                //   findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
            }
        }

    }

