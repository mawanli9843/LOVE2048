package com.mawanli.love2048;

import com.mawanli.love2048.R;
import com.mawanli.love2048.util.MediaPalyer;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;

public class ShareActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
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

}
