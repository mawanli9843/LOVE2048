package com.mawanli.love2048.view;

import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.mawanli.love2048.R;
import com.mawanli.love2048.util.ScreenUtil;

public class ScreenShotView extends RelativeLayout {

	AnimatorSet animatorSet;

	private Bitmap bitmap;

	Animation animation2;

	LinearLayout layout;
	RoundCornerImageView imageView;

	Handler handler;

	public ScreenShotView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ScreenShotView(Context context) {
		super(context);
		init();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void init() {
		handler = new Handler();
		setGravity(Gravity.CENTER);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setLayoutParams(lp);
		setBackgroundResource(R.color.sub_grey);

		imageView = new RoundCornerImageView(getContext());

		bitmap = ScreenUtil.takeScreenShot((Activity) getContext());
		imageView.setImageBitmap(bitmap);
		addView(imageView, lp);

		if (Build.VERSION.SDK_INT > 10) {

			layout = new LinearLayout(getContext());
			layout.setBackgroundResource(R.drawable.white_round_corner);
			addView(layout, lp);
			ObjectAnimator layoutOA = ObjectAnimator.ofFloat(layout, "alpha", 1f, 0f);

			ObjectAnimator imageOAX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0.7f);
			ObjectAnimator imageOAY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0.7f);

			ObjectAnimator allOA = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
			allOA.setDuration(1000);

			animatorSet = new AnimatorSet();

			animatorSet.play(layoutOA);
			animatorSet.play(imageOAY).with(imageOAX).after(layoutOA);
			animatorSet.play(allOA).after(imageOAX).after(1000);
		} else {

			animation2 = new ScaleAnimation(1, 0, 1, 0, 0.5f, 0.5f);
			imageView.setAnimation(animation2);
			animation2.setDuration(1000);

		}

	}

	@SuppressLint("NewApi")
	public void addAnimatorListener(AnimatorListener animatorListener) {

		animatorSet.addListener(animatorListener);
		animatorSet.start();

	}

	public void addAnimationListener(AnimationListener animationListener) {
		animation2.setAnimationListener(animationListener);
		animation2.start();

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				imageView.setVisibility(View.GONE);
			}
		}, 1000);
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

}
