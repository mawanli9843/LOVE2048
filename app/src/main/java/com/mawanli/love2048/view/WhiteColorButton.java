package com.mawanli.love2048.view;

import com.mawanli.love2048.R;
import com.mawanli.love2048.util.ThemeUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class WhiteColorButton extends Button {

	private int radius;

	public WhiteColorButton(Context context) {
		super(context);
		init(context);
	}

	public WhiteColorButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public WhiteColorButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		init(context, R.dimen.big_radius);
	}

	private void init(Context context, int radius) {
		this.radius = radius;
		this.setBackgroundDrawable(ThemeUtil.whiteButtonBg(context, radius));
		this.setTextColor(ThemeUtil.getColorStateThemeColor(context));
		// this.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}

	public void refresh() {
		this.setBackgroundDrawable(ThemeUtil.whiteButtonBg(getContext(), radius));
		this.setTextColor(ThemeUtil.getColorStateThemeColor(getContext()));
	}

}
