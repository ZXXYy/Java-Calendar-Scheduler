package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

import scheduler.CalendarEvent;
import scheduler.ClientInfo;
import serverToClientResponse.LoginCheck;
import serverToClientResponse.UpdateDB;

public class HandleAClient implements Runnable {
	private Socket socket;
	private Connection dbConection;
	private ReadWriteLock lock;
	public HandleAClient(Socket socket, Connection dbConection, ReadWriteLock lock) {
		this.socket = socket;
		this.dbConection = dbConection;
		this.lock = lock;
	}
	@Override
	public void run() {
		// Create data input and output streams
        ObjectOutputStream toClient = null;
        ObjectInputStream fromClient = null;
        try {
        	fromClient = new ObjectInputStream(socket.getInputStream());
        	toClient  = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        	e.printStackTrace();
            return;
        }
        while (true) {
            try {
            	System.out.println("[Server] In the Handle a client!");
            	while(fromClient.available()==-1);
                String line = (String)fromClient.readObject();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                	System.out.println("[Server] Socket close...");
                    socket.close();
                    ThreadedServer.clientNo--;
                    return;
                } else if(line.equalsIgnoreCase("LOGIN")){
                	
                	while(fromClient.available()!=-1) {
                		ClientInfo ctsi =(ClientInfo)fromClient.readObject();
                		System.out.println("[Server] "+ctsi.toString());
                		new Thread(new LoginCheck(toClient, dbConection,ctsi.getName(),ctsi.getPassword(),lock)).start();
                		break;
                	}
                    
                } else if(line.equalsIgnoreCase("UPDATE")) {
                	while(fromClient.available()!=-1) {
                		String username = (String)fromClient.readObject();
                		System.out.println("username = "+username);
                		while(fromClient.available()==-1);
                		ArrayList<CalendarEvent> events =(ArrayList<CalendarEvent>)fromClient.readObject();
                		System.out.println("[Server] receieved Events!");
                		new Thread(new UpdateDB(toClient, dbConection, events,username, lock)).start();
                		break;
                	}
                	
                } else if(line.substring(0,4).equalsIgnoreCase("SHARE")) {
                	
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
		
		
	}

}
