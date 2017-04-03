package com.example.dobrowol.styloweplywanie.teammanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.utils.StudentData;
import com.example.dobrowol.styloweplywanie.utils.TeamData;
import com.example.dobrowol.styloweplywanie.utils.TeamDataUtils;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;

/**
 * Created by dobrowol on 30.03.17.
 */

public class TeamDetailsActivity extends AppCompatActivity {
    private static final String KEY = "TeamData";
    private static final int GET_USER_REQUEST = 1;
    private TextView firstLine;
    private TextView secondLine;
    private TextView thirdLine;
    private TextView fourthLine;
    private ImageView imageView;
    private TeamData teamData;
    private Menu menu;
    private Toolbar myToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamdetails);

        firstLine = (TextView) findViewById(R.id.firstLine);
        secondLine = (TextView) findViewById(R.id.secondLine);
        secondLine = (TextView) findViewById(R.id.secondLine);
        thirdLine = (TextView) findViewById(R.id.thirdLine);
        fourthLine = (TextView) findViewById(R.id.fourthLine);
        imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            teamData = (TeamData) intent.getExtras().getSerializable(KEY);
            fillUserDetails();
        }
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    private void createToolbar() {

        final MenuItem menuItem = menu.findItem(R.id.action_addTeamMember);
        final int size = menu.size();
        //view.getLocationOnScreen(location);
        Target homeTarget = new Target() {
            @Override
            public Point getPoint() {
                // Get approximate position of home icon's center
                int actionBarHeight = myToolbar.getHeight();
                int actionBarWidth = myToolbar.getWidth();
                int x = actionBarHeight;
                int y = actionBarWidth - menuItem.getIcon().getIntrinsicWidth()*(size - menuItem.getOrder());
                if (x >0 && y > 0) {
                    return new Point(y, x);
                }
                return new Point(actionBarHeight/2, actionBarWidth/2);
            }
        };
        new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(homeTarget)
                .setContentTitle("Dodaj ucznia")
                .setContentText("Tu możesz dodac nowego ucznia")
                .hideOnTouchOutside()
                .build();
    }

    private void fillUserDetails() {
        firstLine.setText(teamData.getTeamName());
        secondLine.setText(teamData.getCoachName());
        thirdLine.setText("Ilość uczniow " + teamData.getSize());
        //thirdLine.setText(teamData.getBio());
        //String pluralText = getResources().getQuantityString(R.plurals.followers, teamData.getFollowers());
        //fourthLine.setText(teamData.getFollowers() +" "+ pluralText);
        //String imageUrl = teamData.getImageUrl();
        //Picasso.with(this).load(imageUrl).into(imageView);
    }

    public static void startDetailsActivity(TeamData teamData, Context context) {
        Intent intent = new Intent(context, TeamDetailsActivity.class);
        intent.putExtra(KEY, teamData);

        context.startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_teamdetails, menu);
        this.menu = menu;
        createToolbar();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_addTeamMember:
                showAddStudent();
                return true;
            case R.id.action_addTeam:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddStudent() {

        Intent intent = new Intent(this, AddStudentActivity.class);
        
        //Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ));
         // Show user only contacts w/ phone numbers
        startActivityForResult(intent, GET_USER_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        Log.d("DUPA", "onActivityResult");
        if (requestCode == GET_USER_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                StudentData studentData = (StudentData) data.getExtras().getSerializable(AddStudentActivity.RETURNED_DATA_KEY);
                teamData.addStudent(studentData);
                thirdLine.setText("Ilość uczniow " + teamData.getSize());
                TeamDataUtils teamDataUtils = new TeamDataUtils(getApplicationContext());
                teamDataUtils.updateTeam(teamData);
            }

        }
    }
}