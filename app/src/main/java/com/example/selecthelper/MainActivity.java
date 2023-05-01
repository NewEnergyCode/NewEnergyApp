package com.example.selecthelper;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selecthelper.adapters.CategoryAdapter;
import com.example.selecthelper.adapters.DirectionAdapter;
import com.example.selecthelper.model.Catagory;
import com.example.selecthelper.model.Direction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView categoryRecycler;
    RecyclerView directionRecycler;
    CategoryAdapter categoryAdapter;
    static DirectionAdapter directionAdapter;
    static List<Direction> directionList = new ArrayList<>();

    static List<Direction> fullDirectionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Resources res = getResources();

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
        Intent intent = new Intent(this, MapMenu.class);
        startActivity(intent);
    }

}