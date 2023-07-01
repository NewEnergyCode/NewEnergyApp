package com.example.newenergyschool;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newenergyschool.adapters.CategoryAdapter;
import com.example.newenergyschool.adapters.DirectionAdapter;
import com.example.newenergyschool.model.Catagory;
import com.example.newenergyschool.model.Direction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView categoryRecycler;
    RecyclerView directionRecycler;
    CategoryAdapter categoryAdapter;
    static DirectionAdapter directionAdapter;
    static List<Direction> directionList = new ArrayList<>();

    static List<Direction> fullDirectionList = new ArrayList<>();


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Resources res = getResources();
        mAuth = FirebaseAuth.getInstance();

        // Проверяем состояние аутентификации при запуске активности
        FirebaseUser currentUser = mAuth.getCurrentUser();
        TextView textView = findViewById(R.id.userRegister);

        if (currentUser != null) {
            // Пользователь уже аутентифицирован, отображаем надпись для зарегистрированных пользователей
            textView.setText("Авторизированный пользователь: " + currentUser.getPhoneNumber());
            showButtonsForAuthOrNotAuthUsers(true);

        } else {
            // Пользователь не аутентифицирован, скрываем надпись для зарегистрированных пользователей
            textView.setText("Авторизируйтесь для отображения дополнительных возможностей.");
            showButtonsForAuthOrNotAuthUsers(false);
        }
        textView.setVisibility(View.VISIBLE);


        List<Catagory> categoryAgeList = new ArrayList<>();
        categoryAgeList.add(new Catagory(1, "1 год"));
        categoryAgeList.add(new Catagory(2, "2 года"));
        categoryAgeList.add(new Catagory(3, "3 года"));
        categoryAgeList.add(new Catagory(4, "4 года"));
        categoryAgeList.add(new Catagory(5, "5-12 лет"));


        setCategoryRecycler(categoryAgeList);


        directionList.add(new Direction(1, "Раннее\nразвитие", "direction",
                "#FFFFFFFF", "1-3 года", "early_development", res.getString(R.string.direction_rev_earlyDev), 1));
        directionList.add(new Direction(3, "Мини-сад", "direction", "#FFFFFFFF",
                "2-5 лет", "direction_logo", res.getString(R.string.direction_rev_min), 2));
        directionList.add(new Direction(4, "Английский\nязык ", "direction", "#FFFFFFFF",
                "2-6 лет", "school", res.getString(R.string.direction_english), 2));
        directionList.add(new Direction(5, "Рисование", "direction", "#FFFFFFFF",
                "4-12 лет", "drawing", res.getString(R.string.direction_rev_draw), 3));
        directionList.add(new Direction(6, "Подготовка\nк школе ", "direction", "#FFFFFFFF",
                "4-6 лет", "school", res.getString(R.string.direction_school), 3));

        fullDirectionList.addAll(directionList);
        setDirectionRecycler(directionList);
    }

    private void setCategoryRecycler(List<Catagory> catagoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        categoryRecycler = findViewById(R.id.CategoryRecycler);
        categoryRecycler.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, catagoryList);
        categoryRecycler.setAdapter(categoryAdapter);
    }

    private void setDirectionRecycler(List<Direction> directionList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        directionRecycler = findViewById(R.id.directionRec);
        directionRecycler.setLayoutManager(layoutManager);

        directionAdapter = new DirectionAdapter(this, directionList);
        directionRecycler.setAdapter(directionAdapter);
    }

    public void showButtonsForAuthOrNotAuthUsers(Boolean s) {
        Button buttonGallery = findViewById(R.id.button_gallery_main);
        Button buttonAuthOrCalendar = findViewById(R.id.button_auth_or_calendar_main);
        if (s) {
            buttonGallery.setVisibility(View.VISIBLE);
            buttonAuthOrCalendar.setText("Календарь занятий");
            buttonAuthOrCalendar.setVisibility(View.VISIBLE);
        } else {
            buttonGallery.setVisibility(View.GONE);
            buttonAuthOrCalendar.setText("Авторизация");
            buttonAuthOrCalendar.setVisibility(View.VISIBLE);
            buttonAuthOrCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DirectoryRegister.class);
                    startActivity(intent);
                }
            });

        }
    }

    public static void showDirectionByAge(int age) {
        directionList.clear();
        directionList.addAll(fullDirectionList);

        List<Direction> filterDirections = new ArrayList<>();
        switch (age) {
            case 1:
                for (Direction s : directionList) {
                    if (s.getAgeCategory() == 1) {
                        filterDirections.add(s);
                    }
                }
                break;
            case 2:
            case 3:
                for (Direction s : directionList) {
                    if (s.getAgeCategory() == 1 || s.getAgeCategory() == 2) {
                        filterDirections.add(s);
                    }
                }
                break;
            case 4:
            case 5:
                for (Direction s : directionList) {
                    if (s.getAgeCategory() == 2 || s.getAgeCategory() == 3) {
                        filterDirections.add(s);
                    }
                }
                break;
        }
        directionList.clear();
        directionList.addAll(filterDirections);
        directionAdapter.notifyDataSetChanged();
    }

    public void showMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }


}