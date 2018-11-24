package com.toly1994.ds4android.model;

import android.support.annotation.NonNull;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:8:46<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：模型层双链表的单体
 */
public class TreeNode<T extends Comparable<T>> extends Viewable implements Comparable<TreeNode<T>> {

    public T data;//链表结构承载的核心数据

    @Override
    public boolean equals(Object obj) {
        return ((TreeNode) obj).data.equals(data);
    }

    public TreeNode(T data) {
        this.data = data;
    }

    public TreeNode(float x, float y) {
        super(x, y);
    }


    @Override
    public int compareTo(@NonNull TreeNode<T> o) {
        return data.compareTo(o.data);
    }

}
