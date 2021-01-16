package MVC;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import scheduler.CalendarEvent;

public class CalendarModel {
	private ArrayList<CalendarEvent> events = new ArrayList<>();
	private boolean doubleClicked = false;
	private CalendarEvent newEvent;
	private LocalDate currentStartDay;
	private Point loc;
	
	private ArrayList<ActionListener> actionListenerList;
	
	public CalendarModel(LocalDate currentStartDay) {
		this.currentStartDay = currentStartDay;
	}
	
	public void doubleClick(CalendarEvent event, Point loc) {
		doubleClicked = true;
		newEvent = event;
		boolean flag = false;
		for(CalendarEvent e:events) {
			if(newEvent.getDate().compareTo(e.getDate())==0 && 
			 newEvent.getStart().compareTo(e.getStart())>=0 &&
			 newEvent.getEnd().compareTo(e.getEnd())<=0) {
				newEvent = events.get(events.indexOf(e));
				flag = true;
				break;
			}
		}
		if(!flag)
			events.add(event);
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "addEvent"));
		//processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "showWidgets"));
	}
	
	public void reviseStartDay(LocalDate newStartDay, String comment) {
		this.currentStartDay = newStartDay;
		if(comment.equals("nextWeek"))
			processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "showHeadersNextWeek"));
		else if(comment.equals("preWeek")) {
			processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "showHeadersPreWeek"));
		}
		else if(comment.equals("preMonth")) {
			processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "showHeadersPreMonth"));
		}
		else if(comment.equals("nextMonth")) {
			processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "showHeadersNextMonth"));
		}
	}
	
	public void singleClick(Point p) {
		this.loc = p;
		if(doubleClicked) {
			processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "clearWidget"));
		}
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "IsOnEvent"));
	}
	
	public void updateWidget(CalendarEvent newEvent) {
		this.newEvent = newEvent;
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "clearWidget"));
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "updateEvent"));
	}
	
	public synchronized void addActionListener(ActionListener l){
		if(actionListenerList == null){
			actionListenerList = new ArrayList<ActionListener>();
		}
		 actionListenerList.add(l);
	}
	
	public synchronized void removeActionListener(ActionListener l){
		 if(actionListenerList != null && actionListenerList.contains(l)){
			 actionListenerList.remove(l);
		 }
	}
	
	private void processEvent(ActionEvent e){
		ArrayList list;
		
		synchronized(this){
			if(actionListenerList == null) return;
			list = (ArrayList)actionListenerList.clone();
	    }
	 	for(int i = 0; i < list.size(); i++){
	 		ActionListener listener = (ActionListener)list.get(i);
	 		listener.actionPerformed(e);
	 	}
	}

	
// getters and setters
	public ArrayList<CalendarEvent> getEvents() {
		return events;
	}
	public void addEvents(CalendarEvent event) {
		events.add(event);
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "showEvent"));
	}
	public void addEventsList(ArrayList<CalendarEvent> eventList) {
		for(CalendarEvent event:eventList)
			events.add(event);
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "showEvent"));
	}
	public void setEvents(ArrayList<CalendarEvent> events) {
		this.events = events;
	}

	public boolean isDoubleClicked() {
		return doubleClicked;
	}

	public void setDoubleClicked(boolean doubleClicked) {
		this.doubleClicked = doubleClicked;
	}

	public LocalDate getCurrentStartDay() {
		return currentStartDay;
	}

	public void setCurrentStartDay(LocalDate currentStartDay) {
		this.currentStartDay = currentStartDay;
	}

	public CalendarEvent getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(CalendarEvent newEvent) {
		this.newEvent = newEvent;
	}

	public Point getLoc() {
		return loc;
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}
}
