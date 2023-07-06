package com.example.newenergyschool.ativity;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newenergyschool.R;
import com.example.newenergyschool.decorators.EventDecorator;
import com.example.newenergyschool.model.Lesson;
import com.example.newenergyschool.model.LoggedInUser;
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
//                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
//
//                // Преобразование даты в строку
//                String dateString = dateFormat.format(date.getDate());
//                databaseReference.child(telephoneNumber).child(dateString).setValue(new Lesson(telephoneNumber, "Мини сад", "12.00", "12-07-2023"));
//            }
//        });
        calendarView.setSelectionColor(Color.rgb(0, 188, 212));
        calendarView.setDateSelected(calendarDate(null), true);
        calendarView.addDecorator(todayDay());

        databaseReference.child(telephoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String date = childSnapshot.getKey();
                    CalendarDay calendarDay = convertDateToCalendarDay(date);
                    highlightedDates.add(calendarDay);
                }
                calendarView.addDecorators(new EventDecorator(highlightedDates)); // Применение декоратора с выделенными датами
                calendarView.invalidateDecorators();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибок при чтении из базы данных
            }
        });
        // Установите цвет выделения для каждой даты
        calendarView.addDecorator(new EventDecorator(highlightedDates));


    }


    public CalendarDay convertDateToCalendarDay(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
            Date parsedDate = dateFormat.parse(date);
            return calendarDate(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CalendarDay calendarDate(@Nullable Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return CalendarDay.from(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    public DayViewDecorator todayDay() {
        return new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return day.equals(calendarDate(null));
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(10, Color.rgb(0, 188, 212))); // Замените цветом
            }
        };
    }
}

