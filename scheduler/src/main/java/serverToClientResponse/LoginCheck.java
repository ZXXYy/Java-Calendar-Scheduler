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

import scheduler.CalendarEvent;

public class LoginCheck implements Runnable {
	
	private Socket socket;
	private Connection dbConection;
	private String name;
	private String password;
	
	public LoginCheck(Socket socket,Connection dbConnection,String name,String password) {
		this.socket = socket;
		this.dbConection = dbConection;
		this.name =name;
		this.password = password;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
			String sql1="select * from User where user_name == ?";
			PreparedStatement ptmt=dbConection.prepareStatement(sql1.toString());
			ptmt.setString(1, name);
			ResultSet rs=ptmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("password").equals(password)) {
					 out.writeBytes("Successfully Login!" + "\n\r");
					 out.flush();
					 sql1="select * from CalendarEvent where owner == ?";
					 ptmt=dbConection.prepareStatement(sql1.toString());
					 ptmt.setString(1, name);
					 rs = ptmt.executeQuery();
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
						
						 String []temp =colorString.split(",");
						 Color color = new Color(Integer.valueOf(temp[0]),Integer.valueOf(temp[1]),Integer.valueOf(temp[2]));
						 CalendarEvent  event = new CalendarEvent(day, start, end, text, color);
						 event.setLocation(location);
						 event.setNotes(notes);
						 temp = invitorString.split(",");
						 Collections.addAll(event.getInvitors(), temp);
						 temp = pendInvitorString.split(",");
						 Collections.addAll(event.getPendingInvitors(), temp);
						 if(pending==0) event.setPending(false);
						 else event.setPending(true);
						 objOut.writeObject(event);
					 }
				}
				else {
					out.writeBytes("Wrong password!" + "\n\r");
					out.flush();
				}
			}
			else {
				out.writeBytes("No such user!" + "\n\r");
				out.flush();
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
