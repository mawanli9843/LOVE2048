package com.mawanli.love2048.view;

import com.mawanli.love2048.R;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.ShapeUtil;
import com.mawanli.love2048.util.ThemeUtil;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class BlurEditTextDialog extends Dialog {

	private TextView textView;
	private EditText editText;
	private Button button1;
	private Button button2;
	private Button button3;

	public BlurEditTextDialog(Activity context, int theme) {
		super(context, theme);
		init(context);
	}

	public BlurEditTextDialog(Activity context) {
		super(context, R.style.Dialog);
		init(context);
	}

	private void init(Activity context) {
		this.getWindow().setContentView(R.layout.edit_text_dialog_layout);
		this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		this.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.getWindow().setWindowAnimations(R.style.dialog_animation);
		RoundCornerImageView bgView = (RoundCornerImageView) this.getWindow().findViewById(R.id.dialog_bg);
		bgView.setImageBitmap(ScreenUtil.getBlurBitmap(context));

		int color = ThemeUtil.getThemeColor(getContext());

		textView = (TextView) this.getWindow().findViewById(R.id.textView1);
		textView.setTextColor(color);

		editText = (EditText) this.getWindow().findViewById(R.id.editText1);
		editText.setBackgroundDrawable(ShapeUtil.getWhiteStrokeButtonBg(color, getContext().getResources().getDimensionPixelSize(R.dimen.middle_radius), 0));
		button1 = (Button) this.getWindow().findViewById(R.id.button1);
		button2 = (Button) this.getWindow().findViewById(R.id.button2);
		button3 = (Button) this.getWindow().findViewById(R.id.button3);

	}

	public void setText(String text) {
		textView.setText(text);
	}

	public String getEditText() {
		return this.editText.getText().toString().trim();
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

	public void setButton3(String text, View.OnClickListener onClickListener) {
		button3.setVisibility(View.VISIBLE);
		button3.setOnClickListener(onClickListener);
		button3.setText(text);
	}
}
