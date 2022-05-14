package com.mawanli.love2048.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.mawanli.love2048.R;
import com.mawanli.love2048.util.BitmapUtil;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.ThemeUtil;

public class CardPicBlurDialog extends Dialog {

	private Button button1;
	private Button button2;

	private ColorPickerCardView colorPickerCardView;

	private SeekBar blurSeekBar;


	private Bitmap mBitmap;

	Bitmap tempbitmap;

	public CardPicBlurDialog(Activity context, int number) {
		super(context, R.style.Dialog2);
		init(context, number);
	}

	int blurNumber = 1;


	private void init(Activity context, int number) {
		this.setCancelable(true);
		this.getWindow().setContentView(R.layout.card_pic_blur_dialog_layout);
		this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		this.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.getWindow().setWindowAnimations(R.style.dialog_animation);
		RoundCornerImageView bgView = (RoundCornerImageView) this.getWindow().findViewById(R.id.dialog_bg);
		bgView.setImageBitmap(ScreenUtil.getBlurBitmap(context));

		button1 = (Button) this.getWindow().findViewById(R.id.button1);
		button2 = (Button) this.getWindow().findViewById(R.id.button2);

		colorPickerCardView = (ColorPickerCardView) this.getWindow().findViewById(R.id.color_picker_card_view);
		colorPickerCardView.setNumber(number);

		blurSeekBar = (SeekBar) this.getWindow().findViewById(R.id.blur_seekbar);

		int i = ((int) (Math.log(number) / Math.log(2))) - 1;

		Log.e("---------------", number + "---");


			mBitmap = BitmapUtil.getBitmapByNumber(number + "");



		Log.e("========bitmap===========", "bitmap" + mBitmap);

		int color = ThemeUtil.getThemeColor(getContext());


		GradientDrawable thumb =(GradientDrawable) getContext().getResources().getDrawable(R.drawable.thumb);
		thumb.setColor(color);
		GradientDrawable progress =(GradientDrawable) getContext().getResources().getDrawable(R.drawable.progress);
		progress.setColor(color);
		blurSeekBar.setThumb(getContext().getResources().getDrawable(R.drawable.thumb));
		blurSeekBar.setProgressDrawable(progress);

		blurSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				if (blurNumber == progress /5) {
					return;
				}
				Log.e("=========onProgressChanged============", progress + "---" + blurNumber);
				blurNumber = progress /5;
				tempbitmap = ScreenUtil.getBlurBitmap(mBitmap, blurNumber, false);
				if (tempbitmap != null) {
					colorPickerCardView.setBitmap(tempbitmap);
				}

			}
		});

	}

	public Bitmap getBlurBitmap() {
		return tempbitmap;
	}

	public void setButton1(String text, View.OnClickListener onClickListener) {
		button1.setOnClickListener(onClickListener);
		button1.setText(text);
	}

	public void setButton2(String text, View.OnClickListener onClickListener) {
		button2.setVisibility(View.VISIBLE);
		button2.setOnClickListener(onClickListener);
		button2.setText(text);
	}

}
