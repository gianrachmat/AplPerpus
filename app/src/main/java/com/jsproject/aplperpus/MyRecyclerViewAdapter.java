package com.jsproject.aplperpus;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by J on 18/04/2016.
 */
class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    private ArrayList<Buku> mDataset;
    private static MyClickListener myClickListener;

    static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView label;
        TextView dateTime;
        ImageView bookImage;

        DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            bookImage = (ImageView) itemView.findViewById(R.id.img);
            String LOG_TAG = "MyRecyclerViewAdapter";
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).get_nama_buku());
        holder.dateTime.setText(mDataset.get(position).get_nama_pengarang());
        holder.bookImage.setImageBitmap(DbBitmapUtility.getImage(mDataset.get(position).get_gambar_buku()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItem(Buku dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public MyRecyclerViewAdapter(ArrayList<Buku> bukus) {
        mDataset = bukus;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        MyRecyclerViewAdapter.myClickListener = myClickListener;
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
}