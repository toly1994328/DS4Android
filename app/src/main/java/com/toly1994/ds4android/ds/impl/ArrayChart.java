package com.toly1994.ds4android.ds.impl;

import com.toly1994.ds4android.ds.itf.IChart;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:8:18<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：数组实现线性表结构
 */
public class ArrayChart<T> implements IChart<T> {
    @Override
    public void add(int index, T el) {

    }

    @Override
    public void addFirst(T el) {

    }

    @Override
    public void addLast(T el) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public int removeEl(T el) {
        return 0;
    }

    @Override
    public boolean removeEls(T el) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T set(int index, T el) {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public int[] getIndex(T el) {
        return new int[0];
    }

    @Override
    public boolean contains(T el) {
        return false;
    }

    @Override
    public IChart<T> contact(IChart<T> iChart) {
        return null;
    }

    @Override
    public IChart<T> contact(int index, IChart<T> iChart) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }
}
