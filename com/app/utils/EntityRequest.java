package com.app.utils;

import java.awt.Color;
import javax.swing.*;
import com.app.main.Gui;

public class EntityRequest extends JPanel {
    // gui
    private final int FIELD_WIDTH = 5;

    private final JButton mBuildButton = new JButton("Build"),
            mExitButton  = new JButton("Done");

    private final JTextField mNameField = new JTextField("EntityName", FIELD_WIDTH*3),
            // X
            mPositionXField = new JTextField("0", FIELD_WIDTH),
            mVelocityXField = new JTextField("1", FIELD_WIDTH),
            mAccelerationXField = new JTextField("4", FIELD_WIDTH),
            // Y
            mPositionYField = new JTextField("0", FIELD_WIDTH),
            mVelocityYField = new JTextField("1", FIELD_WIDTH),
            mAccelerationYField = new JTextField("4", FIELD_WIDTH);

    // logic
    private int mDone = 0;
    private final Object lock = new Object();

    public EntityRequest() {
        JPanel xPanel = new JPanel();
        Gui.addCategory(xPanel, new JLabel("X:"), mPositionXField);
        Gui.addCategory(xPanel, new JLabel("Vx:"), mVelocityXField);
        Gui.addCategory(xPanel, new JLabel("Ax:"), mAccelerationXField);
        JPanel yPanel = new JPanel();
        Gui.addCategory(yPanel, new JLabel("Y:"), mPositionYField);
        Gui.addCategory(yPanel, new JLabel("Vy:"), mVelocityYField);
        Gui.addCategory(yPanel, new JLabel("Ay:"), mAccelerationYField);
        JPanel bPanel = new JPanel();
        Gui.addCategory(bPanel, mExitButton, mBuildButton, 
            Gui.addCategory(bPanel, new JLabel("Name:"), mNameField)
        );
        Gui.addCategory(this, xPanel, yPanel, bPanel);

        JFrame frame = Gui.startOnFrame(this, false);
        mBuildButton.addActionListener(e -> {
            frame.dispose();
            synchronized (lock) {
                mDone = 1;
                lock.notify();
            }
        });

        mExitButton.addActionListener(e -> {
            frame.dispose();
            synchronized (lock) {
                mDone = 2;
                lock.notify();
            }
        });
    }

    public Entity waitForEntity() {
        synchronized (lock) {
            while (mDone == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException ignored) {
                    // ignore
                }
            }
        }

        if (mDone == 1) {
            Float x = getFloat(mPositionXField),
                vx = getFloat(mVelocityXField),
                ax = getFloat(mAccelerationXField),
                y = getFloat(mPositionYField),
                vy = getFloat(mVelocityYField),
                ay = getFloat(mAccelerationYField);

            if (x == null || vx == null || ax == null ||
                y == null || vy == null || ay == null) {
                System.exit(1);
            }

            return new EntityBuilder().setName(mNameField.getText())
                    // x
                    .setX(x)
                    .setVx(vx)
                    .setAx(ax)
                    // y
                    .setY(y)
                    .setVy(vy)
                    .setAy(ay)
                    .build();
        }

        return null;
    }

    public Float getFloat(JTextField field) {
        try {
            return Float.parseFloat(field.getText());
        } catch (Exception e) {
            field.setBackground(Color.RED);
            JOptionPane.showMessageDialog(this, "This is not an integer.");
            return null;
        }
    }

}
