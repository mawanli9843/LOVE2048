package com.mawanli.love2048.util;

import com.mawanli.love2048.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ScreenUtil {

	public static int SCALE_SIZE = 15

			;
	public static int RADIUS = 10;

	private ScreenUtil() {

	}
	public static int dpToPx(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;

		return (int) (dp * scale + 0.5f);
	}

	public static int pxToDp(Context context, float px) {

		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	public static int getScreenWith(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static float getDensityDpi(Context context) {
		return context.getResources().getDisplayMetrics().densityDpi;
	}

	public static float getDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	public static int getPicCorner(Context context, int youPicWidth) {

		int realCardWidth = (((Mapplication) context.getApplicationContext()).getCardWith() - context.getResources().getDimensionPixelSize(
				R.dimen.card_margin) * 2);
		return context.getResources().getDimensionPixelSize(R.dimen.card_radius) * youPicWidth / realCardWidth;
	}

	/**
	 * 屏幕截图
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap takeScreenShot(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top;
		System.out.println(statusBarHeight);

		int height = activity.getWindowManager().getDefaultDisplay().getHeight();
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		// Point point = new Point();
		// activity.getWindowManager().getDefaultDisplay().getSize(point);
		// int width = point.x;
		// int height = point.y;

		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return RoundCornerImageUtil.toRoundCorner(bitmap2, 10);
	}

	/**
	 * 缩小指定倍数的屏幕截图
	 * 
	 * @param activity
	 * @param scaleSize
	 * @return
	 */
	public static Bitmap takeScreenShot(Activity activity, int scaleSize) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache(true);

		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top;
		Log.e("-----statusBarHeight------", "statusBarHeight" + statusBarHeight);

		

		int height = activity.getWindowManager().getDefaultDisplay().getHeight();
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
//		Point point = new Point();
//		activity.getWindowManager().getDefaultDisplay().getSize(point);
//
//		int width = point.x;
//		int height = point.y;
		// Log.e("width=" + width, "height=" + height);

		// Bitmap bitmap2 = Bitmap.createBitmap(bitmap,
		// ScreenUtil.dpToPx(activity, 5), ScreenUtil.dpToPx(activity, 5),
		// width - ScreenUtil.dpToPx(activity, 5) * 2, height -
		// ScreenUtil.dpToPx(activity, 5) * 2);
		Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, width / scaleSize, height / scaleSize, false);

		view.destroyDrawingCache();
		return bitmap2;
	}

	public static Bitmap getBlurBitmap(Bitmap bitmap, float radius) {
		return FastBlur.doBlur(bitmap, (int) radius, true);
	}

	public static Bitmap getBlurBitmap(Bitmap bitmap, float radius, boolean canReuse) {
		return FastBlur.doBlur(bitmap, (int) radius, canReuse);
	}

	public static Bitmap getBlurBitmap(Activity activity) {
		return FastBlur.doBlur(takeScreenShot(activity, SCALE_SIZE), RADIUS, true);
	}

	public static void showBlurBg(Activity activity, ImageView view) {
		view.setImageBitmap(getBlurBitmap(takeScreenShot(activity, SCALE_SIZE), RADIUS));
	}

}
