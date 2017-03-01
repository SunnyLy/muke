package sunny.sunnychartdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import sunny.sunnychartdemo.R;

/**
 * ------------------------------------------------
 * Copyright © 2014年 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @Author sunny
 * @Date 2017/3/1  11:27
 * @Version v1.0.0
 * @Annotation 创建一个图表抽象基类
 *
 *    注意事项：
 *     1，图表控件宽高的确定
 *        可以通过获取屏幕的宽高，再减去边距
 *
 *     2，坐标轴上文字的标记
 *     3，标题的位置
 *         由屏幕宽度的一半再减去标题字符串宽度的一半，就得到标题显示在屏幕正中间
 */
public abstract class BaseGraphView extends View {

    //坐标轴画笔
    private Paint mXYAxisPaint;
    private Paint mGraphTitlePaint;
    private Paint mAxisNamePaint;
    public Paint mPaint;


    private String mGrapthTitle;
    private String mXAxisName;
    private String mYAxisName;
    private int mAxisTextColor;
    private float mAxisTextSize;

    //X坐标轴最大值
    public float maxAxisValueX = 900;
    //X坐标轴刻度线数量
    public int axisDivideSizeX = 9;
    //Y坐标轴最大值
    public float maxAxisValueY = 700;
    //Y坐标轴刻度线数量
    public int axisDivideSizeY = 7;
    //视图宽度
    public int width;
    //视图高度
    public int height;
    //坐标原点位置
    public final int originX = 100;
    public final int originY = 800;
    //柱状图数据
    public int columnInfo[][];


    public BaseGraphView(Context context) {
        this(context,null);
    }

    public BaseGraphView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public BaseGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SunnyGraphStyle);
        mGrapthTitle = typedArray.getString(R.styleable.SunnyGraphStyle_graphTitle);
        mXAxisName = typedArray.getString(R.styleable.SunnyGraphStyle_X_AxisName);
        mYAxisName = typedArray.getString(R.styleable.SunnyGraphStyle_Y_AxisName);
        mAxisTextColor = typedArray.getColor(R.styleable.SunnyGraphStyle_axisTextColor,context.getResources().getColor(android.R.color.black));
        mAxisTextSize = typedArray.getDimension(R.styleable.SunnyGraphStyle_axisTextSize,12);

        if(typedArray != null){
            typedArray.recycle();
        }

        initPaint(context);

    }

    private void initPaint(Context context) {

        if(mPaint == null){
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
        }
    }


    public void setGrapthTitle(String grapthTitle) {
        mGrapthTitle = grapthTitle;
    }

    public void setXAxisName(String XAxisName) {
        mXAxisName = XAxisName;
    }

    public void setYAxisName(String YAxisName) {
        mYAxisName = YAxisName;
    }

    public void setAxisTextColor(int axisTextColor) {
        mAxisTextColor = axisTextColor;
    }

    public void setAxisTextSize(float axisTextSize) {
        mAxisTextSize = axisTextSize;
    }

    /**
     * 手动设置X轴最大值及等份数
     * @param maxAxisValueX
     * @param dividedSize
     */
    public void setXAxisValue(float maxAxisValueX,int dividedSize) {
        this.maxAxisValueX = maxAxisValueX;
        this.axisDivideSizeX = dividedSize;
    }

    /**
     * 手动设置Y轴最大值及等份数
     * @param maxAxisValueY
     * @param dividedSize
     */
    public void setYAxisValue(float maxAxisValueY,int dividedSize) {
        this.maxAxisValueY = maxAxisValueY;
        this.axisDivideSizeY = dividedSize;
    }

    /**
     * 传入柱状图数据
     * @param columnInfo
     */
    public void setColumnInfo(int[][] columnInfo) {
        this.columnInfo = columnInfo;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        width = MeasureSpec.getSize(widthMeasureSpec)  ;
//        height = MeasureSpec.getSize(heightMeasureSpec);

        Log.e(",,,,,,,,,","width:"+width+",height:"+height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth() - originX-100;
        height = getHeight() - 400;
        drawAxisX(canvas, mPaint);
        drawAxisY(canvas, mPaint);
        drawAxisScaleMarkX(canvas, mPaint);
        drawAxisScaleMarkY(canvas, mPaint);
        drawAxisArrowsX(canvas, mPaint);
        drawAxisArrowsY(canvas, mPaint);
        drawAxisScaleMarkValueX(canvas, mPaint);
        drawAxisScaleMarkValueY(canvas, mPaint);
        drawColumn(canvas, mPaint);
        drawTitle(canvas, mPaint);
    }

    protected abstract void drawAxisScaleMarkValueY(Canvas canvas, Paint paint);

    protected abstract void drawAxisScaleMarkValueX(Canvas canvas, Paint paint);

    //画标题
    private void drawTitle(Canvas canvas, Paint paint) {
        //画标题
        if (mGrapthTitle != null) {
            //设置画笔绘制文字的属性
            mPaint.setColor(mAxisTextColor);
            mPaint.setTextSize(mAxisTextSize);
            mPaint.setFakeBoldText(true);
            canvas.drawText(mGrapthTitle, (getWidth()/2)-(paint.measureText(mGrapthTitle)/2), originY + 100, paint);
        }
    }

    //开始 画中间的矩形
    protected abstract void drawColumn(Canvas canvas, Paint paint);


    private void drawAxisArrowsY(Canvas canvas, Paint paint) {
        //画三角形（Y轴箭头）
        Path mPathX = new Path();
        mPathX.moveTo(originX, originY - height - 30);//起始点
        mPathX.lineTo(originX - 10, originY - height);//下一点
        mPathX.lineTo(originX + 10, originY - height);//下一点
        mPathX.close();
        canvas.drawPath(mPathX, paint);
        canvas.drawText(mYAxisName,originX-50,originY-height-30,paint);
    }

    /**
     * X轴上的箭头
     * @param canvas
     * @param paint
     */
    private void drawAxisArrowsX(Canvas canvas, Paint paint) {
        //画三角形（X轴箭头）
        Path mPathX = new Path();
        mPathX.moveTo(originX + width + 30, originY);//起始点
        mPathX.lineTo(originX + width, originY - 10);//下一点
        mPathX.lineTo(originX + width, originY + 10);//下一点
        mPathX.close();
        canvas.drawPath(mPathX, paint);
        canvas.drawText(mXAxisName,originX+width,originY+30,paint);
    }

    /**
     * Y轴上的标记
     * @param canvas
     * @param paint
     */
    protected abstract void drawAxisScaleMarkY(Canvas canvas, Paint paint);

    /**
     * X轴上的标记
     * @param canvas
     * @param paint
     */
    protected abstract void drawAxisScaleMarkX(Canvas canvas, Paint paint);

    protected abstract void drawAxisY(Canvas canvas, Paint paint);

    protected abstract void drawAxisX(Canvas canvas, Paint paint);
}
