package com.toly1994.ds4android.ds.tree;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/24 0024:12:36<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface OnOrderListener<E extends Comparable> {
    void onLevelOrder(E el);
}
