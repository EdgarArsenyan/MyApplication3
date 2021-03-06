package com.noringerazancutyun.myapplication.activ;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.noringerazancutyun.myapplication.R;
//import com.noringerazancutyun.myapplication.map.MapsActivity;

public class EmailPasswordActivity extends BaseActivity  implements
        View.OnClickListener {

    private static final String TAG = "EmailPassword";

    Button login, register, laterBtn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText loginEmail, loginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_password);
        register = findViewById(R.id.registerBtn);
        login = findViewById(R.id.loginBtn);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        laterBtn = findViewById(R.id.laterBtn);
        loginEmail = findViewById(R.id.email_text);
        loginPassword = findViewById(R.id.password_text);
        mAuth = FirebaseAuth.getInstance();
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EmailPasswordActivity.this, SearchActivity.class);
//                startActivity(intent);
//            }
//        });
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EmailPasswordActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }


    private boolean validateForm() {
        boolean valid = true;

        String email = loginEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            loginEmail.setError("Required.");
            valid = false;
        } else {
            loginEmail.setError(null);
        }

        String password = loginPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            loginPassword.setError("Required.");
            valid = false;
        } else {
            loginPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.signOut();
        updateUI(null);
    }


    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
//        if (user != null) {
//
//            findViewById(R.id.loginBtn).setVisibility(View.GONE);
//            findViewById(R.id.edit_layout).setVisibility(View.GONE);
//
//        } else {
//
//            findViewById(R.id.loginBtn).setVisibility(View.VISIBLE);
//            findViewById(R.id.edit_layout).setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.registerBtn) {
            createAccount(loginEmail.getText().toString(), loginPassword.getText().toString());
            Intent intent = new Intent(EmailPasswordActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else if (i == R.id.loginBtn) {
            signIn(loginEmail.getText().toString(), loginPassword.getText().toString());
        }

//            if (i == R.id.loginBtn) {
//                signIn(loginEmail.getText().toString(), loginPassword.getText().toString());
//                Intent intent = new Intent(EmailPasswordActivity.this, MapsActivity.class);
//                startActivity(intent);
//            }

    }
}
