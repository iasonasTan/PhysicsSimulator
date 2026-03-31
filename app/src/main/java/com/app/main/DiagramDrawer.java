package com.app.main;

import com.app.utils.*;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.ArrayList;

public class DiagramDrawer {
    public static final int DEFAULT_WIDTH  = 350;
    public static final int DEFAULT_HEIGHT = 200;

    private final List<IntegerSet> mData = new ArrayList<>();
    private final Entity mEntity;
    private RenderType mType;
    private int mWidth = DEFAULT_WIDTH, mHeight = DEFAULT_HEIGHT;
    private int mPosX = 0, mPosY = 0;

    public DiagramDrawer(Entity entity, RenderType type, int px, int py) {
        mEntity = entity;
        mType = type;
        mPosX = px;
        mPosY = py;
    }

    public void setRenderType(RenderType type) {
        mData.clear();
        mType = type;
    }

    public void update() {
        IntegerSet point = new IntegerSet(){};
        switch (mType) {
            case VEL:
                point.x = mEntity.getVelX();
                point.y = mEntity.getVelY();
                break;
            case POS:
                point.x = mEntity.getX();
                point.y = mEntity.getY();
                break;
        }
        mData.add(point);
    }

    private void fitInBounds(List<IntegerSet> data, int base) {
        float max = Integer.MIN_VALUE;
        for (IntegerSet set: data) {
            max = Math.max(set.get(base), max);
        }

        if(max <= mHeight)
            return;

        float f = max/mHeight;
        for(IntegerSet set: data) {
            set.divide(base, f);
        }
    }

    public void render(Graphics g) {
        g.drawLine(mPosX, mPosY,  mPosX+   mWidth, mPosY                ); // left to right (top)
        g.drawLine(mPosX, mPosY+  mHeight, mPosX+  mWidth, mPosY+mHeight); // left to right (bottom)
        g.drawLine(mPosX, mPosY,  mPosX,   mPosY+  mHeight              ); // top to bottom (left)
        g.drawLine(mPosX+ mWidth, mPosY,   mPosX+  mWidth, mPosY+mHeight); // top to bottom (right)
        renderData(g); // render data
    }

    public List<IntegerSet> getConfiguredData() {
        List<IntegerSet> out = new ArrayList<>();
        for (int i = 0; i < mData.size(); i+=Math.max(1, mData.size()/mWidth))
            out.add(mData.get(i).copy());
        fitInBounds(out, IntegerSet.COMPARE_X);
        fitInBounds(out, IntegerSet.COMPARE_Y);
        return out;
    }

    private void renderData(Graphics g) {
        List<IntegerSet> configuredData = getConfiguredData();

        final Font gFont = g.getFont();
        Font smallFont = gFont.deriveFont(10f);
        g.setFont(smallFont);
        g.setColor(mType==RenderType.VEL?Color.BLUE:Color.GREEN);
        for (int i = 0; i < Math.min(mWidth, configuredData.size()); i++) {
            IntegerSet data = configuredData.get(i);
            g.drawOval(mPosX+i, mPosY+mHeight-data.getX(), 1, 1);
            g.drawOval(mPosX+i, mPosY+mHeight-data.getY(), 1, 1);
            if(i%100==0) {
                g.drawString(mType.name()+"_X", mPosX+i, mPosY+mHeight-data.getX()-10);
                g.drawString(mType.name()+"_Y", mPosY+i, mPosY+mHeight-data.getY()-10);
            }
        }
        g.setFont(gFont);
        g.setColor(Color.BLACK);
    }

    public enum RenderType {
        VEL,
        POS;
    }

    public void setX(int x) {
        mPosX = x;
    }

    public int getX() {
        return mPosX;
    }

    public void setY(int y) {
        mPosY = y;
    }

    public int getY() {
        return mPosY;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
