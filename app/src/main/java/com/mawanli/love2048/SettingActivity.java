package com.mawanli.love2048;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
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
import com.mawanli.love2048.util.Constant;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.ShapeUtil;
import com.mawanli.love2048.util.ThemeUtil;
import com.mawanli.love2048.view.ColorBgTextSwither;
import com.mawanli.love2048.view.WhiteColorButton;
import com.umeng.analytics.MobclickAgent;

public class SettingActivity extends BaseActivity {

	LinearLayout layout;
	Button button;
	Button unLockButton;

	Button aboutButton;

	LinearLayout settingLayout;

	LinearLayout soundLayout;
	TextView soundTextView;
	ColorBgTextSwither soundButton;

	LinearLayout musicLayout;
	TextView musicTextView;
	ColorBgTextSwither musicButton;

	LinearLayout modeLayout;
	TextView modeTextView;
	ColorBgTextSwither modeButton;

	Handler handler;

	private LinearLayout buttonLayout01;
	private LinearLayout buttonLayout02;

	RelativeLayout rootLayout;

	ImageView roundImageView;

	int oldColor;

	int oldMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		rootLayout = (RelativeLayout) View.inflate(SettingActivity.this, R.layout.activity_setting, null);
		rootLayout.setBackgroundDrawable(ThemeUtil.getActivityBg(SettingActivity.this));
		setContentView(rootLayout);

		MobclickAgent.updateOnlineConfig(SettingActivity.this);
		handler = new Handler();

		oldMode = ((Mapplication) getApplication()).getCardBackGroundType();
		oldColor = ((Mapplication) getApplication()).getThemeColor();

		init(true);
		musicLayout.setVisibility(View.INVISIBLE);
		soundLayout.setVisibility(View.INVISIBLE);
		modeLayout.setVisibility(View.INVISIBLE);
		startAnimation();
	}

	private void init(boolean isFirst) {

		settingLayout = (LinearLayout) findViewById(R.id.setting_layout);

		soundLayout = (LinearLayout) findViewById(R.id.sound_layout);
		soundTextView = (TextView) findViewById(R.id.soundTextView);
//		soundTextView.setTextColor(ThemeUtil.getThemeColor(SettingActivity.this));
		soundButton = (ColorBgTextSwither) findViewById(R.id.soundButton);
		if (isFirst) {
			setSoundFirstText();
		}

		soundButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((Mapplication) getApplication()).isSoundOn()) {
					soundButton.setText("关");
					((Mapplication) getApplication()).setSoundOn(false);

				} else {
					soundButton.setText("开");
					((Mapplication) getApplication()).setSoundOn(true);
				}
			}
		});

		musicLayout = (LinearLayout) findViewById(R.id.music_layout);
		musicTextView = (TextView) findViewById(R.id.musicTextView);
//		musicTextView.setTextColor(ThemeUtil.getThemeColor(SettingActivity.this));
		musicButton = (ColorBgTextSwither) findViewById(R.id.musicButton);
		if (isFirst) {
			setMusicFirstText();
		}
		musicButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((Mapplication) getApplication()).isMusicOn()) {
					musicButton.setText("关");
					((Mapplication) getApplication()).setMusicOn(false);

				} else {
					musicButton.setText("开");
					((Mapplication) getApplication()).setMusicOn(true);
				}
			}
		});

		modeLayout = (LinearLayout) findViewById(R.id.mode_layout);
		modeTextView = (TextView) findViewById(R.id.modeTextView);
//		modeTextView.setTextColor(ThemeUtil.getThemeColor(SettingActivity.this));
		modeButton = (ColorBgTextSwither) findViewById(R.id.modeButton);
		modeButton.setOnString("图片");
		modeButton.setOffString("颜色");
		if (isFirst) {
			setModeButtonFirstText();
		}
		modeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (((Mapplication) getApplication()).getCardBackGroundType() == Constant.COLOR_CARD) {
					modeButton.setText("图片");
					((Mapplication) getApplication()).setCardBackGroundType(Constant.PIC_CARD);
				} else {
					modeButton.setText("颜色");
					((Mapplication) getApplication()).setCardBackGroundType(Constant.COLOR_CARD);
				}

			}
		});

		layout = (LinearLayout) findViewById(R.id.layout);
		button = (WhiteColorButton) findViewById(R.id.button0);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				backToMainActivity();

			}
		});

		unLockButton = (Button) findViewById(R.id.unlock_button);

			unLockButton.setText(getResources().getString(R.string.define));
			unLockButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					goToThemeSettingActivity();

				}

			});

		aboutButton = (Button) findViewById(R.id.button_about);
		aboutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
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

		startAnimation2();
		buttonLayout01.startAnimation(AnimationUtils.loadAnimation(SettingActivity.this, R.anim.setting_color_button_layout_animation));
		buttonLayout02.startAnimation(AnimationUtils.loadAnimation(SettingActivity.this, R.anim.setting_color_button_layout_animation2));
	}

	private void startAnimation2() {

		final Animation a = AnimationUtils.loadAnimation(SettingActivity.this, R.anim.left_in_animation);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				soundLayout.setVisibility(View.VISIBLE);
				soundLayout.startAnimation(a);
			}
		}, 100);

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				musicLayout.setVisibility(View.VISIBLE);
				musicLayout.startAnimation(a);
			}

		}, 300);

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				modeLayout.setVisibility(View.VISIBLE);
				modeLayout.startAnimation(a);
			}

		}, 500);

	}

	private void setMyTheme(int theme) {

		ThemeUtil.setThemeColor(SettingActivity.this, Constant.defaultThemeColors[theme]);
		((Mapplication) getApplication()).setColorNumber(theme);

		int color = ThemeUtil.getThemeColor(SettingActivity.this);
		roundImageView.setBackgroundDrawable(ShapeUtil.getShape(ScreenUtil.dpToPx(SettingActivity.this, 125), color, 0, color));

		float scaleSize = ScreenUtil.getScreenHeight(SettingActivity.this) / ScreenUtil.dpToPx(SettingActivity.this, 170) + 2;

		Animation scaleAinAnimation = new ScaleAnimation(1f, scaleSize, 1f, scaleSize, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAinAnimation.setDuration(800);

		rootLayout = (RelativeLayout) View.inflate(SettingActivity.this, R.layout.activity_setting, null);
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

				rootLayout.setBackgroundDrawable(ThemeUtil.getActivityBg(SettingActivity.this));
				setContentView(rootLayout);
				init(true);
				roundImageView.setVisibility(View.INVISIBLE);

			}
		});
		roundImageView.startAnimation(scaleAinAnimation);

	}

	private void setModeButtonFirstText() {
		if ((((Mapplication) getApplication()).getCardBackGroundType() == Constant.COLOR_CARD)) {
			modeButton.setText("颜色");
		} else {
			modeButton.setText("图片");
		}
	}

	private void setMusicFirstText() {
		if (((Mapplication) getApplication()).isMusicOn()) {
			musicButton.setText("开");
		} else {
			musicButton.setText("关");
		}
	}

	private void setSoundFirstText() {
		if (((Mapplication) getApplication()).isSoundOn()) {
			soundButton.setText("开");
		} else {
			soundButton.setText("关");
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			backToMainActivity();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void backToMainActivity() {
		Intent intent = new Intent(SettingActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void goToUnDoActivity() {
		Intent intent = new Intent(SettingActivity.this, GetUndoAndFullVersionActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
	}

	private void goToThemeSettingActivity() {
		Intent intent = new Intent(SettingActivity.this, ThemeSettingActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
	}

}
