package scheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.text.Document;

import events.CalendarDoubleClickEvent;
import events.WidgetReviseEvent;
import listeners.CalendarDoubleClickListener;
import listeners.WidgetReviseListener;
public class EventWidget extends WidgetsOutline{
	
	protected static final int width = 270;
	protected static final int height = 240;
	CalendarEvent event;
	
	private JPanel subw;
	private JTextField eventJTF;
	private JTextField locationJTF;
	private JTextField inviteJTF;
	private JTextField noteJTF;
	private JLabel allDayJl;
	private JCheckBox allDayCheckBox;
	private JLabel repeatJL;
	private JLabel startJl;
	private JPanel startDetail;
	private JLabel startDayJL;
	private JLabel startTimeJL;
	private JTextField startHourJTF;
	private JTextField startMinJTF;
	private JLabel endJL;
	private JPanel endDetail;
	private JLabel endDayJL;
	private JLabel endTimeJL;
	private JTextField endHourJTF;
	private JTextField endMinJTF;
	private JComboBox<String> repeatCmb;
	
	private EventListenerList listenerList = new EventListenerList();
	
	public EventWidget(CalendarEvent event) {
		 
		super(width, height);
		this.event = event;
//		setSize(width,height);
//		WidgetsOutline w = new WidgetsOutline(getWidth()-2,getHeight()-5);
		LocalDate day = event.getDate();
		LocalTime eventTime = event.getStart();
		LocalTime endTime = event.getEnd();
		String text = event.getText();
		String location = event.getLocation();
		String notes = event.getNotes();
		ArrayList<String> invitors = event.getInvitors();
		
		subw = new JPanel();
		subw.setPreferredSize(new Dimension(width, height/4));
		eventJTF = new JTextField(text);
		locationJTF = new JTextField(location);
		StringBuffer invitorList = new StringBuffer();
		if(invitors.isEmpty()) {
			inviteJTF = new JTextField("添加受邀人");
		}
		else {
			for(String invitor : invitors) {
				invitorList.append(invitor);
				if(!(invitor.equals(invitors.get(invitors.size()-1))))
					invitorList.append(",");
			}
			inviteJTF = new JTextField(invitorList.toString());
		}
		noteJTF = new JTextField(notes);
		
		mySetBackground(eventJTF);
		mySetBackground(subw);
		mySetBackground(locationJTF);
		mySetBackground(inviteJTF);
		mySetBackground(noteJTF);
		
		eventJTF.setFont(new Font("宋体",Font.PLAIN,20));
		locationJTF.setFont(new Font("宋体",Font.PLAIN,12));
		locationJTF.setForeground(Color.GRAY);
		inviteJTF.setFont(new Font("宋体",Font.PLAIN,12));
		inviteJTF.setForeground(Color.GRAY);
		noteJTF.setFont(new Font("宋体",Font.PLAIN,12));
		noteJTF.setForeground(Color.GRAY);
		
		setLayout(null);
		eventJTF.setBounds(width/10, 5, width, height/8);
		add(eventJTF);
		locationJTF.setBounds(width/10, 5+height/8, width, height/8);
		add(locationJTF);
		subw.setBounds(width/10, 5+height/4, width-width/8, 7*height/15);
		add(subw);
		inviteJTF.setBounds(width/10, 5+height/4+7*height/15, width, height/8);
		add(inviteJTF); 
		noteJTF.setBounds(width/10, 39*height/45, width, height/8);
		add(noteJTF); 
		
		int subw_wid = subw.getWidth();
		int subw_hei = subw.getHeight();
		
		allDayJl = new JLabel("全天：");
		allDayJl.setFont(new Font("宋体",Font.PLAIN,10));
		allDayJl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		allDayCheckBox = new JCheckBox();
		if(eventTime.compareTo(LocalTime.of(8, 0))==0 ||endTime.compareTo(LocalTime.of(22, 0))==0)
			allDayCheckBox.setSelected(true);
		repeatJL = new JLabel("重复：");
		repeatJL.setFont(new Font("宋体",Font.PLAIN,10));
		repeatJL.setHorizontalAlignment(SwingConstants.RIGHT);
		
		startJl = new JLabel("开始时间：");
		startJl.setFont(new Font("宋体",Font.PLAIN,10));
		startJl.setHorizontalAlignment(SwingConstants.RIGHT);

	
		startDetail = new JPanel();
		startDetail.setLayout(null);
		mySetBackground(startDetail);
		
		startDayJL = new JLabel(day.toString());
		
		startHourJTF = new JTextField(String.format("%02d",eventTime.getHour()));
		startMinJTF = new JTextField(String.format("%02d",eventTime.getMinute()));
		 LocalTime time1 = LocalTime.parse("12:00:00");
		if(eventTime.compareTo(time1)<0) {
			startTimeJL = new JLabel("上午");
		}else {
			startTimeJL = new JLabel("下午");
		}
		
		startDayJL.setFont(new Font("宋体",Font.PLAIN,10));
		startTimeJL.setFont(new Font("宋体",Font.PLAIN,10));
		
		
		endJL = new JLabel("结束：");
		endJL.setFont(new Font("宋体",Font.PLAIN,10));
		endJL.setHorizontalAlignment(SwingConstants.RIGHT);
		endDetail = new JPanel();
		endDetail.setLayout(null);
		mySetBackground(endDetail);
		
		endDayJL = new JLabel(day.toString());
		endDayJL.setFont(new Font("宋体",Font.PLAIN,10));
		
		if(eventTime.compareTo(time1)<0) {
			endTimeJL = new JLabel("上午");
		}else {
			endTimeJL = new JLabel("下午");
		}
		endTimeJL.setFont(new Font("宋体",Font.PLAIN,10));
		endHourJTF = new JTextField(String.format("%02d",endTime.getHour()));
		endMinJTF = new JTextField(String.format("%02d",endTime.getMinute()));

		subw.setLayout(null);
		
		allDayJl.setBounds(0,0,subw_wid/4,subw_hei/4);
		subw.add(allDayJl);
		allDayCheckBox.setBounds(subw_wid/4,0,3*subw_wid/4,subw_hei/4);
		subw.add(allDayCheckBox);
		
		startJl.setBounds(0, subw_hei/4, subw_wid/4, subw_hei/4);
		subw.add(startJl);
		startDetail.setBounds(subw_wid/4,subw_hei/4,3*subw_wid/4,subw_hei/4);
		subw.add(startDetail);
		int pan_wid = startDetail.getWidth();
		int pan_hei = startDetail.getHeight();
		startDayJL.setBounds(0, 0, 3*pan_wid/7, pan_hei);
		startTimeJL.setBounds(3*pan_wid/7, 0, pan_wid/7, pan_hei);
		startHourJTF.setBounds(4*pan_wid/7, 0, pan_wid/6, pan_hei);
		startMinJTF.setBounds(4*pan_wid/7+pan_wid/6, 0, pan_wid/6, pan_hei);
		startDetail.add(startDayJL);
		startDetail.add(startTimeJL);
		startDetail.add(startHourJTF);
		startDetail.add(startMinJTF);
		
		
		endJL.setBounds(0, subw_hei/2, subw_wid/4,subw_hei/4);
		subw.add(endJL);
		endDetail.setBounds(subw_wid/4,subw_hei/2,3*subw_wid/4,subw_hei/4);
		subw.add(endDetail);
		pan_wid = endDetail.getWidth();
		pan_hei = endDetail.getHeight();
		endDayJL.setBounds(0, 0, 3*pan_wid/7, pan_hei);
		endTimeJL.setBounds(3*pan_wid/7, 0, pan_wid/7, pan_hei);
		endHourJTF.setBounds(4*pan_wid/7, 0, pan_wid/6, pan_hei);
		endMinJTF.setBounds(4*pan_wid/7+pan_wid/6, 0, pan_wid/6, pan_hei);
		endDetail.add(endDayJL);
		endDetail.add(endTimeJL);
		endDetail.add(endHourJTF);
		endDetail.add(endMinJTF);
		
		repeatCmb=new JComboBox<String>(); 
		repeatCmb.addItem("无");
		repeatCmb.addItem("每天");
		repeatCmb.addItem("每周");
		repeatCmb.addItem("每月");
		repeatCmb.addItem("每年");
		repeatCmb.setFont(new Font("宋体",Font.PLAIN,10));
		repeatCmb.setForeground(Color.BLACK);
		repeatJL.setBounds(0, 3*subw_hei/4, subw_wid/4,subw_hei/4);
		//subw.add(repeatJL);
		repeatCmb.setBounds(subw_wid/4, 3*subw_hei/4, 1*subw_wid/3,subw_hei/4);
		//subw.add(repeatCmb);
		setBackground(new Color(200, 200, 200, 64));
//		add(w);
		setupEventListeners();
	}
	
	public void setupEventListeners() {
		eventJTF.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent documentEvent) {
				String text = eventJTF.getText();
				event.setText(text);
				fireWidgetRevise(event, false);
			}

			@Override
			public void removeUpdate(DocumentEvent documentEvent) {
				String text = eventJTF.getText();
				event.setText(text);
				fireWidgetRevise(event,false);
			}
		});
		
		KeyAdapter startKeyAdapter= new KeyAdapter(){ 
			public void keyPressed(KeyEvent e)    
			{    
				if(e.getKeyChar()==KeyEvent.VK_ENTER )   //按回车键执行相应操作; 
				{ 
					String hours = startHourJTF.getText();
					String mins = startMinJTF.getText();
					LocalTime updateStartTime = LocalTime.of(Integer.parseInt(hours), Integer.parseInt(mins), 0);
					event.setStart(updateStartTime);
					fireWidgetRevise(event,true);
				} 
			}
		};
		KeyAdapter endKeyAdapter= new KeyAdapter(){ 
			public void keyPressed(KeyEvent e)    
			{    
				if(e.getKeyChar()==KeyEvent.VK_ENTER )   //按回车键执行相应操作; 
				{ 
					String hours = endHourJTF.getText();
					String mins = endMinJTF.getText();
					LocalTime endStartTime = LocalTime.of(Integer.parseInt(hours), Integer.parseInt(mins), 0);
					event.setEnd(endStartTime);
					
					fireWidgetRevise(event,true);
				} 
			}
		};
		startHourJTF.addKeyListener(startKeyAdapter);
		startMinJTF.addKeyListener(startKeyAdapter);
		endHourJTF.addKeyListener(endKeyAdapter);
		endMinJTF.addKeyListener(endKeyAdapter);
		
		allDayCheckBox.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e) {
		          if(allDayCheckBox.isSelected()) {
		        	  System.out.println("Here!");
		        	  
		        	  startHourJTF.setText("08");
		        	  startMinJTF.setText("00");
		        	  endHourJTF.setText("22");
		        	  endMinJTF.setText("00");
						fireWidgetRevise(event,true);
		          }
		        }
		      });
	}
	public int getHeight() {
		return height;
	}
	public CalendarEvent getEvent() {
		return event;
	}

	public JTextField getEventJTF() {
		return eventJTF;
	}

	public JTextField getLocationJTF() {
		return locationJTF;
	}

	public JTextField getInviteJTF() {
		return inviteJTF;
	}

	public JTextField getNoteJTF() {
		return noteJTF;
	}

	public JTextField getStartHourJTF() {
		return startHourJTF;
	}

	public JTextField getStartMinJTF() {
		return startMinJTF;
	}

	public JTextField getEndHourJTF() {
		return endHourJTF;
	}

	public JTextField getEndMinJTF() {
		return endMinJTF;
	}

	@Override
    public Dimension getPreferredSize() {
  	  return new Dimension(width, height);
    }
	public void mySetBackground(JComponent com) {
		com.setBorder(null);
		com.setBackground(null);
		com.setOpaque(false);
	}
	
	public void addWidgetReviseListener(WidgetReviseListener l) {
        listenerList.add(WidgetReviseListener.class, l);
    }

    public void removeWidgetReviseListener(WidgetReviseListener l) {
        listenerList.remove(WidgetReviseListener.class, l);
    }

    private void fireWidgetRevise(CalendarEvent event, boolean isMove) {
        Object[] listeners = listenerList.getListenerList();
        WidgetReviseEvent widgetReviseEvent;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == WidgetReviseListener.class) {
            	widgetReviseEvent= new WidgetReviseEvent(this, event, isMove);
                ((WidgetReviseListener) listeners[i + 1]).widgetRevise(widgetReviseEvent);
            }
        }
    }
    
}

abstract class WidgetsOutline extends JPanel{
	int WID_WIDTH; 
	int WID_HEIGHT; 
	int WID_TRI;
    public WidgetsOutline(int wid, int hei) {
    	WID_WIDTH = wid;
    	WID_HEIGHT = hei;
    	WID_TRI = WID_WIDTH/15;
    	setOpaque(false);
    	Color alphaGray = new Color(200, 200, 200, 64);
    	setBackground(alphaGray);
    }
    @Override
    public Dimension getPreferredSize() {
  	  return new Dimension(WID_WIDTH, WID_HEIGHT);
    }
    @Override
    public Insets getInsets() {
        int xOffset = WID_WIDTH ;
        int yOffset = WID_HEIGHT;
        // These are magic numbers, you might like to calculate
        // your own values based on your needs
        Insets insets = new Insets(
        		0,
        		xOffset / 9,
        		yOffset / 10,
        		xOffset / 10);
        return insets;
    }
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	int X = 0;
    	int Y = WID_HEIGHT/2;
    	
    	// the coordinates of the event widget
    	double []xValues = {X, X+WID_TRI*0.5, X+WID_TRI, X+WID_TRI, 
    						X+WID_TRI, X+WID_TRI, X+WID_TRI*1.5,
    						X+WID_WIDTH-WID_TRI*0.5,X+WID_WIDTH,X+WID_WIDTH,
    						X+WID_WIDTH, X+WID_WIDTH,X+WID_WIDTH-0.5*WID_TRI,
    						X+WID_TRI*1.5, X+WID_TRI, X+WID_TRI,
    						X+WID_TRI, X+WID_TRI, X+WID_TRI*0.5};
        double []yValues = {Y, Y-WID_TRI*0.5, Y-WID_TRI, Y-WID_TRI*1.5, 
        					Y-WID_HEIGHT/2+WID_TRI*0.5, Y-WID_HEIGHT/2,Y-WID_HEIGHT/2,
        					Y-WID_HEIGHT/2,Y-WID_HEIGHT/2,Y-WID_HEIGHT/2+0.5*WID_TRI,
        					Y+WID_HEIGHT/2-0.5*WID_TRI,Y+WID_HEIGHT/2, Y+WID_HEIGHT/2,
        					Y+WID_HEIGHT/2, Y+WID_HEIGHT/2,Y+WID_HEIGHT/2-0.5*WID_TRI,
        					Y+WID_TRI*1.5, Y+WID_TRI, Y+WID_TRI*0.5};
        Graphics2D g2 = (Graphics2D) g.create();
    	
    	// Rendering hints try to turn anti-aliasing on which improves quality
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xValues.length);
        path.moveTo(xValues[0], yValues[0]);
        for (int i = 1; i < xValues.length; i=i+3) {
            // Adds a point to the path by drawing a straight 
            // line from the current position to the specified coordinates.
            path.lineTo(xValues[i], yValues[i]);
            path.curveTo(xValues[i], yValues[i], xValues[i+1], yValues[i+1],xValues[i+2], yValues[i+2]);
        }
        
        path.closePath();
        
        g2.setColor(getBackground());
        g2.fill(path);
        g2.setColor(getBackground());
        g2.draw(path);
        
        g2.setColor(Color.GRAY);
        g2.drawLine(WID_TRI, 4*WID_HEIGHT/15, WID_WIDTH, 4*WID_HEIGHT/15);
        g2.drawLine(WID_TRI, 11*WID_HEIGHT/15, WID_WIDTH, 11*WID_HEIGHT/15);
        g2.drawLine(WID_TRI, 13*WID_HEIGHT/15, WID_WIDTH, 13*WID_HEIGHT/15);
        g2.dispose();
    }
    
}
