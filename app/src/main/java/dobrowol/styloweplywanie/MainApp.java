package dobrowol.styloweplywanie;

import android.app.Application;

import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

/**
 * Created by dobrowol on 07.03.18.
 */
@ReportsCrashes(
        httpMethod = HttpSender.Method.PUT,
        reportType = HttpSender.Type.JSON,
        formUri = "http://77.55.238.79:5984/acra-styloweplywanie/_design/acra-storage/_update/report",
        formUriBasicAuthLogin = "dobrowol",
        formUriBasicAuthPassword = "adKR85&$we"
)
public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
