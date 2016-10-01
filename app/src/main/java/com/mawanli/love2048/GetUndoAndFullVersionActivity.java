package com.mawanli.love2048;

import cn.bmob.v3.listener.UpdateListener;

import com.mawanli.love2048.util.BmobUtil;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.ThemeUtil;
import com.mawanli.love2048.util.ToastUtil;
import com.mawanli.love2048.view.BlurDialog;
import com.mawanli.love2048.view.MyTextSwither;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GetUndoAndFullVersionActivity extends BaseActivity {
	private RelativeLayout rootLayout;

	TextView coinsNumberTextView;
	Handler handler;

	private final int COINS_NUMBER_REFRESH = 1;

	private int pm = 1;// 数字增或者减
	private int mCurrentNumber;
	private int mGoalNumber;
	private int mRate = 5;

	private MyTextSwither unDoTextViewSwither;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {

				switch (msg.what) {
					case COINS_NUMBER_REFRESH:
						if (pm == 1) {
							if (mCurrentNumber < mGoalNumber) {
								coinsNumberTextView.setText(mCurrentNumber + "");
								handler.sendEmptyMessageDelayed(COINS_NUMBER_REFRESH, 15);
								mCurrentNumber += mRate * pm;
							} else {
								coinsNumberTextView.setText(mGoalNumber + "");
								mCurrentNumber = mGoalNumber;
							}
						} else {
							if (mCurrentNumber > mGoalNumber) {
								coinsNumberTextView.setText(mCurrentNumber + "");
								handler.sendEmptyMessageDelayed(COINS_NUMBER_REFRESH, 30);
								mCurrentNumber += mRate * pm;
							} else {
								coinsNumberTextView.setText(mGoalNumber + "");
								mCurrentNumber = mGoalNumber;
							}
						}

						break;

					default:
						break;
				}

			}
		};

		rootLayout = (RelativeLayout) View.inflate(GetUndoAndFullVersionActivity.this, R.layout.activity_get_undo_and_full_version, null);
		rootLayout.setBackgroundDrawable(ThemeUtil.getActivityBg(GetUndoAndFullVersionActivity.this));
		setContentView(rootLayout);

		coinsNumberTextView = (TextView) findViewById(R.id.coins_number);
		coinsNumberTextView.setPadding(0, 0, 0, 0);



		/**
		 * 积分墙
		 */
		findViewById(R.id.earn_more_coin_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNumber(mCurrentNumber+10,0);

				overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
			}
		});

		/**
		 * 积分兑换
		 */
		findViewById(R.id.coin_unlock_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

					unLockByCoins();
					ToastUtil.toastWhiteBg(GetUndoAndFullVersionActivity.this, "您的积分不够,快去获得更多积分吧~");
			}

		});

		/**
		 * Flyme 账户购买
		 */
		findViewById(R.id.flyme_unlock_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});



		setNumber(99);


		((TextView) rootLayout.findViewById(R.id.undo_title)).setTextColor(ThemeUtil.getThemeColor(GetUndoAndFullVersionActivity.this));
		unDoTextViewSwither = (MyTextSwither) rootLayout.findViewById(R.id.undo_number);
		unDoTextViewSwither.setText(((Mapplication) getApplication()).getUndoCount() + "");
		unDoTextViewSwither.setTextColor(ThemeUtil.getThemeColor(GetUndoAndFullVersionActivity.this));

		/**
		 * 兑换Undo
		 */
		rootLayout.findViewById(R.id.exhange_undo_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

						((Mapplication) getApplication()).setUndoCount(((Mapplication) getApplication()).getUndoCount() + 1);
						unDoTextViewSwither.setText(((Mapplication) getApplication()).getUndoCount() + "");
					}
		});

	}

	private void unLockByCoins() {

		/**
		 * 减去积分
		 */

		BmobUtil.unLocked(GetUndoAndFullVersionActivity.this, new UpdateListener() {

			@Override
			public void onSuccess() {
				((Mapplication) getApplication()).setUnLock(true);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				ToastUtil.toastWhiteBg(GetUndoAndFullVersionActivity.this, "兑换失败了,请检查网络后再试！");
			}
		});

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void setNumber(int number){
		setNumber(number,500);
	}

	private void setNumber(int number,int delay) {

		mGoalNumber = number;

		mCurrentNumber = Integer.parseInt(coinsNumberTextView.getText().toString());

		if (mGoalNumber > mCurrentNumber) {
			pm = 1;
		} else if (mGoalNumber < mCurrentNumber) {
			pm = -1;
		} else {
			return;
		}

		int temp = Math.round(Math.abs(mGoalNumber - mCurrentNumber) / 50f);
		mRate = temp > 1 ? temp : 1;

		handler.sendEmptyMessageDelayed(COINS_NUMBER_REFRESH, delay);
	}

	private void showUnLockSuccessDialog() {

		final BlurDialog dialog = new BlurDialog(GetUndoAndFullVersionActivity.this);
		dialog.show();
		dialog.setCancelable(true);

		dialog.setTitle("兑换成功,立即去体验完整功能？");

		dialog.setButton1("NO", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setButton2("YES", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				goThemeSettingActivity();
			}

		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			goMainActivity();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void goMainActivity() {
		Intent intent = new Intent(GetUndoAndFullVersionActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void goThemeSettingActivity() {
		Intent intent = new Intent(GetUndoAndFullVersionActivity.this, ThemeSettingActivity.class);
		startActivity(intent);
		finish();
	}

}
