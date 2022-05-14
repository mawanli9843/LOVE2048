package com.mawanli.love2048.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.mawanli.love2048.R;
import com.mawanli.love2048.util.Constant;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.ShapeUtil;
import java.util.ArrayList;
import java.util.List;

public class MProgressView extends LinearLayout {

	private RelativeLayout mRootLayout;
	private ImageView mImageView01;
	private ImageView mImageView02;

	Handler handler = new Handler();

	List<Drawable> drawableList = new ArrayList<Drawable>();

	private boolean changeFlag;

	public MProgressView(Context context) {
		super(context);
		init();
	}

	public MProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {

		mRootLayout = (RelativeLayout) View.inflate(getContext(), R.layout.my_progress_layout, null);
		addView(mRootLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mImageView01 = (ImageView) mRootLayout.findViewById(R.id.imageView01);
		mImageView02 = (ImageView) mRootLayout.findViewById(R.id.imageView02);

		ChangeColor();

		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.progress_view_animation);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				if (changeFlag) {
					ChangeColor();
					changeFlag = false;
				} else {
					changeFlag = true;
				}

			}

			@Override
			public void onAnimationEnd(Animation animation) {

			}
		});

		Animation animation2 = new AlphaAnimation(0, 1);
		animation2.setDuration(getResources().getInteger(R.integer.my_progress_duration));
		animation2.setRepeatCount(Animation.INFINITE);
		animation2.setRepeatMode(Animation.REVERSE);
		animation2.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				mImageView01.setBackgroundDrawable(drawable);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				mImageView01.setBackgroundDrawable(drawable);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

			}
		});
		mImageView02.startAnimation(animation);
		mImageView01.startAnimation(animation2);

	}

	private int i = 0;
	private Drawable drawable;

	private void ChangeColor() {

		if (i >= Constant.defaultThemeColors.length) {
			i = 0;
		}
		if (i == ((Mapplication) getContext().getApplicationContext()).getColorNumber()) {
			i++;
			ChangeColor();
			return;
		}

		if (i >= drawableList.size()) {
			int color = Constant.defaultThemeColors[i];
			drawable = ShapeUtil.getShape(1000, color, 0, color);
			drawableList.add(drawable);
		} else {
			drawable = drawableList.get(i);
		}
		mImageView02.setBackgroundDrawable(drawable);
		i++;
	}
}
