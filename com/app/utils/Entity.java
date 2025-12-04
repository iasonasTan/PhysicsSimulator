package com.app.utils;

import java.awt.Color;
import java.awt.Graphics;

public final class Entity {
    private float mX, mY;
    private float mW, mH;
    private float mVx, mVy;
    private float mAx, mAy;
    private String mName;

    public Entity() {
        mX = 0;
        mY = 0;
        mW = 50f;
        mH = 50f;
        mVx = 0f;
        mVy = 0f;
        mAx = 0f;
        mAy = 0f;
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine(getX(), getY()-getH(), getX(), getY()+getH()); // vertical line
        g.drawLine(getX()-getW(), getY(), getX()+getW()/2, getY()); // horizontal line
        g.drawString(mName, getX()+getW()/2, getY()); // Character
        g.setColor(Color.BLACK);

        final int GAP = 15;
        // values x
        g.drawString("X:"+mX, getX()+5, getY()-GAP);
        g.drawString("Ux:"+mVx, getX()+5, getY()-GAP*2);
        g.drawString("Ax:"+mAx, getX()+5, getY()-GAP*3);
        // values y
        g.drawString("Y:"+mY, getX()+5, getY()+GAP);
        g.drawString("Uy:"+mVy, getX()+5, getY()+GAP*2);
        g.drawString("Ay:"+mAy, getX()+5, getY()+GAP*3);
    }

    public void reset() {
    }

    public void update(float delta) {
        mX += mVx * delta;
        mY += mVy * delta;
        mVx += mAx * delta;
        mVy += mAy * delta;
    }

    public void setAx(float ax) {
        mAx = ax;
    }

    public void setAy(float ay) {
        mAy = ay;
    }

    public void setName(String n) {
        mName = n;
    }

    public void setX(float x) {
        mX = x;
    }

    public void setY(float y) {
        mY = y;
    }

    public void setVx(float vx) {
        this.mVx = vx;
    }

    public void setVy(float vy) {
        mVx = vy;
    }

    public String getName() {
        return mName;
    }

    public int getX() {
        return (int)mX;
    }

    public int getY() {
        return (int)mY;
    }

    public int getW() {
        return (int)mW;
    }

    public int getH() {
        return (int)mH;
    }
}
