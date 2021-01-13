package MVC;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import scheduler.CalendarEvent;
import scheduler.EventWidget;
import scheduler.WeekCalendar;

public class CalendarView extends JPanel implements ActionListener{
	private CalendarModel model;
	private CalendarController controller;
	public CalendarView() {
		
	}
	public void setModel(CalendarModel newModel) {
		model = newModel;
		if(model != null){
			model.addActionListener(this);
		}
	}
	
	public void setController(CalendarController newController) {
		controller = newController;
	}
	public CalendarController getController() {
		return controller;
	}
	
	public CalendarModel getModel(){
		return model;
	}
	 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("addEvent")) {
			ArrayList<CalendarEvent> events = model.getEvents();
			CalendarEvent newEvent = model.getNewEvent();
			EventWidget eventAdd = new EventWidget(newEvent.getDate(),newEvent.getStart());
			this.removeAll();
			displayEvent(events);
			displayWidget(eventAdd, newEvent);
			repaint();
		}
	}
	void displayWidget(EventWidget eventAdd, CalendarEvent newEvent) {
		Point weekCalLoc = controller.getWeekCal().getLocation();
		int x = (int)controller.getWeekCal().dayToPixel(newEvent.getDate().plusDays(1).getDayOfWeek());
		int y = (int)((controller.getWeekCal().timeToPixel(newEvent.getStart())+
					  controller.getWeekCal().timeToPixel(newEvent.getEnd()))/2
					  +weekCalLoc.getY()-eventAdd.getHeight()/2);
		eventAdd.setBounds(x, y, 275, 250);
    	eventAdd.setBorder(null);
    	eventAdd.setBackground(null);
    	eventAdd.setOpaque(false);
    	add(eventAdd);
	}
	void displayEvent(ArrayList<CalendarEvent> events) {
		
		for(CalendarEvent event :events) {
			JPanel eventJP = new JPanel();
        	JTextArea eventText = new JTextArea("新建日程");
        	JTextArea eventTime = new JTextArea(event.getStart().toString());
        	
        	eventText.setFont(new Font("楷体",Font.BOLD,16));
        	eventText.setForeground(Color.WHITE);
        	eventText.setBorder(null);
        	eventText.setBackground(null);
        	eventText.setOpaque(false);
        	
        	eventTime.setFont(new Font("楷体",Font.PLAIN,12));
        	eventTime.setForeground(Color.WHITE);
        	eventTime.setBorder(null);
        	eventTime.setBackground(null);
        	eventTime.setOpaque(false);
        	
        	Point weekCalLoc = controller.getWeekCal().getLocation();
        	int x = (int)controller.getWeekCal().dayToPixel(event.getDate().getDayOfWeek());
        	int y = (int)(controller.getWeekCal().timeToPixel(event.getStart())+weekCalLoc.getY());
        	int width = (int)controller.getWeekCal().getDayWidth();
        	int height = (int)(controller.getWeekCal().getTimeScale()*60*60);
        	System.out.println("x="+x+" y="+y+" width="+width+" height="+height);
        	eventJP.setBounds(x,y,width,height);
        	eventJP.setBackground(new Color(63,181,245));
        	eventJP.setLayout(new GridLayout(2,1));
        	eventJP.add(eventTime);
        	eventJP.add(eventText);
        	add(eventJP);
		}
	}

}
