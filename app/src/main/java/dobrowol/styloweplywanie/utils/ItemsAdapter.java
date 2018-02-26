package dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dobrowol.styloweplywanie.R;
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
    public void removeItem(int position) {
        TeamData teamData = items.get(position);
        listener.onItemRemoved(teamData.teamName);
        items.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_teamlistitem, parent, false);
        return new ViewHolder(listItemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("DUPA size ", String.valueOf(items.size()));
        TeamData teamAtPosition = items.get(position);
        //holder.setPosition();
        if (teamAtPosition != null)
        {
            holder.fillView(teamAtPosition, imageLoader);
        }
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

        void onItemRemoved(String teamName);

        void OnItem(int adapterPosition);
    }
}
