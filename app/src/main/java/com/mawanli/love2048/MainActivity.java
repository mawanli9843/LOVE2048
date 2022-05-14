package com.mawanli.love2048;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import com.mawanli.love2048.util.BmobUtil;
import com.mawanli.love2048.util.Constant;
import com.mawanli.love2048.util.DeviceUtil;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.MediaPalyer;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.SharePreferenceUtil;
import com.mawanli.love2048.util.ShareUtil;
import com.mawanli.love2048.util.ThemeUtil;
import com.mawanli.love2048.util.ToastUtil;
import com.mawanli.love2048.view.BlurDialog;
import com.mawanli.love2048.view.BlurEditTextDialog;
import com.mawanli.love2048.view.GameBackView;
import com.mawanli.love2048.view.GameView;
import com.mawanli.love2048.view.ScreenShotView;
import com.mawanli.love2048.view.WhiteColorButton;

public class MainActivity extends BaseActivity {

	private RelativeLayout rootLayout;

	GameView gameView;
	GameBackView gameBackView;

	TextSwitcher scoreView;
	TextSwitcher highScoreView;
	TextSwitcher rankView;

	private int score;
	private static int highScore;

	private WhiteColorButton unDoButton;

	private WhiteColorButton settingButton;

	private WhiteColorButton restartButton;

	private Mapplication mapplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!SharePreferenceUtil.getPrefBoolean(getApplicationContext(),
				Constant.FISRT_START, false)){
			goToFirstActivity();
			finish();
			return;
		}

		init();
	}

	private void goToFirstActivity() {
		Intent intent = new Intent(MainActivity.this,FirstActivity.class);
		startActivity(intent);
	}

	private void init() {
		rootLayout = (RelativeLayout) View.inflate(MainActivity.this, R.layout.activity_main, null);
		rootLayout.setBackgroundDrawable(ThemeUtil.getActivityBg(MainActivity.this));
		setContentView(rootLayout);

		((TextView) findViewById(R.id.score_tag)).setTextColor(ThemeUtil.getThemeColor(MainActivity.this));
		((TextView) findViewById(R.id.high_score_tag)).setTextColor(ThemeUtil.getThemeColor(MainActivity.this));
		((TextView) findViewById(R.id.rank_tag)).setTextColor(ThemeUtil.getThemeColor(MainActivity.this));

		mapplication = (Mapplication) getApplication();

		handler = new Handler();
		unDoButton = (WhiteColorButton) findViewById(R.id.button2);

		unDoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

//				if (mapplication.getUndoCount() > 0) {
//					gameView.unDo();
//					unDoButton.setText(getResources().getString(R.string.undo) + mapplication.getUndoCount());
//				} else {
//					goUnDoAndFullVersionActivity();
//				}
									gameView.unDo();
			}
		});

		unDoButton.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				goUnDoAndFullVersionActivity();

				return true;
			}

		});

		restartButton = (WhiteColorButton) findViewById(R.id.button1);
		restartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final BlurDialog dialog = new BlurDialog(mainActivity);
				dialog.show();
				dialog.setTitle("重新开始游戏？");
				dialog.setButton1("否", new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.setButton2("是", new OnClickListener() {

					@Override
					public void onClick(View v) {
						gameView.startGame(true);
						dialog.dismiss();
					}
				});
			}
		});
		restartButton.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				takeScreenShot();
				return true;
			}
		});

		settingButton = (WhiteColorButton) findViewById(R.id.button3);
		settingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToSettingActivity();
			}

		});
		settingButton.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				String sn = "[" + DeviceUtil.getSN(MainActivity.this) + "]";

				ShareUtil.showShare(mainActivity, "将DeviceID 发给我", sn, null);

				return true;
			}
		});

		gameView = (GameView) findViewById(R.id.game_view);
		gameView.setRowCount(4);
		gameView.setColumnCount(4);

		gameView.getLayoutParams().height = ScreenUtil.getScreenWith(MainActivity.this)

		- ScreenUtil.dpToPx(MainActivity.this, 20);
		gameBackView = (GameBackView) findViewById(R.id.game_back_view);
		gameBackView.getLayoutParams().height = ScreenUtil.getScreenWith(MainActivity.this)

		- ScreenUtil.dpToPx(MainActivity.this, 20);

		gameBackView.setRowCount(4);
		gameBackView.setColumnCount(4);

		scoreView = (TextSwitcher) findViewById(R.id.score);
		highScoreView = (TextSwitcher) findViewById(R.id.high_score);

		highScore = mapplication.getHighScore();
		highScoreView.setText(highScore + "");
		score = SharePreferenceUtil.getPrefInt(mainActivity, "score", 0);
		scoreView.setText(score + "");
		rankView = (TextSwitcher) findViewById(R.id.rank);
		rankView.setText(mapplication.getRank() > 0 ? "" + mapplication.getRank() : "?");

		rankView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goRankActivity();
			}
		});

		dialogTest();
	}

	private void dialogTest() {
		scoreView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				showGameOverDialog();
				return true;
			}
		});

		highScoreView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				showHighScoreDialog();
				return true;
			}
		});

		rankView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				showEnterNameDialog();
				return true;
			}
		});
	}


	public void showExitDialog() {

		final BlurDialog dialog = new BlurDialog(MainActivity.this);

		dialog.show();
		dialog.setCancelable(true);

		dialog.setTitle("退出游戏?");

		dialog.setButton1("否", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setButton2("是", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

				finish();

				exit();

				ShareUtil.stopShareSDK(mainActivity);
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitDialog();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onStop() {

		int k = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int x = gameView.getCards()[i][j].getNumber();
				// Log.e("================", "onStop" + x);
				//存储当前状态
				SharePreferenceUtil.setPrefInt(mainActivity, k + "", x);
				
				int y = gameView.getPreStates()[i][j];
				SharePreferenceUtil.setPrefInt(mainActivity, "pre"+k, y);
				k++;

			}

		}

		SharePreferenceUtil.setPrefBoolean(mainActivity, "isNew", false);

		mapplication.setHighScore(highScore);

		BmobUtil.setHighScore(getApplicationContext(), highScore);

		SharePreferenceUtil.setPrefInt(mainActivity, "score", score);

		super.onStop();
	}

	public static MainActivity getMainAcivity() {
		return mainActivity;
	}

	public MainActivity() {
		mainActivity = this;
	}

	private static MainActivity mainActivity = null;

	Handler handler = null;

	public void showWinDialog(int coins) {
		if (coins <= 0) {
			return;
		}

		final BlurDialog dialog = new BlurDialog(mainActivity);
		dialog.setCancelable(false);
		dialog.setTitle("恭喜!您获得了 " + coins + " 积分!");
		dialog.setButton1("返回", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.setButton2("积分", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				goUnDoAndFullVersionActivity();
			}
		});
		dialog.show();

	}

	public void showLostDialog() {

		if (highScore == score) {// 获得最高分

			BmobUtil.setHighScore(MainActivity.this, highScore);

			if (((Mapplication) getApplication()).getUserName() != null) {// 已经有名字

				showHighScoreDialog();

			} else {// 还没有名字
				showEnterNameDialog();

			}
		} else {

			showGameOverDialog();

		}
	}

	private void showHighScoreDialog() {

		final BlurDialog dialog = new BlurDialog(mainActivity);
		dialog.setCancelable(false);
		dialog.setTitle("恭喜!您获得了最高分\n" + highScore);

		dialog.setButton1("分享", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				takeScreenShot();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						gameView.startGame(true);
					}
				}, 2000);
			}
		});

		dialog.setButton2("排行", new OnClickListener() {

			@Override
			public void onClick(View v) {
				gameView.startGame(true);
				goRankActivity();
				dialog.dismiss();
			}
		});

		dialog.setButton3("重玩", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				gameView.startGame(true);
			}
		});

		dialog.show();
	}

	private void showEnterNameDialog() {
		final BlurEditTextDialog dialog = new BlurEditTextDialog(MainActivity.this);

		dialog.setCancelable(false);

		dialog.setText("恭喜!您获得了最高分\n" + highScore);

		dialog.setButton1("排行", new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (TextUtils.isEmpty(dialog.getEditText())) {
					ToastUtil.toast(MainActivity.this, "请输入您的名字");
				} else {
					gameView.startGame(true);
					BmobUtil.setUserName(MainActivity.this, dialog.getEditText());

					goRankActivity();

					dialog.dismiss();
				}

			}

		});

		dialog.setButton2("分享", new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!TextUtils.isEmpty(dialog.getEditText())) {
					BmobUtil.setUserName(MainActivity.this, dialog.getEditText());
				}
				dialog.dismiss();
				takeScreenShot();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						gameView.startGame(true);
					}
				}, 2000);

			}
		});

		dialog.setButton3("重玩", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				gameView.startGame(true);
			}
		});

		dialog.show();

	}

	private void showGameOverDialog() {
		final BlurDialog dialog = new BlurDialog(mainActivity);
		dialog.setCancelable(false);
		dialog.setTitle("输了~ 分数:" + score + " 再来一次 ?");
		dialog.setButton1("分享", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				takeScreenShot();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						gameView.startGame(true);
					}
				}, 2000);
			}
		});

		dialog.setButton2("再来", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				gameView.startGame(true);
			}
		});
		dialog.show();

	}

	public void onResume() {
		Log.e("--", "onResume");
//		unDoButton.setText(getResources().getString(R.string.undo) + mapplication.getUndoCount());
		if (((Mapplication) getApplication()).isMusicOn()) {
			MediaPalyer.play(MainActivity.this, R.raw.bg01);
		}

		super.onResume();
//		MobclickAgent.onResume(this); // 友盟统计时长？
	}

	public void onPause() {

		Log.e("--", "onPause");
		MediaPalyer.stop();

		super.onPause();
//		MobclickAgent.onPause(this);
	}

	public void addScore(int addScore) {
		if (addScore > 0) {
			score += addScore;
			scoreView.setText(score + "");

			if (score > highScore) {
				highScore = score;
				highScoreView.setText(highScore + "");
			}
		}
	}

	public void resetScore() {
		score = 0;
		scoreView.setText(score + "");
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void takeScreenShot() {

		final ScreenShotView screenShotView = new ScreenShotView(mainActivity);
		rootLayout.addView(screenShotView);

		if (Build.VERSION.SDK_INT > 10) {
			screenShotView.addAnimatorListener(new AnimatorListener() {

				@Override
				public void onAnimationStart(Animator animation) {
					Log.e("=================", "takeScreenShot_onAnimationStart");
					ShareUtil.storeBitmapToFile(screenShotView.getBitmap());
				}

				@Override
				public void onAnimationRepeat(Animator animation) {

				}

				@Override
				public void onAnimationEnd(Animator animation) {
					
					rootLayout.removeView(screenShotView);
					share();
				}

				@Override
				public void onAnimationCancel(Animator animation) {
					// TODO Auto-generated method stub

				}
			});
		} else {
			screenShotView.addAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					ShareUtil.storeBitmapToFile(screenShotView.getBitmap());
					rootLayout.removeView(screenShotView);
					share();
				}
			});
		}

	}

	private void share() {

		ShareUtil.showShare(mainActivity, "哈哈哈,我就是宇宙之王！！！", "我就是宇宙之王！！！我在Love&2048的最高得分为：" + highScore + ",不服来战！哈哈！ 附下载链接>>"
				+ Constant.DOWN_LOAD_URL, Constant.SHARE_PIC_PATH);

	}

	private void goToSettingActivity() {

//		Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//		startActivity(intent);
//		finish();
//		overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
//
//
	goToThemeSettingActivity();
	}

	private void goToThemeSettingActivity() {
		Intent intent = new Intent(MainActivity.this, ThemeSettingActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
	}

	private void goUnDoAndFullVersionActivity() {
		Intent intent = new Intent(MainActivity.this, GetUndoAndFullVersionActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
	}

	private void goRankActivity() {
		Intent intent = new Intent(getApplicationContext(), RankActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
	}

}
