package listeners;

import java.util.EventListener;

import events.CalendarDoubleClickEvent;

public interface CalendarDoubleClickListener extends EventListener {
    // Event dispatch methods
    void calendarDoubleClick(CalendarDoubleClickEvent e);
}
