package dobrowol.styloweplywanie;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.atomic.AtomicInteger;

import static android.content.ContentValues.TAG;

/**
 * Created by dobrowol on 08.03.17.
 */

public class StudentInstanceIdService extends FirebaseInstanceIdService {
    private AtomicInteger msgId;
    private String SENDER_ID = "236904290998";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        FirebaseMessaging fm = FirebaseMessaging.getInstance();

        fm.send(new RemoteMessage.Builder(SENDER_ID + "@gcm.googleapis.com")
                .setMessageId(Integer.toString(msgId.incrementAndGet()))
                .addData("my_message", "refreshedToken")
                .addData("my_action","SAY_HELLO")
                .build());


    }
}
