package com.view.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @name wave
 * @anthor FuNaiFu QQ:695680567
 * @time 2018/3/20 下午3:55
 * @class 水波动画
 */

/**
 * <attr name="wave_stroke_color" format="color"/> 波的边框颜色
 * <attr name="wave_stroke_width" format="dimension"/> 波的边框宽度
 * <attr name="wave_crest" format="dimension"/> 波峰高度
 * <attr name="wave_length" format="dimension"/> 波长
 * <attr name="wave_duration" format="integer"/> 波的动画时间
 */
public class WaveView extends View {

    private static int DEF_STYLE_ATTR = 0;
    private static int ANIMATOR_START = 0;
    private static int ANIMATOR_END = 10;
    private int mWaveStrokeColor = 0x000000;//波的边框颜色
    private int mWaveStrokeWidth = 5;//波边框宽度
    private int mWaveCrest = 20;//波峰高度,默认值为20
    private int mWaveLength = 100;//波长，默认值为100
    private int duration = 1000;//波的动画时间，默认值为1000毫秒
    private int dx = 0;//动画平移的动态值
    private Path path;
    private Paint paint;
    private int mWidth;
    private int mHeight;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, DEF_STYLE_ATTR);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        mWaveStrokeColor = typedArray.getColor(R.styleable.WaveView_wave_stroke_color, mWaveStrokeColor);
        mWaveStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.WaveView_wave_stroke_width, mWaveStrokeWidth);
        mWaveCrest = typedArray.getDimensionPixelOffset(R.styleable.WaveView_wave_crest, mWaveCrest);
        mWaveLength = typedArray.getDimensionPixelOffset(R.styleable.WaveView_wave_length, mWaveLength);
        duration = typedArray.getInteger(R.styleable.WaveView_wave_duration, duration);
        typedArray.recycle();
        init();
    }


    /**
     * 初始化数据
     */
    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mWaveStrokeColor);
        paint.setStyle(Paint.Style.STROKE);
        if (mWaveStrokeWidth <= 0) {
            mWaveStrokeWidth = 1;
        }
        paint.setStrokeWidth(mWaveStrokeWidth);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(mWaveStrokeColor);
        setDrawData();
        canvas.drawPath(path, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //从MeasureSpec中获取Mode和Size
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //依据模式获取预设的width和height
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            int desired = (int) (getPaddingLeft() + getPaddingRight());
            mWidth = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            int desired = (int) (getPaddingTop() + getPaddingBottom());
            mHeight = desired;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    /**
     * 绘制波浪
     */
    private void setDrawData() {
        if (mWaveCrest > mHeight) {
            mWaveCrest = mHeight;
        }
        path.reset();
        int halfWave = mWaveLength / 2;
        path.moveTo(-mWaveLength + dx, mHeight / 2);
        for (int i = -mWaveLength; i < mWidth + mWaveLength; i = i + mWaveLength) {
            path.rQuadTo(halfWave / 2, mWaveCrest / 2, halfWave, 0);
            path.rQuadTo(halfWave / 2, -mWaveCrest / 2, halfWave, 0);
        }
    }

    /**
     * 波浪移动属性动画
     */
    public void start() {
        ValueAnimator animator = ValueAnimator.ofInt(ANIMATOR_START, ANIMATOR_END);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                dx = mWaveLength * value / 10;
                invalidate();
            }
        });
        if (duration <= 0) {
            duration = 1000;
        }
        animator.setDuration(duration);
        animator.start();
    }
}
