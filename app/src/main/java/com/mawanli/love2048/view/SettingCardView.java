package com.mawanli.love2048.view;

import com.mawanli.love2048.R;
import com.mawanli.love2048.util.Constant;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.ShapeUtil;
import com.mawanli.love2048.util.ThemeUtil;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingCardView extends FrameLayout {

	private TextView lable;
	private ImageView imageView;
	private int number;
	LayoutParams lp_bottom;
	LayoutParams lp_center;

	private int x;
	private int y;

	Mapplication mapplication;

	private Handler handler = new Handler();

	public SettingCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SettingCardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SettingCardView(Context context) {
		super(context);
		init();
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private void init() {

		mapplication = ((Mapplication) getContext().getApplicationContext());

		int margin = getContext().getResources().getDimensionPixelSize(R.dimen.card_margin);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
		lp.setMargins(margin, margin, margin, margin);
		imageView = new ImageView(getContext());
		imageView.setImageResource(0);
		imageView.setBackgroundDrawable(ShapeUtil.getShape(getContext().getResources().getDimensionPixelSize(R.dimen.card_radius),
				Color.WHITE, 0, Color.WHITE));
		int pad = getContext().getResources().getDimensionPixelSize(R.dimen.card_stroke_with);
		imageView.setPadding(pad, pad, pad, pad);
		addView(imageView, lp);
		LayoutParams lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.RIGHT);
		lp2.setMargins(margin * 2, 0, margin * 2, margin);
		// LayoutParams lp2 = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT, Gravity.CENTER);

		lable = new TextView(getContext());
		lable.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getContext().getResources().getDimension(R.dimen.card_text_size_2));
		lable.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		lable.setTextColor(Color.WHITE);
		lable.setGravity(Gravity.CENTER);
		lable.setShadowLayer(10,0,0,ThemeUtil.getThemeColor(getContext()));
		lp_bottom = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.RIGHT);
		lp_bottom.setMargins(margin * 2,margin, margin * 2, margin);
		lp_center = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
		lp_center.setMargins(margin * 2,margin, margin * 2, margin);
		addView(lable, lp2);

	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {

		this.number = number;
		Log.e("card" + x + y + "number", "" + number);

	}

	public TextView getLable() {
		return lable;
	}

	public void setLableText(int number) {
		if (getCardBackGroundType(getContext()) == Constant.COLOR_CARD) {
			lable.setLayoutParams(lp_center);
			changeTextSize(number);
		} else{
			lable.setLayoutParams(lp_bottom);
		}

		lable.setText(number + "");
		ThemeUtil.addCardBackGround(imageView, number, getContext());

	}

	public static int getCardBackGroundType(Context context) {
		return ((Mapplication) context.getApplicationContext()).getCardBackGroundType();
	}

	private void changeTextSize(int number) {
		String str =  String.valueOf(number);
		int length = str.length();
		if(length<4&&getCardBackGroundType(getContext()) == Constant.COLOR_CARD){
			lable.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getContext().getResources().getDimension(R.dimen.card_text_size));
		}else {
			lable.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getContext().getResources().getDimension(R.dimen.card_text_size_2));
		}
		invalidate();
	}


	private void resetTextSize(TextView lable, int number) {
		switch (number) {
		case 2:
		case 4:
		case 8:
			lable.setTextSize(getContext().getResources().getInteger(R.integer.card_text_size01));
			break;
		case 16:
		case 32:
		case 64:
			lable.setTextSize(getContext().getResources().getInteger(R.integer.card_text_size02));
			break;

		case 128:
		case 256:
		case 512:
			lable.setTextSize(getContext().getResources().getInteger(R.integer.card_text_size03));
			break;
		case 1024:
		case 2048:
		case 4096:
		case 8192:
			lable.setTextSize(getContext().getResources().getInteger(R.integer.card_text_size04));
			break;
		default:
			lable.setTextSize(getContext().getResources().getInteger(R.integer.card_text_size05));
			break;
		}

	}

	/**
	 * Combine show
	 */
	public void show() {
		if (getCardBackGroundType(getContext()) == Constant.COLOR_CARD) {
			lable.setLayoutParams(lp_center);

		} else{
			lable.setLayoutParams(lp_bottom);
		}
		changeTextSize(number);
		lable.setShadowLayer(10,0,0,ThemeUtil.getThemeColor(getContext()));
		setLableText(number);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void onAnimationEnd() {
		setLableText(number);
		this.setLayerType(FrameLayout.LAYER_TYPE_NONE, null);
		super.onAnimationEnd();
	}

}
