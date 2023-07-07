package com.example.newenergyschool;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InformActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        String buttonText = getIntent().getStringExtra("text");
        TextView textView = findViewById(R.id.inform_page);
        TextView textView4 = findViewById(R.id.textView4);
        if (buttonText.equals("about")) {
            textView.setText(R.string.about_our_school);
        } else {
            textView4.setText("Контакты:");
            textView.setText(R.string.contacts);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

    }


}