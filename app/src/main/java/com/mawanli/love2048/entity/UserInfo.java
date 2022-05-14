package com.mawanli.love2048.entity;

import cn.bmob.v3.BmobObject;

public class UserInfo extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userID;
	private boolean unLocked;
	private int coins;
	private String userName;
	private int highScore;
	private boolean blur;
	

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public boolean isUnLocked() {
		return unLocked;
	}

	public void setUnLocked(boolean unLocked) {
		this.unLocked = unLocked;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public boolean isBlur() {
		return blur;
	}

	public void setBlur(boolean blur) {
		this.blur = blur;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	

}
