package dobrowol.styloweplywanie.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.utils.TeamDataUtils;

/**
 * Created by dobrowol on 13.03.17.
 */

public class InitialWelcome extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "InitialWelcome";
    private Button buttonCreateTeam;
    private Button buttonjoinTeam;
    private Button buttonClearCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialwelcome);

        buttonCreateTeam = (Button) findViewById(R.id.button_createteam);
        buttonjoinTeam = (Button) findViewById(R.id.button_jointeam);
        buttonClearCache = (Button) findViewById(R.id.button_clearCache);

        buttonCreateTeam.setOnClickListener(this);
        buttonjoinTeam.setOnClickListener(this);
        buttonClearCache.setOnClickListener(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId())
        {
            case R.id.button_createteam:
                Log.d(TAG, "dupa createteam");
                intent =  new Intent(this, CreateTeamActivity.class);

                startActivity(intent);
                break;
            case R.id.button_jointeam:
                Log.d(TAG, "dupa jointeam");
                 intent = new Intent(this, JoinTeamActivity.class);

                startActivity(intent);
                break;
            case R.id.button_clearCache:
                TeamDataUtils teamDataUtils = new TeamDataUtils(getApplicationContext());
                teamDataUtils.clearCache();
                break;
            default:
                Log.d(TAG,"dupa default");
                break;
        }

    }
}
