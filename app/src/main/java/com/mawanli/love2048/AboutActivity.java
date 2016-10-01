package com.mawanli.love2048;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.mawanli.love2048.util.ThemeUtil;
import com.umeng.fb.FeedbackAgent;

public class AboutActivity extends BaseActivity {

	private RelativeLayout rootLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootLayout = (RelativeLayout) View.inflate(AboutActivity.this, R.layout.activity_about, null);
		rootLayout.setBackgroundDrawable(ThemeUtil.getActivityBg(AboutActivity.this));
		setContentView(rootLayout);

		findViewById(R.id.feedback).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FeedbackAgent agent = new FeedbackAgent(AboutActivity.this);
				agent.startFeedbackActivity();
				overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
			}
		});
	}

}
