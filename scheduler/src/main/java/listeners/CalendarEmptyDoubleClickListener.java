package listeners;

import java.util.EventListener;

import events.CalendarEmptyClickEvent;

public interface CalendarEmptyDoubleClickListener extends EventListener {
    // Event dispatch methods
    void calendarEmptyDoubleClick(CalendarEmptyClickEvent e);
}
