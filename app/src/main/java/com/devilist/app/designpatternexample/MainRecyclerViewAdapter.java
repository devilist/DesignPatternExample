package com.devilist.app.designpatternexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zengpu on 2016/8/26.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String[]> list;
    private OnItemClickListener mOnItemClickListener;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView itemTv;
        private ImageView icon;
        private OnItemClickListener mListener;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;

            itemTv = (TextView) itemView.findViewById(R.id.tv_item);

            itemTv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v, getLayoutPosition());
        }
    }

    public MainRecyclerViewAdapter(Context context, List<String[]> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_main_recyclerview, parent, false);
        return new ViewHolder(v, mOnItemClickListener);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mViewHolder = (ViewHolder) holder;
        mViewHolder.itemTv.setText(list.get(position)[0]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}

