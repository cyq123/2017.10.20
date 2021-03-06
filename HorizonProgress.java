package com.cyq.allprogress.progress;

import com.cyq.allprogresss.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ProgressBar;
/**
 * 水平进度条  
 * 样式：======30%=========
 * @author cyq
 *
 */
public class HorizonProgress extends ProgressBar {
	private static final int DEFAULT_PROGRESS_COLOR = Color.BLUE;
	private static final int DEFAULT_VALUE_COLOR = Color.RED;
	private static final int DEFAULT_VALUE_SIZE = 16;//sp
	private static final int DEFAULT_TEXT_PADING = 10;
	private static final int DEFAULT_PROGRESS_COLOR_SECOND =Color.GRAY;
	private static final int DEFAULT_START_X = 100;
	private static final int DEFAULT_START_Y = 100;
	private static final int DEFAULT_STOP_Y = DEFAULT_START_Y;

	private int progressColor = DEFAULT_PROGRESS_COLOR;
	private int progressColorSecond = DEFAULT_PROGRESS_COLOR_SECOND;
	private int valueColor = DEFAULT_VALUE_COLOR;
	private float valueSize = sp2px(DEFAULT_VALUE_SIZE);
	private float textPading = DEFAULT_TEXT_PADING;
	private float start_x = DEFAULT_START_X;
	private float start_y = DEFAULT_START_Y;
	private float stop_y = start_y;
	private int stop_x;
	private Paint paint;
	private String text;

	public HorizonProgress(Context context) {
		this(context, null);
	}

	public HorizonProgress(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HorizonProgress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.progress_style);

		progressColor = ta.getColor(R.styleable.progress_style_progress_color,
				DEFAULT_PROGRESS_COLOR);
		valueColor = ta.getColor(
				R.styleable.progress_style_progress_value_color,
				DEFAULT_VALUE_COLOR);
		valueSize = ta.getDimension(
				R.styleable.progress_style_progress_value_size,
				sp2px(DEFAULT_VALUE_SIZE));
		textPading = ta.getDimension(R.styleable.progress_style_text_pading,
				DEFAULT_TEXT_PADING);
		start_x = getPaddingLeft();
		
		progressColorSecond = ta.getColor(R.styleable.progress_style_progress_color_second, DEFAULT_PROGRESS_COLOR_SECOND);
		paint = new Paint();
		paint.setTextSize(valueSize);
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wdWith=wm.getDefaultDisplay().getWidth();
		
		ta.recycle();
	}
	private int wdWith;
	
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int with = MeasureSpec.getSize(widthMeasureSpec);
		int withMode = MeasureSpec.getMode(widthMeasureSpec);
		if (withMode == MeasureSpec.EXACTLY) {
			
		}else{
			with = wdWith-getPaddingLeft()-getPaddingRight();
		}
		
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode == MeasureSpec.EXACTLY) {
			
		}else {
			float tHeight = Math.abs(paint.ascent())+Math.abs(paint.descent());
			height = (int) tHeight;
		}
		
		setMeasuredDimension(with, height);
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		text=getProgress() + "%";
		
		paint.setColor(progressColor);//已完成进度条
		paint.setStyle(Paint.Style.FILL); // 设置空心
		paint.setStrokeWidth(15); // 
		paint.setAntiAlias(true);
		float textWith = paint.measureText(text);
		stop_x=(int) ((getWidth()-getPaddingLeft()-getPaddingRight()-textWith-textPading*2) / getMax() * getProgress()+start_x);
		start_y = getHeight()/2;
		stop_y = start_y;
		canvas.drawLine(start_x, start_y, stop_x, stop_y, paint);

		paint.setColor(valueColor);//进度值文字
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(1); // 设置圆环的宽度
		canvas.drawText(text, stop_x
				+ textPading, start_y-((paint.ascent()+paint.descent())/2), paint);
		
		if (getProgress()<100) {
			paint.setColor(progressColorSecond);
			paint.setStyle(Paint.Style.FILL); // 设置空心
			paint.setStrokeWidth(15); // 
			paint.setAntiAlias(true);
			float unproLength = getWidth() - ((stop_x+textWith+textPading*2))-getPaddingRight();//未完成进度条长度
			canvas.drawLine(stop_x+textWith+textPading*2, start_y, stop_x+textWith+textPading*2+unproLength, stop_y, paint);
		}
	}

	public int dp2px(float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
		dpVal, getResources().getDisplayMetrics());
	}

	public int sp2px(float spVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
		spVal, getResources().getDisplayMetrics());
	}

}
