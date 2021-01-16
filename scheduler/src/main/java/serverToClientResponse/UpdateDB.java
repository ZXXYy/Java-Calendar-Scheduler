package serverToClientResponse;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;

import scheduler.CalendarEvent;

public class UpdateDB implements Runnable{
	private ObjectOutputStream toClient;
	private Connection dbConection;
	private String username;
	private ArrayList<CalendarEvent> events;
	private ReadWriteLock lock;
	
	public UpdateDB(ObjectOutputStream toClient,Connection dbConection,ArrayList<CalendarEvent> events, String username, ReadWriteLock  lock) {
		this.toClient = toClient;
		this.dbConection =dbConection;
		this.events = events;
		this.username = username;
		this.lock = lock;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		PreparedStatement ptmt;
		try {
			lock.writeLock().lock();
			String sql1="delete from CalendarEvent where owner == ?";
			ptmt = dbConection.prepareStatement(sql1.toString());
			ptmt.setString(1, username);
			int deletNum=ptmt.executeUpdate();
			dbConection.commit();
			System.out.println("[Server] delete "+deletNum);
			sql1 = "insert into CalendarEvent(id, owner, startTime, endTime, eventName, location, notes, invitor, color, pendingInvitor, pendingReact)"+
					"values(?,?,?,?,?,?,?,?,?,?,?)";
			ptmt = dbConection.prepareStatement(sql1.toString());
			dbConection.setAutoCommit(false);
			for(CalendarEvent event:events) {
				LocalDate day = event.getDate();
				LocalTime start = event.getStart();
				LocalTime end = event.getEnd();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				String startTime = dtf.format(LocalDateTime.of(day, start));
				String endTime = dtf.format(LocalDateTime.of(day, end));
				ArrayList<String> invitors = event.getInvitors();
				ArrayList<String> pendingInvitors =  event.getPendingInvitors();
				StringBuffer invitorStringBuff = new  StringBuffer();
				StringBuffer pedningInvitorStringBuff  = new  StringBuffer();
				int rgb = event.getColor().getRGB();
				int r = (rgb & 16711680) >> 16;
				int g = (rgb & 65280) >> 8;
				int b = (rgb & 255);
				String color = r+","+g+","+b;
				
				if(!invitors.isEmpty()) {
					for(String  invitor:invitors) {
						invitorStringBuff.append(invitor);
						invitorStringBuff.append(",");
					}
				}
				if(!pendingInvitors.isEmpty()) {
					for(String  invitor:pendingInvitors) {
						pedningInvitorStringBuff.append(invitor);
						pedningInvitorStringBuff.append(",");
					}
				}
				
				
				ptmt.setInt(1,event.hashCode()*31+username.hashCode());
				ptmt.setString(2, username);
				ptmt.setString(3, startTime);
				ptmt.setString(4, endTime);
				ptmt.setString(5, event.getText());
				ptmt.setString(6, event.getLocation());
				ptmt.setString(7, event.getNotes());
				ptmt.setString(8, invitorStringBuff.toString());
				ptmt.setString(9, color);
				ptmt.setString(10, pedningInvitorStringBuff.toString());
				ptmt.setInt(11,  0);
				int insertNum = ptmt.executeUpdate();
				System.out.println("[Server] insert "+insertNum);
			}
			dbConection.commit();
			toClient.writeObject("update DB finised!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			lock.writeLock().unlock();
		}
		
		return;
	}
}
