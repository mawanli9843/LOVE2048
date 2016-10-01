package com.mawanli.love2048.view;

import com.mawanli.love2048.util.ThemeUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class WhiteTransparentButton extends Button {

	public WhiteTransparentButton(Context context) {
		super(context);
		init(context);
	}

	public WhiteTransparentButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public WhiteTransparentButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		this.setBackgroundDrawable(ThemeUtil.whiteTransparentButtonBg(context));
		this.setTextColor(ThemeUtil.getColorStateThemeColor(context));
	}

	public void resetColor(Context context) {
		this.setTextColor(ThemeUtil.getColorStateThemeColor(context));
	}

}
