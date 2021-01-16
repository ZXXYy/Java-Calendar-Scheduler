package ClientToServerResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import scheduler.CalendarEvent;
import scheduler.ClientInfo;
import scheduler.SharedVariable;
import scheduler.Week;

public class LoginToServer implements Runnable{
	private Socket socket;
	private String username;
	private String password;
	private SharedVariable control;
	ObjectOutputStream toServer;
	ObjectInputStream fromServer;
	public LoginToServer(ObjectInputStream fromServer, ObjectOutputStream toServer,Socket socket, String user, String pw, Object control) {
		this.socket = socket;
		this.username = user;
		this.password = pw;
		this.control = (SharedVariable)control;
		this.toServer = toServer;
		this.fromServer = fromServer;
		System.out.println("System start!");
	}
	@Override
	public void run() {
		System.out.println("System start!");
		
			
				try {
					toServer.writeObject("LOGIN");
					toServer.flush();
					ClientInfo c = new ClientInfo("0", username, password);
					toServer.writeObject(c);
					String line = (String)fromServer.readObject();
					if(line.equalsIgnoreCase("Successfully Login!")) {
						control.flag = true;
					}else if(line.equals("Wrong password!") || line.equals("No such user!")) {
						JOptionPane.showMessageDialog(null, "用户名或密码错误"); 
						toServer.close();
						fromServer.close();
						socket.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
	}
	
}
