package com.mawanli.love2048.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import com.mawanli.love2048.util.ScreenUtil;

public class ColorTextSwither extends TextSwitcher {

	private float textSize;
	private int gravity;
	private int minEms;
	private String onString = "开";
	private String offString = "关";

	public ColorTextSwither(Context context) {
		super(context);
		init(context);
	}

	public ColorTextSwither(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, new int[] { android.R.attr.textSize, android.R.attr.textColor, android.R.attr.gravity,
				android.R.attr.textStyle, android.R.attr.minEms });
		textSize = ta.getDimensionPixelSize(0, 15);
		gravity = ta.getInt(2, Gravity.CENTER);
		minEms = ta.getInt(4, 2);
		ta.recycle();
		init(context);

	}

	private void init(Context context) {

		setFactory(new MyTextFactory());
	}

	private class MyTextFactory implements ViewFactory {

		@Override
		public View makeView() {
			TextView textView = new TextView(getContext());
			textView.setBackgroundDrawable(null);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.gravity = gravity;
			textView.setLayoutParams(layoutParams);
			textView.setMinEms(minEms);
			textView.setGravity(gravity);
			textView.setTextColor(Color.WHITE);
			int pad = ScreenUtil.dpToPx(getContext(), 20);
			textView.setPadding(pad, 0, pad, 0);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
			textView.setSingleLine(true);
			return textView;
		}
	}

	public void setOnString(String onString) {
		this.onString = onString;
	}

	public void setOffString(String offString) {
		this.offString = offString;
	}

}
