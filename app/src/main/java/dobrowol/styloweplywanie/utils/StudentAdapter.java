package dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dobrowol.styloweplywanie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dobrowol on 29.03.17.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    private List<StudentData> items;
    private StudentSelectedListener listener;
    private Picasso imageLoader;

    public StudentAdapter(StudentSelectedListener listener)
    {

        this.listener = listener;

        //this.imageLoader = imageLoader;
    }
    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_teamlistitem, parent, false);
        return new StudentViewHolder(listItemView, listener);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        StudentData textAtPosition = items.get(position);
        holder.fillView(textAtPosition, imageLoader);
    }

    public void setItems(List<StudentData> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public interface StudentSelectedListener
    {
        public void onItemSelected(StudentData item);
    }
}
