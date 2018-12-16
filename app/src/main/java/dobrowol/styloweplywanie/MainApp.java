package dobrowol.styloweplywanie;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

/**
 * Created by dobrowol on 07.03.18.
 */
public class MainApp extends MultiDexApplication{
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
