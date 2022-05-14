package com.mawanli.love2048;

import android.app.Activity;
import android.os.Bundle;
import androidx.core.view.WindowCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseActivity extends Activity {

	private static List<Activity> mActivitys = new ArrayList<Activity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		mActivitys.add(this);

	}

	public void exit() {

		Iterator iterator = mActivitys.iterator();

		while (iterator.hasNext()) {
			((Activity) iterator.next()).finish();
		}
		mActivitys.clear();

	}

	@Override
	protected void onDestroy() {
		mActivitys.remove(this);
		super.onDestroy();
	}

}
