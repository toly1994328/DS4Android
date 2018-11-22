package com.toly1994.ds4android.ds.impl;

import com.toly1994.ds4android.ds.itf.IChart;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/22 0022:15:36<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class SingleLinkedChart<T> implements IChart<T> {

    /**
     * 虚拟头结点
     */
    private Node headNode;

    protected int size;

    public SingleLinkedChart() {
        clear();
    }


    @Override
    public void add(int index, T el) {
        // index可以取到size，在链表末尾空位置添加元素。
        if (index + 1 < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index");
        }
        addNode(index + 1, el);
    }

    @Override
    public void add(T el) {
        add(size, el);
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Remove failed. Illegal index");
        }
        return removeNode(index + 1);
    }

    @Override
    public T remove() {
        return remove(size - 1);
    }

    @Override
    public int removeEl(T el) {
        int[] indexes = getIndex(el);
        int index = -1;
        if (indexes.length > 0) {
            index = indexes[0];
            remove(indexes[0]);
        }
        return index;
    }

    @Override
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

    @Override
    public void clear() {
        headNode = new Node(null, null);
        size = 0;
    }

    @Override
    public T set(int index, T el) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("ISet failed. Illegal index");
        }
        return setNode(index + 1, el).el;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Get failed. Illegal index");
        }
        //消除headNode影响，所以+1
        return getNode(index + 1).el;
    }

    @Override
    public int[] getIndex(T el) {
        //临时数组
        int[] tempArray = new int[size];
        //重复个数
        int index = 0;
        int count = 0;
        Node node = headNode.next;
        while (node != null) {
            if (el.equals(node.el)) {
                tempArray[index] = -1;
                count++;
            }
            index++;
            node = node.next;
        }
        //将临时数组压缩
        int[] indexArray = new int[count];
        int indexCount = 0;
        for (int i = 0; i < tempArray.length; i++) {
            if (tempArray[i] == -1) {
                indexArray[indexCount] = i;
                indexCount++;
            }
        }
        return indexArray;
    }

    @Override
    public boolean contains(T el) {
        return getIndex(el).length != 0;
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
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return size;
    }


    /**
     * 内部私有节点类
     */
    private class Node {
        /**
         * 节点数据元素
         */
        public T el;
        /**
         * 下一节点的引用
         */
        public Node next;

        public Node(Node next, T el) {
            this.el = el;
            this.next = next;
        }
    }

    ////////////////////------------节点操作

    /**
     * 在指定链表前添加节点
     *
     * @param index 索引
     * @param el    数据
     */
    private void addNode(int index, T el) {
        Node target = getNode(index - 1);
        //新建节点，同时下一节点指向target的下一节点
        Node tNode = new Node(target.next, el);
        //目标节点的next指向新节点
        target.next = tNode;
        size++;
    }

    /**
     * 移除指定索引的节点
     *
     * @param index 索引
     * @return 删除的元素
     */
    private T removeNode(int index) {
        //目标节点前一节点
        Node targetPrev = getNode(index - 1);
        //目标节点
        Node target = targetPrev.next;
        //目标节点前一节点的next指向目标节点下一节点
        targetPrev.next = target.next;
        target.next = null;
        size--;
        return target.el;
    }

    /**
     * 修改节点
     *
     * @param index 节点位置
     * @param el    数据
     * @return 修改后的节点
     */
    private Node setNode(int index, T el) {
        Node node = getNode(index);
        node.el = el;
        return node;
    }

    /**
     * 根据索引寻找节点
     *
     * @param index 索引
     * @return 节点
     */
    private Node getNode(int index) {
        //声明目标节点
        Node targetNode = headNode;
        for (int i = 0; i < index; i++) {
            //一个挨着一个找，直到index
            targetNode = targetNode.next;
        }
        return targetNode;
    }
}
