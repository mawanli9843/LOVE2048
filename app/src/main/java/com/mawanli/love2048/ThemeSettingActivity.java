package com.mawanli.love2048;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mawanli.love2048.holocolorpicker.ColorPicker.OnColorChangedListener;
import com.mawanli.love2048.util.BitmapUtil;
import com.mawanli.love2048.util.Constant;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.RoundCornerImageUtil;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.ShapeUtil;
import com.mawanli.love2048.util.ThemeUtil;
import com.mawanli.love2048.view.BlurDialog;
import com.mawanli.love2048.view.CardHoloColorPickerDialog;
import com.mawanli.love2048.view.CardPicBlurDialog;
import com.mawanli.love2048.view.ColorTextSwither;
import com.mawanli.love2048.view.SettingCardView;
import com.mawanli.love2048.view.SettingGameView;
import com.mawanli.love2048.view.ThemeHoloColorPickerDialog;
import com.mawanli.love2048.view.WhiteColorButton;

public class ThemeSettingActivity extends BaseActivity {



	LinearLayout layout;

	LinearLayout settingLayout;

	LinearLayout soundLayout;
	TextView soundTextView;
	ColorTextSwither soundButton;

	LinearLayout musicLayout;
	TextView musicTextView;
	ColorTextSwither musicButton;

	LinearLayout modeLayout;
	TextView modeTextView;
	ColorTextSwither modeButton;

	Handler handler;

	RelativeLayout rootLayout;

	private SettingGameView settingGameView;

	private WhiteColorButton resetButton;
	private WhiteColorButton themeColorButton;
	private WhiteColorButton saveButton;

	private Mapplication mapplication;

	private SettingCardView[][] cards;

	private int REQUEST_CODE_PICK =1000000;

	TextView tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler();
		rootLayout = (RelativeLayout) View.inflate(ThemeSettingActivity.this, R.layout.activity_theme_setting, null);
		rootLayout.setBackgroundDrawable(ThemeUtil.getActivityBg(ThemeSettingActivity.this));
		setContentView(rootLayout);
		init(true);
		musicLayout.setVisibility(View.INVISIBLE);
		soundLayout.setVisibility(View.INVISIBLE);
		modeLayout.setVisibility(View.INVISIBLE);
		startAnimation();

		tag = ((TextView) findViewById(R.id.textView2));
		tag.setTextColor(ThemeUtil.getThemeColor(ThemeSettingActivity.this));
		mapplication = (Mapplication) getApplication();

		settingGameView = (SettingGameView) findViewById(R.id.setting_game_view);
		settingGameView.getLayoutParams().height = ScreenUtil.getScreenWith(ThemeSettingActivity.this)
				- ScreenUtil.dpToPx(ThemeSettingActivity.this, 20);

		settingGameView.setRowCount(4);
		settingGameView.setColumnCount(4);

		cards = settingGameView.getCards();
		if (cards != null) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					cards[i][j].setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							showChooseDialog(((SettingCardView) v).getNumber());
						}
					});

				}
			}

		}

		resetButton = (WhiteColorButton) findViewById(R.id.button1);
		resetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showResetDialog();

			}
		});

		saveButton = (WhiteColorButton) findViewById(R.id.button2);
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goMainActivity();
			}
		});

		themeColorButton = (WhiteColorButton) findViewById(R.id.theme_color_button);
		themeColorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showThemeColorPickerDialog();
			}
		});

	}

	private void startAnimation() {
		final Animation a = AnimationUtils.loadAnimation(ThemeSettingActivity.this, R.anim.left_in_animation);
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

	private void init(boolean isFirst) {

		settingLayout = (LinearLayout) findViewById(R.id.setting_layout);

		soundLayout = (LinearLayout) findViewById(R.id.sound_layout);
		soundTextView = (TextView) findViewById(R.id.soundTextView);
		soundButton = (ColorTextSwither) findViewById(R.id.soundButton);
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
		musicButton = (ColorTextSwither) findViewById(R.id.musicButton);
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
		modeButton = (ColorTextSwither) findViewById(R.id.modeButton);
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
				refreshAll();
			}
		});
	}



	public void showResetDialog() {

		final BlurDialog dialog = new BlurDialog(ThemeSettingActivity.this);

		dialog.show();
		dialog.setCancelable(true);

		dialog.setTitle("你确定要恢复默认主题?");

		dialog.setButton1("不", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setButton2("是", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

				setThemeColor(Constant.defaultThemeColors[0]);
				((Mapplication) getApplication()).setColorNumber(0);

				if (mapplication.getCardBackGroundType() == Constant.USER_PIC_CARD) {
					mapplication.setCardBackGroundType(Constant.PIC_CARD);
					RoundCornerImageUtil.cleanBitmaps();
					settingGameView.refreshAll();
				} else if (mapplication.getCardBackGroundType() == Constant.COLOR_CARD) {
					ThemeUtil.cleanUserColors(ThemeSettingActivity.this);
					settingGameView.refreshAll();
				}

				tag.setTextColor(ThemeUtil.getThemeColor(ThemeSettingActivity.this));

			}
		});

	}

	public void showThemeColorPickerDialog() {

		final ThemeHoloColorPickerDialog themeHoloColorPickerDialog = new ThemeHoloColorPickerDialog(ThemeSettingActivity.this);
		themeHoloColorPickerDialog.setButton1("X", new OnClickListener() {

			@Override
			public void onClick(View v) {
				themeHoloColorPickerDialog.dismiss();
				setThemeColor(themeHoloColorPickerDialog.getColor());
			}
		});
		themeHoloColorPickerDialog.show();
		themeHoloColorPickerDialog.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChanged(int color) {
				changeColor(color);
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			goMainActivity();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK&&data!=null) {

		    if(requestCode> REQUEST_CODE_PICK){
				Uri uri = data.getData();
				goToCrop(uri,requestCode - REQUEST_CODE_PICK);

		    }else if(requestCode%2 ==0){
				showPicBlurDialog(requestCode);
				settingGameView.refresh(requestCode);
			}
		}

		super.onActivityResult(requestCode, resultCode, data);

	}



	private void showPicBlurDialog(final int number) {

		final CardPicBlurDialog dialog = new CardPicBlurDialog(ThemeSettingActivity.this, number);
		dialog.setCancelable(false);

		dialog.setButton1("取消", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setButton2("保存", new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (dialog.getBlurBitmap() != null) {
					BitmapUtil.saveBitmapToFile(number + "", dialog.getBlurBitmap());
					RoundCornerImageUtil.removeBitmap(Constant.USER_PIC_DIR + "/" + Constant.PIC + number + ".jpg");
					settingGameView.refresh(number);
				}
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	private void changeColor(int color) {
		rootLayout.setBackgroundDrawable(ShapeUtil.getShape(ScreenUtil.dpToPx(ThemeSettingActivity.this, 5), color, 0, color));
		resetButton.setTextColor(color);
		saveButton.setTextColor(color);
		themeColorButton.setTextColor(color);
		tag.setTextColor(color);
	}

	private void setThemeColor(int color) {
		ThemeUtil.setThemeColor(ThemeSettingActivity.this, color);

		rootLayout.setBackgroundDrawable(ThemeUtil.getActivityBg(ThemeSettingActivity.this));
		resetButton.refresh();
		saveButton.refresh();
		themeColorButton.refresh();
		settingGameView.refreshAll();

	}

	public void showChooseDialog(final int number) {

		final BlurDialog dialog = new BlurDialog(ThemeSettingActivity.this);

		dialog.show();
		dialog.setCancelable(true);
		dialog.setTitle("请选择卡片背景的类型");

		dialog.setButton1("图片", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if (mapplication.getCardBackGroundType() != Constant.USER_PIC_CARD) {
					mapplication.setCardBackGroundType(Constant.USER_PIC_CARD);
					refreshAll();
				}


				getPicture( number);
			}

		});
		dialog.setButton2("颜色", new OnClickListener() {

			@Override
			public void onClick(View v) {
				showHoloColorPickerDialog(number);
				mapplication.setCardBackGroundType(Constant.COLOR_CARD);
				refreshAll();
				dialog.dismiss();
			}
		});

	}

	private void getPicture(int number) {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//		intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
		intent.setType("image/*");
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent,REQUEST_CODE_PICK+ number);

	}


	private void goToCrop(Uri uri ,int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX",  mapplication.getCardWith());
		intent.putExtra("outputY", mapplication.getCardWith());
		intent.putExtra("return-data", true);
		String path = Constant.USER_PIC_DIR + "/" + Constant.PIC + requestCode + ".jpg";
		File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		RoundCornerImageUtil.removeBitmap(path);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,  Uri.fromFile(file));
		startActivityForResult(intent,requestCode);
	}


	public void showHoloColorPickerDialog(final int number) {
		final CardHoloColorPickerDialog dialog = new CardHoloColorPickerDialog(ThemeSettingActivity.this, number);
		dialog.setButton1("取消", new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setButton2("保存", new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mapplication.getCardBackGroundType() != Constant.COLOR_CARD) {
					mapplication.setCardBackGroundType(Constant.COLOR_CARD);
					refreshAll();
				}

				int i = ((int) (Math.log(number) / Math.log(2))) - 1;
				ThemeUtil.setCardColor(i, dialog.getColor(), ThemeSettingActivity.this);
				settingGameView.refresh(number);
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private void refreshAll() {
		settingGameView.refreshAll();
	}

	private void goMainActivity() {
		Intent intent = new Intent(ThemeSettingActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
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

}
