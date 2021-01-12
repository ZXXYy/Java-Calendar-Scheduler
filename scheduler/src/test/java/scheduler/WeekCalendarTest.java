package scheduler;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class WeekCalendarTest {
    public static void main(String[] args) {
        JFrame frm = new JFrame();
        JLayeredPane layeredPane = frm.getLayeredPane();

        ArrayList<CalendarEvent> events = new ArrayList<>();
        events.add(new CalendarEvent(LocalDate.of(2021, 1, 4), LocalTime.of(14, 0), LocalTime.of(14, 20), "Test 11/11 14:00-14:20"));
        events.add(new CalendarEvent(LocalDate.of(2021, 1, 4), LocalTime.of(9, 0), LocalTime.of(9, 20), "Test 14/11 9:00-9:20"));
        events.add(new CalendarEvent(LocalDate.of(2021, 1, 5), LocalTime.of(12, 0), LocalTime.of(13, 20), "Test 15/11 12:00-13:20"));
        events.add(new CalendarEvent(LocalDate.of(2021, 1, 6), LocalTime.of(9, 0), LocalTime.of(9, 20), "Test 16/11 9:00-9:20"));
        events.add(new CalendarEvent(LocalDate.of(2021, 1, 7), LocalTime.of(12, 15), LocalTime.of(14, 20), "Test 17/11 12:15-14:20"));
        events.add(new CalendarEvent(LocalDate.of(2021, 1, 8), LocalTime.of(9, 30), LocalTime.of(10, 00), "Test 18/11 9:30-10:00"));
        events.add(new CalendarEvent(LocalDate.of(2021, 1, 8), LocalTime.of(16, 00), LocalTime.of(16, 45), "Test 18/11 16:00-16:45"));

        WeekCalendar cal = new WeekCalendar(events);

        cal.addCalendarEventClickListener(e -> System.out.println(e.getCalendarEvent()));
        cal.addCalendarEmptyClickListener(e -> {
        	LocalDateTime localDateTime = e.getDateTime();
        	Point p = e.getP();
        	
        	EventWidget eventAdd = new EventWidget(localDateTime.toLocalDate(),Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
        	eventAdd.setBounds((int)p.getX(), (int)p.getY()-eventAdd.getHeight()/2+frm.getHeight()/15, 275, 250);
        	eventAdd.setBorder(null);
        	eventAdd.setBackground(null);
        	
        	
        	layeredPane.add(eventAdd,200,0);
        	System.out.println(e.getDateTime());
            System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
        });
        // use lambda expression
        JButton goToTodayBtn = new JButton("Today");
        goToTodayBtn.addActionListener(e -> cal.goToToday());

        JButton nextWeekBtn = new JButton(">");
        nextWeekBtn.addActionListener(e -> cal.nextWeek());

        JButton prevWeekBtn = new JButton("<");
        prevWeekBtn.addActionListener(e -> cal.prevWeek());

        JButton nextMonthBtn = new JButton(">>");
        nextMonthBtn.addActionListener(e -> cal.nextMonth());

        JButton prevMonthBtn = new JButton("<<");
        prevMonthBtn.addActionListener(e -> cal.prevMonth());

        JPanel weekControls = new JPanel();
        weekControls.add(prevMonthBtn);
        weekControls.add(prevWeekBtn);
        weekControls.add(goToTodayBtn);
        weekControls.add(nextWeekBtn);
        weekControls.add(nextMonthBtn);
        
        frm.setSize(1000, 700);
        weekControls.setBounds(0,0,frm.getWidth(),frm.getHeight()/15);
        cal.setBounds(0, frm.getHeight()/15, frm.getWidth(), 13*frm.getHeight()/15);
        layeredPane.add(weekControls, 100);
        layeredPane.add(cal, 100 );

        
        frm.setVisible(true);
        frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
}
