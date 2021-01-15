package MVC;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;

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
		if(e.getActionCommand().equals("addEvent")|| e.getActionCommand().equals("updateEvent")) {
			ArrayList<CalendarEvent> events = model.getEvents();
			CalendarEvent newEvent = model.getNewEvent();
			EventWidget eventAdd = new EventWidget(newEvent);
			eventAdd.addWidgetReviseListener(w->{
				// due to shallow copy we can revise the events in model
				// CalendarEvent reviseEvent = w.getEvent();
				if(w.isMoveWidget()) {
					model.updateWidget(newEvent);
				}
				else {
					Component[] componentList = this.getComponents();
					//Loop through the components
					for(Component c : componentList){
					    //Find the components you want to remove
					    if(!(c instanceof EventWidget)){
					        //Remove it
					        this.remove(c);
					    }
					}
					displayEvent(events, newEvent);
					repaint();
				}
				
			});
			
			this.removeAll();
			setLayout(new OverlayLayout(this));
			displayWidget(eventAdd, newEvent);
			displayEvent(events, newEvent);
			repaint();
		}
		if( e.getActionCommand().equals("showHeadersNextMonth")||
			e.getActionCommand().equals("showHeadersNextWeek") ||
			e.getActionCommand().equals("showHeadersPreWeek")  ||
			e.getActionCommand().equals("showHeadersPreMonth") 
			) {
			ArrayList<CalendarEvent> events = model.getEvents();
			this.removeAll();
			displayEvent(events,null);
			repaint();
		}
		if(e.getActionCommand().equals("clearWidget")) {
			
			Component[] componentList = this.getComponents();
			//Loop through the components
			for(Component c : componentList){
			    //Find the components you want to remove
			    if((c instanceof EventWidget)){
			    	CalendarEvent event = ((EventWidget) c).getEvent();
			    	JTextField eventJTF = ((EventWidget) c).getEventJTF();
			    	JTextField locationJTF = ((EventWidget) c).getLocationJTF();
			    	JTextField inviteJTF = ((EventWidget) c).getInviteJTF();
			    	JTextField noteJTF = ((EventWidget) c).getNoteJTF();
			    	JTextField startHourJTF = ((EventWidget) c).getStartHourJTF();
			    	JTextField startMinJTF = ((EventWidget) c).getStartMinJTF();
			    	JTextField endHourJTF = ((EventWidget) c).getEndHourJTF();
			    	JTextField endMinJTF = ((EventWidget) c).getEndMinJTF();
			    	
			    	String hours = startHourJTF.getText();
			    	String mins = startMinJTF.getText();
			    	if(Integer.parseInt(hours)<8) {
			    		hours = "08";
			    		mins = "00";
			    	}
			    	else if(Integer.parseInt(hours)>=22) {
			    		hours = "21";
			    	}
					
					LocalTime updateStartTime = LocalTime.of(Integer.parseInt(hours), Integer.parseInt(mins), 0);
					event.setStart(updateStartTime);
					
					hours = endHourJTF.getText();
					mins = endMinJTF.getText();
					if(Integer.parseInt(hours)<8) {
			    		hours = "9";
			    	}
			    	else if(Integer.parseInt(hours)>=22) {
			    		mins = "00";
			    		hours = "22";
			    	}
					LocalTime endStartTime = LocalTime.of(Integer.parseInt(hours), Integer.parseInt(mins), 0);
					event.setEnd(endStartTime);
					
			    	event.setText(eventJTF.getText());
			    	event.setLocation(locationJTF.getText());
			    	event.setNotes(noteJTF.getText());
			    	
			    	String text = inviteJTF.getText();
					String [] names = text.split(",");
					ArrayList<String> invitors = event.getInvitors();
					invitors.clear();
					for(String name:names) {
						invitors.add(name);
					}
			        //Remove it
			        this.remove(c);
			    }
			}
			repaint();
		}
		if(e.getActionCommand().equals("IsOnEvent")) {
			ArrayList<CalendarEvent> events = model.getEvents();
			Point p = model.getLoc();
			for(CalendarEvent event :events) {
				if (!isDayInRange(event)) continue;

	            int x0 = (int) controller.getWeekCal().dayToPixel(event.getDate().getDayOfWeek());
	            int y0 = (int) controller.getWeekCal().timeToPixel(event.getStart());
	            int x1 = (int) (controller.getWeekCal().dayToPixel(event.getDate().getDayOfWeek()) + controller.getWeekCal().getDayWidth());;
	            int y1 = (int) controller.getWeekCal().timeToPixel(event.getEnd());

	            if (p.getX() >= x0 && p.getX() <= x1 && p.getY() >= y0 && p.getY() <= y1) {
	            	this.removeAll();
	            	displayEvent(events, event);
	            	repaint();
	                break;
	            }
			}
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
    	//eventAdd.setOpaque(false);
    	add(eventAdd);
	}
	void displayEvent(ArrayList<CalendarEvent> events, CalendarEvent highlightEvent) {
		
		for(CalendarEvent event :events) {
			if(!isDayInRange(event))
				continue;
			
			Point weekCalLoc = controller.getWeekCal().getLocation();
        	int x = (int)controller.getWeekCal().dayToPixel(event.getDate().getDayOfWeek());
        	int y = (int)(controller.getWeekCal().timeToPixel(event.getStart())+weekCalLoc.getY());
        	int width = (int)controller.getWeekCal().getDayWidth();
        	int height = (int)(controller.getWeekCal().timeToPixel(event.getEnd())-
        					controller.getWeekCal().timeToPixel(event.getStart()));
        	
			JPanel eventJP = new JPanel();
        	JLabel eventText = new JLabel(event.getText());
        	JLabel eventTime = new JLabel(event.getStart().toString());
        	
			if(highlightEvent!=null && event.equals(highlightEvent)) {
				eventJP.setBackground(new Color(57,162,203));
			}
			else {
				eventJP.setBackground(new Color(63,181,245));
			}
			
        	eventText.setBounds(0, 0, width, 20);
        	eventText.setFont(new Font("楷体",Font.BOLD,16));
        	eventText.setForeground(Color.WHITE);
        	eventText.setBorder(null);
        	eventText.setBackground(null);
        	eventText.setOpaque(false);
        	
        	eventTime.setBounds(0, 20, width, 20);
        	eventTime.setFont(new Font("楷体",Font.PLAIN,12));
        	eventTime.setForeground(Color.WHITE);
        	eventTime.setBorder(null);
        	eventTime.setBackground(null);
        	eventTime.setOpaque(false);
        	
        	
//        	System.out.println(event);
        	eventJP.setBounds(x,y,width,height);
        	eventJP.setLayout(new GridLayout(2,1));
        	eventJP.add(eventTime);
        	eventJP.add(eventText);
       
        	add(eventJP);
        	
		}
	}
	public boolean isDayInRange(CalendarEvent event) {
		LocalDate currentStartDay = model.getCurrentStartDay();
		if((currentStartDay.toEpochDay()-event.getDate().toEpochDay())>0 ||
			(currentStartDay.plusDays(7).toEpochDay()-event.getDate().toEpochDay())<0)
			return false;
		return true;
			
	}

}
