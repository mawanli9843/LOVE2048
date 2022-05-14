package com.mawanli.love2048.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.mawanli.love2048.R;
import com.mawanli.love2048.util.Constant;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.ThemeUtil;

public class CardView extends FrameLayout {

   private TextView lable;
   private ImageView roundCornerImageView;
   private int number;
    LayoutParams lp_bottom;
    LayoutParams lp_center;

   private int x;
   private int y;

   private Handler handler = new Handler();

   public CardView(Context context, AttributeSet attrs) {
       super(context, attrs);
       init();
   }

   public CardView(Context context, AttributeSet attrs, int defStyle) {
       super(context, attrs, defStyle);
       init();
   }

   public CardView(Context context) {
       super(context);
       init();
   }

   public void setXY(int x, int y) {
       this.x = x;
       this.y = y;
   }

   private void init() {
       int margin = getContext().getResources().getDimensionPixelSize(R.dimen.card_margin);
       LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
       lp.setMargins(margin, margin, margin, margin);

       roundCornerImageView = new ImageView(getContext());

       int pad = getContext().getResources().getDimensionPixelSize(R.dimen.card_stroke_with);
       roundCornerImageView.setPadding(pad, pad, pad, pad);
       addView(roundCornerImageView, lp);

       lp_bottom = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.RIGHT);
       lp_bottom.setMargins(margin * 2,margin, margin * 2, margin);
       lp_center = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
       lp_center.setMargins(margin * 2,margin, margin * 2, margin);


       lable = new TextView(getContext());
       lable.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getContext().getResources().getDimension(R.dimen.card_text_size_2));
       lable.setTextColor(Color.WHITE);
       lable.setBackgroundDrawable(null);
       lable.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
       addView(lable, lp_center);
       setBackgroundResource(R.color.transparent);

   }

   public int getNumber() {
       return number;
   }

   public void setNumber(int number) {

       this.number = number;
       Log.e("card" + x + y + "number", "" + number);

   }

   public TextView getLable() {
       return lable;
   }

   public void setLableText(int number) {

       if (getCardBackGroundType(getContext()) == Constant.COLOR_CARD) {
           lable.setLayoutParams(lp_center);
           changeTextSize(number);
       } else{
           lable.setLayoutParams(lp_bottom);
       }

       if (number == 0) {
           lable.setText(" ");
           roundCornerImageView.setImageResource(R.color.transparent);
       } else {

           lable.setText(number + "");
           ThemeUtil.addCardBackGround(roundCornerImageView, number, getContext());
           lable.setShadowLayer(10,0,0,ThemeUtil.getThemeColor(getContext()));

       }
   }

    private void changeTextSize(int number) {
        String str =  String.valueOf(number);
        int length = str.length();
        if(length<4){
            lable.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getContext().getResources().getDimension(
                    R.dimen.card_text_size));
        }else {
            lable.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getContext().getResources().getDimension(
                    R.dimen.card_text_size_2));
        }
        invalidate();
    }

    /**
    * Combine show
    */
   public void show() {
       setLableText(number);
//		this.setLayerType(FrameLayout.LAYER_TYPE_HARDWARE, null);
       Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.card_show_animation);
       this.startAnimation(animation);
   }

   /**
    * move here and show ,this method has been discarded
    */
   public void show2() {
       handler.postDelayed(new Runnable() {

           @Override
           public void run() {
               setLableText(number);
           }
       }, getResources().getInteger(R.integer.move_duration));

   }

   /**
    * first addRandom
    */
   public void show3() {
       setLableText(number);
//		this.setLayerType(FrameLayout.LAYER_TYPE_HARDWARE, null);
       Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.first_add_card_show_animation);
       this.startAnimation(animation);
   }

   public void move(final CardView cv, int xStep, int yStep) {
       setNumber(0);

       Animation anim = new TranslateAnimation(0, getWidth() * xStep, 0, getWidth() * yStep);
       anim.setDuration(getResources().getInteger(R.integer.move_duration));
       anim.setInterpolator(getContext(), android.R.anim.accelerate_decelerate_interpolator);
       anim.setAnimationListener(new AnimationListener() {

           @Override
           public void onAnimationStart(Animation animation) {
               // TODO Auto-generated method stub

           }

           @Override
           public void onAnimationRepeat(Animation animation) {
               // TODO Auto-generated method stub

           }

           @Override
           public void onAnimationEnd(Animation animation) {
               setLableText(number);
               cv.show2();
           }
       });

       this.startAnimation(anim);

   }

   public void move(int xStep, int yStep) {
       setNumber(0);
//		this.setLayerType(FrameLayout.LAYER_TYPE_HARDWARE, null);
       Animation anim = new TranslateAnimation(0, getWidth() * xStep, 0, getWidth() * yStep);

       anim.setDuration(getResources().getInteger(R.integer.move_duration));
       anim.setInterpolator(getContext(), android.R.anim.accelerate_decelerate_interpolator);

       anim.setAnimationListener(new AnimationListener() {

           @Override
           public void onAnimationStart(Animation animation) {
               // TODO Auto-generated method stub

           }

           @Override
           public void onAnimationRepeat(Animation animation) {
               // TODO Auto-generated method stub

           }

           @Override
           public void onAnimationEnd(Animation animation) {
               setLableText(number);

           }
       });
       this.startAnimation(anim);

   }

   public void move2(int xStep, int yStep) {
       setLableText(number);
//		this.setLayerType(FrameLayout.LAYER_TYPE_HARDWARE, null);
       Animation anim = new TranslateAnimation(getWidth() * xStep, 0, getWidth() * yStep, 0);
       anim.setDuration(getResources().getInteger(R.integer.move_duration));
       anim.setInterpolator(getContext(), android.R.anim.accelerate_decelerate_interpolator);
       this.startAnimation(anim);

   }

   public boolean equal(CardView cv) {
       return cv.getNumber() == getNumber();
   }

   @Override
   protected void onAnimationEnd() {
       setLableText(number);
       super.onAnimationEnd();
   }

    public static int getCardBackGroundType(Context context) {
        return ((Mapplication) context.getApplicationContext()).getCardBackGroundType();
    }

}
