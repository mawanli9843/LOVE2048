package com.mawanli.love2048.view;

import com.mawanli.love2048.R;
import com.mawanli.love2048.holocolorpicker.ColorPicker;
import com.mawanli.love2048.holocolorpicker.ColorPicker.OnColorChangedListener;
import com.mawanli.love2048.holocolorpicker.SVBar;
import com.mawanli.love2048.holocolorpicker.SaturationBar;
import com.mawanli.love2048.holocolorpicker.ValueBar;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.ThemeUtil;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class CardHoloColorPickerDialog extends Dialog {

	private Button button1;
	private Button button2;

	private ColorPickerCardView colorPickerCardView;

	private ColorPicker colorPicker;
	private SVBar svBar;
	private SaturationBar saturationBar;
	private ValueBar valueBar;

	private TextView r;
	private TextView g;
	private TextView b;

	public CardHoloColorPickerDialog(Activity context, int number) {
		super(context, R.style.Dialog2);
		init(context, number);
	}

	private void init(Activity context, int number) {
		this.setCancelable(true);
		this.getWindow().setContentView(R.layout.card_holo_color_picker_dialog_layout);
		this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		this.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.getWindow().setWindowAnimations(R.style.dialog_animation);
		RoundCornerImageView bgView = (RoundCornerImageView) this.getWindow().findViewById(R.id.dialog_bg);
		bgView.setImageBitmap(ScreenUtil.getBlurBitmap(context));

		button1 = (Button) this.getWindow().findViewById(R.id.button1);
		button2 = (Button) this.getWindow().findViewById(R.id.button2);

		colorPickerCardView = (ColorPickerCardView) this.getWindow().findViewById(R.id.color_picker_card_view);
		colorPickerCardView.setNumber(number);
		colorPicker = (ColorPicker) this.getWindow().findViewById(R.id.picker);

		svBar = (SVBar) this.getWindow().findViewById(R.id.svbar);
		// colorPicker.addSVBar(svBar);

		saturationBar = (SaturationBar) this.getWindow().findViewById(R.id.saturation);
		colorPicker.addSaturationBar(saturationBar);

		valueBar = (ValueBar) this.getWindow().findViewById(R.id.valuebar);
		colorPicker.addValueBar(valueBar);

		int i = ((int) (Math.log(number) / Math.log(2))) - 1;
		Log.e("------", i + "");

		int color = ThemeUtil.getCardColor(i, getContext());
		colorPicker.setColor(color);

		r = (TextView) this.getWindow().findViewById(R.id.r);
		g = (TextView) this.getWindow().findViewById(R.id.g);
		b = (TextView) this.getWindow().findViewById(R.id.b);

		r.setText("R:" + Color.red(color));
		r.setTextColor(color);
		g.setText("	G:" + Color.green(color));
		g.setTextColor(color);
		b.setText("	B:" + Color.blue(color));
		b.setTextColor(color);

		colorPicker.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChanged(int color) {
				Log.e("---------------", "onColorChanged");
				colorPickerCardView.setColor(color);

				r.setText("R:" + Color.red(color));
				r.setTextColor(color);
				g.setText("	G:" + Color.green(color));
				g.setTextColor(color);
				b.setText("	B:" + Color.blue(color));
				b.setTextColor(color);
			}
		});

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

	public int getColor() {
		return colorPicker.getColor();
	}
}
