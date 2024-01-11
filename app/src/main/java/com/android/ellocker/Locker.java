package com.android.ellocker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.ellocker.transaction.Transaction;

public class Locker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker);

        Intent getIntent = getIntent();
        Transaction transaction = getIntent.getParcelableExtra("TRANSACTION");

        Log.d("PAGE", "ORDER");

        Log.d("LOCKER+CHECK", "TRANSACTION ID : " + transaction.getTransaction_id());
        Log.d("LOCKER+CHECK", "LOCKER ID : " + transaction.getLocker_Id());
    }
}