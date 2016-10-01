package com.mawanli.love2048.util;

import java.util.List;

import android.content.Context;
import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.mawanli.love2048.entity.UserInfo;

public class BmobUtil {

	public static void unLocked(final Context context, final UpdateListener mUpdateListener) {
		String objectID = ((Mapplication) context.getApplicationContext()).getObjectId();
		if (objectID == null) {
			findUserInfo(context, new FindListener<UserInfo>() {

				@Override
				public void onError(int arg0, String arg1) {

				}

				@Override
				public void onSuccess(List<UserInfo> list) {
					if (list.size() > 0) {

						String sn = DeviceUtil.getSN(context);
						UserInfo mUserInfo = list.get(0);
						mUserInfo.setUnLocked(true);
						mUserInfo.setUserID(sn);
						mUserInfo.update(context, list.get(0).getObjectId(), mUpdateListener);
						((Mapplication) context.getApplicationContext()).setUserInfo(mUserInfo);

					}
				}
			});
		} else {
			String sn = DeviceUtil.getSN(context);
			UserInfo mUserInfo = ((Mapplication) context.getApplicationContext()).getUserInfo();
			mUserInfo.setUnLocked(true);
			mUserInfo.setUserID(sn);
			mUserInfo.update(context, objectID, mUpdateListener);

			((Mapplication) context.getApplicationContext()).setUnLock(true);

		}

	}

	public static void register(Context context, SaveListener mSaveListener) {

		String sn = DeviceUtil.getSN(context);

		UserInfo mUserInfo = new UserInfo();
		mUserInfo.setUserID(sn);
		mUserInfo.save(context, mSaveListener);

	}

	public static void findUserInfo(Context context, FindListener<UserInfo> findListener) {

		String sn = DeviceUtil.getSN(context);

		BmobQuery<UserInfo> bmobQuery = new BmobQuery<UserInfo>();
		bmobQuery.addWhereEqualTo("userID", sn);
		bmobQuery.findObjects(context, findListener);
	}

	public static void getRankList(Context context, FindListener<UserInfo> findListener) {
		BmobQuery<UserInfo> bmobQuery = new BmobQuery<UserInfo>();
		bmobQuery.order("-highScore");
		bmobQuery.setLimit(5000);
		bmobQuery.findObjects(context, findListener);
	}

	public static void getRank(Context context, CountListener countListener) {
		BmobQuery<UserInfo> bmobQuery = new BmobQuery<UserInfo>();
		bmobQuery.addWhereGreaterThan("highScore", ((Mapplication) context.getApplicationContext()).getHighScore());
		bmobQuery.count(context, UserInfo.class, countListener);
	}

	public static void setHighScore(final Context context, final int highScore) {


		findUserInfo(context, new FindListener<UserInfo>() {

			@Override
			public void onError(int arg0, String arg1) {

			}

			@Override
			public void onSuccess(List<UserInfo> list) {
				if (list.size() > 0) {

					UserInfo mUserInfo = list.get(0);
					mUserInfo.setHighScore(highScore);
					mUserInfo.update(context, list.get(0).getObjectId(), null);
					((Mapplication) context.getApplicationContext()).setUserInfo(mUserInfo);
				}
			}
		});

	}

	public static void setCoins(final Context context, final int coins) {


		findUserInfo(context, new FindListener<UserInfo>() {

			@Override
			public void onError(int arg0, String arg1) {
				Log.e("------setCoins_findUserInfo------", arg0 + arg1);
			}

			@Override
			public void onSuccess(List<UserInfo> list) {
				if (list.size() > 0) {

					UserInfo mUserInfo = list.get(0);
					mUserInfo.setCoins(coins);
					mUserInfo.update(context, list.get(0).getObjectId(), new UpdateListener() {

						@Override
						public void onSuccess() {
							Log.e("------setCoins------", "setCoins=" + coins);
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Log.e("------setCoins------", arg0 + arg1);
						}
					});

					((Mapplication) context.getApplicationContext()).setUserInfo(mUserInfo);
				}
			}
		});

	}

	public static void setUserName(final Context context, final String userName) {


		findUserInfo(context, new FindListener<UserInfo>() {

			@Override
			public void onError(int arg0, String arg1) {
				Log.e("------setCoins_findUserInfo------", arg0 + arg1);
			}

			@Override
			public void onSuccess(List<UserInfo> list) {
				if (list.size() > 0) {

					final UserInfo mUserInfo = list.get(0);
					mUserInfo.setUserName(userName);
					mUserInfo.update(context, list.get(0).getObjectId(), new UpdateListener() {

						@Override
						public void onSuccess() {
							Log.e("------setUserName------", "setCoins=" + userName);

						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Log.e("------setUserName------", arg0 + arg1);
						}
					});

					((Mapplication) context.getApplicationContext()).setUserInfo(mUserInfo);
				}
			}
		});

	}

}
