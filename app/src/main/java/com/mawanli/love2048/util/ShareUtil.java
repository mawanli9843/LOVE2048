package com.mawanli.love2048.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

public class ShareUtil {

	public static void shareMsg(Context context, String activityTitle, String msgTitle, String msgText, String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/png");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, activityTitle));
	}

	public static void storeBitmapToFile(Bitmap bitmap) {

		File file = new File(Constant.SHARE_PIC_PATH);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
			FileOutputStream out;
			try {
				
				Log.e("----------", "vstoreBitmapToFile");
				out = new FileOutputStream(file);
				if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
					out.flush();
					out.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void initShareSDK(Context context) {
//		ShareSDK.initSDK(context);
	}

	public static void stopShareSDK(Context context) {
//		ShareSDK.stopSDK(context);
	}

	public static void showShare(Context context, String title,  String text, String imagePath) {
		shareMsg(context,title,title,text,imagePath);
	}

}
