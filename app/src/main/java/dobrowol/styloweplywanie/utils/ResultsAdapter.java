package dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dobrowol.styloweplywanie.R;

/**
 * Created by dobrowol on 29.03.17.
 */

public class ResultsAdapter extends RecyclerView.Adapter<ResultsViewHolder> {

    private List<String> items;
    private ResultsSelectedListener listener;

    public ResultsAdapter(ResultsSelectedListener listener)
    {
        this.listener = listener;
    }
    @Override
    public ResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_trainingsetlistitem, parent, false);
        return new ResultsViewHolder(listItemView, listener);
    }

    @Override
    public void onBindViewHolder(ResultsViewHolder holder, int position) {
        String textAtPosition = items.get(position);
        holder.fillView(textAtPosition);
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public interface ResultsSelectedListener
    {
        public void onItemSelected(String key);
        public void onItem(int position);
    }
}
