package com.mawanli.love2048.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class MediaPalyer {
	private static MediaPlayer mediaPlayer;

	public static void play(Context context, int rawId) {
		mediaPlayer = MediaPlayer.create(context, rawId);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
		mediaPlayer.setVolume(0.5f, 0.5f);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				mp = null;
			}
		});
	}

	public static void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

}
