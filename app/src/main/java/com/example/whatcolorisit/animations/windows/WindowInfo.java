package com.example.whatcolorisit.animations.windows;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ScrollView;

import com.example.whatcolorisit.MainActivity;

public class WindowInfo extends Window {
    private static ScrollView settingsView = null;

    public static ScrollView getSettingsView() {
        return settingsView;
    }

    public WindowInfo(){
        super();
    }

    public static void setSettingsView(ScrollView settingsView) {
        WindowInfo.settingsView = settingsView;
    }

    @Override
    public void showWindow() {
        ObjectAnimator animator = ObjectAnimator.ofFloat( settingsView, "translationX", 1000f, 0f);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                WindowInfo.getSettingsView().setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {}

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animator.start();
        this.setVisible(true);
    }

    @Override
    public void hideWindow() {
        ObjectAnimator animator = ObjectAnimator.ofFloat( settingsView, "translationX", 0f, 1000f );;
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                WindowInfo.getSettingsView().setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animator.start();
        this.setVisible(false);
    }

    @Override
    public void onClose(){
        hideWindow();
        MainActivity.FABMore.setEnabled(true);
    }
}
