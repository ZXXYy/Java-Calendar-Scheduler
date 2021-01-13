package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;

import scheduler.Week;

public class Main {
	private CalendarController controller;
	private CalendarModel model;
	private CalendarView view;
	private static JFrame frm = new JFrame();
	private static JLayeredPane layeredPane = frm.getLayeredPane();
	
	public static void main(String args[] ) {
		Main m = new Main();
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
		Week week = new Week(LocalDate.now());
		LocalDate startDay = week.getDay(DayOfWeek.MONDAY);
		model = new CalendarModel(startDay);
		
		view = new CalendarView();
		controller = new CalendarController();
		
		view.setModel(model);
		view.setController(controller);
		controller.setModel(model);
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
