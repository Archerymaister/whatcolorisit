package com.example.whatcolorisit.animations;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.view.View;

import com.example.whatcolorisit.MainActivity;

import java.util.HashMap;


public class ExtendButtons {

    private static HashMap<Integer, View> listOfViews = new HashMap<>();
    private static long animationTime = 100;
    private static int curPlace;
    private static boolean visible = false;

    public static void show(){
        visible = !visible;
        showSingleView(0);
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(MainActivity.getFABMore(),"rotation", 0f);
        rotateAnimation.setDuration(animationTime*listOfViews.size());
        rotateAnimation.start();


    }

    private static void showSingleView(int i){
        curPlace = i;
        ObjectAnimator.ofFloat( listOfViews.get(i), "translationX", 0f).setDuration(0).start();
        ObjectAnimator animation = ObjectAnimator.ofFloat( listOfViews.get(i), "translationY", 170f, 0f);
        animation.setDuration(animationTime);
        animation.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                listOfViews.get(curPlace).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(curPlace+1 < listOfViews.size())
                    showSingleView(curPlace +1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animation.start();
    }

    public static void hide(){
        visible = !visible;
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(MainActivity.getFABMore(),"rotation", -225f);
        rotateAnimation.setDuration(animationTime*(listOfViews.size()));
        rotateAnimation.start();

        hideSingleView(listOfViews.size()-1);
    }

    private static void hideSingleView(int i){
        curPlace = i;
        ObjectAnimator animation = ObjectAnimator.ofFloat( listOfViews.get(curPlace), "translationY", 0f, 170f);
        animation.setDuration(animationTime);
        animation.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                listOfViews.get(curPlace).setVisibility(View.GONE);
                if(curPlace-1 >= 0)
                    hideSingleView(curPlace-1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        animation.start();
    }

    public static void addView(View view){
        listOfViews.put(listOfViews.size(), view);
    }

    public static HashMap<Integer, View> getListOfViews() {
        return listOfViews;
    }

    public static boolean isVisible() {
        return visible;
    }

    public static void setVisible(boolean visible) {
        ExtendButtons.visible = visible;
    }
}
