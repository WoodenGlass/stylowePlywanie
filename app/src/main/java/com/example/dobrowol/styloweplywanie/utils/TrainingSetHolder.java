package com.example.dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.dobrowol.styloweplywanie.R;

/**
 * Created by dobrowol on 29.03.17.
 */
public class TrainingSetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView distanceLabel;
    private final TextView descriptionLabel;
    private final TextView styleLabel;
    private TrainingSetAdapter.TrainingSetSelectedListener listener;
    private TrainingSet trainingSet;

    public TrainingSetHolder(View itemView, TrainingSetAdapter.TrainingSetSelectedListener listener) {
        super(itemView);
        distanceLabel = (TextView) itemView.findViewById(R.id.distance_textView);
        descriptionLabel = (TextView) itemView.findViewById(R.id.description_textView);
        styleLabel = (TextView) itemView.findViewById(R.id.style_textView);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    public void fillView(TrainingSet trainingSet) {
        this.trainingSet = trainingSet;
        distanceLabel.setText(String.valueOf(this.trainingSet.distance));
        descriptionLabel.setText(this.trainingSet.description);
        styleLabel.setText(this.trainingSet.style.toString());
        //imageLoader.load(this.trainingSet.getImageUrl()).into(styleLabel);
    }

    @Override
    public void onClick(View v) {
        listener.onItemSelected(trainingSet);
    }
}

