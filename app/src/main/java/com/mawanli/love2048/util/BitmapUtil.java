package com.mawanli.love2048.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.mawanli.love2048.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {
	public static Bitmap getRoundCornerBitmapByNumber(String number, Context context) {

		String filepath = Constant.USER_PIC_DIR + "/" + Constant.PIC + number + ".jpg";
		Bitmap bitmap = BitmapFactory.decodeFile(filepath);
		return RoundCornerImageUtil.toRoundCorner(bitmap, context.getResources().getDimensionPixelSize(
                R.dimen.card_radius));
	}
	
	
	public static Bitmap getBitmapByNumber(String number) {

		String filepath = Constant.USER_PIC_DIR + "/" + Constant.PIC + number + ".jpg";
		return BitmapFactory.decodeFile(filepath);
	}

	public static void saveBitmapToFile(String number, Bitmap bitmap) {
		String filepath = Constant.USER_PIC_DIR + "/" + Constant.PIC + number + ".jpg";

		File f = new File(filepath);

		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		} else {
			if (f.exists()) {
				f.delete();
			}
		}

		try {
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
