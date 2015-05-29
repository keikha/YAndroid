package com.myinc.keikha.animationdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;


public class AnimatingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animating);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_animating, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAnimate(View view) {


        Intent i = new Intent(this , SecondActivity.class);
        startActivity(i);
        overridePendingTransition(R.animator.right_in , R.animator.left_out);

        // Fade out the button
        //Object Animator


        // hard way
//        ObjectAnimator fadeOutAnim = ObjectAnimator.ofFloat(view , "alpha" , 1.0f , 0.0f);
//        fadeOutAnim.setDuration(3000);
//        fadeOutAnim.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//        fadeOutAnim.setInterpolator(new BounceInterpolator());
//
//        fadeOutAnim.start();

        // ==== improved way
//        view.animate().alpha(20).xBy(100f).setDuration(3000).setInterpolator(new BounceInterpolator());//rotationX(180f)


//
//        // another way
//        AnimatorSet set = new AnimatorSet();
//        ObjectAnimator moveDown = ObjectAnimator.ofFloat(view, "translationY", 100f);
//
//        ObjectAnimator moveLeft = ObjectAnimator.ofFloat(view, "translationX", -50f);
//        moveLeft.setDuration(1000);
//
//        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view , "alpha" , 0.3f);
//
////        set.playSequentially(moveDown , moveLeft , fadeOut);
//      set.play(moveDown).before(moveLeft).before(fadeOut);
//
//        set.start();



    }
}
