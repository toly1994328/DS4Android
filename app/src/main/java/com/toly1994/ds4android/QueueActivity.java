package com.toly1994.ds4android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.toly1994.ds4android.analyze.gold12.ZRandom;
import com.toly1994.ds4android.view.QueueView;
import com.toly1994.ds4android.view.other.CtrlClickAdapter;

public class QueueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QueueView<String> view = new QueueView<>(this);

        view.setOnCtrlClickListener(new CtrlClickAdapter<QueueView<String>>() {
            @Override
            public void onAdd(QueueView<String> view) {
                view.addData(ZRandom.randomCnName());
//                view.addData(ZRandom.rangeChar(ZRandom.KUO_HAO));
            }

            @Override
            public void onRemove(QueueView<String> view) {
                view.removeData();
            }

            @Override
            public void onFind(QueueView<String> view) {
                String data = view.findData();
                Toast.makeText(QueueActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });


        setContentView(view);

    }
}
