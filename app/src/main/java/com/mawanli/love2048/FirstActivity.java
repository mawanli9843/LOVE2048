package com.mawanli.love2048;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mawanli.love2048.R;
import com.mawanli.love2048.util.Constant;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.MediaPalyer;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.SharePreferenceUtil;
import com.mawanli.love2048.util.ThemeUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

/**
 * 
 * @author mawanli
 * 
 *         2014年5月31日
 */
public class FirstActivity extends BaseActivity {

	LinearLayout layout;

	TextView textView1;
	TextView textView2;

	Button fightButton;

	Handler handler;

	private ImageView my;
	private ImageView her;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		layout = (LinearLayout) View.inflate(FirstActivity.this, R.layout.activity_first, null);
		layout.setBackgroundDrawable(ThemeUtil.getActivityBg(FirstActivity.this));
		setContentView(layout);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		if (((Mapplication) getApplication()).isMusicOn()) {
			playMusic();
		}
		init();

		UmengUpdateAgent.update(this);

	}

	private void playMusic() {
		MediaPalyer.play(FirstActivity.this, R.raw.without_you);
	}

	int men[] = { R.drawable.p1_1, R.drawable.p1_2, R.drawable.p1_3, R.drawable.p1_4, R.drawable.p1_5, R.drawable.p1_6, R.drawable.p1_7 };

	int women[] = { R.drawable.p2_1, R.drawable.p2_2, R.drawable.p2_3, R.drawable.p2_4, R.drawable.p2_5 };

	String[] words = { "MAYBE", "MAYBE", "MAYBE", "MAYBE", "MAYBE", "MAYBE", "WHAT EVER IT TAKES" };

	String[] words2 = { "YOU ARE STILL ALONE", "YOU JUST MET HER", "YOU ARE IN LOVE WITH HER", "YOU DEEPLY IN LOVE WITH EACH OTHER",
			"YOU QUARREL EVERY DAY", "YOU ARE DISAPPOINTED IN LOVE", "YOU SHOULD NEVER FORGET TO KEEP FIGHTING" };

	private void init() {

		textView2 = (TextView) findViewById(R.id.textView2);
		textView1 = (TextView) findViewById(R.id.textView1);

		fightButton = (Button) findViewById(R.id.button0);
		fightButton.setText("跳过");
		Animation animation = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.skip_button_show_animation);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				fightButton.startAnimation(AnimationUtils.loadAnimation(FirstActivity.this, R.anim.fight_button_rotate_animation));
			}
		});
		fightButton.startAnimation(animation);
		fightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 SharePreferenceUtil.setPrefBoolean(getApplicationContext(),
				 Constant.FISRT_START, true);
				Intent intent = new Intent(FirstActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
				MediaPalyer.stop();
			}
		});

		my = (ImageView) findViewById(R.id.imageView1);
		her = (ImageView) findViewById(R.id.imageView2);

		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWith(FirstActivity.this) / 2);
		layoutParams.weight = 1;
		my.setLayoutParams(layoutParams);
		her.setLayoutParams(layoutParams);

		handler = new Handler();

		showMen(0);

	}

	/**
	 * 播放动画
	 * 
	 * @param i
	 */
	private void showMen(final int i) {

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				/**
				 * 播放男孩图片
				 */
				if (i < 6) {
					my.setImageResource(men[i]);
					my.startAnimation(AnimationUtils.loadAnimation(FirstActivity.this, R.anim.my_image_show_animation));

				} else if (i == 6) {
					my.setImageResource(men[i]);
					my.startAnimation(AnimationUtils.loadAnimation(FirstActivity.this, R.anim.my_image_end_show_animation));
				}

				/**
				 * 播放女孩图片
				 */
				if (i > 0 && i < 6) {
					her.setImageResource(women[i - 1]);
					her.startAnimation(AnimationUtils.loadAnimation(FirstActivity.this, R.anim.my_image_show_animation));
				} else if (i == 6) {
					her.setVisibility(View.GONE);
				}

				/**
				 * 播放文字
				 */
				if (i < 6) {
					textView1.setText(words[i]);
					textView2.setText(words2[i]);
					textView1.startAnimation(AnimationUtils.loadAnimation(FirstActivity.this, R.anim.text1_show_animation));
					textView2.startAnimation(AnimationUtils.loadAnimation(FirstActivity.this, R.anim.text2_show_animation));
				} else if (i == 6) {
					textView1.setText(words[i]);
					textView2.setText(words2[i]);
					textView1.startAnimation(AnimationUtils.loadAnimation(FirstActivity.this, R.anim.text1_end_show_animation));
					textView2.startAnimation(AnimationUtils.loadAnimation(FirstActivity.this, R.anim.text2_end_show_animation));
				}

				/**
				 * 显示button
				 */
				if (i == 7) {

					Animation animation = AnimationUtils.loadAnimation(FirstActivity.this, R.anim.fight_button_show_animation);
					animation.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							fightButton.setText("开始");
						}

						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub

						}
					});
					fightButton.startAnimation(animation);

				}

				/**
				 * 递归七次，期间播放图片，播放文字，最后播放fightButton动画
				 */
				if (i < 7) {
					showMen(i + 1);
				}

			}
		}, i == 0 ? 0 : getResources().getInteger(R.integer.duration2));

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
