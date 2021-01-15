package MVC;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.WindowConstants;

import scheduler.CalendarEvent;
import scheduler.ClientInfo;
import scheduler.Week;
import serverToClientResponse.LoginCheck;

public class Main {
	private CalendarController controller;
	private CalendarModel model;
	private CalendarView view;
	private static JFrame frm = new JFrame();
	private static JLayeredPane layeredPane = frm.getLayeredPane();
	private  Socket socket;
	private  static boolean flag = false;
	public static void main(String args[] ) {
		
		Main m = new Main();
		while(!flag);
		frm.setSize(1000,750);
		m.controller.setBounds(0,0,1000,700);
		m.view.setBounds(0, 0, 1000, 700);
		m.view.setBackground(null);
		m.view.setBorder(null);
		m.view.setOpaque(false);
		layeredPane.add(m.controller,JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(m.view,JLayeredPane.MODAL_LAYER);
		
//		frm.add(view);
		frm.setVisible(true);
	    frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public Main() {
		InetAddress address;
		try {
			address = InetAddress.getLocalHost();
			socket =  new Socket(address, 1978);
			System.out.println("[Client] Connected to Server");
			frm.setSize(300,400);
			frm.setLayout(new GridLayout(2,1));
			JPanel JP = new  JPanel();
			
			JLabel userJL =  new JLabel("用户名");
			JLabel pwJL = new JLabel("密码");
			JTextField userJTF = new JTextField();
			JTextField pwJTF = new JPasswordField();
			JButton loginJB = new JButton("登录");
			
			JP.setLayout(new GridLayout(2,2));
			JP.add(userJL);
			JP.add(userJTF);
			JP.add(pwJL);
			JP.add(pwJTF);
			frm.add(JP);
			frm.add(loginJB);
			frm.setVisible(true);
		    frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		    
			loginJB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					while(true) {
					try {
						DataOutputStream toServer2 = new DataOutputStream(socket.getOutputStream());
						ObjectOutputStream toServer = new  ObjectOutputStream(socket.getOutputStream());
						BufferedReader fromServerString = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
						toServer2.writeBytes("LOGIN\n");
						toServer2.flush();
						String username = userJTF.getText();
						String password  = pwJTF.getText();
						ClientInfo c = new ClientInfo("0", username, password);
						toServer.writeObject(c);
						String line = fromServerString.readLine();
						if(line.compareTo("Successfully Login!")==0) {
							frm.removeAll();
							Week week = new Week(LocalDate.now());
							LocalDate startDay = week.getDay(DayOfWeek.MONDAY);
							model = new CalendarModel(startDay);
							
							view = new CalendarView();
							controller = new CalendarController();
							
							view.setModel(model);
							view.setController(controller);
							controller.setModel(model);
							while(ois.available()!=-1) {
								CalendarEvent event =(CalendarEvent)ois.readObject();
								model.addEvents(event);
							}
							flag = true;
							break;
						}else if(line.compareTo("Wrong password!")==0 || line.compareTo("No such user!")==0) {
							JOptionPane.showMessageDialog(null, "用户名或密码错误", "提示",JOptionPane.PLAIN_MESSAGE); 
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						System.out.println("[Client] Get event erro");
						e.printStackTrace();
					}
				}
				}
			
			});
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[Client] Connection Error");
			e.printStackTrace();
		}
		//model.addActionListener(this);
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		if(e.getActionCommand().equals("addEvent")) {
//			System.out.println("Here");
//			
//			layeredPane.repaint();
//		}
//	}
}
