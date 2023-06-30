package com.example.newenergyschool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

public class DirectoryRegister extends AppCompatActivity {
    FirebaseAuth auth;
    Button buttonRegister, buttonVerification;
    private String mVerificationId;

    private static final String TAG = "PhoneAuthActivity";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth firebaseAuth;
    private EditText phone;
    private EditText otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        auth = FirebaseAuth.getInstance();

        buttonRegister = findViewById(R.id.direction_button_write);
        buttonVerification = findViewById(R.id.direction_button_ver);
        phone = this.findViewById(R.id.editTextPhone);
        otp = this.findViewById(R.id.otp);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        String code = credential.getSmsCode();
                        verifyPhoneNumberWithCode(code);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(DirectoryRegister.this, "Ошибка авторизации, попробуйте снова.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        super.onCodeSent(s, token);
                        mVerificationId = s;
                    }
                };
                String telephoneNumber = phone.getText().toString();
                if (telephoneNumber == null) {
                    Toast.makeText(DirectoryRegister.this, "Введите номер телефона.", Toast.LENGTH_SHORT).show();
                } else if (telephoneNumber.toCharArray().length != 10) {
                    Toast.makeText(DirectoryRegister.this, "Введеный номер не верен.", Toast.LENGTH_SHORT).show();
                } else {
                    startPhoneNumberVerification(phone.getText().toString());
                }
            }
        });

        buttonVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumberWithCode(otp.getText().toString());
            }
        });

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
//        if (currentUser.getUid().equals(firebaseAuth.getUid())) {
            updateUI(currentUser);
//        }else {
//            Toast.makeText(DirectoryRegister.this, "Пользоватль существует.", Toast.LENGTH_SHORT).show();
//        }
    }


    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+38" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void verifyPhoneNumberWithCode(String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
        // [END verify_with_code]
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {


        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DirectoryRegister.this, "Авторизация прошла успешно!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DirectoryRegister.this, MainActivity.class));
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


    private void updateUI(FirebaseUser user) {

    }


}