package listeners;

import java.util.EventListener;

import events.CalendarEventClickEvent;

public interface CalendarEventDoubleClickListener extends EventListener {
    // Event dispatch methods
    void calendarEventDoubleClick(CalendarEventClickEvent e);
}
