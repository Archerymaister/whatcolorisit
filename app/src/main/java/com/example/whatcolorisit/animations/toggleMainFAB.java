package com.example.whatcolorisit.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.example.whatcolorisit.MainActivity;

public class toggleMainFAB {

    private static long animationTime = 500;
    private static final float translationX = 250f;
    private static boolean visible = true;
    private static View curView;

    public static void showFAB(){
        ObjectAnimator animation = ObjectAnimator.ofFloat(MainActivity.getFABMore(), "translationX", translationX, 0f);
        animation.setDuration(animationTime);
        animation.start();
    }

    public static void hideFAB(){
        if(ExtendButtons.isVisible()){
            ExtendButtons.setVisible(!ExtendButtons.isVisible());

            ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(MainActivity.getFABMore(),"rotation", -225f);
            rotateAnimation.setDuration(200);
            rotateAnimation.start();

            for(View view : ExtendButtons.getListOfViews().values()){
                curView = view;
                ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 0f, translationX);
                animation.setDuration(animationTime);
                animation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        curView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        curView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {


                    }
                });
                animation.start();
            }
        }
        ObjectAnimator animation = ObjectAnimator.ofFloat(MainActivity.getFABMore(), "translationX", 0f, translationX);
        animation.setDuration(animationTime);
        animation.start();
    }

    public static boolean isVisible() {
        return visible;
    }

    public static void setVisible(boolean visible) {
        toggleMainFAB.visible = visible;
    }
}
