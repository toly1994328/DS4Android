package com.toly1994.ds4android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.toly1994.ds4android.analyze.gold12.ZRandom;
import com.toly1994.ds4android.view.StackView;
import com.toly1994.ds4android.view.other.CtrlClickAdapter;

public class StackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StackView<String> view = new StackView<>(this);

        view.setOnCtrlClickListener(new CtrlClickAdapter<StackView<String>>() {
            @Override
            public void onAdd(StackView<String> view) {
//                view.addData(ZRandom.randomCnName());
                view.addData(ZRandom.rangeChar(ZRandom.KUO_HAO));
            }

            @Override
            public void onRemove(StackView<String> view) {
                view.removeData();
            }

            @Override
            public void onFind(StackView<String> view) {
                String data = view.findData();
                Toast.makeText(StackActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });


        setContentView(view);

    }
}
