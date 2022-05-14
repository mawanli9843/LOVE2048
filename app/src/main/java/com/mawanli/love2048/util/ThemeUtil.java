package com.mawanli.love2048.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.ImageView;
import com.mawanli.love2048.R;

public class ThemeUtil {

	/**
	 * Get the Activity background with roundCorner and ThemeColor
	 * 
	 * @param context
	 * @return
	 */
	public static Drawable getActivityBg(Context context) {

		return ShapeUtil.getShape(context.getResources().getDimensionPixelSize(R.dimen.activity_round_corner_radius), getThemeColor(context), 0,
				getThemeColor(context));
	}

	/**
	 * Set the theme color
	 * 
	 * @param context
	 * @param themeColor
	 */
	public static void setThemeColor(Context context, int themeColor) {

		((Mapplication) context.getApplicationContext()).setThemeColor(themeColor);
		cleanUserColors(context);

	}

	/**
	 * Get the Theme Color
	 * 
	 * @param context
	 * @return
	 */
	public static int getThemeColor(Context context) {

		return ((Mapplication) context.getApplicationContext()).getThemeColor();
	}

	/**
	 * Get the ColorStateList for Button default color is White
	 * 
	 * @param context
	 * @return
	 */
	public static ColorStateList getColorStateWhite(Context context) {

		return new ColorStateList(new int[][] { { android.R.attr.state_pressed }, { 0 } }, new int[] { getThemeColor(context), Color.WHITE });
	}

	/**
	 * Get the ColorStateList for ButtonText default color is ThemeColor
	 * 
	 * @param context
	 * @return
	 */
	public static ColorStateList getColorStateThemeColor(Context context) {

		return new ColorStateList(new int[][] { { android.R.attr.state_pressed }, { 0 } }, new int[] { Color.WHITE, getThemeColor(context) });
	}

	/**
	 * Get the StateListDrawable for Button background default color is White
	 * 
	 * @param context
	 * @param radius
	 *            This is the button radius, but it doesn’t seem to work
	 * @return
	 */
	public static StateListDrawable whiteButtonBg(Context context, int radius) {

		StateListDrawable sld = new StateListDrawable();
		sld.addState(new int[] { android.R.attr.state_pressed },
				ShapeUtil.getWhiteStrokeButtonBg(getThemeColor(context), radius, context.getResources().getInteger(
                        R.integer.big_stroke_width)));
		sld.addState(new int[] { 0 },
				ShapeUtil.getColorStrokeButtonBg(Color.WHITE, radius, context.getResources().getInteger(
                        R.integer.big_stroke_width)));
		return sld;
	}

	/**
	 * Get the StateListDrawable for Button background default color is Theme
	 * color
	 * 
	 * @param context
	 * @return
	 */
	public static StateListDrawable themeColorButtonBg(Context context) {

		StateListDrawable sld = new StateListDrawable();
		sld.addState(
				new int[] { android.R.attr.state_pressed },
				ShapeUtil.getColorStrokeButtonBg(getThemeColor(context), R.dimen.big_radius,
						context.getResources().getInteger(R.integer.big_stroke_width)));
		sld.addState(
				new int[] { 0 },
				ShapeUtil.getWhiteStrokeButtonBg(getThemeColor(context), R.dimen.big_radius,
						context.getResources().getInteger(R.integer.big_stroke_width)));
		return sld;
	}

	public static StateListDrawable themeColorButtonBgWithOutStroke(Context context) {

		StateListDrawable sld = new StateListDrawable();
		sld.addState(
				new int[] { android.R.attr.state_pressed },
				ShapeUtil.getColorStrokeButtonBg(getThemeColor(context), R.dimen.big_radius,
						context.getResources().getInteger(R.integer.big_stroke_width)));
		sld.addState(new int[] { 0 }, ShapeUtil.getWhiteStrokeButtonBg(getThemeColor(context), R.dimen.big_radius, 0));
		return sld;
	}

	/**
	 * The number cards radius
	 */
	public static int colorBgRadius;

	/**
	 * add background to number card
	 * 
	 * @param imageView
	 *            the number card background ,actually it is a imageView
	 * @param number
	 *            the number of the number card
	 * @param context
	 */
	public static void addCardBackGround(ImageView imageView, int number, Context context) {

		int i = ((int) (Math.log(number) / Math.log(2))) - 1;

		if (colorBgRadius == 0) {

			colorBgRadius =context.getResources().getDimensionPixelSize(R.dimen.card_radius);
		}

		if (i < 16) {

			if (getCardBackGroundType(context) == Constant.COLOR_CARD) {

				imageView.setImageDrawable(ShapeUtil.getShape(context.getResources().getDimensionPixelSize(
                        R.dimen.card_radius)-context.getResources().getDimensionPixelSize(R.dimen.card_stroke_with), getCardColor(i, context), 2, getCardColor(i, context)));

			} else if (getCardBackGroundType(context) == Constant.PIC_CARD) {
				
					imageView.setImageBitmap(RoundCornerImageUtil.getRoundCorner(Constant.defaultPicIDs[i], context));

				
				
				

			} else if (getCardBackGroundType(context) == Constant.USER_PIC_CARD) {

				imageView.setImageBitmap(RoundCornerImageUtil.getRoundCorner(number + "", context));
			}
		} else {

			imageView.setImageDrawable(ShapeUtil.getShape(context.getResources().getDimensionPixelSize(
                    R.dimen.card_radius)-context.getResources().getDimensionPixelSize(R.dimen.card_stroke_with), Color.BLACK, 0, 0));

		}

	}

	private static int[] colors = new int[16];

	public static int getCardColor(int i, Context context) {

		if (colors[i] == 0) {
			colors[i] = SharePreferenceUtil.getPrefInt(context, Constant.COLOR + i,
					Constant.defaultCardColors[((Mapplication) context.getApplicationContext()).getColorNumber()][i]);
		}
		return colors[i];
	}

	public static void setCardColor(int i, int color, Context context) {

		colors[i] = color;
		SharePreferenceUtil.setPrefInt(context, Constant.COLOR + i, color);
	}

	public static void cleanUserColors(Context context) {

		for (int i = 0; i < 16; i++) {

			colors[i] = 0;
			SharePreferenceUtil.remove(context, Constant.COLOR + i);

		}

	}

	/**
	 * get the card background type Constant.PIC_CARD is default picture mode
	 * ,Constant.COLOR_CARD is color mode (include default and user set),
	 * Constant.USER_PIC_CARD is user set picture
	 * 
	 * @param context
	 * @return
	 */
	public static int getCardBackGroundType(Context context) {
		return ((Mapplication) context.getApplicationContext()).getCardBackGroundType();
	}

	/**
	 * set card background type
	 * 
	 * @param type
	 * @param context
	 */
	public static void setCardBackGroundType(int cardBackGroundType, Context context) {
		((Mapplication) context.getApplicationContext()).setCardBackGroundType(cardBackGroundType);
	}

	public static StateListDrawable transparentWhiteButtonBg(Context context) {

		StateListDrawable sld = new StateListDrawable();
		sld.addState(new int[] { android.R.attr.state_pressed }, ShapeUtil.getColorStrokeButtonBg(Color.TRANSPARENT, R.dimen.big_radius, context
				.getResources().getInteger(R.integer.big_stroke_width)));
		sld.addState(new int[] { 0 }, ShapeUtil.getWhiteStrokeButtonBg(Color.TRANSPARENT, R.dimen.big_radius,
				context.getResources().getInteger(R.integer.big_stroke_width)));
		return sld;
	}

	public static StateListDrawable whiteTransparentButtonBg(Context context) {
		StateListDrawable sld = new StateListDrawable();
		sld.addState(new int[] { android.R.attr.state_pressed },
				ShapeUtil.getWhiteStrokeSubfillBg(
                        R.dimen.big_radius, context.getResources().getInteger(R.integer.big_stroke_width)));
		sld.addState(new int[] { 0 }, ShapeUtil.getColorButtonBg(Color.WHITE, R.dimen.big_radius));
		return sld;
	}

	public static StateListDrawable transparentThemeStrokeButtonBg(Context context) {

		StateListDrawable sld = new StateListDrawable();
		sld.addState(new int[] { android.R.attr.state_pressed }, ShapeUtil.getWhiteStrokeButtonBg(Color.TRANSPARENT, R.dimen.big_radius, context
				.getResources().getInteger(R.integer.big_stroke_width)));
		sld.addState(
				new int[] { 0 },
				ShapeUtil.getColorStrokeSubfillBg(getThemeColor(context), R.dimen.big_radius,
						context.getResources().getInteger(R.integer.big_stroke_width)));
		return sld;
	}

	public static StateListDrawable transparentWhiteStrokeButtonBg(Context context) {

		StateListDrawable sld = new StateListDrawable();
		sld.addState(
				new int[] { android.R.attr.state_pressed },
				ShapeUtil.getColorStrokeSubfillBg(getThemeColor(context), R.dimen.big_radius,
						context.getResources().getInteger(R.integer.big_stroke_width)));
		sld.addState(new int[] { 0 }, ShapeUtil.getWhiteStrokeButtonBg(Color.TRANSPARENT, R.dimen.big_radius,
				context.getResources().getInteger(R.integer.big_stroke_width)));
		return sld;
	}

}
