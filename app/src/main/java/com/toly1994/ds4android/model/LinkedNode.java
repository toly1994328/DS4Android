package com.toly1994.ds4android.model;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:8:46<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：模型层双链表的单体
 */
public class LinkedNode<T> extends Viewable {

    public int index;//链表单体序号
    public T data;//链表结构承载的核心数据

    @Override
    public boolean equals(Object obj) {
        return ((LinkedNode) obj).data.equals(data);
    }

    public LinkedNode(T data) {
        this.data = data;
    }

    public LinkedNode(float x, float y) {
        super(x, y);
    }
}
