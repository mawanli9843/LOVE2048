package com.mawanli.love2048.util;

import android.graphics.Color;
import android.os.Environment;
import com.mawanli.love2048.R;

public class Constant {

	/**
	 * The key to get the Theme number
	 */
	public static final String THEME = "theme";

	/**
	 * The key to get is the application first start from SharePreference
	 */
	public static final String FISRT_START = "first";

	/**
	 * The key to get Theme Color from SharePreference
	 */
	public static final String COLOR = "color";

	/**
	 * The key to get pictureID or picturePath from SharePreference
	 */
	public static final String PIC = "pic";

	/**
	 * The key to get the card background type from SharePreference
	 */
	public static final String CARD_BACKGROUND_TYPE_KEY = "cardBgType";

	public static final String CARD_WIDTH = "cardWidth";

	/**
	 * card background is color
	 */
	public static final int COLOR_CARD = 0;
	/**
	 * card background is default picture
	 */
	public static final int PIC_CARD = 1;

	/**
	 * card background is user set picture
	 */
	public static final int USER_PIC_CARD = 2;

	public static final String APP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.mawanli.love2048";

	public final static String USER_PIC_DIR = APP_DIR + "/userPics";

	public final static String SHARE_PIC_PATH = APP_DIR + "/sharePics/share.png";

	public static final String SOUND_STATUS = "soundStatus";
	public static final String MUSIC_STATUS = "musicStatus";

	public static final String UNDO_COUNT_KEY = "undoCount";

	public static final String DOWN_LOAD_URL = "http://love2048.bmob.cn";

	public static final String RANK = "rank";

	public static final String HIGH_SCORE = "highScore";

	public static final String COLOR_NUMBER = "colorNumber";

	public static final String OBJECT_ID = "objectID";

	public static final String USER_NAME = "userName";

	// public static final int FIRST_ACTIVITY = 5;
	// public static final int LOGIN_ACTIVITY = 6;
	public static final int MAIN_ACTIVITY = 4;
	public static final int GET_UNDO_ACTIVITY = 1;
	public static final int SETTING_ACTIVITY = 2;
	public static final int THEME_SETTING_ACITIVITY = 3;

	public static final int PIC_PIEX = 250;

	
	
	

	/**
	 * eight default colors
	 */

	public static final int[] defaultThemeColors = { Color.parseColor("#333333"), Color.parseColor("#e75270"), Color.parseColor("#fd6732"),
			Color.parseColor("#fdca30"), Color.parseColor("#00b07a"), Color.parseColor("#00b5b8"), Color.parseColor("#2e65fd"),
			Color.parseColor("#9656f2") };

	/**
	 * card color with different number
	 */
	public static final int[][] defaultCardColors = new int[][] {
			// 灰色
			{ Color.parseColor("#ff94ab"), Color.parseColor("#ff6e8d"), Color.parseColor("#ff4169"), Color.parseColor("#ff003c"),
					Color.parseColor("#ffb250"), Color.parseColor("#ff953d"), Color.parseColor("#ff7400"), Color.parseColor("#ff4f00"),
					Color.parseColor("#63ddaf"), Color.parseColor("#3bce97"), Color.parseColor("#01b88a"), Color.parseColor("#009f77"),
					Color.parseColor("#4ad7f2"), Color.parseColor("#3DC0D3"), Color.parseColor("#00a3c0"), Color.parseColor("#008eaa") },

			// 红
			{ Color.parseColor("#7FFF7F"), Color.parseColor("#28E428"), Color.parseColor("#00c900"), Color.parseColor("#2CB200"),
					Color.parseColor("#7Effff"), Color.parseColor("#1EE2E2"), Color.parseColor("#00C9C9"), Color.parseColor("#00A3A3"),
					Color.parseColor("#63ddaf"), Color.parseColor("#3bce97"), Color.parseColor("#01b88a"), Color.parseColor("#009f77"),
					Color.parseColor("#FF7CC6"), Color.parseColor("#ff3eab"), Color.parseColor("#FF2BA8"), Color.parseColor("#FA008E") },
			// 橙
			{ Color.parseColor("#63ddaf"), Color.parseColor("#3bce97"), Color.parseColor("#01b88a"), Color.parseColor("#009f77"),
					Color.parseColor("#77c9ff"), Color.parseColor("#20A7FF"), Color.parseColor("#1393E0"), Color.parseColor("#107abf"),
					Color.parseColor("#FF8D65"), Color.parseColor("#FF6F3A"), Color.parseColor("#FF5F25"), Color.parseColor("#FF4100"),
					Color.parseColor("#FF5B92"), Color.parseColor("#FF3B83"), Color.parseColor("#FF1E74"), Color.parseColor("#FF0059") },
			// 黄
			{ Color.parseColor("#b381f5"), Color.parseColor("#7E0AF5"), Color.parseColor("#5F0FC8"), Color.parseColor("#4600AC"),
					Color.parseColor("#7Effff"), Color.parseColor("#1EE2E2"), Color.parseColor("#00C9C9"), Color.parseColor("#00A3A3"),
					Color.parseColor("#ffb250"), Color.parseColor("#ff953d"), Color.parseColor("#ff7400"), Color.parseColor("#ff4f00"),
					Color.parseColor("#7FFF7F"), Color.parseColor("#28E428"), Color.parseColor("#00c900"), Color.parseColor("#2CB200") },
			// 绿
			{ Color.parseColor("#7FFF7F"), Color.parseColor("#28E428"), Color.parseColor("#00c900"), Color.parseColor("#2CB200"),
					Color.parseColor("#7A7AFF"), Color.parseColor("#5959FF"), Color.parseColor("#3B3BFF"), Color.parseColor("#0000FF"),
					Color.parseColor("#ffb250"), Color.parseColor("#ff953d"), Color.parseColor("#ff7400"), Color.parseColor("#ff4f00"),
					Color.parseColor("#63ddaf"), Color.parseColor("#3bce97"), Color.parseColor("#01b88a"), Color.parseColor("#009f77") },
			// 蓝
			{ Color.parseColor("#FF8D65"), Color.parseColor("#FF6F3A"), Color.parseColor("#FF5F25"), Color.parseColor("#FF4100"),
					Color.parseColor("#7FFF7F"), Color.parseColor("#28E428"), Color.parseColor("#00c900"), Color.parseColor("#2CB200"),
					Color.parseColor("#7A7AFF"), Color.parseColor("#5959FF"), Color.parseColor("#3B3BFF"), Color.parseColor("#0000FF"),
					Color.parseColor("#5edada"), Color.parseColor("#00BDBD"), Color.parseColor("#00A8A8"), Color.parseColor("#009191") },
			// 靛
			{ Color.parseColor("#F2EC91"), Color.parseColor("#FFE65D"), Color.parseColor("#FFDF31"), Color.parseColor("#FFD500"),
					Color.parseColor("#ffb250"), Color.parseColor("#ff953d"), Color.parseColor("#ff7400"), Color.parseColor("#ff4f00"),
					Color.parseColor("#b381f5"), Color.parseColor("#7E0AF5"), Color.parseColor("#5F0FC8"), Color.parseColor("#4600AC"),
					Color.parseColor("#7A7AFF"), Color.parseColor("#5959FF"), Color.parseColor("#3B3BFF"), Color.parseColor("#0000FF") },
			// 紫
			{ Color.parseColor("#F2EC91"), Color.parseColor("#FFE65D"), Color.parseColor("#FFDF31"), Color.parseColor("#FFD500"),
					Color.parseColor("#7A7AFF"), Color.parseColor("#5959FF"), Color.parseColor("#3B3BFF"), Color.parseColor("#0000FF"),
					Color.parseColor("#FF5B92"), Color.parseColor("#FF3B83"), Color.parseColor("#FF1E74"), Color.parseColor("#FF0059"),
					Color.parseColor("#b885ff"), Color.parseColor("#a05cff"), Color.parseColor("#7f3cde"), Color.parseColor("#6619d1") } };

	/**
	 * default number cards background drawable ids
	 */
	public static final int[] defaultPicIDs = { R.drawable.p2, R.drawable.p4, R.drawable.p8, R.drawable.p16, R.drawable.p32, R.drawable.p64,
			R.drawable.p128, R.drawable.p256, R.drawable.p512, R.drawable.p1024, R.drawable.p2048, R.drawable.p4096, R.drawable.p8192,
			R.drawable.p16384, R.drawable.p32768, R.drawable.p65536 };


	public static final String YOU_MI_APP_ID = "ddc4c3f0766e2cf4";
	public static final String YOU_MI_APP_SECRET = "7aaa574538a000fa";

	public static final int COST_COINS = 25;

	public static final String BMOB_APP_ID = "efb8c076a726721a997088d25e25a3c2";

	public static int UNLOCK_COINS_COUNT = 1500;
	public static int REACH_2048 = 100;
	public static int REACH_4096 = 300;
	public static int REACH_8192 = 500;

	public static final String MEIZU_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBUw3/DoxgPhM6Kkawj8xml8wf1ztnuhhYdoQ9HDLQ4It+Acd7s1g0P0AlwDIoB6aNm0K555gyVBf1J1rDSFIH/etjzd01RZSbuEad/6RueEIiCAesrjcpaglUg4zsPvuEGZUc2SA0u2HeMTlrQtR408Vlv9oB2sUNdYIqutyIOwIDAQAB";
	public static final String MEIZU_SN = "040AHGKJ4CQ6";

}
