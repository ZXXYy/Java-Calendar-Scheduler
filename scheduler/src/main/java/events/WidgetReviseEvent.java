package events;

import java.awt.AWTEvent;
import java.awt.Point;

import scheduler.CalendarEvent;

public class WidgetReviseEvent extends AWTEvent{
	private CalendarEvent event;

    public WidgetReviseEvent(Object source, CalendarEvent event) {
        super(source, 0);
        this.event = event;
    }

	public CalendarEvent getEvent() {
		return event;
	}

}
