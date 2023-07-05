package com.example.newenergyschool.ativity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newenergyschool.R;


public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_menu);
    }

    public void myCabinet(View view) {
        Intent intent = new Intent(this, DirectoryRegisterActivity.class);
        startActivity(intent);
    }
    public void contacts (View view) {//TODO: make contact page
        Intent intent = new Intent(this, DirectoryRegisterActivity.class);
        startActivity(intent);
    }


}