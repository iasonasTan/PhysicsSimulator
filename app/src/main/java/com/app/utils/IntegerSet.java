package com.app.utils;

public class IntegerSet {
    public static final int COMPARE_X = 0;
    public static final int COMPARE_Y = 1;

    public float x, y;

    public IntegerSet(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public IntegerSet() {
        this(0, 0);
    }

    public IntegerSet(IntegerSet data) {
        this(data.x, data.y);
    }

    public IntegerSet copy() {
        return new IntegerSet(this);
    }

    public boolean hasMoreThan(IntegerSet other, int base) {
        if(base==COMPARE_X)
            return other.x < x;
        if(base==COMPARE_Y)
            return other.y < y;
        throw new IllegalArgumentException("Illegal base "+base);
    }

    @Override
    public String toString() {
        return "Set:{x:"+x+",y:"+y+"}";
    }

    public float get(int i) {
        if(i==COMPARE_X)
            return x;
        if(i==COMPARE_Y)
            return y;
        throw new IllegalArgumentException("Illegal argument "+i);
    }

    public void add(int i, float diff) {
        if(i==COMPARE_X) {
            x += diff;
            return;
        }
        if(i==COMPARE_Y) {
            y += diff;
            return;
        }
        throw new IllegalArgumentException("Illegal argument "+i);
    }

    public void divide(int i, float f) {
        if(i==COMPARE_X) {
            x /= f;
            return;
        }
        if(i==COMPARE_Y) {
            y /= f;
            return;
        }
        throw new IllegalArgumentException("Illegal argument "+i);
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }
}