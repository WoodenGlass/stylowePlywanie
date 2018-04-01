package dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dobrowol.styloweplywanie.R;
import com.squareup.picasso.Picasso;

/**
 * Created by dobrowol on 29.03.17.
 */
public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView nameLabel;
    private final TextView surnameLabel;
    private final ImageView profileImage;
    private StudentAdapter.StudentSelectedListener listener;
    private StudentData userDetails;

    public StudentViewHolder(View itemView, StudentAdapter.StudentSelectedListener listener) {
        super(itemView);
        nameLabel = (TextView) itemView.findViewById(R.id.team_label);
        surnameLabel = (TextView) itemView.findViewById(R.id.description_label);
        profileImage = (ImageView) itemView.findViewById(R.id.profile_image);

        //profileImage.setImageDrawable( R.drawable.ic_person_add_black_24dp);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    public void fillView(StudentData userDetails, Picasso imageLoader) {
        this.userDetails = userDetails;
        nameLabel.setText(this.userDetails.name+" "+this.userDetails.surname);
        surnameLabel.setText(this.userDetails.dateOfBirth);
        //imageLoader.load(this.userDetails.getImageUrl()).into(profileImage);
    }

    @Override
    public void onClick(View v) {
        listener.onItemSelected(userDetails);
    }
}

