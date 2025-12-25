import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
frame.setBackground(AppColors.BACKGROUND());



        int radius = 30;
        frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), radius, radius));

        RightSidePanel rightSidePanel = new RightSidePanel();
        MazePanel mazePanel = new MazePanel(rightSidePanel);

        navPanel nav = new navPanel(mazePanel,rightSidePanel);

        frame.add(nav, BorderLayout.NORTH);
        frame.add(mazePanel, BorderLayout.CENTER);
        frame.add(rightSidePanel, BorderLayout.EAST);

        final Point[] mouseDownCompCoords = {null};
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords[0] = e.getPoint();
            }
        });
        frame.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                frame.setLocation(currCoords.x - mouseDownCompCoords[0].x, currCoords.y - mouseDownCompCoords[0].y);
            }
        });
        frame.setVisible(true);
        SwingUtilities.invokeLater(mazePanel::requestFocusInWindow);
    }

}
