package com.mawanli.love2048.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.mawanli.love2048.R;
import com.mawanli.love2048.util.ScreenUtil;
import com.mawanli.love2048.util.ShapeUtil;
import com.mawanli.love2048.util.ThemeUtil;

public class ColorPickerCardView extends FrameLayout {

	private TextView lable;
	private RoundCornerImageView roundCornerImageView;
	private int number;

	public ColorPickerCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ColorPickerCardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
		roundCornerImageView = new RoundCornerImageView(getContext());
		roundCornerImageView.setRoundWidth(20);
		roundCornerImageView.setRoundHeight(20);
		addView(roundCornerImageView, lp);
		int margin = getContext().getResources().getDimensionPixelSize(R.dimen.card_margin);
		LayoutParams lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.RIGHT);
		lp2.setMargins(margin * 2, 0, margin * 2, margin);

		lable = new TextView(getContext());
		lable.setTextSize(30);
		lable.setTextColor(Color.WHITE);
		lable.setBackgroundDrawable(null);
		lable.setGravity(Gravity.CENTER);
		lable.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		addView(lable, lp2);

	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
		lable.setText(number + "");
		ThemeUtil.addCardBackGround(roundCornerImageView, number, getContext());
	}

	public void setColor(int color) {
		roundCornerImageView.setImageDrawable(ShapeUtil.getShape(ScreenUtil.dpToPx(getContext(), 12), color, 0, color));
	}

	public void setBitmap(Bitmap bitmap) {
		roundCornerImageView.setImageBitmap(bitmap);
	}

}
