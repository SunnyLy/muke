package sunny.sunnychartdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * ------------------------------------------------
 * Copyright © 2014年 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @Author sunny
 * @Date 2017/3/1  11:25
 * @Version v1.0.0
 * @Annotation
 */
public class ColumnView extends BaseGraphView {
    public ColumnView(Context context) {
        super(context);
    }

    public ColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void drawAxisScaleMarkValueY(Canvas canvas, Paint paint) {
        float cellHeight = height / axisDivideSizeY;
        float cellValue = maxAxisValueY / axisDivideSizeY;
        for (int i = 1; i < axisDivideSizeY; i++) {
//            canvas.drawText(String.valueOf(cellValue * i), originX - 80, originY - cellHeight * i + 10, paint);
            canvas.drawText(String.valueOf(i), originX - 30, originY - cellHeight * i + 10, paint);
        }
    }

    @Override
    protected void drawAxisScaleMarkValueX(Canvas canvas, Paint paint) {
        //设置画笔绘制文字的属性
        paint.setColor(Color.GRAY);
        paint.setTextSize(12);
        paint.setFakeBoldText(true);

        float cellWidth = width / axisDivideSizeX;
        float cellValue = maxAxisValueX / axisDivideSizeX;
        for (int i = 1; i < axisDivideSizeX; i++) {
//            canvas.drawText(String.valueOf(cellValue * i), cellWidth * i + originX - 35, originY + 30, paint);
            canvas.drawText(String.valueOf(i), cellWidth * i + originX - 35, originY + 30, paint);
        }
    }

    @Override
    protected void drawColumn(Canvas canvas, Paint paint) {
        if(columnInfo == null)
            return;
        float cellWidth = width / axisDivideSizeX;
        for (int i = 0; i < columnInfo.length; i++) {
            paint.setColor(columnInfo[i][1]);
            float leftTopY = originY - height * columnInfo[i][0] / maxAxisValueY;
            //左上角x,y右下角x,y，画笔
            canvas.drawRect(originX + cellWidth * (i + 1), leftTopY, originX + cellWidth * (i + 2), originY, mPaint);
        }
    }

    @Override
    protected void drawAxisScaleMarkY(Canvas canvas, Paint paint) {
        float cellHeight = height / axisDivideSizeY;
        for (int i = 0; i < axisDivideSizeY - 1; i++) {
            canvas.drawLine(originX, (originY - cellHeight * (i + 1)), originX + 10, (originY - cellHeight * (i + 1)), paint);
        }
    }

    @Override
    protected void drawAxisScaleMarkX(Canvas canvas, Paint paint) {
        float cellWidth = width / axisDivideSizeX;
        for (int i = 0; i < axisDivideSizeX - 1; i++) {
            canvas.drawLine(cellWidth * (i + 1) + originX, originY,
                    cellWidth * (i + 1) + originX, originY - 10, paint);
        }
    }

    @Override
    protected void drawAxisY(Canvas canvas, Paint paint) {
        //画竖轴(Y)
        canvas.drawLine(originX, originY, originX, originY - height, paint);//参数说明：起始点左边x,y，终点坐标x,y，画笔

    }

    @Override
    protected void drawAxisX(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        //设置画笔宽度
        paint.setStrokeWidth(5);
        //设置画笔抗锯齿
        paint.setAntiAlias(true);
        //画横轴(X)
        canvas.drawLine(originX, originY, originX + width, originY, paint);

    }

}
