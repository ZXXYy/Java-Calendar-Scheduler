package serverToClientResponse;

import java.awt.Color;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

import scheduler.CalendarEvent;

public class LoginCheck implements Runnable {
	
	private ObjectOutputStream toClient;
	private Connection dbConection;
	private String name;
	private String password;
	private ReadWriteLock lock;
	
	public LoginCheck(ObjectOutputStream toClient,Connection dbConection,String name,String password, ReadWriteLock lock) {
		this.toClient = toClient;
		this.dbConection = dbConection;
		this.name =name;
		this.password = password;
		this.lock = lock;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			String sql1="select * from User where name == ?";
			PreparedStatement ptmt=dbConection.prepareStatement(sql1.toString());
			ptmt.setString(1, name);
			ResultSet rs=ptmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("password").equals(password)) {
					toClient.writeObject("Successfully Login!");
					toClient.flush();
					lock.readLock().lock();
					try {
					 sql1="select * from CalendarEvent where owner == ?";
					 ptmt=dbConection.prepareStatement(sql1.toString());
					 ptmt.setString(1, name);
					 rs = ptmt.executeQuery();
					}catch(SQLException e) {
						e.printStackTrace();
					}finally {
						lock.readLock().unlock();
					}
					 ArrayList<CalendarEvent>  events = new ArrayList<>();
					 while(rs.next()) {
						 String startTime = rs.getString("startTime");
						 String endTime = rs.getString("endTime");
						 String text = rs.getString("eventName");
						 String location = rs.getString("location");
						 String invitorString = rs.getString("invitor");
						 String pendInvitorString = rs.getString("pendingInvitor");
						 String notes = rs.getString("notes");
						 String colorString = rs.getString("color");
						 int  pending = rs.getInt("pendingReact");
						 
						 
						 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
						 LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
						 LocalDate day = dateTime.toLocalDate();
						 LocalTime start = dateTime.toLocalTime();
						 dateTime = LocalDateTime.parse(endTime, formatter);
						 LocalTime end = dateTime.toLocalTime();
						 String []temp;
						 Color color ;
						 if(colorString!=null) {
							 temp =colorString.split(",");
							 color = new Color(Integer.valueOf(temp[0]),Integer.valueOf(temp[1]),Integer.valueOf(temp[2]));
						 }
						 else color = new Color(63,181,245);
						 CalendarEvent  event = new CalendarEvent(day, start, end, text, color);
						 event.setLocation(location);
						 event.setNotes(notes);
						 if(invitorString!=null&& !invitorString.equals("")) {
							 temp = invitorString.split(",");
							 Collections.addAll(event.getInvitors(), temp);
						 }
						 if(pendInvitorString!=null && !pendInvitorString.equals("")) {
							 temp = pendInvitorString.split(",");
							 Collections.addAll(event.getPendingInvitors(), temp);
						 }
						 if(pending==0) event.setPending(false);
						 else event.setPending(true);
						 toClient.writeObject(event);
						 System.out.println("[Server] sending event");
					 }
					 
					 toClient.writeObject("All Events send!");
					 System.out.println("[Server] Write event");
					 
				}
				else {
					toClient.writeObject("Wrong password!");
					toClient.flush();
				}
			}
			else {
				toClient.writeObject("No such user!");
				toClient.flush();
			}
			
		} catch (IOException e) {
			System.out.println("[Server] Error IO in LoginCheck!");
			e.printStackTrace();
		}catch(SQLException e){
			System.out.println("[Server] Error SQL in LoginCheck!");
			e.printStackTrace();
		}
	}

}
