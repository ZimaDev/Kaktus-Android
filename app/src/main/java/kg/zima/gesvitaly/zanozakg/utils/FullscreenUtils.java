package kg.zima.gesvitaly.zanozakg.utils;

import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import static android.content.ContentValues.TAG;

public class FullscreenUtils {
    public static boolean fullScreen(Context context, ViewGroup fullscreenImageWrapper) {
        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = ((AppCompatActivity) context).getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)

        if (Build.VERSION.SDK_INT >= 19) {
            boolean isImmersiveModeEnabled=
                    ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
            if (isImmersiveModeEnabled) {
                Log.i(TAG, "Turning immersive mode mode off. ");
            } else {
                Log.i(TAG, "Turning immersive mode mode on.");
            }
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 19) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        ((AppCompatActivity) context).getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)

        ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setShowHideAnimationEnabled(true);
            if (actionBar.isShowing()) {
                ((AppCompatActivity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Спрятать статус-бар
                ((AppCompatActivity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); // Перекрыть SoftKeyBar
                actionBar.hide(); // Спрятать тулбар

                // Ставит ImageView по центру
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(fullscreenImageWrapper.getLayoutParams());

                lp.setMargins(0, 0, 0, 0);
                fullscreenImageWrapper.setLayoutParams(lp);

                /*// Нужно, чтобы при анимации тулбара ImageView не скакала туда-сюда
                FrameLayout frameLayout = ((MainActivity)getActivity()).getFrameLayout();
                CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams)(frameLayout.getLayoutParams());
                clp.setBehavior(null);
                frameLayout.setLayoutParams(clp);*/

            }
            else {
                ((AppCompatActivity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Показать статус-бар
                ((AppCompatActivity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                actionBar.show(); // Показать тулбар

                // Ставит ImageView по центру
                int topMargin = -(getStatusBarHeight(context) + getActionBarHeight(context)) + getSoftButtonsBarHeight(context);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(fullscreenImageWrapper.getLayoutParams());

                lp.setMargins(0, topMargin, 0, 0);
                fullscreenImageWrapper.setLayoutParams(lp);

                /*// После отображения тулбара возвращает старое поведение
                FrameLayout frameLayout = ((MainActivity)getActivity()).getFrameLayout();
                CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams)(frameLayout.getLayoutParams());
                clp.setBehavior(new AppBarLayout.ScrollingViewBehavior());
                frameLayout.setLayoutParams(clp);*/
            }
        }
        return true;
    }

    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static int getActionBarHeight(Context context) {
        // Calculate ActionBar height
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            return TypedValue.complexToDimensionPixelSize(tv.data,context.getResources().getDisplayMetrics());
        }
        return 0;
    }

    private static int getSoftButtonsBarHeight(Context context) {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }
}
