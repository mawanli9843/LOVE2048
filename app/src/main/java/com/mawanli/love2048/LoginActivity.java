package com.mawanli.love2048;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mawanli.love2048.interpolator.MyInterpolator;
import com.mawanli.love2048.util.Constant;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.ShapeUtil;
import com.mawanli.love2048.util.ThemeUtil;
import com.mawanli.love2048.view.ThemeColorWhiteStrokeButton;

public class LoginActivity extends BaseActivity {

	// private EditText editText;
	LinearLayout layout;
	TextView textView;
	TextView textView2;
	ThemeColorWhiteStrokeButton button;

	Handler handler;

	private LinearLayout buttonLayout01;
	private LinearLayout buttonLayout02;

	RelativeLayout rootLayout;

	ImageView roundImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootLayout = (RelativeLayout) View.inflate(LoginActivity.this, R.layout.activity_login, null);
		rootLayout.setBackgroundDrawable(ThemeUtil.getActivityBg(LoginActivity.this));
		setContentView(rootLayout);
		init();
		startAnimation();

//		MeiZuUtil.checkUnLock(LoginActivity.this);

	}

	private void init() {
//		MobclickAgent.updateOnlineConfig(LoginActivity.this);
		handler = new Handler();
		textView = (TextView) findViewById(R.id.textView1);
		textView2 =(TextView) findViewById(R.id.textView2);
		layout = (LinearLayout) findViewById(R.id.layout);
		button = (ThemeColorWhiteStrokeButton) findViewById(R.id.button0);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				textView.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.top_out_animation));

				textView.setVisibility(View.INVISIBLE);

				textView2.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.top_out_animation));

				textView2.setVisibility(View.INVISIBLE);
				layout.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.login_dialog_out_animation));

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {

						textView.setVisibility(View.INVISIBLE);
						layout.setVisibility(View.INVISIBLE);
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
						overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
					}
				}, 400);

			}
		});

		int ids[] = { R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8 };
		for (int i = 0; i < ids.length; i++) {

			Button colorButton = (Button) findViewById(ids[i]);
			colorButton.setTag(i);
			colorButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int i = (Integer) v.getTag();
					setMyTheme(i);
				}
			});
		}
		buttonLayout01 = (LinearLayout) findViewById(R.id.button_layout01);
		buttonLayout02 = (LinearLayout) findViewById(R.id.button_layout02);
		roundImageView = (ImageView) findViewById(R.id.roundImageView);
	}

	private void startAnimation() {
		
		Animation animation =AnimationUtils.loadAnimation(LoginActivity.this, R.anim.top_in_animation);
				animation.setInterpolator(new MyInterpolator());
		textView.startAnimation(animation);
		textView2.startAnimation(animation);

		// layout.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this,
		// R.anim.login_dialog_layout_show_animation));
		button.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.login_start_buttion_show_animation));
		buttonLayout01.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.color_button_layout_animation));
		buttonLayout02.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.color_button_layout_animation2));
	}

	private void setMyTheme(int theme) {

		ThemeUtil.setThemeColor(LoginActivity.this, Constant.defaultThemeColors[theme]);
		((Mapplication) getApplication()).setColorNumber(theme);
		button.resetColor(this);

		int color = ThemeUtil.getThemeColor(LoginActivity.this);

		roundImageView.setBackgroundDrawable(ShapeUtil.getShape(ScreenUtil.dpToPx(LoginActivity.this, 125), color, 0, color));

		float scaleSize = ScreenUtil.getScreenHeight(LoginActivity.this) / roundImageView.getWidth() + 3;

		Animation scaleAinAnimation = new ScaleAnimation(1f, scaleSize, 1f, scaleSize, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAinAnimation.setDuration(800);

		rootLayout = (RelativeLayout) View.inflate(LoginActivity.this, R.layout.activity_login, null);
		scaleAinAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				roundImageView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				rootLayout.setBackgroundDrawable(ThemeUtil.getActivityBg(LoginActivity.this));
				rootLayout.invalidate();
				setContentView(rootLayout);
				init();
				roundImageView.setVisibility(View.INVISIBLE);
			}
		});
		roundImageView.startAnimation(scaleAinAnimation);

	}

	public void onResume() {
		super.onResume();
//		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
//		MobclickAgent.onPause(this);
	}
}
