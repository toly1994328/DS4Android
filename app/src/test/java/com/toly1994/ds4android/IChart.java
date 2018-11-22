package com.toly1994.ds4android;

/**
 * 作者：张风捷特烈
 * 时间：2018/9/25 0025:8:25
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public interface IChart<T> {
//region -------------添加操作------------

    /**
     * 定点添加
     *
     * @param index 索引
     * @param el    数据元素
     */
    void add(int index, T el);

    /**
     * 首添加
     *
     * @param el 数据元素
     */
    void addFirst(T el);

    /**
     * 添加尾
     *
     * @param el 数据元素
     */
    void addLast(T el);

//endregion

//region -------------删除操作------------

    /**
     * 定位删除
     *
     * @param index 索引
     * @return 删除的元素
     */
    T remove(int index);

    /**
     * 删除首位
     *
     * @return 删除的元素
     */
    T removeFirst();

    /**
     * 删除尾位
     *
     * @return 删除的元素
     */
    T removeLast();

    /**
     * 删除指定元素的第一次出现时
     *
     * @param el 数据元素
     * @return 元素位置
     */
    int removeEl(T el);

    /**
     * 删除所有指定元素
     *
     * @param el 数据元素
     */
    boolean removeEls(T el);

    /**
     * 清空集合
     */
    void clear();

//endregion

//region -------------改查操作------------

    /**
     * 设置某位置的元素新值
     *
     * @param index 索引
     * @param el    新值
     * @return 旧值
     */
    T set(int index, T el);

    /**
     * 根据指定位置获取元素
     *
     * @param index 索引
     * @return 数据元素
     */
    T get(int index);

    /**
     * 根据指定元素获取匹配索引
     *
     * @param el 数据元素
     * @return 索引集
     */
    int[] getIndex(T el);

//endregion

//region ------------其他操作-------------

    /**
     * 集合是否包含某元素
     *
     * @param el 数据元素
     * @return 是否包含
     */
    public boolean contains(T el);

    /**
     * 连接两个集合
     *
     * @param iChart 插入集合
     * @return 合并后的集合
     */
    public IChart<T> contact(IChart<T> iChart);

    /**
     * 定点连接两个集合
     *
     * @param index  索引
     * @param iChart 插入集合
     * @return 合并后的集合
     */
    IChart<T> contact(int index, IChart<T> iChart);

    /**
     * 是否为空
     *
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 返回集合大小
     *
     * @return 大小
     */
    int size();

//endregion

}
