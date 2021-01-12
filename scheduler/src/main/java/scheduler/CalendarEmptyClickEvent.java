package scheduler;

import java.awt.*;
import java.time.LocalDateTime;

public class CalendarEmptyClickEvent extends AWTEvent {
    private LocalDateTime dateTime;
    private Point p;

    public CalendarEmptyClickEvent(Object source, LocalDateTime dateTime, Point p) {
        super(source, 0);
        this.dateTime = dateTime;
        this.p = p;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public Point getP() {
    	return p;
    }
}
