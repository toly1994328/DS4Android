package com.toly1994.ds4android.ds.tree;


import java.util.ArrayList;

/**
 * 作者：张风捷特烈
 * 时间：2018/9/23 0023:8:41
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class AVLTree<E extends Comparable<E>> {
    private Node root;
    private int size;


    public void add(E el) {
        root = addNode(root, el);
    }

    public void remove(E el) {
        Node node = getNode(root, el);
        if (node != null) {
            root = removeNode(root, el);
        }
    }

    public boolean contains(E el) {
        return getNode(root, el) != null;
    }

    /**
     * 是否是平衡二叉树
     *
     * @return 是否是平衡二叉树
     */
    public boolean isBalanced() {
        return isBalanced(root);
    }

    /**
     * 删除掉以target为根的二分搜索树中值为e的节点, 递归算法 返回删除节点后新的二分搜索树的根
     *
     * @param target 根
     * @param el    键
     * @return 删除节点后新的二分搜索树的根
     */
    private Node removeNode(Node target, E el) {
        if (target == null) {
            return null;
        }

        Node resNode = null;

        if (el.compareTo(target.mKey) < 0) {
            target.left = removeNode(target.left, el);
            resNode = target;
        } else if (el.compareTo(target.mKey) > 0) {
            target.right = removeNode(target.right, el);
            resNode = target;
        } else {//相等时
            switch (target.getType()) {
                case LEFT_NULL://左残--
                case LEAF:
                    Node rightNode = target.right;
                    target.right = null;
                    size--;
                    resNode = rightNode;
                    break;
                case RIGHT_NULL:
                    Node leftNode = target.left;
                    target.left = null;
                    size--;
                    resNode = leftNode;
                    break;
                case FULL:
                    //找后继节点
                    Node successor = getMinNode(target.right);
                    successor.right = removeNode(target.right, successor.mKey);
                    successor.left = target.left;
                    target.left = target.right = null;
                    resNode = successor;
                    break;
            }
        }

        if (resNode == null) {
            return null;
        }

        //更新高度值
        updateHeight(resNode);
        //获取平衡因子
        int balanceFactor = getBalanceFactor(resNode);

        //左子树的平衡因子大于等于0,说明左侧有问题--右旋转
        //LL
        if (balanceFactor > 1 && getBalanceFactor(resNode.left) >= 0) {
            return rightRotate(resNode);
        }
        //RR
        if (balanceFactor < -1 && getBalanceFactor(resNode.right) <= 0)
            return leftRotate(resNode);
        //LR
        if (balanceFactor > 1 && getBalanceFactor(resNode.left) < 0) {
            resNode.left = leftRotate(resNode.left);
            return rightRotate(resNode);
        }
        //RL
        if (balanceFactor < -1 && getBalanceFactor(resNode.right) > 0) {
            resNode.right = rightRotate(resNode.right);
            return leftRotate(resNode);
        }
        return resNode;
    }

    /**
     * 返回最大值节点
     *
     * @param target 目标根节点
     * @return 最大值节点
     */
    private Node getMinNode(Node target) {
        //右子不为空就一直拿右子
        return target.left == null ? target : getMinNode(target.left);
    }

    /**
     * 获取节点
     *
     * @param node 节点
     * @param el  键
     * @return
     */
    private Node getNode(Node node, E el) {
        if (node == null) {
            return null;
        }

        if (el.compareTo(node.mKey) == 0) {
            return node;
        } else if (el.compareTo(node.mKey) > 0) {
            return getNode(node.right, el);
        } else {
            return getNode(node.left, el);
        }

    }


    /**
     * 返回插入新节点后的二分搜索树的根
     *
     * @param target 目标节点
     * @param el    插入元素
     * @return 插入新节点后的二分搜索树的根
     */
    private Node addNode(Node target, E el) {
        if (target == null) {
            size++;
            return new Node(null, null, el);
        }

        if (el.compareTo(target.mKey) < 0) {
            target.left = addNode(target.left, el);
        } else if (el.compareTo(target.mKey) > 0) {
            target.right = addNode(target.right, el);
        } 

        //更新高度值
        updateHeight(target);
        //获取平衡因子
        int balanceFactor = getBalanceFactor(target);

        //左子树的平衡因子大于等于0,说明左侧有问题--右旋转
        if (balanceFactor > 1 && getBalanceFactor(target.left) >= 0) {
            return rightRotate(target);
        }
        if (balanceFactor < -1 && getBalanceFactor(target.right) <= 0)
            return leftRotate(target);

        if (balanceFactor > 1 && getBalanceFactor(target.left) < 0) {
            target.left = leftRotate(target.left);
            return rightRotate(target);
        }

        if (balanceFactor < -1 && getBalanceFactor(target.right) > 0) {
            target.right = rightRotate(target.right);
            return leftRotate(target);
        }

        return target;
    }

    /**
     * // 对节点y进行向左旋转操作，返回旋转后新的根节点x
     * //    deep                             x
     * //  /  \                          /   \
     * // T1   x      向左旋转 (deep)       deep     z
     * //     / \   - - - - - - - ->   / \   / \
     * //   T2  z                     T1 T2 T3 T4
     * //      / \
     * //     T3 T4
     *
     * @param y
     * @return
     */
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;
        // 向左旋转过程
        x.left = y;
        y.right = T2;
        // 更新height
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    /**
     * // 对节点y进行向右旋转操作，返回旋转后新的根节点x
     * //        deep                              x
     * //       / \                           /   \
     * //      x   T4     向右旋转 (deep)        z     deep
     * //     / \       - - - - - - - ->    / \   / \
     * //    z   T3                       T1  T2 T3 T4
     * //   / \
     * // T1   T2
     *
     * @param y
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node t3 = x.right;
        //向右旋转
        x.right = y;
        y.left = t3;
        //更新height
        updateHeight(y);
        updateHeight(x);
        return x;
    }


    /**
     * 添加一个元素后，更新高度值
     *
     * @param node 待更新的节点
     */
    private void updateHeight(Node node) {
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }


    /**
     * 获取一个节点的高度值
     *
     * @param target
     * @return
     */
    private int getHeight(Node target) {
        if (target == null) {
            return 0;
        } else {
            return target.height;
        }
    }

    /**
     * 计算平衡因子
     *
     * @param target
     * @return
     */
    private int getBalanceFactor(Node target) {
        if (target == null) {
            return 0;
        }
        return getHeight(target.left) - getHeight(target.right);
    }

    /**
     * 判断二叉树是否是二分搜索树
     *
     * @return
     */
    public boolean isBST() {
        ArrayList<E> keys = new ArrayList<>();
        //中序遍历
        orderIn(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断以target为跟节点的二叉树是否是平衡二叉树
     *
     * @param target
     * @return
     */
    private boolean isBalanced(Node target) {
        if (target == null) {
            return true;
        }
        int bf = getBalanceFactor(target);
        if (Math.abs(bf) > 1) {
            return false;
        }
        return isBalanced(target.left) && isBalanced(target.right);
    }

    /**
     * 中序遍历
     *
     * @param target
     * @param keys
     */
    private void orderIn(Node target, ArrayList<E> keys) {
        if (target == null) {
            return;
        }
        orderIn(target.left, keys);
        keys.add(target.mKey);
        orderIn(target.right, keys);
    }

    public void orderIn( ArrayList<E> keys){
        orderIn(root,keys);

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

//////////////////////----------------辅助绘图的方法-------------

    public Node getRoot() {
        return root;
    }


//////////////////////----------------辅助绘图的方法-------------



    enum NodeType {
        FULL,
        LEAF,
        RIGHT_NULL,
        LEFT_NULL;
    }

    /**
     * 节点类
     */
    public class Node {

        /**
         * 储存的数据元素
         */
        public E mKey;
        /**
         * 左子
         */
        public Node left;
        /**
         * 右子
         */
        public Node right;

        public int height;

        /**
         * 构造函数
         *
         * @param left  左子
         * @param right 右子
         * @param el   储存的数据元素
         */
        public Node(Node left, Node right, E el) {
            this.mKey = el;
            this.left = left;
            this.right = right;
            this.height = 1;
        }

        public NodeType getType() {

            if (this.right == null) {
                if (this.left == null) {
                    return NodeType.LEAF;
                } else {
                    return NodeType.RIGHT_NULL;
                }
            }

            if (this.left == null) {
                return NodeType.LEFT_NULL;
            } else {
                return NodeType.FULL;
            }

        }
    }
}
