package com.mawanli.love2048.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class ShapeUtil {

	static int roundRadius = 10; // 8dp

	public static GradientDrawable getShape(int roundRadius, int strokeColor, int strokeWidth, int fillColor) {
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(fillColor);
		gd.setCornerRadius(roundRadius);
		gd.setStroke(strokeWidth, strokeColor);
		return gd;
	}

	public static Drawable getColorButtonBg(int color, int roundRadius) {
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(color);
		gd.setCornerRadius(roundRadius);
		gd.setStroke(0, color);
		return gd;
	}

	public static Drawable getWhiteStrokeButtonBg(int fillColor, int roundRadius, int strokeWidth) {
		return getShape(roundRadius, Color.WHITE, strokeWidth, fillColor);
	}

	public static Drawable getColorStrokeButtonBg(int strokeColor, int roundRadius, int strokeWidth) {
		return getShape(roundRadius, strokeColor, strokeWidth, Color.WHITE);
	}

	public static Drawable getWhiteStrokeSubfillBg(int roundRadius, int strokeWidth) {
		return getShape(roundRadius, Color.WHITE, strokeWidth, Color.TRANSPARENT);
	}

	public static Drawable getColorStrokeSubfillBg(int strokeColor, int roundRadius, int strokeWidth) {
		return getShape(roundRadius, strokeColor, strokeWidth, Color.TRANSPARENT);
	}
}
