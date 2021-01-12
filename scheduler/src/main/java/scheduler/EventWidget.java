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
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
public class EventWidget extends WidgetsOutline{
	
	protected static final int width = 270;
	protected static final int height = 240;
	
	public EventWidget(LocalDate day, LocalTime time) {
		super(width, height);
//		setSize(width,height);
//		WidgetsOutline w = new WidgetsOutline(getWidth()-2,getHeight()-5);
		
		JPanel subw = new JPanel();
		subw.setPreferredSize(new Dimension(width, height/4));
		JTextField eventJTF = new JTextField("新建日程");
		JTextField locationJTF = new JTextField("添加位置");
		JTextField inviteJTF = new JTextField("添加受邀人");
		JTextField noteJTF = new JTextField("添加备注");
		
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
		
		JLabel allDayJl = new JLabel("全天：");
		allDayJl.setFont(new Font("宋体",Font.PLAIN,10));
		allDayJl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JCheckBox allDayCheckBox = new JCheckBox();
		
		JLabel repeatJL = new JLabel("重复：");
		repeatJL.setFont(new Font("宋体",Font.PLAIN,10));
		repeatJL.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel startJl = new JLabel("开始时间：");
		startJl.setFont(new Font("宋体",Font.PLAIN,10));
		startJl.setHorizontalAlignment(SwingConstants.RIGHT);

	
		JPanel startDetail = new JPanel();
		startDetail.setLayout(null);
		mySetBackground(startDetail);
		
		JLabel startDayJL = new JLabel(day.toString());
		JLabel startTimeJL = new JLabel("下午");
		JTextField startHourJTF = new JTextField("1");
		JTextField startMinJTF = new JTextField("45");

		startDayJL.setFont(new Font("宋体",Font.PLAIN,10));
		startTimeJL.setFont(new Font("宋体",Font.PLAIN,10));
		
		
		JLabel endJL = new JLabel("结束：");
		endJL.setFont(new Font("宋体",Font.PLAIN,10));
		endJL.setHorizontalAlignment(SwingConstants.RIGHT);
		JPanel endDetail = new JPanel();
		endDetail.setLayout(null);
		mySetBackground(endDetail);
		
		JLabel endDayJL = new JLabel(day.toString());
		endDayJL.setFont(new Font("宋体",Font.PLAIN,10));
		JLabel endTimeJL = new JLabel("下午");
		endTimeJL.setFont(new Font("宋体",Font.PLAIN,10));
		JTextField endHourJTF = new JTextField("1");
		JTextField endMinJTF = new JTextField("45");

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
		
		JComboBox<String> repeatCmb=new JComboBox<String>(); 
		repeatCmb.addItem("无");
		repeatCmb.addItem("每天");
		repeatCmb.addItem("每周");
		repeatCmb.addItem("每月");
		repeatCmb.addItem("每年");
		repeatCmb.setFont(new Font("宋体",Font.PLAIN,10));
		repeatJL.setBounds(0, 3*subw_hei/4, subw_wid/4,subw_hei/4);
		subw.add(repeatJL);
		repeatCmb.setBounds(subw_wid/4, 3*subw_hei/4, 1*subw_wid/3,subw_hei/4);
		subw.add(repeatCmb);
		setBackground(new Color(200, 200, 200, 64));
//		add(w);
		
	}
	public int getHeight() {
		return height;
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
	
	public static void main(String args[]) {
    	JFrame frame = new JFrame("Test");
    	EventWidget p = new EventWidget(LocalDate.of(2021, 1, 4),LocalTime.of(14, 0));
    	System.out.println("JFrame:"+frame.getBackground());
    	
    	frame.setLayout(new BorderLayout());
    	frame.add(p);
    	frame.setSize(500, 400);
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
