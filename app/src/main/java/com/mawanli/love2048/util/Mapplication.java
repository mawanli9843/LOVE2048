package com.mawanli.love2048.util;

import java.util.List;

import com.mawanli.love2048.R;
import com.mawanli.love2048.entity.UserInfo;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import android.app.Application;
import android.content.Context;
import android.util.Log;

public class Mapplication extends Application {

	/**
	 * game sound status, true is on , false is off
	 */
	private boolean soundOn;

	/**
	 * game background music status, true is on , false is off
	 */
	private boolean musicOn;

	/**
	 * the theme color of the application
	 */
	private int themeColor;

	/**
	 * the type of numberCard background
	 */
	private int cardBackGroundType = -1;

	private int cardWith;

	private int undoCount;

	private boolean isUnLock;

	private int highScore;

	private int rank;

	private int colorNumber = -1;

	private int coins;

	private String objectId;

	private String userName;

	private UserInfo userInfo;

	private boolean BLUR = false;

	@Override
	public void onCreate() {

		/**
		 * 初始化Bmob
		 */
		Bmob.initialize(getApplicationContext(), Constant.BMOB_APP_ID);

		/**
		 * 有米初始化
		 */

		soundOn = SharePreferenceUtil.getPrefBoolean(getApplicationContext(), Constant.SOUND_STATUS, true);
		musicOn = SharePreferenceUtil.getPrefBoolean(getApplicationContext(), Constant.MUSIC_STATUS, true);
		themeColor = SharePreferenceUtil.getPrefInt(getApplicationContext(), Constant.COLOR, Constant.defaultThemeColors[0]);
		cardBackGroundType = SharePreferenceUtil.getPrefInt(getApplicationContext(), Constant.CARD_BACKGROUND_TYPE_KEY, Constant.COLOR_CARD);
		undoCount = SharePreferenceUtil.getPrefInt(getApplicationContext(), Constant.UNDO_COUNT_KEY, 0);
		BLUR =SharePreferenceUtil.getPrefBoolean(getApplicationContext(), "blur", false);

		/**
		 * Bmob检查是否解锁
		 * 
		 * 
		 */
		checkLock();

		/**
		 * 检查排名
		 */
		checkRank();

		super.onCreate();
	}

	public void checkRank() {
		BmobUtil.getRank(getApplicationContext(), new CountListener() {

			@Override
			public void onSuccess(int rank) {
				setRank(rank + 1);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.e("--BmobUtil.getRank--", arg0 + arg1);
			}
		});
	}

	public void checkLock() {
		BmobUtil.findUserInfo(getApplicationContext(), new FindListener<UserInfo>() {

			@Override
			public void onError(int arg0, String arg1) {

				Log.e("=======checkLock_onError========", arg0 + arg1);

			}

			@Override
			public void onSuccess(List<UserInfo> list) {

				if (list.size() > 0) {
					setUserInfo(list.get(0));
					Log.e("checkUnLocked", isUnLock + "---");
				} else {
					BmobUtil.register(getApplicationContext(), new SaveListener() {

						@Override
						public void onSuccess() {
							Log.e("----register----", "success");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Log.e("----register----onFailure", arg0 + "|" + arg1);
						}
					});
				}

			}
		});
	}

	public boolean isSoundOn() {
		return soundOn;
	}

	public void setSoundOn(boolean soundOn) {
		this.soundOn = soundOn;
		SharePreferenceUtil.setPrefBoolean(getApplicationContext(), Constant.SOUND_STATUS, soundOn);
	}

	public boolean isMusicOn() {
		return musicOn;
	}

	public void setMusicOn(boolean musicOn) {
		this.musicOn = musicOn;
		SharePreferenceUtil.setPrefBoolean(getApplicationContext(), Constant.MUSIC_STATUS, musicOn);
	}

	public int getThemeColor() {
		return themeColor;
	}

	public void setThemeColor(int themeColor) {
		this.themeColor = themeColor;
		SharePreferenceUtil.setPrefInt(getApplicationContext(), Constant.COLOR, themeColor);
	}

	public int getCardBackGroundType() {
		if (cardBackGroundType < 0) {
			cardBackGroundType = SharePreferenceUtil.getPrefInt(getApplicationContext(), Constant.CARD_BACKGROUND_TYPE_KEY, Constant.COLOR_CARD);
		}

		return cardBackGroundType;
	}

	public void setCardBackGroundType(int cardBackGroundType) {
		this.cardBackGroundType = cardBackGroundType;
		SharePreferenceUtil.setPrefInt(getApplicationContext(), Constant.CARD_BACKGROUND_TYPE_KEY, cardBackGroundType);
	}

	public int getCardWith() {
		if (cardWith <= 0) {
			cardWith = SharePreferenceUtil.getPrefInt(getApplicationContext(), Constant.CARD_WIDTH, 0);
		}
		return cardWith;
	}

	public void setCardWith(int cardWith) {
		this.cardWith = cardWith;
		SharePreferenceUtil.setPrefInt(getApplicationContext(), Constant.CARD_WIDTH, cardWith);
	}

	public int getUndoCount() {
		return undoCount;
	}

	public void setUndoCount(int undoCount) {
		this.undoCount = undoCount;
		SharePreferenceUtil.setPrefInt(getApplicationContext(), Constant.UNDO_COUNT_KEY, undoCount);
	}

	public boolean isUnLock() {
		return isUnLock;
	}

	public void setUnLock(boolean isUnLock) {
		this.isUnLock = isUnLock;
	}

	public int getHighScore() {
		if (highScore <= 0) {
			highScore = SharePreferenceUtil.getPrefInt(getApplicationContext(), Constant.HIGH_SCORE, 0);
		}
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
		SharePreferenceUtil.setPrefInt(getApplicationContext(), Constant.HIGH_SCORE, highScore);
	}

	public int getRank() {
		if (rank <= 0) {
			rank = SharePreferenceUtil.getPrefInt(getApplicationContext(), Constant.RANK, 0);
		}
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
		SharePreferenceUtil.setPrefInt(getApplicationContext(), Constant.RANK, rank);
	}

	public int getColorNumber() {
		if (colorNumber < 0) {
			colorNumber = SharePreferenceUtil.getPrefInt(getApplicationContext(), Constant.COLOR_NUMBER, 0);
		}
		return colorNumber;
	}

	public void setColorNumber(int colorNumber) {
		this.colorNumber = colorNumber;
		SharePreferenceUtil.setPrefInt(getApplicationContext(), Constant.COLOR_NUMBER, colorNumber);
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public String getObjectId() {
		if (objectId == null) {
			objectId = SharePreferenceUtil.getPrefString(getApplicationContext(), Constant.OBJECT_ID, null);
		}
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
		if (objectId != null) {
			SharePreferenceUtil.setPrefString(getApplicationContext(), Constant.OBJECT_ID, objectId);
		}

	}

	public String getUserName() {
		if (userName == null) {
			userName = SharePreferenceUtil.getPrefString(getApplicationContext(), Constant.USER_NAME, null);
		}
		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
		if (userName != null) {
			SharePreferenceUtil.setPrefString(getApplicationContext(), Constant.USER_NAME, userName);
		}

	}

	public void setUserInfo(UserInfo userInfo) {
		setUnLock(userInfo.isUnLocked());
		setHighScore(userInfo.getHighScore());
		setCoins(userInfo.getCoins());
		setObjectId(userInfo.getObjectId());
		setUserName(userInfo.getUserName());
		setBLUR(userInfo.isBlur());
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		if (userInfo == null) {
			userInfo = new UserInfo();
		}
		userInfo.setCoins(getCoins());
		userInfo.setHighScore(getHighScore());
		userInfo.setUnLocked(isUnLock());
		userInfo.setUserID(DeviceUtil.getSN(getApplicationContext()));
		userInfo.setUserName(getUserName());
		return userInfo;
	}

	public int getPicWidth(Context context) {
		return (int) (getCardWith() - context.getResources().getDimension(R.dimen.card_stroke_with));
	}

	public boolean isBLUR() {
		return BLUR;
	}

	public void setBLUR(boolean bLUR) {
		BLUR = bLUR;
		SharePreferenceUtil.setPrefBoolean(getApplicationContext(), "blur", bLUR);
	}

}
