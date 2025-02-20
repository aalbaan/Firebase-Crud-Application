package com.example.firebasecrudapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // creating variable for edit text, textview,
    // button, progress bar and firebase auth.
    private TextInputEditText userNameEtd,pwdEtd;
    private Button loginBtn;
    private ProgressBar loadingPB;
    private TextView registerTV;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initializing all our variables
        userNameEtd = findViewById(R.id.idEtdUserName);
        pwdEtd = findViewById(R.id.idEdtPwd);
        loginBtn = findViewById(R.id.idBtnLogin);
        loadingPB = findViewById(R.id.idPBLoading);
        registerTV = findViewById(R.id.idTVRegister);
        mAuth = FirebaseAuth.getInstance();

        // adding click listener for our new user tv.
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);

            }
        });
        // adding on click listener for our login button.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hiding our progress bar.
                loadingPB.setVisibility(View.VISIBLE);

                // getting data from our edit text on below line.
                String userName = userNameEtd.getText().toString();
                String pwd = pwdEtd.getText().toString();

                // on below line validating the text input.
                if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginActivity.this, "Please enter your credentials",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    // on below line we are calling a sign in method and passing email and password to it.

                    mAuth.signInWithEmailAndPassword(userName,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // on below line we are checking if the task is success or not.
                            if (task.isSuccessful()){
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this,"Login Succesfull..." ,Toast.LENGTH_SHORT).show();
                                // on below line we are opening our mainactivity.
                                Intent i  = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                // hiding our progress bar and displaying a toast message.
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Please enter valid user credentials..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


        @Override
            protected void onStart(){
            super.onStart();
            // in on start method checking if
            // the user is already sign in
        FirebaseUser user =mAuth.getCurrentUser();
        if (user != null){
            // if the user is not null then we are
            // opening a main activity on below line.
            Intent i =new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            this.finish();

        }
    }
}