package com.example.dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dobrowol.styloweplywanie.R;

/**
 * Created by dobrowol on 29.03.17.
 */
public class TrainingSetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView teamLabel;
    private final TextView descriptionLabel;
    private final ImageView profileImage;
    private TrainingSetAdapter.TrainingSetSelectedListener listener;
    private TrainingSet userDetails;

    public TrainingSetHolder(View itemView, TrainingSetAdapter.TrainingSetSelectedListener listener) {
        super(itemView);
        teamLabel = (TextView) itemView.findViewById(R.id.team_label);
        descriptionLabel = (TextView) itemView.findViewById(R.id.description_label);
        profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    public void fillView(TrainingSet userDetails) {
        this.userDetails = userDetails;
        teamLabel.setText(this.userDetails.style.toString());
        descriptionLabel.setText(this.userDetails.description);
        //imageLoader.load(this.userDetails.getImageUrl()).into(profileImage);
    }

    @Override
    public void onClick(View v) {
        listener.onItemSelected(userDetails);
    }
}

