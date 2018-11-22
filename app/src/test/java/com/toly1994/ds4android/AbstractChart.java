package com.toly1994.ds4android;

/**
 * 作者：张风捷特烈
 * 时间：2018/9/19 0019:7:42
 * 邮箱：1981462002@qq.com
 * 说明：集合的基类
 */
public abstract class AbstractChart<T> implements IChart<T> {
    protected int size;

    /**
     * {@inheritDoc}
     *
     * @param index 索引
     * @param el    数据元素
     */
    public abstract void add(int index, T el);

    /**
     * {@inheritDoc}
     *
     * @param el 数据元素
     */
    public void addFirst(T el) {
        add(0, el);
    }

    /**
     * 添加尾
     *
     * @param el 数据元素
     */
    public void addLast(T el) {
        add(size - 1, el);
    }

    /**
     * {@inheritDoc}
     *
     * @param index 索引
     * @return 删除的元素
     */
    public abstract T remove(int index);

    /**
     * 删除首位
     *
     * @return 删除的元素
     */
    public T removeFirst() {
        return remove(0);
    }

    /**
     * 删除尾位
     *
     * @return 删除的元素
     */
    public T removeLast() {
        return remove(size - 1);
    }

    /**
     * 删除指定元素的第一次出现时
     *
     * @param el 数据元素
     * @return 元素位置
     */
    public int removeEl(T el) {
        int[] indexes = getIndex(el);
        int index = -1;
        if (indexes.length > 0) {
            index = indexes[0];
            remove(indexes[0]);
        }
        return index;
    }

    /**
     * 删除所有指定元素
     *
     * @param el 数据元素
     */
    public boolean removeEls(T el) {

        int[] indexArray = getIndex(el);
        if (indexArray.length != 0) {
            for (int i = 0; i < indexArray.length; i++) {
                remove(indexArray[i] - i); // 注意-i
            }
            return true;
        }
        return false;
    }

    /**
     * 清空集合
     */
    public abstract void clear();

    /**
     * 设置某位置的元素新值
     *
     * @param index 索引
     * @param el    新值
     * @return 旧值
     */
    public abstract T set(int index, T el);

    /**
     * 根据指定位置获取元素
     *
     * @param index 索引
     * @return 数据元素
     */
    public abstract T get(int index);

    /**
     * 根据指定元素获取匹配索引
     *
     * @param el 数据元素
     * @return 索引集
     */
    public abstract int[] getIndex(T el);

    /**
     * 集合是否包含某元素
     *
     * @param el 数据元素
     * @return 是否包含
     */
    public boolean contains(T el) {
        return getIndex(el).length != 0;
    }

    /**
     * 连接两个集合
     *
     * @param iChart 插入集合
     * @return 合并后的集合
     */
    public IChart<T> contact(IChart<T> iChart) {
        return contact(size - 1, iChart);
    }

    /**
     * 定点连接两个集合
     *
     * @param index  索引
     * @param iChart 插入集合
     * @return 合并后的集合
     */
    public abstract IChart<T> contact(int index, IChart<T> iChart);

    /**
     * 是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 返回集合大小
     *
     * @return 大小
     */
    public int size() {
        return size;
    }
}
