package dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import dobrowol.styloweplywanie.R;

/**
 * Created by dobrowol on 29.03.17.
 */

public class AchievementsItemsAdapter extends RecyclerView.Adapter<AchievementsViewHolder> {

    private List<StudentAchievement> items;
    private ItemsSelectedListener listener;
    private Picasso imageLoader;

    public AchievementsItemsAdapter(ItemsSelectedListener listener)
    {

        this.listener = listener;

        //this.imageLoader = imageLoader;
    }
    public void removeItem(int position) {
        StudentAchievement teamData = items.get(position);
        listener.onItemRemoved(position);


        notifyItemRemoved(position);
    }
    @Override
    public AchievementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_studentachievement, parent, false);
        return new AchievementsViewHolder(listItemView, listener);
    }

    @Override
    public void onBindViewHolder(AchievementsViewHolder holder, int position) {

        Log.d("DUPA size ", String.valueOf(items.size()));
        StudentAchievement studentAchievement = items.get(position);
        //holder.setPosition();
        if (studentAchievement != null)
        {
            holder.fillView(studentAchievement, imageLoader);
        }
    }

    public void setItems(List<StudentAchievement> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public interface ItemsSelectedListener
    {
        void onItemSelected(StudentAchievement item, int adapterPosition);

        void onItemRemoved(int position);

        void OnItem(int adapterPosition);
    }
}
