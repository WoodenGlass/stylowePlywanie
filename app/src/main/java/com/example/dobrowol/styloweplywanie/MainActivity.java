package com.example.dobrowol.styloweplywanie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dobrowol.styloweplywanie.welcome.InitialWelcome;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button connectButton;
    private TextView textView;
    private String messageContent="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        connectButton = (Button) findViewById(R.id.connectButton);
        if (EventBus.getDefault() == null) {
            EventBus.builder()
                    .logNoSubscriberMessages(false)
                    .sendNoSubscriberEvent(false)
                    .throwSubscriberException(false)
                    .installDefaultEventBus();
        }
        EventBus.getDefault().register(this);
        retrieveDataFromSavedState(savedInstanceState);

        textView.setText(messageContent);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Intent intent = new Intent(this, InitialWelcome.class);

        startActivity(intent);

    }
    private void retrieveDataFromSavedState(Bundle savedInstanceState)
    {
        if(savedInstanceState == null){
            //it is the first time the fragment is being called
            messageContent = "";
        }else{
            //not the first time so we will check SavedInstanceState bundle
            messageContent = savedInstanceState.getString("messageContent"); //here zero is the default value
        }
    }
    @Subscribe
    public void onReceiverEvent(ReceiverEvent event){
        Log.d(TAG, "DUPA " + event.message);
        messageContent = event.message;
    }
    @Override
    public void onClick(View v) {
        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Token: " + token);
        TextView txt = (TextView) findViewById(R.id.textView);
        txt.setText(token);

    }
    @Override
    public void onResume() {
        super.onResume();
        if (getIntent().hasExtra("messageContent")) {
            Log.d(TAG, "DUPA onResume intent has messageContent " + messageContent);
            messageContent = (String) getIntent().getStringExtra("messageContent");
            Log.d(TAG, "DUPA onResume retrieved messageContent " + messageContent);
        }
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(messageContent);
        // Register mMessageReceiver to receive messages.

    }
    @Override
    public void onStop()
    {
        super.onStop();
        if (messageContent != null && messageContent != "") {
            Log.d(TAG, "DUPA onStop put messageConent to intent " + messageContent);
            getIntent().putExtra("messageContent", messageContent);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "DUPA messageContent"+ messageContent);
        if (messageContent!="" && messageContent != null) {
            outState.putString("messageContent", messageContent);

        }
    }

}
