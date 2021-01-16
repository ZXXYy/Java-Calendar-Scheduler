package ClientToServerResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import MVC.CalendarModel;
import scheduler.CalendarEvent;

public class GetEventsFromServer {
	public static void getEventsFromServer(ObjectInputStream fromServer, ObjectOutputStream toServer, Socket socket, CalendarModel  model) {
		try {
			ArrayList<CalendarEvent> eventList = new  ArrayList<CalendarEvent>();
			while(fromServer.available()!=-1) {
				Object temp = fromServer.readObject();
				if(temp instanceof  String) {
					toServer.writeObject("QUIT");
					socket.close();
					fromServer.close();
					toServer.close();
					break;
				}
				CalendarEvent event = (CalendarEvent)temp;
				eventList.add(event);		
				System.out.println("Add Event!");
			}
			model.addEventsList(eventList);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
