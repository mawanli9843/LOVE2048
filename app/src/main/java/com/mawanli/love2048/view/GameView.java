package com.mawanli.love2048.view;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import com.mawanli.love2048.MainActivity;
import com.mawanli.love2048.R;
import com.mawanli.love2048.util.AudioUtil;
import com.mawanli.love2048.util.Constant;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.SharePreferenceUtil;
import com.mawanli.love2048.util.ToastUtil;
import java.util.ArrayList;

public class GameView extends GridLayout {

	private CardView[][] cards = new CardView[4][4];

	private boolean isCombine;
	private boolean isMove;

	private boolean isStart;

	private Mapplication app;

	Handler handler = new Handler();

	public GameView(Context context) {
		super(context);
		init();

	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CardView[][] getCards() {
		return cards;
	}

	private void init() {

		app = (Mapplication) (getContext().getApplicationContext());

		int with = app.getCardWith();
		if (with > 0) {
			initCards(with);
		}

		setOnTouchListener(new OnTouchListener() {
			float startX;
			float startY;
			float offsetX;
			float offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startY = event.getY();
					startX = event.getX();
					isCombine = false;
					isMove = false;
					break;

				case MotionEvent.ACTION_UP:

					savePreviousState();// 保存当前状态

					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;

					Log.e(offsetX + "", offsetY + "");

					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX > 5) {
							right();
						} else if (offsetX < -5) {
							left();
						}

					} else {

						if (offsetY > 5) {
							down();
						} else if (offsetY < -5) {
							up();
						}

					}
					if (isCombine && app.isSoundOn()) {
						AudioUtil.getInstance(getContext()).combine();
					}
					if (isCombine || isMove) {
						addRandomCard();
						if (app.isSoundOn()) {
							AudioUtil.getInstance(getContext()).move();
						}
					}
					break;
				}

				return true;
			}

			public void up() {

				int addScore = 0;

				for (int x = 0; x < 4; x++) {

					for (int y = 0; y < 4; y++) {

						for (int y1 = y + 1; y1 < 4; y1++) {

							if (cards[y1][x].getNumber() > 0) {

								if (cards[y][x].getNumber() == 0) {
									cards[y][x].setNumber(cards[y1][x].getNumber());
									// cards[y][x].show2();
									// cards[y1][x].move(0, y - y1);

									cards[y][x].move2(0, y1 - y);
									cards[y1][x].setNumber(0);
									cards[y1][x].setLableText(0);

									y--;
									isMove = true;

								} else if (cards[y][x].equal(cards[y1][x])) {

									checkNumber(cards[y1][x].getNumber() * 2);

									isCombine = true;
									cards[y][x].setNumber(cards[y1][x].getNumber() * 2);

									cards[y][x].show();
									cards[y1][x].move(0, y - y1);
									addScore += cards[y][x].getNumber();

								}
								break;
							}

						}

					}

				}

				MainActivity.getMainAcivity().addScore(addScore);

			}

			public void down() {
				int addScore = 0;

				for (int x = 0; x < 4; x++) {
					for (int y = 3; y >= 0; y--) {

						for (int y1 = y - 1; y1 >= 0; y1--) {

							if (cards[y1][x].getNumber() > 0) {

								if (cards[y][x].getNumber() == 0) {
									cards[y][x].setNumber(cards[y1][x].getNumber());
									//
									// cards[y][x].show2();
									// cards[y1][x].move(0, y - y1);

									cards[y][x].move2(0, y1 - y);
									cards[y1][x].setNumber(0);
									cards[y1][x].setLableText(0);

									y++;
									isMove = true;
								} else if (cards[y][x].equal(cards[y1][x])) {

									checkNumber(cards[y][x].getNumber() * 2);

									cards[y][x].setNumber(cards[y][x].getNumber() * 2);
									cards[y][x].show();
									cards[y1][x].move(0, y - y1);
									isCombine = true;
									addScore += cards[y][x].getNumber();
								}
								break;
							}

						}

					}

				}

				MainActivity.getMainAcivity().addScore(addScore);

			}

			public void left() {

				int addScore = 0;
				for (int y = 0; y < 4; y++) {

					for (int x = 0; x < 4; x++) {

						for (int x1 = x + 1; x1 < 4; x1++) {

							if (cards[y][x1].getNumber() > 0) {

								if (cards[y][x].getNumber() == 0) {

									cards[y][x].setNumber(cards[y][x1].getNumber());
									// cards[y][x].show2();
									// cards[y][x1].move(x - x1, 0);
									// Log.e(y + "==" + x1, x - x1 + "step");

									cards[y][x].move2(x1 - x, 0);
									cards[y][x1].setNumber(0);
									cards[y][x1].setLableText(0);
									x--;
									isMove = true;
								} else if (cards[y][x].equal(cards[y][x1])) {

									checkNumber(cards[y][x1].getNumber() * 2);

									cards[y][x].setNumber(cards[y][x1].getNumber() * 2);
									cards[y][x].show();
									cards[y][x1].move(x - x1, 0);
									isCombine = true;
									addScore += cards[y][x].getNumber();
								}
								break;
							}

						}

					}

				}
				MainActivity.getMainAcivity().addScore(addScore);
			}

			public void right() {
				int addScore = 0;
				for (int y = 0; y < 4; y++) {
					for (int x = 3; x >= 0; x--) {
						for (int x1 = x - 1; x1 >= 0; x1--) {

							if (cards[y][x1].getNumber() > 0) {

								if (cards[y][x].getNumber() == 0) {

									cards[y][x].setNumber(cards[y][x1].getNumber());
									// cards[y][x].show2();
									// Log.e(y + "==" + x1,"-------------");
									// cards[y][x1].move(x - x1, 0);
									cards[y][x].move2(x1 - x, 0);
									cards[y][x1].setNumber(0);
									cards[y][x1].setLableText(0);
									isMove = true;

									x++;
								} else if (cards[y][x].equal(cards[y][x1])) {

									checkNumber(cards[y][x1].getNumber() * 2);

									cards[y][x].setNumber(cards[y][x1].getNumber() * 2);
									cards[y][x].show();
									cards[y][x1].setNumber(0);
									cards[y][x1].move(x - x1, 0);
									isCombine = true;
									addScore += cards[y][x].getNumber();
								}
								break;
							}
						}
					}
				}
				MainActivity.getMainAcivity().addScore(addScore);
			}
		});

	}

	private void checkNumber(int i) {
		if (i == 2048 || i == 4096 || i == 8192) {

			if (app.isSoundOn()) {
				AudioUtil.getInstance(getContext()).win();
			}

			int coins = 0;

			switch (i) {
			case 2048:
				coins = Constant.REACH_2048;
				break;
			case 4096:
				coins = Constant.REACH_4096;
				break;
			case 8192:
				coins = Constant.REACH_8192;
				break;
			}
			MainActivity.getMainAcivity().showWinDialog(coins);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		if (!isStart) {

			int with = (Math.min(w, h)) / 4;
			initCards(with);
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void initCards(int with) {

		app.setCardWith(with);
		addCard(with, with);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				startGame(SharePreferenceUtil.getPrefBoolean(getContext(), "isNew", true));
			}
		}, getContext().getResources().getInteger(R.integer.activity_duration));

		isStart = true;
	}

	private void addCard(int cardWidth, int cardHeight) {

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				CardView cardView = new CardView(getContext());
				cardView.setXY(i, j);
				addView(cardView, cardWidth, cardHeight);
				cards[i][j] = cardView;
			}
		}

	}

	public void startGame(boolean isNew) {

		if (isNew) {
			MainActivity.getMainAcivity().resetScore();
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 4; x++) {
					cards[y][x].setNumber(0);
					cards[y][x].setLableText(0);
					cards[y][x].show3();
				}
			}
			addRandomCard();
			addRandomCard();
		} else {
			getCardState();
			getPreCardState();
		}

		if (app.isSoundOn()) {
			AudioUtil.getInstance(getContext()).start();
		}

	}

	private void getPreCardState() {
		int z = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				int i = SharePreferenceUtil.getPrefInt(getContext(), "pre" + (z++), 0);
				preStates[y][x] = i;
			}
		}
	}

	private void getCardState() {
		int z = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {

				int i = SharePreferenceUtil.getPrefInt(getContext(), z++ + "", 0);
				cards[y][x].setNumber(i);
				cards[y][x].setLableText(i);
				cards[y][x].show3();
			}
		}
	}

	private ArrayList<Point> emptyCardsPoints = new ArrayList<Point>();

	private void addRandomCard() {

		emptyCardsPoints.clear();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cards[y][x].getNumber() == 0) {
					emptyCardsPoints.add(new Point(x, y));
				}
			}
		}

		if (emptyCardsPoints.size() > 0) {
			Point p = emptyCardsPoints.remove((int) (Math.random() * emptyCardsPoints.size()));
			cards[p.y][p.x].setNumber(Math.random() > 0.1 ? 2 : 4);
			cards[p.y][p.x].show3();
		}
		if (emptyCardsPoints.size() == 0) {
			checkLose();
		}

	}

	private void checkLose() {

		boolean lose = true;

		A: for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				if (cards[i][j].equal(cards[i][j + 1])) {
					lose = false;
					break A;
				}
			}
		}
		B: if (lose == true) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 3; j++) {
					if (cards[j][i].equal(cards[j + 1][i])) {
						lose = false;
						break B;
					}
				}

			}
		}

		if (lose == true) {
			lose();
		}

	}

	private void lose() {

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				MainActivity.getMainAcivity().showLostDialog();

			}
		}, 200);
	}

	private int[][] preStates = new int[4][4];

	public int[][] getPreStates() {
		return preStates;
	}

	public void unDo() {

		boolean hasState = false;
		C: for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (preStates[y][x] > 0) {
					setPreviousState();
					hasState = true;
					break C;
				}
			}

		}

		if (!hasState) {
			ToastUtil.toast(getContext(), getContext().getResources().getString(R.string.one_step_each_time));
		}

	}

	private void savePreviousState() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int x = cards[i][j].getNumber();
				preStates[i][j] = x;
			}
		}
	}

	private void setPreviousState() {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				int i = preStates[y][x];
				cards[y][x].setNumber(i);
				cards[y][x].setLableText(i);
				cards[y][x].show3();
				preStates[y][x] = 0;
			}
		}

		app.setUndoCount(app.getUndoCount() - 1);

	}

}
