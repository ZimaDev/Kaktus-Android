package kg.zima.gesvitaly.zanozakg;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.UUID;

import cat.ereza.customactivityoncrash.config.CaocConfig;

public class MainApp extends Application {
    private String uniqueID;

    public String getUUID() {
        return uniqueID;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG);

        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM)
                .minTimeBetweenCrashesMs(12000)
                .apply();

        SharedPreferences sharedPreferences = getSharedPreferences("Zanoza", MODE_PRIVATE);
        String uniqueID = sharedPreferences.getString("UUID", null);
        if (uniqueID == null || uniqueID.isEmpty()) {
            uniqueID = UUID.randomUUID().toString();
            sharedPreferences
                    .edit()
                    .putString("UUID", uniqueID)
                    .apply();
        }
        this.uniqueID = uniqueID;
    }
}

