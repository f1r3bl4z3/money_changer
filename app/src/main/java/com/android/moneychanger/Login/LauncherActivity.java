package com.android.moneychanger.Login;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.android.moneychanger.RequestorMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.android.moneychanger.MainActivity;


public class LauncherActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set persistent database (store data in phone cache)
//        if (savedInstanceState == null) {
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        }

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }else{
            Intent intent = new Intent(LauncherActivity.this, AuthenticationActivity.class);
            startActivity(intent);
            finish();
            return;
        }
    }
}
