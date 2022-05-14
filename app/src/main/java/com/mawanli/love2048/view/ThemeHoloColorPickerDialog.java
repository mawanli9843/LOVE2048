package com.mawanli.love2048.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.mawanli.love2048.R;
import com.mawanli.love2048.holocolorpicker.ColorPicker;
import com.mawanli.love2048.holocolorpicker.ColorPicker.OnColorChangedListener;
import com.mawanli.love2048.holocolorpicker.SVBar;
import com.mawanli.love2048.holocolorpicker.SaturationBar;
import com.mawanli.love2048.holocolorpicker.ValueBar;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.ThemeUtil;

public class ThemeHoloColorPickerDialog extends Dialog {

	private Button button1;

	private ColorPicker colorPicker;
	private SVBar svBar;
	private SaturationBar saturationBar;
	private ValueBar valueBar;

	private TextView r;
	private TextView g;
	private TextView b;

	public ThemeHoloColorPickerDialog(Activity context) {
		super(context, R.style.Dialog2);
		init(context);
	}

	private void init(Activity context) {
		this.setCancelable(true);
		this.getWindow().setContentView(R.layout.theme_holo_color_picker_dialog_layout);
		this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		this.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.getWindow().setWindowAnimations(R.style.dialog_animation);
		RoundCornerImageView bgView = (RoundCornerImageView) this.getWindow().findViewById(R.id.dialog_bg);
		bgView.setImageBitmap(ScreenUtil.getBlurBitmap(context));
		button1 = (Button) this.getWindow().findViewById(R.id.button1);
		colorPicker = (ColorPicker) this.getWindow().findViewById(R.id.picker);

		svBar = (SVBar) this.getWindow().findViewById(R.id.svbar);
		// colorPicker.addSVBar(svBar);

		saturationBar = (SaturationBar) this.getWindow().findViewById(R.id.saturation);
		colorPicker.addSaturationBar(saturationBar);

		valueBar = (ValueBar) this.getWindow().findViewById(R.id.valuebar);
		colorPicker.addValueBar(valueBar);

		int color = ThemeUtil.getThemeColor(getContext());
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

	}

	public void setButton1(String text, View.OnClickListener onClickListener) {
		button1.setOnClickListener(onClickListener);
		button1.setText(text);
	}

	public int getColor() {
		return colorPicker.getColor();
	}

	public void setOnColorChangedListener(final OnColorChangedListener onColorChangedListener) {
		colorPicker.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChanged(int color) {
				button1.setTextColor(new ColorStateList(new int[][] { { android.R.attr.state_pressed }, { 0 } }, new int[] { Color.WHITE, color }));
				onColorChangedListener.onColorChanged(color);
				r.setText("R:" + Color.red(color));
				r.setTextColor(color);
				g.setText("	G:" + Color.green(color));
				g.setTextColor(color);
				b.setText("	B:" + Color.blue(color));
				b.setTextColor(color);

			}
		});
	}
}
