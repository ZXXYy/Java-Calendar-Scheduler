package events;

import java.awt.AWTEvent;
import java.awt.Point;

import scheduler.CalendarEvent;

public class WidgetReviseEvent extends AWTEvent{
	private CalendarEvent event;
	private boolean moveWidget;

    public WidgetReviseEvent(Object source, CalendarEvent event, boolean moveWidget) {
        super(source, 0);
        this.event = event;
        this.moveWidget = moveWidget;
    }

	public CalendarEvent getEvent() {
		return event;
	}

	public boolean isMoveWidget() {
		return moveWidget;
	}

}
