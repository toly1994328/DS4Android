package com.toly1994.ds4android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.toly1994.ds4android.analyze.gold12.ZRandom;
import com.toly1994.ds4android.view.ArrayView;
import com.toly1994.ds4android.view.other.OnCtrlClickListener;

import java.util.Arrays;

public class ArrayChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayView<String> view = new ArrayView<>(this);

        view.setOnCtrlClickListener(new OnCtrlClickListener<ArrayView<String>>() {
            @Override
            public void onAdd(ArrayView<String> view) {
//                view.addDataById(ZRandom.randomOf3Name());
                view.addData(ZRandom.randomCnName());
            }

            @Override
            public void onAddByIndex(ArrayView<String> view) {
                view.addDataById(view.getSelectIndex(), ZRandom.randomCnName());
            }

            @Override
            public void onRemove(ArrayView<String> view) {
                view.removeData();
            }


            @Override
            public void onRemoveByIndex(ArrayView<String> view) {
                view.removeData(view.getSelectIndex());
            }

            @Override
            public void onSet(ArrayView<String> view) {
                view.setData(view.getSelectIndex(), ZRandom.randomCnName());
            }

            @Override
            public void onFind(ArrayView<String> view) {
                String data = view.findData(view.getSelectIndex());
                Toast.makeText(ArrayChartActivity.this, data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFindByData(ArrayView<String> view) {
                int[] data = view.findData(view.getSelectData());
                Toast.makeText(ArrayChartActivity.this, Arrays.toString(data), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClear(ArrayView<String> view) {
                view.clearData();
            }



        });

        setContentView(view);

    }
}
