package com.mawanli.love2048.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class SquareLinearLayout extends LinearLayout {

	int width;

	public SquareLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed && width == 0) {
			width = getWidth();
			Log.e("===========width========", "=" + getWidth());
			this.setLayoutParams(new LayoutParams(width, width));
		}
		super.onLayout(changed, l, t, r, b);
	}

}
