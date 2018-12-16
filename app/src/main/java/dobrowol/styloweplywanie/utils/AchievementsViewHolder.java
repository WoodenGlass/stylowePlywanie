package dobrowol.styloweplywanie.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import dobrowol.styloweplywanie.R;

/**
 * Created by dobrowol on 29.03.17.
 */
public class AchievementsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener, View.OnFocusChangeListener, TextView.OnEditorActionListener {

    private EditText distanceEt;
    private EditText styleEt;
    private EditText timeET;
    private EditText strokeCountEt;
    private EditText dateEt;
    private TextView distanceTv;
    private TextView styleTv;
    private TextView timeTv;
    private TextView strokeCountTv;
    private TextView dateTv;
    private  String dataFile;
    private AchievementsItemsAdapter.ItemsSelectedListener listener;
    private StudentAchievement studentAchievement;
    private View.OnCreateContextMenuListener contextMenuListener;
    private LinearLayout withTextView;
    private LinearLayout withEditText;

    public AchievementsViewHolder(View itemView, AchievementsItemsAdapter.ItemsSelectedListener listener) {

        super(itemView);
        styleEt = (EditText) itemView.findViewById(R.id.eT_achievement_style);
        distanceEt = (EditText) itemView.findViewById(R.id.eT_achievement_distance);
        strokeCountEt = (EditText) itemView.findViewById(R.id.eT_achievement_stroke_count);
        timeET = (EditText) itemView.findViewById(R.id.eT_achievement_time);
        dateEt = (EditText) itemView.findViewById(R.id.eT_achievement_date);

        styleTv = (TextView) itemView.findViewById(R.id.tv_achievement_style);
        distanceTv = (TextView) itemView.findViewById(R.id.tv_achievement_distance);
        strokeCountTv = (TextView) itemView.findViewById(R.id.tv_achievement_stroke_count);
        timeTv = (TextView) itemView.findViewById(R.id.tv_achievement_time);
        dateTv = (TextView) itemView.findViewById(R.id.tv_achievement_date);

        withEditText = (LinearLayout) itemView.findViewById(R.id.layoutWithEditText);
        withTextView = (LinearLayout) itemView.findViewById(R.id.layoutWithTextView);
        withEditText.setVisibility(View.INVISIBLE);
        withTextView.setVisibility(View.VISIBLE);
        dateEt.setOnClickListener(this);

        dateEt.setHint("dd/mm/YYYY");

        this.listener = listener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

        timeET.setOnFocusChangeListener(this);
        dateEt.setOnFocusChangeListener(this);
        styleEt.setOnFocusChangeListener(this);
        distanceEt.setOnFocusChangeListener(this);
        strokeCountEt.setOnFocusChangeListener(this);
        timeET.setOnEditorActionListener(this);
        styleEt.setOnEditorActionListener(this);
        strokeCountEt.setOnEditorActionListener(this);
        dateEt.setOnEditorActionListener(this);
        distanceEt.setOnEditorActionListener(this);

        styleTv.setOnClickListener(this);
        distanceTv.setOnClickListener(this);
        strokeCountTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        dateTv.setOnClickListener(this);
        setSoftkeyEnterListener();
       /* dateTv.setVisibility(View.INVISIBLE);
        strokeCountTv.setVisibility(View.INVISIBLE);
        distanceTv.setVisibility(View.INVISIBLE);
        styleTv.setVisibility(View.INVISIBLE);
        strokeCountTv.setVisibility(View.INVISIBLE);*/
    }

    public void fillView(StudentAchievement studentAchievement, Picasso imageLoader) {
        this.studentAchievement = studentAchievement;
        styleEt.setText(studentAchievement.style);
        distanceEt.setText(studentAchievement.distance);
        strokeCountEt.setText(studentAchievement.strokeCount);
        timeET.setText(studentAchievement.formatTime());
        dateEt.setText(studentAchievement.displayDate());

        styleTv.setText(studentAchievement.style);
        distanceTv.setText(studentAchievement.distance);
        strokeCountTv.setText(studentAchievement.strokeCount);
        timeTv.setText(studentAchievement.formatTime());
        dateTv.setText(studentAchievement.displayDate());
    }

    @Override
    public void onClick(View v) {Log.d("DUPA", "click");

        switch (v.getId())
        {
            case R.id.tv_achievement_date:
            case R.id.tv_achievement_distance:
            case R.id.tv_achievement_stroke_count:
            case R.id.tv_achievement_style:
            case R.id.tv_achievement_time:
                /*dateEt.setVisibility(View.VISIBLE);
                strokeCountEt.setVisibility(View.VISIBLE);
                distanceEt.setVisibility(View.VISIBLE);
                styleEt.setVisibility(View.VISIBLE);
                strokeCountEt.setVisibility(View.VISIBLE);
                dateTv.setVisibility(View.INVISIBLE);
                strokeCountTv.setVisibility(View.INVISIBLE);
                distanceTv.setVisibility(View.INVISIBLE);
                styleTv.setVisibility(View.INVISIBLE);
                strokeCountTv.setVisibility(View.INVISIBLE);*/
                withEditText.setVisibility(View.VISIBLE);
                withTextView.setVisibility(View.INVISIBLE);

        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.add(0, v.getId(), 0, "Delete");

    }

    @Override
    public boolean onLongClick(View v) {
        listener.OnItem(getAdapterPosition());
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        switch (v.getId())
        {
            case R.id.eT_achievement_style:
                if (hasFocus == false) {


                }
                break;
            case R.id.eT_achievement_distance:
                if (hasFocus == false) {


                }
                break;
            case R.id.eT_achievement_stroke_count:
                if (hasFocus == false) {


                }
                break;
            case R.id.eT_achievement_time:
                if (hasFocus == false) {


                }
                break;
            case R.id.eT_achievement_date:
                if (hasFocus == true) {
                    dateEt.setHint("dd/MM/yyyy");


                }
                break;
        }
    }
    private void captureDate() {
        String date = dateEt.getText().toString();
        studentAchievement.setDate(ConvertUtils.stringToDate(date));
    }
    private void captureAchievement()
    {
        studentAchievement.setTime(timeET.getText().toString());
        studentAchievement.strokeCount = strokeCountEt.getText().toString();
        studentAchievement.distance = distanceEt.getText().toString();
        studentAchievement.style = styleEt.getText().toString();
        studentAchievement.calculateStrokeIndex();
        captureDate();
        listener.onItemSelected(studentAchievement, getAdapterPosition());

    }
    private void setSoftkeyEnterListener()
    {
        View.OnKeyListener onKeyListener = new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_0:
                            captureAchievement();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        };
        dateEt.setOnKeyListener(onKeyListener);
        distanceEt.setOnKeyListener(onKeyListener);
        strokeCountEt.setOnKeyListener(onKeyListener);
        styleEt.setOnKeyListener(onKeyListener);
        timeET.setOnKeyListener(onKeyListener);

    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event==null) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                captureAchievement();

            }
        }
        return false;
    }
}

