package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import JDBC.SQLiteJDBC;

public class ThreadedServer {

    static final int PORT = 1975;
    protected static int clientNo  =  0;
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    public static void main(String args[]) {
    	ThreadedServer server = new ThreadedServer();
        ServerSocket serverSocket = null;
        Socket socket = null;
        Connection dbConnection = SQLiteJDBC.ConnectToDB();
        
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("[Server] Listening......");
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                System.out.println("[Server] Connection Error");
            }
            server.clientNo++;
            InetAddress inetAddress = socket.getInetAddress();
            System.out.println("[Server] Connected to Client"+server.clientNo+
            		"'s host name is"+inetAddress.getHostName());
            System.out.println("[Server] Connected to Client"+server.clientNo+
            		"'s IP address is"+inetAddress.getHostAddress());
            // new thread for a client
            new Thread(new  HandleAClient(socket, dbConnection, lock)).start();
        }
    }
}
