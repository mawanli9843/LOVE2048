package com.mawanli.love2048;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.listener.FindListener;

import com.mawanli.love2048.entity.UserInfo;
import com.mawanli.love2048.util.BmobUtil;
import com.mawanli.love2048.util.Constant;
import com.mawanli.love2048.util.Mapplication;
import com.mawanli.love2048.util.MediaPalyer;
import com.mawanli.love2048.util.ShapeUtil;
import com.mawanli.love2048.util.ThemeUtil;
import com.mawanli.love2048.util.ToastUtil;
import com.mawanli.love2048.view.MProgressView;
import com.umeng.analytics.MobclickAgent;

public class RankActivity extends BaseActivity {

	private RelativeLayout rootLayout;
	private ListView listView;

	private List<UserInfo> mUserInfos = new ArrayList<UserInfo>();

	private MApdapter mAdapter;

	private MProgressView mProgressView;

	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootLayout = (RelativeLayout) View.inflate(RankActivity.this, R.layout.activity_rank, null);
		rootLayout.setBackgroundDrawable(ThemeUtil.getActivityBg(RankActivity.this));
		setContentView(rootLayout);

		mProgressView = (MProgressView) findViewById(R.id.my_progress_view);

		initItemBackDrawable();

		listView = (ListView) findViewById(R.id.rank_list);
		mAdapter = new MApdapter();
		listView.setAdapter(mAdapter);

		handler = new Handler();

		BmobUtil.getRankList(RankActivity.this, new FindListener<UserInfo>() {

			@Override
			public void onSuccess(final List<UserInfo> userInfos) {

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						if (userInfos != null) {
							mUserInfos = userInfos;
							mProgressView.setVisibility(View.GONE);
							mAdapter.notifyDataSetChanged();
						}
					}
				}, 5000);

			}

			@Override
			public void onError(int arg0, String arg1) {
				
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mProgressView.setVisibility(View.GONE);
						ToastUtil.toastWhiteBg(RankActivity.this,"请检查您的网络状态");
					}
				}, 5000);
				
				
			}
		});

	}

	private List<Drawable> mItemBackDrawables = new ArrayList<Drawable>();

	private int[] colors = new int[7];

	int x;

	private void initItemBackDrawable() {
		for (int i = 0; i < Constant.defaultThemeColors.length; i++) {
			if (i != ((Mapplication) getApplication()).getColorNumber()) {
				colors[x++] = Constant.defaultThemeColors[i];
				mItemBackDrawables.add(ShapeUtil.getShape(20, Color.WHITE, 0, Constant.defaultThemeColors[i]));
				Log.e("----------initItemBackDrawable--------", "i=" + i);
			}
		}
	}

	@Override
	protected void onStop() {
		MediaPalyer.stop();
		super.onStop();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private class MApdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mUserInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView==null){
				viewHolder = new ViewHolder();
				convertView = View.inflate(RankActivity.this, R.layout.item_rank_list, null);
				viewHolder.mRankTV = (TextView) convertView.findViewById(R.id.rank);
				viewHolder.mUserName = (TextView) convertView.findViewById(R.id.user_name);
				viewHolder.mScore = (TextView) convertView.findViewById(R.id.score);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}

			convertView.setBackgroundDrawable((getViewBackGround(position)));
			viewHolder.mRankTV.setTextColor(colors[position % 7]);
			UserInfo mUserInfo = mUserInfos.get(position);
			viewHolder.mRankTV.setText(position + 1 + "");
			if(mUserInfo.getUserName()==null||mUserInfo.getUserName().equals("null")){
				viewHolder.mUserName.setText("");
			}else{
				viewHolder.mUserName.setText(mUserInfo.getUserName() + "");
			}
			viewHolder.mScore.setText(mUserInfo.getHighScore() + "");
			return convertView;
		}

	}

	private Drawable getViewBackGround(int position) {
		return mItemBackDrawables.get(position % 7);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}



private static class ViewHolder
{
	TextView mRankTV;
	TextView mUserName;
	TextView mScore;
}


}
