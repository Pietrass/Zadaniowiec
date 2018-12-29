package com.example.piotr.zadaniowiec;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> zadania;
    private OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public MyViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }

        public void bind(final String item, final OnItemClickListener listener) {
            mTextView.setText(item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public MyAdapter(ArrayList<String> zadania, OnItemClickListener listener) {
        this.zadania = zadania;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(zadania.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return zadania.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String item);
    }


}
