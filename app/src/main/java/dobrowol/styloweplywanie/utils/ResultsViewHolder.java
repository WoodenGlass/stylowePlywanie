package dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import dobrowol.styloweplywanie.R;

/**
 * Created by dobrowol on 29.03.17.
 */
public class ResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {

    private final TextView distanceLabel;
    private final TextView styleLabel;
    private final TextView time;
    private ResultsAdapter.ResultsSelectedListener listener;
    private String result;

    public ResultsViewHolder(View itemView, ResultsAdapter.ResultsSelectedListener listener) {
        super(itemView);
        distanceLabel = (TextView) itemView.findViewById(R.id.distance_textView);
        styleLabel = (TextView) itemView.findViewById(R.id.style_textView);
        time = (TextView) itemView.findViewById(R.id.description_textView);
        time.setText("");
        styleLabel.setText("");

        this.listener = listener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void fillView(String result) {
        this.result = result;
        distanceLabel.setText(ConvertUtils.formatTime(this.result));
    }

    @Override
    public void onClick(View v) {
        listener.onItem(getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View view) {
        listener.onItem(getAdapterPosition());
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

    }
}

