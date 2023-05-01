package com.example.selecthelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.selecthelper.ui.login.LoginActivity;


public class MapMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_menu);
    }

    public void myCabinet(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}