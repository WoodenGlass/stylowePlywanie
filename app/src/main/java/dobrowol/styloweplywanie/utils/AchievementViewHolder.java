package dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dobrowol.styloweplywanie.R;

/**
 * Created by dobrowol on 29.03.17.
 */
public class AchievementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView distanceLabel;
    private final TextView styleLabel;
    private final TextView time;
    private AchievementAdapter.AchievementSelectedListener listener;
    private StudentAchievement achievement;

    public AchievementViewHolder(View itemView, AchievementAdapter.AchievementSelectedListener listener) {
        super(itemView);
        distanceLabel = (TextView) itemView.findViewById(R.id.distance_textView);
        styleLabel = (TextView) itemView.findViewById(R.id.style_textView);
        time = (TextView) itemView.findViewById(R.id.description_textView);

        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    public void fillView(StudentAchievement achievement) {
        this.achievement = achievement;
        distanceLabel.setText(this.achievement.distance);
        styleLabel.setText(this.achievement.style);
        time.setText(achievement.formatTime());
    }

    @Override
    public void onClick(View v) {
        listener.onItemSelected(achievement);
    }
}

