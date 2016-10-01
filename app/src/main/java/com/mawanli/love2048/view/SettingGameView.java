package com.mawanli.love2048.view;

import com.mawanli.love2048.util.Mapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridLayout;

public class SettingGameView extends GridLayout {

	private SettingCardView[][] cards = new SettingCardView[4][4];
	private boolean isStart;

	Handler handler = new Handler();

	public SettingGameView(Context context) {
		super(context);
		init();

	}

	public SettingGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SettingGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SettingCardView[][] getCards() {
		return cards;
	}

	private void init() {
		Mapplication mapp = ((Mapplication) getContext().getApplicationContext());
		int with = mapp.getCardWith();
		addCard(with, with);
		startGame();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		if (!isStart) {

			isStart = true;
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void addCard(int cardWidth, int cardHeight) {

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				SettingCardView cardView = new SettingCardView(getContext());
				cardView.setXY(i, j);
				addView(cardView, cardWidth, cardHeight);
				cards[i][j] = cardView;
			}
		}

	}

	public void startGame() {

		int i = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cards[y][x].setNumber(2 << i);
				cards[y][x].setLableText(2 << i);
				cards[y][x].show();
				i++;
			}
		}
	}

	BlurDialog dialog;

	public void showDialog(String title, String buttonText, OnClickListener onClickListener) {
		dialog = new BlurDialog((Activity) getContext());
		dialog.setCancelable(false);
		dialog.setTitle(title);
		dialog.setButton1(buttonText, onClickListener);
		dialog.show();

	}

	public void hideDailog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	public void refreshAll() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				cards[i][j].show();
			}
		}
	}

	public void refresh(int number) {

		int i = ((int) (Math.log(number) / Math.log(2))) - 1;

		int x = i / 4;
		int y = i % 4;

		Log.e("-i-x-y-", i + "----" + x + "----" + y);
		cards[x][y].show();

	}

	public SettingCardView[][] getCardViews() {
		return cards;
	}

}
