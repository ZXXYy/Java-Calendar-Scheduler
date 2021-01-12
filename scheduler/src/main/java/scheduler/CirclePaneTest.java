package scheduler;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;

public class CirclePaneTest {

    public static void main(String[] args) {
        new CirclePaneTest();
    }

    public CirclePaneTest() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GridBagLayout gridBag = new GridBagLayout();
        		GridBagConstraints gridBagConstr = new GridBagConstraints();
        		GridBagConstraints c = new GridBagConstraints();
        		frame.setLayout(gridBag);
        		
        		JButton button = new JButton("Button 1");
        		c.fill = GridBagConstraints.HORIZONTAL;
        		c.gridx = 0;
        		c.gridy = 0;
        		frame.add(button, c);

        		button = new JButton("Button 2");
        		c.fill = GridBagConstraints.HORIZONTAL;
        		//c.weightx = 0.5;
        		c.gridx = 1;
        		c.gridy = 0;
        		frame.add(button, c);

        		button = new JButton("Button 3");
        		c.fill = GridBagConstraints.HORIZONTAL;
        		//c.weightx = 0.5;
        		c.gridx = 2;
        		c.gridy = 0;
        		frame.add(button, c);

        		button = new JButton("Long-Named Button 4");
        		c.fill = GridBagConstraints.HORIZONTAL;
//        		c.ipady = 40;      //make this component tall
//        		c.weightx = 0.0;
//        		c.gridwidth = 3;
//        		c.gridx = 0;
//        		c.gridy = 1;
        		frame.add(button, c);

        		button = new JButton("5");
        		c.fill = GridBagConstraints.HORIZONTAL;
        		c.ipady = 0;       //reset to default
        		c.weighty = 1.0;   //request any extra vertical space
        		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        		c.insets = new Insets(10,0,0,0);  //top padding
        		c.gridx = 1;       //aligned with button 2
        		c.gridwidth = 2;   //2 columns wide
        		c.gridy = 2;       //third row
        		frame.add(button, c);
        		frame.setSize(900,800);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        public TestPane() {
        	setSize(500,500);
            setBackground(Color.RED);
            setLayout(new GridBagLayout());
            //EventWidgetsContainer circlePane = new EventWidgetsContainer();
            CirclePane circlePane = new CirclePane();
            JLabel label = new JLabel("This is a test");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            // This is a test to show the usable bounds
            label.setBorder(new LineBorder(Color.BLUE));
            circlePane.setLayout(new BorderLayout());
            circlePane.add(label);
            add(circlePane);
        }

    }

    public class CirclePane extends JPanel {

        public CirclePane() {
            setOpaque(false);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        protected int getRadius() {
            // Determines the radius based on the smaller of the width
            // or height, so we stay symmetrical
            return Math.min(getWidth(), getHeight());
        }

        @Override
        public Insets getInsets() {
            int radius = getRadius();
            int xOffset = (getWidth() - radius) / 2;
            int yOffset = (getHeight() - radius) / 2;
            // These are magic numbers, you might like to calculate
            // your own values based on your needs
            Insets insets = new Insets(
                    radius / 6,
                    radius / 6,
                    radius / 6,
                    radius / 6);
            return insets;
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            int radius = getRadius();
            int xOffset = (getWidth() - radius) / 2;
            int yOffset = (getHeight() - radius) / 2;

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillOval(xOffset, yOffset, radius, radius);
            g2d.setColor(Color.GRAY);
            g2d.drawOval(xOffset, yOffset, radius, radius);
            g2d.dispose();

        }
    }
}