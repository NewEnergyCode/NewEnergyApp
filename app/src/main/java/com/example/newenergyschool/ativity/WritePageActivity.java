package com.example.newenergyschool.ativity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newenergyschool.R;
import com.example.newenergyschool.model.Direction;
import com.example.newenergyschool.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class WritePageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_page);
        mAuth = FirebaseAuth.getInstance();

        changeTelephoneNumberForAuthUser();
        runDirectionAdapter();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("RegistrationForCourses");
        Button buttonWrite = findViewById(R.id.button_write);
        EditText phone = findViewById(R.id.editTextPhone);
        EditText name = findViewById(R.id.editTextTextPersonName);


        buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telephoneNumber = phone.getText().toString();
                String personName = name.getText().toString();

                String oldString = direction;
                String newString = oldString
                        .replace(" ", "_")
                        .replace("\n", "_");
                databaseReference.child(telephoneNumber).child(newString).setValue(new LoggedInUser(personName, direction))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Запись в базу данных прошла успешно
                                    Toast.makeText(WritePageActivity.this, "Успешная запись!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(WritePageActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    // Возникла ошибка при записи в базу данных
                                    Toast.makeText(WritePageActivity.this, "Ошибка при записи, повторите попытку!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    public void changeTelephoneNumberForAuthUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            EditText editText = findViewById(R.id.editTextPhone);
            editText.setText(currentUser.getPhoneNumber());
            editText.setVisibility(View.VISIBLE);
        } else {
            EditText editText = findViewById(R.id.editTextPhone);
            editText.setVisibility(View.VISIBLE);
        }
    }

    public void runDirectionAdapter() {
        List<String> list = new ArrayList<>();
        for (Direction s : MainActivity.fullDirectionList) {
            list.add(s.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#B3B2B2")); // Здесь указывается ваш цвет
                direction = textView.getText().toString();
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