package com.example.dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dobrowol.styloweplywanie.R;

import java.util.List;

/**
 * Created by dobrowol on 29.03.17.
 */

public class TrainingSetAdapter extends RecyclerView.Adapter<TrainingSetHolder> {

    private List<TrainingSet> items;
    private TrainingSetSelectedListener listener;
    //private Picasso imageLoader;


    public TrainingSetAdapter(TrainingSetSelectedListener listener)
    {
      //  this.imageLoader = loader;
        this.listener = listener;
    }
    @Override
    public TrainingSetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_trainingsetlistitem, parent, false);
        return new TrainingSetHolder(listItemView, listener);
    }

    @Override
    public void onBindViewHolder(TrainingSetHolder holder, int position) {
        TrainingSet textAtPosition = items.get(position);
        holder.fillView(textAtPosition);
    }

    public void setItems(List<TrainingSet> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public interface TrainingSetSelectedListener
    {
        public void onItemSelected(TrainingSet item);
    }
}
