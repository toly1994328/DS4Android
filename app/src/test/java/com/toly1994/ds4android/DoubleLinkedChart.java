package com.toly1994.ds4android;

/**
 * 作者：张风捷特烈
 * 时间：2018/9/19 0019:8:34
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class DoubleLinkedChart<T> extends AbstractChart<T> {

    /**
     * 虚拟头结点
     */
    private Node headNode;

    /**
     * 虚拟尾节点
     */
    private Node tailNode;

    public DoubleLinkedChart() {
        clear();
    }

    @Override
    public void add(int index, T el) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index");
        }
        addNodeBefore(getNode(index), el);
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Remove failed. Illegal index");
        }
        return removeNode(getNode(index));
    }


    @Override
    public void clear() {
        clearNode();
    }

    @Override
    public T set(int index, T el) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("ISet failed. Illegal index");
        }
        Node<T> node = getNode(index);
        T oldData = node.el;
        node.el = el;
        return oldData;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Get failed. Illegal index");
        }
        return getNode(index).el;
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
    public IChart<T> contact(int index, IChart<T> iChart) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Contact failed. Illegal index");
        }

        DoubleLinkedChart linkedGroup = (DoubleLinkedChart) iChart;
        Node targetNode = getNode(index);
        Node targetNextNode = targetNode.next;

        //目标节点的next指向待接链表的第一个节点
        targetNode.next = linkedGroup.getHeadNode().next;
        //向待接链表的第一个节点的prev指向目标节点
        linkedGroup.getHeadNode().next.prev = targetNode;

        //目标节点的下一节点指的prev向待接链表的最后一个节点
        targetNextNode.prev = linkedGroup.getTailNode().prev;
        //向待接链表的最后一个节点的next指向目标节点的下一节点的
        linkedGroup.getTailNode().prev.next = targetNextNode;

        return this;
    }

    @Override
    public void addLast(T el) {
        add(size, el);
    }

    public Node getHeadNode() {
        return headNode;
    }

    public Node getTailNode() {
        return tailNode;
    }

    /////////////////////////////节点操作//////////////////////////

    /**
     * 根据目标节点插入新节点
     *
     * @param target 目标节点
     * @param data   新节点数据
     */
    private void addNodeBefore(Node<T> target, T data) {
        //新建一个node,将前、后指向分别指向目标前节点和目标节点
        Node<T> newNode = new Node<>(target.prev, target, data);
        //目标前节点next指向新节点
        target.prev.next = newNode;
        //目标节点prev指向新节点
        target.prev = newNode;
        //链表长度+1
        size++;
    }

    /**
     * 移除目标节点
     *
     * @param target 目标节点
     * @return 目标节点数据
     */
    private T removeNode(Node<T> target) {
        //目标前节点的next指向目标节点后节点
        target.prev.next = target.next;
        //目标后节点的prev指向目标节点前节点
        target.next.prev = target.prev;
        //链表长度-1
        size--;
        return target.el;
    }


    /**
     * 清空所有节点
     */
    private void clearNode() {
        //实例化头结点
        headNode = new Node<T>(null, null, null);
        //实例化尾节点，并将prev指向头
        tailNode = new Node<T>(headNode, null, null);
        headNode.next = tailNode;
        //链表长度置零
        size = 0;
    }

    /**
     * 根据索引获取节点
     *
     * @param index 索引
     * @return 索引处节点
     */
    private Node<T> getNode(int index) {
        //声明目标节点
        Node<T> targetNode;
        //索引越界处理
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        //如果索引在前半,前序查找
        if (index < size / 2) {
            targetNode = headNode.next;
            for (int i = 0; i < index; i++) {
                targetNode = targetNode.next;
            }
        } else {  //如果索引在后半,反序查找
            targetNode = tailNode;
            for (int i = size; i > index; i--) {
                targetNode = targetNode.prev;
            }
        }
        return targetNode;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("head: ");
        for (Node cur = headNode.next; cur != null; cur = cur.next) {
            res.append(cur.el + "->");
        }
        return res.toString();
    }

    /**
     * 节点类
     *
     * @param <T>
     */
    private static class Node<T> {
        /**
         * 数据
         */
        private T el;
        /**
         * 前节点
         */
        private Node prev;
        /**
         * 后节点
         */
        private Node next;

        private Node(Node prev, Node next, T el) {
            this.el = el;
            this.prev = prev;
            this.next = next;
        }
    }
}
