package dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.teammanagement.trainingdetails.StudentAchievementUtils;

/**
 * Created by dobrowol on 29.03.17.
 */

public class AchievementAdapter extends RecyclerView.Adapter<AchievementViewHolder> {

    private List<StudentAchievement> items;
    private AchievementSelectedListener listener;

    public AchievementAdapter(AchievementSelectedListener listener)
    {
        this.listener = listener;
    }
    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainingsetitem, parent, false);
        return new AchievementViewHolder(listItemView, listener);
    }

    @Override
    public void onBindViewHolder(AchievementViewHolder holder, int position) {
        StudentAchievement textAtPosition = items.get(position);
        holder.fillView(textAtPosition);
    }

    public void setItems(List<StudentAchievement> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public interface AchievementSelectedListener
    {
        public void onItemSelected(StudentAchievement key);
    }
}
