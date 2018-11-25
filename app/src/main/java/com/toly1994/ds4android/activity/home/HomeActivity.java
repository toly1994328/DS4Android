package com.toly1994.ds4android.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.toly1994.ds4android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/25 0025:10:50<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class HomeActivity extends AppCompatActivity {

    private List<Integer> mData = new ArrayList<>();
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mData.add(R.mipmap.array_list);
        mData.add(R.mipmap.singel_linked);
        mData.add(R.mipmap.linked_list);
        mData.add(R.mipmap.stack);
        mData.add(R.mipmap.queue);
        mData.add(R.mipmap.bst);

        setContentView(R.layout.activity_home);

        mRecyclerView = findViewById(R.id.id_rv);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(new HomeRVAdapter(this, mData));
    }
}
