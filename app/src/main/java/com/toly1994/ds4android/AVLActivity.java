package com.toly1994.ds4android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.toly1994.ds4android.analyze.gold12.ZRandom;
import com.toly1994.ds4android.view.AVLView;
import com.toly1994.ds4android.view.other.CtrlClickAdapter;

public class AVLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVLView<Integer> view = new AVLView<>(this);

        view.setOnCtrlClickListener(new CtrlClickAdapter<AVLView<Integer>>() {
            @Override
            public void onAdd(AVLView<Integer> view) {
//                view.enqueue(ZRandom.randomCnName());
                view.addData(ZRandom.rangeInt(50,200));
            }

            @Override
            public void onRemove(AVLView<Integer> view) {
//                view.pop();
            }

            @Override
            public void onFind(AVLView<Integer> view) {
//                String data = view.peek();
//                Toast.makeText(AVLActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });


        setContentView(view);

    }
}
