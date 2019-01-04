package com.deedat.nkrumahistcircle.finances;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deedat.nkrumahistcircle.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class itemsAdapter extends RecyclerView.Adapter<itemsAdapter.itemViewHolder> {
    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        itemViewHolder ivh=new itemViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        money currentItem=mfinancelist.get(position);
        holder.mimageView.setImageResource(currentItem.getMicon());
       holder.mtype.setText(currentItem.getMtype());
       holder.mdate.setText(currentItem.getMdate());
       holder.mamount.setText(""+currentItem.getMamount());

    }

    public itemsAdapter(ArrayList<money> finacelist){

        mfinancelist=finacelist;
    }
    @Override
    public int getItemCount() {
        return mfinancelist.size();
    }

    private  ArrayList<money> mfinancelist;
    public  static class itemViewHolder extends RecyclerView.ViewHolder{
public ImageView mimageView;
public TextView mtype,mdate,mamount;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            mimageView=itemView.findViewById(R.id.imageView2);
            mtype=itemView.findViewById(R.id.type);
            mdate=itemView.findViewById(R.id.date);
            mamount=itemView.findViewById(R.id.amount);
        }
    }

    public void clear() {
        mfinancelist.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(ArrayList<money> list) {
        mfinancelist.addAll(list);
        notifyDataSetChanged();
    }
}
