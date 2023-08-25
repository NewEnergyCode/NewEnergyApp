package app.newenergyschool.decorators;

import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.List;

public class EventDecorator implements DayViewDecorator {

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
        view.addSpan(new DotSpan(15, Color.rgb(255, 102, 51))); // Замена цвета
    }


}
