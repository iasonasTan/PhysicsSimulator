package com.app.main;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.HashMap;

import com.app.utils.*;

public class Gui extends JPanel implements Runnable, KeyListener {
    // GUI CONSTANTS
    public static final int WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT;

    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        WINDOW_WIDTH  = dimension.width;
        WINDOW_HEIGHT = dimension.height;
    }

    // GUI COMPONENTS
    private final JButton mPauseButton = new JButton("Pause");
    private JFrame mFrame;

    // LOGIC COMPONENTS
    private boolean mRunning = false, mFirstStart = true;
    private Thread mThread;
    private float mTime;
    private final Map<String, Entity> mEntities = new HashMap<>();

    public Gui() {
        setFocusable(true);
        addKeyListener(this);
        requestFocus();
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        mPauseButton.addActionListener(e -> {
            if(mRunning) {
                stop();
                mPauseButton.setText("Resume");
            } else {
                start();
                mPauseButton.setText("Pause");
            }
        });
    }

    public void start() {
        mFrame = startOnFrame(this, true);
        mRunning = true;
        if(mFirstStart) {
            mTime = 0;
            mFirstStart = false;
        }
        mThread = new Thread(this);
        mThread.start();
    }

    public void stop() {
        mRunning = false;
    }

    public void addEntity(String s, Entity e) {
        mEntities.put(s, e);
    }

    public void update(final float delta) {
        mEntities.values().forEach(e -> e.update(delta));
        mTime += delta;
    }

    public void render() {
        repaint();
        paintImmediately(getVisibleRect());
    }

    public void drawCordsAtX(Graphics g, int x) {
        final int LINE_SIZE = 15;
        g.drawLine(x, Gui.WINDOW_HEIGHT/2-LINE_SIZE/2, x, Gui.WINDOW_HEIGHT/2+LINE_SIZE/2);
        g.drawString("x:"+x, x, Gui.WINDOW_HEIGHT/2-(LINE_SIZE/3)*2);
        g.drawString("t:"+mTime, Gui.WINDOW_WIDTH-200, 100);
    }

    public void drawCordsAtY(Graphics g, int y) {
        final int LINE_SIZE = 20;
        g.drawLine(Gui.WINDOW_WIDTH/2-LINE_SIZE/2, y, Gui.WINDOW_WIDTH/2+LINE_SIZE/2, y);
        g.drawString("x:"+y, Gui.WINDOW_WIDTH/2+(LINE_SIZE/3)*2, y);
        g.drawString("t:"+mTime, Gui.WINDOW_WIDTH-200, 100);
    }

    private void configGraphics(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));
        Font font = g2d.getFont();
        font.deriveFont(Font.BOLD);
        g2d.setFont(font);
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        configGraphics(g);

        g.drawLine(0, Gui.WINDOW_HEIGHT/2, WINDOW_WIDTH, Gui.WINDOW_HEIGHT/2);
        for (int i = 0; i < WINDOW_WIDTH; i+=50) {
            drawCordsAtX(g, i);
        }

        g.drawLine(Gui.WINDOW_WIDTH/2, 0, Gui.WINDOW_WIDTH/2, WINDOW_HEIGHT);
        for (int i = 0; i < WINDOW_HEIGHT; i+=30) {
            drawCordsAtY(g, i);
        }
        
        mEntities.values().forEach(e -> e.render(g));
        g.dispose();
    }

    @Override
    public void run() {
        float delta = 0;
        while(mRunning) {
            long before = System.nanoTime();
            update(delta);
            render();
            long after = System.nanoTime();
            delta = (after-before) / 1_000_000_000f;
        }
    }

    public static JFrame startOnFrame(JPanel panel, boolean full) {
        JFrame frame = new JFrame("Simulation");
        if(full) {
            frame.setUndecorated(true);
            GraphicsDevice device = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
            device.setFullScreenWindow(frame);
        }
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    public static JPanel addCategory(JPanel parent, JComponent... comps) {
        JPanel panel = new JPanel(new GridLayout(comps.length, 1));
        for (JComponent comp: comps) {
            panel.add(comp);
        }
        parent.add(panel);
        return panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                if(mRunning) {
                    stop();
                    mPauseButton.setText("Resume");
                } else {
                    start();
                    mPauseButton.setText("Pause");
                }
                break;
            case KeyEvent.VK_ESCAPE:
                stop();
                mFrame.dispose();
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
