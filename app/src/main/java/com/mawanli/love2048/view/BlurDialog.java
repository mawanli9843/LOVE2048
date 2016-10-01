package com.mawanli.love2048.view;

import com.mawanli.love2048.R;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.ThemeUtil;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class BlurDialog extends Dialog {

	private TextView title;
	private Button button1;
	private Button button2;
	private Button button3;

	public BlurDialog(Activity context, int theme) {
		super(context, theme);
		init(context);
	}

	public BlurDialog(Activity context) {
		super(context, R.style.Dialog);
		init(context);
	}

	private void init(Activity context) {

		setCancelable(true);
		this.getWindow().setContentView(R.layout.text_dialog_layout);
		this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		this.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.getWindow().setWindowAnimations(R.style.dialog_animation);
		RoundCornerImageView bgView = (RoundCornerImageView) this.getWindow().findViewById(R.id.dialog_bg);
		bgView.setBackgroundDrawable(new BitmapDrawable(ScreenUtil.getBlurBitmap(context)));

		title = (TextView) this.getWindow().findViewById(R.id.textView1);
		title.setTextColor(ThemeUtil.getThemeColor(getContext()));
		button1 = (Button) this.getWindow().findViewById(R.id.button1);
		button2 = (Button) this.getWindow().findViewById(R.id.button2);
		button3 = (Button) this.getWindow().findViewById(R.id.button3);

	}

	public void setTitle(String title) {
		this.title.setText(title);
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
	
	public void setButton3(String text, View.OnClickListener onClickListener){
		button3.setVisibility(View.VISIBLE);
		button3.setOnClickListener(onClickListener);
		button3.setText(text);
	}
}
