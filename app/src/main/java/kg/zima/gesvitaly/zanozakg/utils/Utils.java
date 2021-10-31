package kg.zima.gesvitaly.zanozakg.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class Utils {
    private Utils() {}

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static View createDummyView(Context context, int width, int height) {
        View dummyView = new View(context);
        dummyView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        return dummyView;
    }

    /*
        Remove extra space from Android spannable
     */
    public static SpannableStringBuilder trimSpannable(SpannableStringBuilder spannable) {
        checkNotNull(spannable);
        int trimStart = 0;
        int trimEnd = 0;

        String text = spannable.toString();

        while (text.length() > 0 && text.startsWith("\n")) {
            text = text.substring(1);
            trimStart += 1;
        }

        while (text.length() > 0 && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
            trimEnd += 1;
        }

        return spannable.delete(0, trimStart).delete(spannable.length() - trimEnd, spannable.length());
    }

    public static String getTimePassedString(int date) {
        return (String) DateUtils.getRelativeTimeSpanString((long)date * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

    private static final String SERVICE_ACTION = "android.support.customtabs.action.CustomTabsService";
    private static final String CHROME_PACKAGE = "com.android.chrome";

    public static boolean isChromeCustomTabsSupported(@NonNull final Context context) {
        Intent serviceIntent = new Intent(SERVICE_ACTION);
        serviceIntent.setPackage(CHROME_PACKAGE);
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentServices(serviceIntent, 0);
        return !(resolveInfos == null || resolveInfos.isEmpty());
    }

    public static void hideKeyboard(AppCompatActivity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static Intent getEmailIntent(String email, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        return intent;
    }

    public static void lockOrientation(Activity activity) {
        int currentOrientation = activity.getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    public static void unlockOrientation(Activity activity) {
        if (activity != null) activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    public static Resolution getScreenResolution(Activity activity) {
        int mScreenWidth;
        int mScreenHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Point size = new Point();
            activity.getWindowManager().getDefaultDisplay().getRealSize(size);
            mScreenWidth = size.x;
            mScreenHeight = size.y;
        } else {
            Display display = activity.getWindowManager().getDefaultDisplay();
            mScreenWidth = display.getWidth();
            mScreenHeight = display.getHeight();

            try {
                Method getHeight = Display.class.getMethod("getRawHeight");
                Method getWidth = Display.class.getMethod("getRawWidth");
                mScreenWidth = (Integer) getHeight.invoke(display);
                mScreenHeight = (Integer) getWidth.invoke(display);
            } catch (Exception e) {
                mScreenWidth = display.getWidth();
                mScreenHeight = display.getHeight();
            }
        }
        if (mScreenHeight > mScreenWidth) return new Resolution(mScreenHeight, mScreenWidth);
        return new Resolution(mScreenWidth, mScreenHeight);
    }

    private static boolean findBinary(String binaryName) {
        boolean found = false;
        String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
                "/data/local/xbin/", "/data/local/bin/",
                "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" };
        for (String where : places) {
            if (new File(where + binaryName).exists()) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static boolean isRooted() {
        return findBinary("su");
    }

    public static String getAppVersion(Context context) {
        PackageInfo pInfo;
        String version = "unknown";
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * Дополняет строку другой строкой до заданной длины символами слева
     * @param input Входная строка
     * @param padLength Длина, до которой нужно дополнить
     * @param padChar Символ, которым нужно дополнять
     * @return Дополненная строка
     */
    public static String padLeft(String input, int padLength, char padChar)
    {
        StringBuilder padded = new StringBuilder(input);
        while (padded.length() < padLength)
        {
            padded.insert(0, padChar);
        }
        return padded.toString();
    }

    /**
     * Дополняет строку другой строкой до заданной длины символами справа
     * @param input Входная строка
     * @param padLength Длина, до которой нужно дополнить
     * @return Дополненная строка
     */
    public static String padRight(String input, int padLength, char padChar)
    {
        StringBuilder padded = new StringBuilder(input);
        while (padded.length() < padLength)
        {
            padded.append(padChar);
        }
        return padded.toString();
    }
}
