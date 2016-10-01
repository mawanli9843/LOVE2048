package com.mawanli.love2048.broadcastreceiver;


import com.mawanli.love2048.util.Mapplication;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class MyBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
			for (int i = 0; i < networkInfos.length; i++) {
				State state = networkInfos[i].getState();
				if (State.CONNECTED == state) {
					System.out.println("------------> Network is ok");
					((Mapplication) context.getApplicationContext()).checkLock();
					((Mapplication) context.getApplicationContext()).checkRank();
					return;
				}
			}
		}

	}

}
