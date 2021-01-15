package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Connection;

import scheduler.ClientInfo;
import serverToClientResponse.LoginCheck;

public class HandleAClient implements Runnable {
	private Socket socket;
	private Connection dbConection;
	public HandleAClient(Socket socket, Connection dbConection) {
		this.socket = socket;
		this.dbConection = dbConection;
	}
	@Override
	public void run() {
		// Create data input and output streams
		InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
           
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                	System.out.println("[Server] Socket close...");
                    socket.close();
                    return;
                } else if(line.equalsIgnoreCase("LOGIN")){
                	ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                	while(ois.available()!=-1) {
                		ClientInfo ctsi =(ClientInfo)ois.readObject();
                		System.out.println("[Server] "+ctsi.toString());
                		new Thread(new LoginCheck(socket, dbConection,ctsi.getName(),ctsi.getPassword())).start();
                	}
                    
                } else if(line.substring(0,5).equalsIgnoreCase("UPDATE")) {
                	
                } else if(line.substring(0,4).equalsIgnoreCase("SHARE")) {
                	
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
		
		
	}

}
