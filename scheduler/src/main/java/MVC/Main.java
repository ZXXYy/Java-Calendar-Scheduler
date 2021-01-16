package MVC;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.WindowConstants;

import ClientToServerResponse.GetEventsFromServer;
import ClientToServerResponse.LoginToServer;
import ClientToServerResponse.SendEventsToServer;
import scheduler.CalendarEvent;
import scheduler.ClientInfo;
import scheduler.SharedVariable;
import scheduler.Week;
import server.HandleAClient;
import serverToClientResponse.LoginCheck;

public class Main implements ActionListener{
	public static Object lock = new Object();
	private CalendarController controller;
	private CalendarModel model;
	private CalendarView view;
	private String username;
	private static JFrame frm = new JFrame();
	private static JLayeredPane layeredPane;
	private static Socket socket;
	private static ObjectOutputStream toServer;
	private static ObjectInputStream fromServer;
	private final static SharedVariable control = new SharedVariable();
	public static void main(String args[] ) {

		frm.setSize(300,200);
		frm.setLayout(null);
		JPanel JP1 = new  JPanel();
		JPanel JP2 = new  JPanel();
		JLabel userJL =  new JLabel("用户名");
		JLabel pwJL = new JLabel("密码");
		JTextField userJTF = new JTextField();
		JPasswordField pwJTF = new JPasswordField();
		JButton loginJB = new JButton("登录");
		
		JP1.setLayout(new GridLayout(1,2));
		JP1.setBounds(20,0,300,50);
		JP2.setLayout(new GridLayout(1,2));
		JP2.setBounds(20,55,300,50);
		JP1.add(userJL);
		JP1.add(userJTF);
		JP2.add(pwJL);
		JP2.add(pwJTF);
		loginJB.setBounds(0,120,300,50);
		frm.add(JP2);
		frm.add(JP1);
		frm.add(loginJB);
		frm.setVisible(true);
	    frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		loginJB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("[Client] ButtonClicked");
				InetAddress address;
				try {
					address = InetAddress.getLocalHost();
					socket =  new Socket(address, 1975);
					System.out.println("[Client] Connected to Server");
					toServer = new  ObjectOutputStream(socket.getOutputStream());
					fromServer = new ObjectInputStream(socket.getInputStream());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				new Thread(new LoginToServer(fromServer, toServer, socket, userJTF.getText(),pwJTF.getText(), control)).start();
			}
			
			
		});
		
		while(!control.flag);
		control.flag =  false;
		Main m = new Main();
		m.username = userJTF.getText();
		
	
		frm.dispose();
		frm = new JFrame();
		layeredPane = frm.getLayeredPane();
		frm.setSize(1000,750);
		m.controller.setBounds(0,0,1000,700);
		m.view.setBounds(0, 0, 1000, 700);
		m.view.setBackground(null);
		m.view.setBorder(null);
		m.view.setOpaque(false);
		layeredPane.add(m.controller,JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(m.view,JLayeredPane.MODAL_LAYER);

		frm.setVisible(true);
	    frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    
	    GetEventsFromServer.getEventsFromServer(fromServer, toServer, socket, m.model);
	    frm.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
 
            }
 
            @Override
            public void windowClosing(WindowEvent e) {
            	System.out.println("Window is closing!");
            	new Thread(new SendEventsToServer(m.model.getEvents(), m.username)).start();
            	System.out.println("Window is closing!");
            }
 
            @Override
            public void windowClosed(WindowEvent e) {
            	
            }
 
            @Override
            public void windowIconified(WindowEvent e) {
            }
 
            @Override
            public void windowDeiconified(WindowEvent e) {
 
            }
 
            @Override
            public void windowActivated(WindowEvent e) {
 
            }
            
            @Override
            public void windowDeactivated(WindowEvent e) {
 
            }
        });


	    frm.repaint();
	}
	
	public Main() {
		
		Week week = new Week(LocalDate.now());
		LocalDate startDay = week.getDay(DayOfWeek.MONDAY);
		model = new CalendarModel(startDay);
		
		view = new CalendarView();
		controller = new CalendarController();
		
		view.setModel(model);
		view.setController(controller);
		controller.setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		
		
	}
}
