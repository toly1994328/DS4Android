package com.toly1994.ds4android.analyze.gold12;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/16 0016:10:25<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：审断之神----捷特麾下十二战神之一,负责审判任何罪恶
 */
public class JudgeMan {

    /**
     * 审断之神能力一：第一形态：区域限定----判断出是否在某点的半径为r圆范围内
     *
     * @param srcX 目标点X
     * @param srcY 目标点Y
     * @param dstX 主动点X
     * @param dstY 主动点Y
     * @param r    半径区域
     * @return 是否在区域内
     */
    public static boolean judgeCircleArea(float srcX, float srcY, float dstX, float dstY, float r) {
        return disPos2d(srcX, srcY, dstX, dstY) <= r;
    }


    /**
     * 审断之神能力一：第二形态：区域限定----判断出是否在某点的半径为r圆范围内
     *
     * @param src 目标点
     * @param dst 主动点
     * @param r   半径
     */
    public static boolean judgeCircleArea(Point src, Point dst, float r) {
        return judgeCircleArea(src.x, src.y, dst.x, dst.y,r);
    }


    /**
     * 审断之神能力一：第三形态：区域限定----判断出是否在某点的半径为r圆范围内
     * @param src 目标点
     * @param dst 主动点
     * @param r 半径区域
     * @return 是否在区域内
     */
    public static boolean judgeCircleArea(PointF src, PointF dst, float r) {
        return disPos2d(src.x, src.y, dst.x, dst.y) <= r;
    }


    /**
     * 审断之神被动能力一：两点间距离函数
     * @param srcX 目标点X
     * @param srcY 目标点Y
     * @param dstX 主动点X
     * @param dstY 主动点Y
     * @return 两点间距离函数
     */
    private static float disPos2d(float srcX, float srcY, float dstX, float dstY) {
        return (float) Math.sqrt((srcX - dstX) * (srcX - dstX) + (srcY - dstY) * (srcY - dstY));
    }

}
