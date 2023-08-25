package app.newenergyschool.ativity;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import app.newenergyschool.R;
import app.newenergyschool.decorators.EventDecorator;
import app.newenergyschool.model.Lesson;
import app.newenergyschool.model.LoggedInUser;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CalendarActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private List<CalendarDay> highlightedDates = new ArrayList<>();
    private List<Lesson> lessonList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = findViewById(R.id.calendarView);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        String telephoneNumber = currentUser.getPhoneNumber();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");

        calendarView.setSelectionColor(Color.rgb(0, 188, 212));
        calendarView.setDateSelected(calendarDate(null), true);
        calendarView.addDecorator(todayDay());

        assert telephoneNumber != null;
        databaseReference.child(telephoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getDatabaseValue(dataSnapshot);
                calendarView.addDecorators(new EventDecorator(highlightedDates)); // Применение декоратора с выделенными датами
                calendarView.invalidateDecorators();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибок при чтении из базы данных
            }
        });
        // Установите цвет выделения для каждой даты
//        calendarView.addDecorator(new EventDecorator(highlightedDates));
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(
                    @NonNull MaterialCalendarView widget,
                    @NonNull CalendarDay date,
                    boolean selected) {
                showDirectionAndTimeOfLesson(date, lessonList);
//                databaseReference.child(telephoneNumber).child(dateString).getDatabase().getReference().child("coursesName");
//                calendarDirection.setText(lesson);
//                String time = dataSnapshot.child("time").getValue(String.class);
            }
        });

    }

    public CalendarDay convertDateToCalendarDay(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
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

    public void showDirectionAndTimeOfLesson(CalendarDay date, List<Lesson> lessonList) {
        // Обработка выбранной даты
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        // Преобразование даты в строку
//                databaseReference.child(telephoneNumber).child(dateString).setValue(new Lesson(telephoneNumber, "Мини сад", "12.00", "12-07-2023"));
        TextView calendarDirection = findViewById(R.id.calendar_direction);
        TextView calendarTime = findViewById(R.id.calendar_time);
        String dateString = dateFormat.format(date.getDate());
        String lesson = null;
        String time = null;

        for (Lesson les : lessonList) {
            if (les.getDayOfWeek().equals(dateString)) {
                lesson = les.getCoursesName();
                time = les.getTime();
            }
        }
        if (lesson != null) {
            calendarDirection.setText(lesson);
            calendarDirection.setVisibility(View.VISIBLE);
            calendarTime.setText(time);
            calendarTime.setVisibility(View.VISIBLE);
        } else {
            calendarDirection.setVisibility(View.GONE);
            calendarTime.setVisibility(View.GONE);
        }
    }

    public void getDatabaseValue(DataSnapshot dataSnapshot) {
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
            Lesson lesson =
                    new Lesson(
                            childSnapshot.child("telephoneNumber").getValue(String.class),
                            childSnapshot.child("coursesName").getValue(String.class),
                            childSnapshot.child("time").getValue(String.class),
                            childSnapshot.child("dayOfWeek").getValue(String.class)
                    );
            lessonList.add(lesson);
            String date = childSnapshot.getKey();
            CalendarDay calendarDay = convertDateToCalendarDay(date);
            highlightedDates.add(calendarDay);
        }
    }
}

