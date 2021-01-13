package events;

import java.awt.*;
import java.time.LocalDateTime;

public class CalendarEmptyClickEvent extends AWTEvent {
    private LocalDateTime dateTime;
    private Point p;
    private double dayWidth;
    private double timeScale;
    public CalendarEmptyClickEvent(Object source, LocalDateTime dateTime, Point p, double dayWidth, double timeScale) {
        super(source, 0);
        this.dateTime = dateTime;
        this.p = p;
        this.dayWidth = dayWidth;
        this.timeScale = timeScale;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public Point getP() {
    	return p;
    }
    public double getDayWidth() {
    	return dayWidth;
    }
    public double  getTimeScale() {
    	return timeScale;
    }
}
