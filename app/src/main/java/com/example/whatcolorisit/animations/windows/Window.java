package com.example.whatcolorisit.animations.windows;

import android.view.View;

import com.example.whatcolorisit.MainActivity;

import java.util.ArrayList;

public abstract class Window extends MainActivity {
    private static ArrayList<Window> windowArrayList = new ArrayList<>();

    public Window(){
        windowArrayList.add(this);
    }

    public abstract void showWindow();

    public abstract void hideWindow();

    public abstract void onClose();

    private boolean isVisible = false;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public static ArrayList<Window> getWindowArrayList() {
        return windowArrayList;
    }
}
