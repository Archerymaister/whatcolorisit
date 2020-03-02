package com.example.whatcolorisit;

import android.graphics.Color;

public class ColorName {
    private String name;
    private float r, g, b;
    private Color color;
    public ColorName(String name, float r, float g, float b){
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float getDistance(Color color){
        int multi = 255;

        return Math.abs((r - color.red()*multi))
                + Math.abs(( g - color.green()*multi))
                + Math.abs(( b - color.blue()*multi));
    }

    public String getName(){
        return this.name;
    }

    public Color getColor(){
        return Color.valueOf(r,g,b);
    }

    public int getColorInt(){
        return Color.argb(r,g,b,0xFD);
    }
}
