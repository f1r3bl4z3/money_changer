package com.android.moneychanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewOrders(View view){
        Intent i = new Intent(getApplicationContext(), OrdersActivity.class);
        startActivity(i);

    }

    public void createOrder(View view){
        Intent i = new Intent(getApplicationContext(), RequestorMainActivity.class);
        startActivity(i);

    }

}