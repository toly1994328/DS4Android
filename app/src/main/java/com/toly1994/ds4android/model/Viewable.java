package com.toly1994.ds4android.model;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:8:50<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：可显示出来的基本条件
 */
public class Viewable {
    public float x;//单体的x坐标
    public float y;//单体的y坐标
    public int color = 0xff43A3FA;//单体的颜色
    public float vX;//单体的水平速度
    public float vY;//单体的数值速度

    public Viewable(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
