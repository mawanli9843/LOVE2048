package com.mawanli.love2048.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.mawanli.love2048.R;

public class RoundCornerImageView extends ImageView {

	private int roundWidth = 10;
	private int roundHeight = 10;

	private final RectF roundRect = new RectF();
	private final Paint maskPaint = new Paint();
	private final Paint zonePaint = new Paint();

	int color;

	public RoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public RoundCornerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public RoundCornerImageView(Context context) {
		super(context);
		init(context, null);
	}

	public void setRoundWidth(int roundWidth) {
		this.roundWidth = roundWidth;
	}

	public void setRoundHeight(int roundHeight) {
		this.roundHeight = roundHeight;
	}

	private void init(Context context, AttributeSet attrs) {

		maskPaint.setAntiAlias(true);
		maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		zonePaint.setAntiAlias(true);
		zonePaint.setColor(Color.WHITE);

		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);
			roundWidth = a.getDimensionPixelSize(R.styleable.RoundCornerImageView_roundWidth, roundWidth);
			roundHeight = a.getDimensionPixelSize(R.styleable.RoundCornerImageView_roundHeight, roundHeight);
			a.recycle();
		} else {
			float density = context.getResources().getDisplayMetrics().density;
			roundWidth = (int) (roundWidth * density);
			roundHeight = (int) (roundHeight * density);
		}

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		int w = getWidth();
		int h = getHeight();

		roundRect.set(0, 0, w, h);

		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	public void draw(Canvas canvas) {

		canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
		canvas.drawRoundRect(roundRect, roundWidth, roundHeight, zonePaint);
		canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
		super.draw(canvas);
		canvas.restore();

	}

}
