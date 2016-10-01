package com.mawanli.love2048.util;

import com.mawanli.love2048.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class AudioUtil {

	private static SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);

	private static AudioUtil audioUtil;

	public static AudioUtil getInstance(Context context) {
		if (audioUtil == null) {
			audioUtil = new AudioUtil();
			soundPool.load(context, R.raw.add, 1);
			soundPool.load(context, R.raw.move, 1);
			soundPool.load(context, R.raw.add, 1);
		}
		return audioUtil;
	}

	public void combine() {
		soundPool.play(1, 0.4f, 0.1f, 0, 0, 1);

	}

	public void move() {
		soundPool.play(2, 0.3f, 0.1f, 0, 0, 1);
	}

	public void add() {
		soundPool.play(3, 0.2f, 0.1f, 0, 0, 1);
	}

	public void start() {
	}

	public void win() {
	}

	public void stop(int i) {
		soundPool.pause(i);
	}

}
