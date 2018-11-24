package com.toly1994.ds4android.model;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:8:46<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：模型层数组的单体
 */
public class StackBox<T> extends Viewable {

    public int index;//数组单体索引
    public T data;//数据结构承载的核心数据

    @Override
    public boolean equals(Object obj) {
        return ((StackBox) obj).data == data;
    }

    public StackBox(T data) {
        this.data = data;
    }

    public StackBox(float x, float y) {
        super(x, y);
    }
}
