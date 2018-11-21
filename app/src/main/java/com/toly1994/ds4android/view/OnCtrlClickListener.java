package com.toly1994.ds4android.view;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:10:17<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public interface OnCtrlClickListener<T> {

    void onAdd(T view);

    void onRemove(T view);

    void onSet(T view);

    void onFind(T view);
}
