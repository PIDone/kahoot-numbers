import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.util.ArrayList;

public class Page {
    public static ArrayList<Point> points = new ArrayList<>();
    
    public static JFrame frame = new JFrame("Kahoot optimizer");
    public static JFrame frame2 = new JFrame("Kahoot optimizer");

    public static Robot robot;

    public Page() throws AWTException {
        robot = new Robot();

        frame2.addKeyListener(new Keys());
        frame2.setExtendedState(JFrame.MAXIMIZED_HORIZ);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setAlwaysOnTop(true);

        JLabel text = new JLabel("Click on red, blue, yellow and green buttons in order", SwingConstants.CENTER);
        text.setFont(new Font("Sans Serif", Font.BOLD, 28));

        frame.add(text);

        frame.addMouseListener(new Mouse());
        frame.setUndecorated(true);
        frame.setBackground(new Color(255, 255, 255, 100));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}

class Mouse implements MouseListener {
    public static int index = 0;

    public void mousePressed(MouseEvent e) {
        Page.points.add(e.getPoint());
        if (Page.points.size() == 4) {
            Page.frame.dispose();
            Page.frame2.setVisible(true);
        }
    }
    public void mouseClicked(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
}

class Keys implements KeyListener {
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyChar() - '0' - 1;
        if (key < 0 || key > 3)
            return;

        Point point = Page.points.get(key);
        Page.robot.mouseMove((int) point.getX(), (int) point.getY());
        Page.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Page.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e1) { }

        Rectangle bounds = Page.frame2.getBounds();
        Page.robot.mouseMove((int) (bounds.getX() + bounds.getWidth() / 2), (int) (bounds.getY() + bounds.getHeight() / 2));
        
        try {
            Thread.sleep(200);
        } catch (InterruptedException e1) { }
        
        Page.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Page.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e) { }
}