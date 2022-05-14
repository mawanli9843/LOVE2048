package com.mawanli.love2048.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import com.mawanli.love2048.R;
import java.util.HashMap;
import java.util.Map;

public class RoundCornerImageUtil {

	private RoundCornerImageUtil() {
	}

	private static Map<String, Bitmap> bitmaps = new HashMap<String, Bitmap>();

	public static Bitmap getRoundCorner(String number, Context context) {
		return getRoundCorner(number, context.getResources().getDimensionPixelSize(R.dimen.card_radius), context);
	}

	@SuppressWarnings("static-access")
	public static Bitmap getRoundCorner(String number, int pixels, Context context) {

		String filepath = Constant.USER_PIC_DIR + "/" + Constant.PIC + number + ".jpg";
		if (bitmaps.containsKey(filepath)) {
			return bitmaps.get(filepath);
		}
		Bitmap bitmap = new BitmapFactory().decodeFile(filepath);
		if (bitmap == null) {
			int i = ((int) (Math.log(Integer.parseInt(number)) / Math.log(2))) - 1;

				bitmap = getRoundCorner(Constant.defaultPicIDs[i], context);

		} else {
			// bitmap =toRoundCorner(bitmap, pixels);
			bitmap = toRoundCorner(bitmap, pixels);
			bitmaps.put(filepath, bitmap);
		}

		return bitmap;

	}

	public static Bitmap getRoundCorner(int resourceID, Context context) {
		return getRoundCorner(resourceID, context, context.getResources().getDimensionPixelSize(R.dimen.card_radius));
	}

	public static Bitmap getRoundCorner(int resourceID, Context context, int pixels) {
		if (bitmaps.containsKey(resourceID + "")) {
			return bitmaps.get(resourceID + "");
		}

		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID);

		int width = ((Mapplication) context.getApplicationContext()).getCardWith();

		bitmap = Bitmap.createScaledBitmap(bitmap, width, width, false);

		Log.e("======bitmap======", "--" + bitmap.getHeight() + "ddfdfdfdf---" + bitmap.getWidth());
		bitmaps.put(resourceID + "", toRoundCorner(bitmap, pixels));

		return bitmaps.get(resourceID + "");
	}

	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		bitmap.recycle();// 后加的
		bitmap = null;
		return output;
	}

	public static void cleanBitmaps() {
		bitmaps.clear();
	}

	public static void removeBitmap(String key) {

		if (bitmaps != null && bitmaps.containsKey(key)) {
			bitmaps.remove(key);
		}

	}
}
