package com.example.selecthelper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.selecthelper.model.Direction;

import java.util.ArrayList;
import java.util.List;

public class WritePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_page);
        List<String> list = new ArrayList<>();
        for (Direction s : MainActivity.fullDirectionList) {
            list.add(s.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setDrawingCacheBackgroundColor(Color.BLACK);
        spinner.setAdapter(adapter);

    }

}