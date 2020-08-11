package com.demo.taptap.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TextColorChangeView extends TextView {
    public TextColorChangeView(Context context) {
        this(context, null);
    }

    public TextColorChangeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextColorChangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        初始化默认画笔设置
        normalPaint.setAntiAlias( true);
        normalPaint.setColor(getTextColors().getDefaultColor());
        normalPaint.setTextSize(getTextSize());
//      初始化变化的画笔设置
        changePaint.setAntiAlias( true);
        changePaint.setColor(Color.RED);
        Log.d(TAG, "TextColorChangeView: " + getTextSize());
        changePaint.setTextSize(getTextSize());
    }

    private static final String TAG = "TextColorChangeView";
//    默认画笔
    private Paint normalPaint = new Paint();
//    可变化的画笔
    private Paint changePaint = new Paint();
//    进度
    private float progress = 0;
//    之前
    private boolean front  = true;
//    之后
    private boolean after = false;


    /**
     * 画出默认的文字和进度的覆盖颜色
     * -判断方向
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();
        if (!TextUtils.isEmpty(text)){
            int position = (int) (getWidth() * progress);
            if (after){
                drawText(canvas, text, 0 , position);
            }else if (front){
                drawText(canvas, text, getWidth() - position , getWidth());
            }

        }
    }

    /**
     * 真正的画出默认文字和高亮部分文字
     *      设置裁剪区域
     *      画出默认文字
     *      根据left 和right值裁剪高亮部分的区域位置
     *      画出高亮部分
     * @param canvas
     * @param text
     * @param left
     * @param right
     */
    private void drawText(Canvas canvas, String text, int left, int right){
        canvas.save();
        canvas.clipRect(0, 0, getWidth(), getHeight());
        int textWidth = (int) normalPaint.measureText(text);
        int textHeight = (int) (normalPaint.descent() + normalPaint.descent());
        canvas.drawText(text, getWidth() / 2 - textWidth / 2 , getHeight() / 2 + textHeight / 2,normalPaint);
        canvas.restore();


        canvas.save();
        canvas.clipRect(left, 0 , right, getHeight());
        canvas.drawText(text, getWidth() / 2 - textWidth / 2 , getHeight() / 2 + textHeight / 2, changePaint);
        canvas.restore();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setAfter(boolean after) {
        this.after = after;
        this.front = !after;
    }
}
