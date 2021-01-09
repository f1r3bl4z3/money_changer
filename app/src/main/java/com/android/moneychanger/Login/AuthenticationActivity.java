package com.android.moneychanger.Login;


import androidx.fragment.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.android.moneychanger.MainActivity;
import com.android.moneychanger.R;
import com.android.moneychanger.Utils.Reference;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class AuthenticationActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    PhoneFragment phoneFragment = new PhoneFragment();

    WelcomeFragment welcomeFragment = new WelcomeFragment();
//    TermsAndConditionsFragment tncFragment = new TermsAndConditionsFragment();
//    PrivacyPolicyFragment privacyPolicyFragment = new PrivacyPolicyFragment();

    boolean firstVisit = true;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    String phoneNumber, mVerificationId;

    ProgressDialog dialog;

    public static final String SHARED_PREF = "sharedPrefs";
    public static final String FIRSTTIME ="firstTime";

    private boolean isFirstTime(){

        SharedPreferences sp = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        return sp.getBoolean(FIRSTTIME,true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        userIsLoggedIn();

        if(isFirstTime())//isFirstTime()
        {
            fm.beginTransaction()
                    .replace(R.id.container, welcomeFragment, "welcomeFragment")
                    .addToBackStack(null)
                    .commit();
        }else
        {
            openPhoneFragment();
        }


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                dialog = ProgressDialog.show(AuthenticationActivity.this, "",
                        "Signing In. Please wait...", true);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                nextClick();
                mVerificationId = verificationId;
            }
        };
    }
//
//    public void openTermsAndConditionsFragment()
//    {
//        fm.beginTransaction()
//                .replace(R.id.container, tncFragment, "TermsAndConditionsFragment")
//                .addToBackStack(null)
//                .commit();
//    }
//
//    public void openPrivacyPolicyFragment()
//    {
//        fm.beginTransaction()
//                .replace(R.id.container, privacyPolicyFragment, "privacyPolicyFragment")
//                .addToBackStack(null)
//                .commit();
//    }

    public void openPhoneFragment()
    {
        fm.beginTransaction()
                .replace(R.id.container, phoneFragment, "phoneFragment")
                .addToBackStack(null)
                .commit();
    }

    public void nextClick(){
        fm.beginTransaction()
                .replace(R.id.container, new CodeFragment(), "CodeFragment")
                .addToBackStack(null)
                .commit();
    }

    public void verifyPhoneNumberWithCode(String code){
        if(code.length() <= 0)
            return;
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user != null){
                        final DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child(Reference.rootNode)
                                .child("Users")
                                .child(user.getUid())
                                .child("userInfo");

                        mUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.exists()){
                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put("id", user.getUid());
                                    userMap.put("phone", user.getPhoneNumber());
                                    userMap.put("name", user.getPhoneNumber());
                                    userMap.put("Terms and Conditions", "agreed");

                                    userMap.put("ac_phone", user.getPhoneNumber());
                                    userMap.put("WA_No", user.getPhoneNumber().substring(1)); // (Int'l format) - w/o plus

                                    mUserDB.updateChildren(userMap);
                                }
                                userIsLoggedIn();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }else{
                    if(dialog!=null)
                        dialog.dismiss();
                }

            }
        });
    }



    public void startPhoneNumberVerification(String phoneNumber) {
        if(phoneNumber.length() <= 0)
            return;

        this.phoneNumber = phoneNumber;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void userIsLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
            return;
        }
    }

    ProgressDialog mDialog;
    public void  showProgressDialog(String message){
        mDialog = new ProgressDialog(AuthenticationActivity.this);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                    if(mDialog.isShowing()) {
                        finish();
                    }
                    return true;
                }
                return false;
            }
        });

        mDialog.setMessage(message);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void  dismissProgressDialog(){
        if(mDialog!=null)
            mDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
