package ClientToServerResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import scheduler.CalendarEvent;

public class SendEventsToServer implements Runnable{
	private ArrayList<CalendarEvent>  events;
	private String username;
	public SendEventsToServer(ArrayList<CalendarEvent> events, String username) {
		this.events = events;
		this.username = username;
		System.out.println("[Client] Start SendEventsToServer!");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		InetAddress address;
		try {
			address = InetAddress.getLocalHost();
			Socket socket =  new Socket(address, 1975);
			System.out.println("[Client] Connected to Server");
			ObjectOutputStream toServer = new  ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
			toServer.writeObject("UPDATE");
			System.out.println("[Client] Send Update");
			toServer.writeObject(username);
			toServer.writeObject(events);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
