package events;

import java.awt.AWTEvent;
import java.awt.Point;

import scheduler.CalendarEvent;


public class CalendarClickEvent extends AWTEvent{
	 private Point p;

	    public CalendarClickEvent(Object source, Point p) {
	        super(source, 0);
	        this.p = p;
	    }

	    public Point getPoint() {
	        return p;
	    }
}
