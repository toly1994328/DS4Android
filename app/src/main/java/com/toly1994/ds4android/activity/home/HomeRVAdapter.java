package com.toly1994.ds4android.activity.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.toly1994.ds4android.R;
import com.toly1994.ds4android.activity.ArrayChartActivity;
import com.toly1994.ds4android.activity.BinarySearchActivity;
import com.toly1994.ds4android.activity.LinkedChartActivity;
import com.toly1994.ds4android.activity.QueueActivity;
import com.toly1994.ds4android.activity.SingleLinkedChartActivity;
import com.toly1994.ds4android.activity.StackActivity;

import java.util.List;

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.MyViewHolder> {
    private Context mContext;
    private List<Integer> mData;

    public HomeRVAdapter(Context context, List<Integer> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_of_cade, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mIvCover.setImageResource(mData.get(position));

        holder.mIvCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        mContext.startActivity(new Intent(mContext, ArrayChartActivity.class));
                        break;
                    case 1:
                        mContext.startActivity(new Intent(mContext, SingleLinkedChartActivity.class));
                        break;
                    case 2:
                        mContext.startActivity(new Intent(mContext, LinkedChartActivity.class));
                        break;
                    case 3:
                        mContext.startActivity(new Intent(mContext, StackActivity.class));
                        break;
                    case 4:
                        mContext.startActivity(new Intent(mContext, QueueActivity.class));
                        break;
                    case 5:
                        mContext.startActivity(new Intent(mContext, BinarySearchActivity.class));
                        break;

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCvContent;
        public ImageView mIvCover;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCvContent = itemView.findViewById(R.id.cv_content);
            mIvCover = itemView.findViewById(R.id.iv_cover);
        }
    }
}