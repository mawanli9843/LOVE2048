package com.mawanli.love2048.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.mawanli.love2048.R;

public class ToastUtil {

	private ToastUtil() {

	}

	public static void toast(Context context, String text) {
		toast(context, text, Color.WHITE, ThemeUtil.getThemeColor(context));
	}

	public static void toastWhiteBg(Context context, String text) {
		toast(context, text, ThemeUtil.getThemeColor(context), Color.WHITE);
	}

	public static void toast(Context context, String text, int textColor, int backgroundColor) {

		View rootView = View.inflate(context, R.layout.my_toast_layout, null);
		TextView textView = (TextView) rootView.findViewById(R.id.toast_text_view);
		rootView.setBackgroundDrawable(ShapeUtil.getColorButtonBg(backgroundColor,
				context.getResources().getDimensionPixelSize(R.dimen.toast_bg_round_corner_radius)));
		textView.setText(text);
		textView.setTextColor(textColor);
		Toast toast = new Toast(context.getApplicationContext());
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(rootView);
		toast.show();
	}

}
