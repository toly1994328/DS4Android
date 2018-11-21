package com.toly1994.ds4android.analyze;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/5 0005:8:39<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class Utils {
    /**
     * 获得屏幕高度
     *
     * @param ctx 上下文
     * @param winSize 屏幕尺寸
     */
    public static void loadWinSize(Context ctx, Point winSize) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        winSize.x = outMetrics.widthPixels;
        winSize.y = outMetrics.heightPixels;
    }

}
