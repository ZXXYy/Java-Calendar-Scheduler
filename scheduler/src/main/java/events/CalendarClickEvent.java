package events;

import java.awt.AWTEvent;
import java.awt.Point;

import scheduler.CalendarEvent;


public class CalendarClickEvent extends AWTEvent{
	 private CalendarEvent calendarEvent;

	    public CalendarClickEvent(Object source, CalendarEvent calendarEvent) {
	        super(source, 0);
	        this.calendarEvent = calendarEvent;
	    }

	    public CalendarEvent getCalendarEvent() {
	        return calendarEvent;
	    }
}
