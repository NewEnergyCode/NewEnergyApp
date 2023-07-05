package com.example.newenergyschool.ativity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.newenergyschool.R;
import com.example.newenergyschool.model.Direction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class WritePageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_page);
        mAuth = FirebaseAuth.getInstance();

        // Проверяем состояние аутентификации при запуске активности
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Пользователь уже аутентифицирован, отображаем надпись для зарегистрированных пользователей
            EditText editText = findViewById(R.id.editTextPhone);
            editText.setText(currentUser.getPhoneNumber());
            editText.setVisibility(View.VISIBLE);
        } else {
            // Пользователь не аутентифицирован, скрываем надпись для зарегистрированных пользователей
            EditText editText = findViewById(R.id.editTextPhone);
            editText.setVisibility(View.VISIBLE);
        }
        List<String> list = new ArrayList<>();
        for (Direction s : MainActivity.fullDirectionList) {
            list.add(s.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list){
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) super.getView(position, convertView, parent);
            textView.setTextColor(Color.parseColor("#B3B2B2")); // Здесь указывается ваш цвет
            return textView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
            textView.setTextColor(Color.parseColor("#B3B2B2")); // Здесь указывается ваш цвет
            return textView;
        }
    };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);


    }

}