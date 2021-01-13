package listeners;

import java.awt.Point;
import java.util.EventListener;

import events.CalendarClickEvent;

public interface CalendarClickListener extends EventListener {
    // Event dispatch methods
    void calendarClick(CalendarClickEvent e);
}

