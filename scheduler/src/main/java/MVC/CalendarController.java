package MVC;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import scheduler.Calendar;
import scheduler.CalendarEvent;
import scheduler.DayCalendar;
import scheduler.WeekCalendar;

public class CalendarController extends JPanel implements ActionListener{
	private CalendarModel model;
	private WeekCalendar weekCal;
	private DayCalendar dayCal;
	JButton goToTodayBtn = new JButton("Today");
	JButton nextWeekBtn = new JButton(">");
	JButton prevWeekBtn = new JButton("<");
	JButton nextMonthBtn = new JButton(">>");
	JButton prevMonthBtn = new JButton("<<");
	
	public CalendarController() {
		weekCal = new WeekCalendar();

		JPanel weekControls = new JPanel();
        weekControls.add(prevMonthBtn);
        weekControls.add(prevWeekBtn);
        weekControls.add(goToTodayBtn);
        weekControls.add(nextWeekBtn);
        weekControls.add(nextMonthBtn);
        
        // add Listeners
        // weekCal.addCalendarEventDoubleClickListener(e -> System.out.println(e.getCalendarEvent()));
        weekCal.addCalendarDoubleClickListener(e -> {
        	System.out.println(e.getDateTime());
        	System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
        	LocalDateTime localDateTime = e.getDateTime();
        	LocalTime roundedTime = Calendar.roundTime(localDateTime.toLocalTime(), 30);
        	CalendarEvent event = new CalendarEvent(localDateTime.toLocalDate(),
        											roundedTime.minusMinutes(30),
        											roundedTime.plusMinutes(30),
        											"新建日程");
        	
        	Point loc = new Point(e.getP());
        	model.doubleClick(event, loc);
        	
        });
        weekCal.addCalendarClickListener(e->{
        	Point loc = e.getPoint();
        	model.singleClick(e.getPoint());
        });
        goToTodayBtn.addActionListener(e -> System.out.println("It's today"));
        nextWeekBtn.addActionListener(e -> {
        	LocalDate startDate = model.getCurrentStartDay();
        	model.reviseStartDay(startDate.plusDays(7), "nextWeek");
        	
        });
        prevWeekBtn.addActionListener(e -> {
        	LocalDate startDate = model.getCurrentStartDay();
        	model.reviseStartDay(startDate.minusDays(7), "preWeek");
        });
        nextMonthBtn.addActionListener(e -> {
        	LocalDate startDate = model.getCurrentStartDay();
        	model.reviseStartDay(startDate.plusWeeks(4), "nextMonth");
        });
        prevMonthBtn.addActionListener(e -> {
        	LocalDate startDate = model.getCurrentStartDay();
        	model.reviseStartDay(startDate.minusWeeks(4), "preMonth");
        });
        
//        setSize(1000, 700);
        setLayout(new BorderLayout());
        add(weekControls, BorderLayout.NORTH);
        add(weekCal, BorderLayout.CENTER);
        
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("showHeadersNextWeek")) {
			weekCal.nextWeek();
		}
		else if(e.getActionCommand().equals("showHeadersPreWeek")) {
			weekCal.prevWeek();
		}
		else if(e.getActionCommand().equals("showHeadersPreMonth")) {
			weekCal.prevMonth();
		}
		else if(e.getActionCommand().equals("showHeadersNextMonth")) {
			weekCal.nextMonth();
		}
		
	}
	
	public void setModel(CalendarModel newModel) {
		model = newModel;
		if(model != null){
			model.addActionListener(this);
		}
	}
	
	public WeekCalendar getWeekCal() {
		return weekCal;
	}

	

	
}
