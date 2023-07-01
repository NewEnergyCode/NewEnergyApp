package com.example.newenergyschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newenergyschool.ui.login.LoginActivity;


public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_menu);
    }

    public void myCabinet(View view) {
        Intent intent = new Intent(this, DirectoryRegister.class);
        startActivity(intent);
    }
    public void contacts (View view) {//TODO: make contact page
        Intent intent = new Intent(this, DirectoryRegister.class);
        startActivity(intent);
    }


}