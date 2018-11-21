package com.toly1994.ds4android.analyze.gold12;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/16 0016:14:27<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：持戒之神----捷特麾下十二战神之一,负责一切精准之事，绝无一丝马虎
 */
public class Ruler {

    public static void handleRectByPos(float x, float y, float w, float h, RectF rectF) {
        rectF.set(x - w / 2, y - h, x + w / 2, y);
    }

    public static void handleRectByPos(int x, int y, int w, int h, Rect rect) {
        rect.set(x - w / 2, y - h, x + w / 2, y);
    }

    public static void handleTextByPos(Object str, Paint paint, Canvas canvas, int x, int y, Rect rect) {
        String res = str.toString();
        paint.getTextBounds(res, 0, res.length(), rect);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.BLACK);
        Paint.FontMetrics fm = paint.getFontMetrics();
        rect.offset(-rect.width() / 2 + x, y);


        canvas.drawRect(rect, paint);

        paint.setColor(Color.WHITE);
        canvas.drawText(res, x, y, paint);

    }

}
