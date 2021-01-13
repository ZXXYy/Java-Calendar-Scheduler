package events;

import java.awt.AWTEvent;

import scheduler.CalendarEvent;
import scheduler.EventWidget;

public class ClearWidgetEvent extends AWTEvent {

    private EventWidget widget;

    public ClearWidgetEvent(Object source, EventWidget widget) {
        super(source, 0);
        this.widget = widget;
    }

    public EventWidget getWidget() {
        return widget;
    }
}
