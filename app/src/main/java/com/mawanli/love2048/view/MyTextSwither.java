package com.mawanli.love2048.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import com.mawanli.love2048.util.ThemeUtil;

public class MyTextSwither extends TextSwitcher {

	private float textSize;
	private int textColor;
	private int gravity;

	// private int textStyle;

	public MyTextSwither(Context context) {
		super(context);
		init(context);
	}

	public MyTextSwither(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, new int[] { android.R.attr.textSize, android.R.attr.textColor, android.R.attr.gravity,
				android.R.attr.textStyle });
		textSize = ta.getDimensionPixelSize(0, 15);
		// Log.e("--textSize--", textSize + "");
		textColor = ta.getColor(1, ThemeUtil.getThemeColor(getContext()));
		// Log.e("--textColor--", textColor + "");
		gravity = ta.getInt(2, Gravity.CENTER);
		// Log.e("--gravity--", gravity + "");
		// textStyle = ta.getInt(3, 0);
		//
		// Log.e("--textStyle--", textStyle + "==NORMAL=" + Typeface.NORMAL +
		// "----BOLD" + Typeface.BOLD);
		ta.recycle();
		init(context);

	}

	public void setTextColor(int color) {
		textColor = color;
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
			textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
			textView.setTextColor(textColor);
			textView.setSingleLine(true);
			// textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
			return textView;
		}
	}

}
