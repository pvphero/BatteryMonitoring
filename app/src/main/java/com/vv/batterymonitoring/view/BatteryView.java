package com.vv.batterymonitoring.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BatteryView extends View {

    private int width = 0;//控件宽度
    private int height = 0;//控件高度
    private int positive_width = 0;//电池正极的宽度
    private int stroke_width = 3;
    private static final int DEFAULT_WIDTH = 116;
    private static final int DEFAULT_HEIGHT = 69;

    private int battery = 0;

    private Paint paint_border;//电池边框画笔
    private Paint paint_battery;//电池电量画笔

    public BatteryView(Context context) {
        this(context,null);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heigthSize = MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode== MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            width = DEFAULT_WIDTH;
        }
        if(heightMode== MeasureSpec.EXACTLY){
            height = heigthSize;
        }else {
            height = DEFAULT_HEIGHT;
        }
        positive_width = width/8;
        setMeasuredDimension(width,height);
    }

    private void init(){
        paint_border = new Paint();
        paint_border.setAntiAlias(true);
        paint_border.setStyle(Paint.Style.STROKE);
        paint_border.setStrokeWidth(stroke_width);
        paint_border.setColor(Color.parseColor("#D1D1D1"));
        paint_battery = new Paint();
        paint_battery.setColor(Color.parseColor("#4da2db"));
        paint_battery.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float[] pts = {
                0 + stroke_width,0 + stroke_width,width-positive_width-stroke_width,0+stroke_width,
                width-positive_width-stroke_width,0+stroke_width,width-positive_width-stroke_width,height/3,
                0+stroke_width,0+stroke_width,0+stroke_width,height-stroke_width,
                0+stroke_width, height-stroke_width,width-positive_width-stroke_width,height-stroke_width,
                width-positive_width-stroke_width,height-stroke_width,width-positive_width-stroke_width,height *2/3,
                width-positive_width-stroke_width, height/3,width-stroke_width-stroke_width,height/3,
                width-stroke_width-stroke_width, height/3,width-stroke_width-stroke_width,height *2/3,
                width-stroke_width-stroke_width,height *2/3,width-positive_width-stroke_width,height *2/3};
        canvas.drawLines(pts,paint_border);

        Rect rect = new Rect(stroke_width*2,stroke_width*2,
                stroke_width*2 + (width-stroke_width*2-positive_width-stroke_width*2)*battery/100,height-stroke_width*2);
        canvas.drawRect(rect,paint_battery);
        super.onDraw(canvas);
    }

    public void setBattery(int battery){
        this.battery = battery;
        invalidate();
    }
}

