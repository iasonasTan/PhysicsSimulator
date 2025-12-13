package com.app.utils;

import java.awt.Color;
import java.awt.Graphics;

import com.app.main.DiagramDrawer;

public final class Entity {
    private float mX, mY;
    private float mW, mH;
    private float mVx, mVy;
    private float mAx, mAy;
    private String mName;
    private final DiagramDrawer mDiagramVel = new DiagramDrawer(this, DiagramDrawer.RenderType.VEL, 0, 0), 
        mDiagramPos = new DiagramDrawer(this, DiagramDrawer.RenderType.POS, 0, 250);

    public Entity(int idx) {
        this();
        mDiagramPos.setX(mDiagramPos.getWidth()*idx);
        mDiagramVel.setX(mDiagramPos.getWidth()*idx);
    }

    public Entity() {
        mX  = mY  = 0  ;
        mW  = mH  = 50f;
        mVx = mVy = 0f ;
        mAx = mAy = 0f ;
    }

    public void render(Graphics g) {
        mDiagramPos.render(g);
        mDiagramVel.render(g);

        g.setColor(Color.RED);
        g.drawLine(getX(), getY()- getH(), getX(), getY()+   getH()); // vertical line
        g.drawLine(getX()- getW(), getY(), getX()+ getW()/2, getY()); // horizontal line
        g.drawString(mName, getX()+getW()/2, getY()); // Character
        g.setColor(Color.BLACK);

        final int GAP = 15;
        // values x
        g.drawString("X:" +mX,  getX()+5, getY()-GAP);
        g.drawString("Ux:"+mVx, getX()+5, getY()-GAP*2);
        g.drawString("Ax:"+mAx, getX()+5, getY()-GAP*3);
        // values y
        g.drawString("Y:" +mY,  getX()+5, getY()+GAP);
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
        mDiagramPos.update();
        mDiagramVel.update();
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

    public int getVelX() {
        return (int)mVx;
    }

    public int getVelY() {
        return (int)mVy;
    }

    // public Diagram getDiagramVel() {
    //     return mDiagramVel;
    // }

    // public Diagram getDiagramPos() {
    //     return mDiagramPos;
    // }
}
