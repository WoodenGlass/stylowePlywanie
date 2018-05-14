package dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.teammanagement.trainingdetails.StudentAchievementUtils;

/**
 * Created by dobrowol on 29.03.17.
 */
public class AchievementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView nameLabel;
    private final TextView surnameLabel;
    private final TextView time;
    private AchievementAdapter.AchievementSelectedListener listener;
    private StudentAchievement achievement;

    public AchievementViewHolder(View itemView, AchievementAdapter.AchievementSelectedListener listener) {
        super(itemView);
        nameLabel = (TextView) itemView.findViewById(R.id.textView_distance);
        surnameLabel = (TextView) itemView.findViewById(R.id.textView_style);
        time = (TextView) itemView.findViewById(R.id.textView_desription);

        //profileImage.setImageDrawable( R.drawable.ic_person_add_black_24dp);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    public void fillView(StudentAchievement achievement) {
        this.achievement = achievement;
        nameLabel.setText(this.achievement.distance);
        surnameLabel.setText(this.achievement.style);
        time.setText(achievement.formatTime());
        //imageLoader.load(this.userDetails.getImageUrl()).into(profileImage);
    }

    @Override
    public void onClick(View v) {
        listener.onItemSelected(achievement);
    }
}

