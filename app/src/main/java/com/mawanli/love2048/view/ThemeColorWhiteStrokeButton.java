package com.mawanli.love2048.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import com.mawanli.love2048.util.ThemeUtil;

public class ThemeColorWhiteStrokeButton extends Button {

	public ThemeColorWhiteStrokeButton(Context context) {
		super(context);
		init(context);
	}

	public ThemeColorWhiteStrokeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ThemeColorWhiteStrokeButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		this.setBackgroundDrawable(ThemeUtil.themeColorButtonBg(context));
		this.setTextColor(ThemeUtil.getColorStateWhite(context));
	}

	public void resetColor(Context context) {
		this.setBackgroundDrawable(ThemeUtil.themeColorButtonBg(context));
		this.setTextColor(ThemeUtil.getColorStateWhite(context));
	}

}
