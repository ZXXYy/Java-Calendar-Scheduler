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
//        events.add(new CalendarEvent(LocalDate.of(2021, 1, 4), LocalTime.of(14, 0), LocalTime.of(14, 20), "Test 11/11 14:00-14:20"));
//        events.add(new CalendarEvent(LocalDate.of(2021, 1, 4), LocalTime.of(9, 0), LocalTime.of(9, 20), "Test 14/11 9:00-9:20"));
//        events.add(new CalendarEvent(LocalDate.of(2021, 1, 5), LocalTime.of(12, 0), LocalTime.of(13, 20), "Test 15/11 12:00-13:20"));
//        events.add(new CalendarEvent(LocalDate.of(2021, 1, 6), LocalTime.of(9, 0), LocalTime.of(9, 20), "Test 16/11 9:00-9:20"));
//        events.add(new CalendarEvent(LocalDate.of(2021, 1, 7), LocalTime.of(12, 15), LocalTime.of(14, 20), "Test 17/11 12:15-14:20"));
//        events.add(new CalendarEvent(LocalDate.of(2021, 1, 8), LocalTime.of(9, 30), LocalTime.of(10, 00), "Test 18/11 9:30-10:00"));
//        events.add(new CalendarEvent(LocalDate.of(2021, 1, 8), LocalTime.of(16, 00), LocalTime.of(16, 45), "Test 18/11 16:00-16:45"));

        WeekCalendar cal = new WeekCalendar();

//        cal.addCalendarEventDoubleClickListener(e -> System.out.println(e.getCalendarEvent()));
//        cal.addCalendarEmptyDoubleClickListener(e -> {
//        	LocalDateTime localDateTime = e.getDateTime();
//        	LocalTime roundedTime = Calendar.roundTime(e.getDateTime().toLocalTime(), 30);
//        	Point p = e.getP();
//        	
//        	double dayWidth = e.getDayWidth();
//        	double timeScale = e.getTimeScale();
//        	System.out.println("datWidth="+dayWidth);
//        	System.out.println("timeScale="+timeScale);
//        	EventWidget eventAdd = new EventWidget(localDateTime.toLocalDate(),roundedTime);
//        	eventAdd.setBounds((int)p.getX(), (int)p.getY()-eventAdd.getHeight()/2+frm.getHeight()/15, 275, 250);
//        	eventAdd.setBorder(null);
//        	eventAdd.setBackground(null);
//        	events.add(new CalendarEvent(localDateTime.toLocalDate(), 
//        								 roundedTime.minusMinutes(30),
//        								 roundedTime.plusMinutes(30),
//        								 "新建日程"));
//        	JPanel eventJP = new JPanel();
//        	JTextArea eventText = new JTextArea("新建日程");
//        	JTextArea eventTime = new JTextArea(roundedTime.minusMinutes(30).toString());
//        	
//        	eventText.setFont(new Font("楷体",Font.BOLD,16));
//        	eventText.setForeground(Color.WHITE);
//        	eventText.setBorder(null);
//        	eventText.setBackground(null);
//        	eventText.setOpaque(false);
//        	
//        	eventTime.setFont(new Font("楷体",Font.PLAIN,12));
//        	eventTime.setForeground(Color.WHITE);
//        	eventTime.setBorder(null);
//        	eventTime.setBackground(null);
//        	eventTime.setOpaque(false);
//        	
//        	eventJP.setBounds((int)(p.getX()-dayWidth),(int)(p.getY()-timeScale*30*60+frm.getHeight()/15+1),(int)dayWidth,(int)(timeScale*60*60));
//        	eventJP.setBackground(new Color(63,181,245));
//        	eventJP.setLayout(new GridLayout(2,1));
//        	eventJP.add(eventTime);
//        	eventJP.add(eventText);
//        	
//        	layeredPane.add(eventAdd,200,0);
//        	layeredPane.add(eventJP,200,0);
//        	System.out.println(e.getDateTime());
//            System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
//        });
        cal.addCalendarClickListener(e ->{
        	System.out.println("It's clicked!");
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
