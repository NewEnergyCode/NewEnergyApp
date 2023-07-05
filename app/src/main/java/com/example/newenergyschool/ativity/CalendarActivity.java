package com.example.newenergyschool.ativity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import com.example.newenergyschool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String selectedDate;
    private FirebaseUser currentUser;
    private String telephoneNumber;
    private MaterialCalendarView calendarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        List<CalendarDay> highlightedDates = new ArrayList<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        telephoneNumber = currentUser.getPhoneNumber();
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");
//        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
//
//            @Override
//            public void onDateSelected(
//                    @NonNull MaterialCalendarView widget,
//                    @NonNull CalendarDay date,
//                    boolean selected) {
//                // Обработка выбранной даты

                databaseReference.child(telephoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String date = childSnapshot.getKey();
                            CalendarDay calendarDay = convertDateToCalendarDay(date);
                            highlightedDates.add(calendarDay);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Обработка ошибок при чтении из базы данных
                    }
                });
                // Установите цвет выделения для каждой даты
                calendarView.addDecorator(new EventDecorator(highlightedDates));
//            }
//        });

    }

    private class EventDecorator implements DayViewDecorator {

        private final List<CalendarDay> highlightedDates;

        public EventDecorator(List<CalendarDay> highlightedDates) {
            this.highlightedDates = highlightedDates;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return highlightedDates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(10, Color.RED)); // Замените цветом
        }


    }
        public CalendarDay convertDateToCalendarDay(String date) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                Date parsedDate = dateFormat.parse(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(parsedDate);
                return CalendarDay.from(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

