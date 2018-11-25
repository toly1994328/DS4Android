package com.toly1994.ds4android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.toly1994.ds4android.view.BinarySearchView;
import com.toly1994.ds4android.view.other.OnBSTClickListener;

import java.util.ArrayList;

public class BinarySearchActivity extends AppCompatActivity {

    private int curIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int[] nums = new int[]{
                10, 66, 6, 2, 8, 1, 17, 55, 4, 3, 5, 99, 15
        };


        BinarySearchView<Integer> view = new BinarySearchView<>(this);
        view.setOnBSTClickListener(new OnBSTClickListener() {
            @Override
            public void onAdd(BinarySearchView view) {
                if (curIndex < nums.length) {
                    view.addData(nums[curIndex]);
                    curIndex++;

                }

            }

            @Override
            public void onRemove(BinarySearchView view) {
                view.remove(2);
            }

            @Override
            public void onRemoveMax(BinarySearchView view) {
                view.removeMax();
            }

            @Override
            public void onRemoveMin(BinarySearchView view) {
                view.removeMin();
            }

            @Override
            public void onGetMax(BinarySearchView view) {
                Toast.makeText(BinarySearchActivity.this, "最大值：" + view.getMax(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGetMin(BinarySearchView view) {
                Toast.makeText(BinarySearchActivity.this, "最小值：" + view.getMin(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOrder(BinarySearchView view) {
                ArrayList<Integer> orderLEVEL = view.order(BinarySearchView.OrderType.LEVEL);
                ArrayList<Integer> orderPREV = view.order(BinarySearchView.OrderType.PREV);
                ArrayList<Integer> orderIN = view.order(BinarySearchView.OrderType.IN);
                ArrayList<Integer> orderPOST = view.order(BinarySearchView.OrderType.POST);


                Toast.makeText(BinarySearchActivity.this,
                        "层序遍历：" + orderLEVEL.toString() + "\n" +
                                "前序遍历：" + orderPREV.toString() + "\n" +
                                "中序遍历：" + orderIN.toString() + "\n" +
                                "后序遍历：" + orderPOST.toString() + "\n"
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onContains(BinarySearchView view) {
                Toast.makeText(BinarySearchActivity.this, "是否包含4：" + view.contains(4), Toast.LENGTH_SHORT).show();
            }
        });

//        view.setOnCtrlClickListener(new OnCtrlClickListener<BinarySearchView<Integer>>() {
//            @Override
//            public void onAdd(BinarySearchView<Integer> view) {
////                view.addDataById(ZRandom.randomOf3Name());
//                view.addData(ZRandom.rangeInt(50,200));
//            }
//
//            @Override
//            public void onAddByIndex(BinarySearchView<Integer> view) {
////                view.addDataById(view.getSelectIndex(), ZRandom.randomCnName());
//            }
//
//            @Override
//            public void onRemove(BinarySearchView<Integer> view) {
//                view.removeMax();
//                                Toast.makeText(BinarySearchActivity.this, "onRemove", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//            @Override
//            public void onRemoveByIndex(BinarySearchView<Integer> view) {
////                view.removeData(view.getSelectIndex());
//            }
//
//            @Override
//            public void onSet(BinarySearchView<Integer> view) {
////                view.setData(view.getSelectIndex(), ZRandom.randomCnName());
//            }
//
//            @Override
//            public void onFind(BinarySearchView<Integer> view) {
////                String data = view.findData(view.getSelectIndex());
////                Toast.makeText(BinarySearchActivity.this, data, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFindByData(BinarySearchView<Integer> view) {
////                int[] data = view.findData(view.getSelectData());
////                Toast.makeText(BinarySearchActivity.this, Arrays.toString(data), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onClear(BinarySearchView<Integer> view) {
////                view.clearData();
//            }
//
//
//
//        });

        setContentView(view);

    }
}
