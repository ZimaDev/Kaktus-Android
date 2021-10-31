package kg.zima.gesvitaly.zanozakg.fragments;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;

public abstract class BaseFragment extends Fragment {
    // Меняет цвет стрелки Назад на accent
    @SuppressLint("PrivateResource")
    protected void changeArrowColor(MainActivity mainActivity) {
        ActionBar actionBar = mainActivity.getSupportActionBar();
        final Drawable upArrow;
        if (Build.VERSION.SDK_INT < 21) upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        else upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material, mainActivity.getTheme());
        if (Build.VERSION.SDK_INT < 23) upArrow.setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_ATOP);
        else upArrow.setColorFilter(getResources().getColor(R.color.accent, mainActivity.getTheme()), PorterDuff.Mode.SRC_ATOP);
        if (actionBar != null) actionBar.setHomeAsUpIndicator(upArrow);
    }

    // Меняет кнопку Hamburger на кнопку Назад
    protected void changeDrawerIconToBackArrow(MainActivity mainActivity) {
        ActionBar actionBar = mainActivity.getSupportActionBar();
        mainActivity.getToggle().setDrawerIndicatorEnabled(false);
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        mainActivity.getToggle().setToolbarNavigationClickListener(v -> mainActivity.onBackPressed());
    }

    // Меняет кнопку Назад на кнопку Hamburger
    protected void changeDrawerIconToHamburger(MainActivity mainActivity) {
        ActionBar actionBar = mainActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            mainActivity.getToggle().setDrawerIndicatorEnabled(true);
        }
    }
}
