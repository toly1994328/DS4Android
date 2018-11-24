package com.toly1994.ds4android.ds.itf;

/**
 * 作者：张风捷特烈
 * 时间：2018/8/17 0017:12:49
 * 邮箱：1981462002@qq.com
 * 说明：栈的接口
 */
public interface IStack<T>  {
    /**
     * 栈元素个数
     * @return  栈元素个数
     */
    int size();

    /**
     * 栈元素容积
     * @return 容积
     */
    int capacity();

    /**
     * 是否为空
     * @return  是否为空
     */
    boolean isEmpty();

    /**
     * 入栈
     * @param el 元素
     */
    void push(T el);

    /**
     * 出栈
     * @return 元素
     */
    T pop();

    /**
     * 取出元素
     * @return 元素
     */
    T peek();
}
