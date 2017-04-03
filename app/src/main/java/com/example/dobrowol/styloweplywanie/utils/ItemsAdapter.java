package com.example.dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dobrowol.styloweplywanie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dobrowol on 29.03.17.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<TeamData> items;
    private ItemsSelectedListener listener;
    private Picasso imageLoader;

    public ItemsAdapter(ItemsSelectedListener listener, Picasso imageLoader)
    {

        this.listener = listener;

        this.imageLoader = imageLoader;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_teamlistitem, parent, false);
        return new ViewHolder(listItemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeamData textAtPosition = items.get(position);
        holder.fillView(textAtPosition, imageLoader);
    }

    public void setItems(List<TeamData> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public interface ItemsSelectedListener
    {
        public void onItemSelected(TeamData item);
    }
}
