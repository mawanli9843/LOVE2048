package com.mawanli.love2048.interpolator;

import android.view.animation.Interpolator;

public class MyInterpolator implements Interpolator {

	@Override
	public float getInterpolation(float input) {
		 double d = -1.6f * input * input + 2.6 * input;
		 return (float) d;

//		if (input < 0.4) {
//			return (float) (input * input);
//		} else if (input >= 0.4 && input <= 0.6) {
//			return (float) (3.4 * input - 1.2);
//		} else
//			return (float) (1.0f - (1.0f - input) * (1.0f - input));
	}

}
