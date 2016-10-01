package com.mawanli.love2048.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

public class DeviceUtil {
	
	
	
	public static String getSN(Context context) {

//		String []propertys = {"ro.boot.serialno", "ro.serialno"};
//		for (String key : propertys){
////          String v = android.os.SystemProperties.get(key);
//			String v = getAndroidOsSystemProperties(key);
//			Log.e("", "get " + key + " : " + v);
//		}

		return getAndroidOsSystemProperties("ro.boot.serialno");
	}

	static Method systemProperties_get = null;

	static String getAndroidOsSystemProperties(String key) {
		String ret;
		try {
			systemProperties_get = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
			if ((ret = (String) systemProperties_get.invoke(null, key)) != null)
				return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return "";
	}

}
