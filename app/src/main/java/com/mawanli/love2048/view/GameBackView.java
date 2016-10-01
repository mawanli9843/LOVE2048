package com.mawanli.love2048.view;

import com.mawanli.love2048.R;
import com.mawanli.love2048.util.Mapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.ImageView;

public class GameBackView extends GridLayout {

	private boolean isStart;

	private Mapplication mapp;

	public GameBackView(Context context) {
		super(context);
		init();

	}

	public GameBackView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GameBackView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mapp = (Mapplication) getContext().getApplicationContext();
		int width = mapp.getCardWith();
		if (width > 0) {
			initCards(width, width);
		}

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		if (!isStart) {
			int cardWidth = (Math.min(w, h)) / 4;
			initCards(cardWidth, cardWidth);
		}

		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void initCards(int cardWidth, int cardHeight) {

		isStart = true;
		mapp.setCardWith(cardWidth);

		for (int i = 0; i < 4; i++) {

			for (int j = 0; j < 4; j++) {

				ImageView iamgeView = new ImageView(getContext());
				iamgeView.setImageResource(R.drawable.white_round_corner_10);
				iamgeView.setPadding(10, 10, 10, 10);
				addView(iamgeView, cardWidth, cardHeight);
			}

		}

	}

}
