package kg.zima.gesvitaly.zanozakg.activities;

import android.animation.AnimatorInflater;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.fragments.AboutFragment;
import kg.zima.gesvitaly.zanozakg.fragments.TabsFragment;
import kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments.SearchFragment;
import kg.zima.gesvitaly.zanozakg.utils.FabOffsetter;
import kg.zima.gesvitaly.zanozakg.utils.Utils;
import kg.zima.gesvitaly.zanozakg.utils.ZanozaUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnSearchViewListener {
    private boolean doubleBackToExitPressedOnce = false;
    private NavigationView navigationView;
    private AppBarLayout mainAppBarLayout;
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private BaseMaterialSearchView searchView;
    private EditText mETSearchText;
    private OnSearchViewListener mListener;
    ////private View shadowView;

    private String search = "";
    public String getSearch() {
        return search;
    }
    public void setSearch(String search) {
        this.search = search;
    }

    public void hideShadowView() {
        ////shadowView.setVisibility(View.GONE);
    }

    public ActionBarDrawerToggle getToggle() {
        return toggle;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    public void checkNavigationItem(int itemId) {
        navigationView.setCheckedItem(itemId);
    }
    
    public void uncheckAllNavItems() {
        MenuItem menuItem = navigationView.getMenu().getItem(0);
        menuItem.setChecked(true);
        menuItem.setChecked(false);
    }

    public void removeToolbarShadow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mainAppBarLayout.setElevation(0);
        } else {
            //ViewCompat.setElevation(mainAppBarLayout, 0);
            ////shadowView.setVisibility(View.GONE);
        }
    }

    public void addToolbarShadow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mainAppBarLayout.setStateListAnimator(AnimatorInflater.loadStateListAnimator(this, R.animator.toolbar_elevation));
        } else {
            ////shadowView.setVisibility(View.VISIBLE);
        }
    }

    public void bindFabToToolbar(FloatingActionButton fab, CoordinatorLayout rootLayout) {
        FabOffsetter fabOffsetter = new FabOffsetter(rootLayout, fab);
        mainAppBarLayout.addOnOffsetChangedListener(fabOffsetter);
    }

    public void showToolbar(boolean animate) {
        mainAppBarLayout.setExpanded(true, animate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Чтобы показывались векторные изображения на Андроиде 4.x
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        setContentView(R.layout.activity_main);
        mainAppBarLayout = findViewById(R.id.main_app_bar_layout);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        searchView = findViewById(R.id.main_search_view);
        searchView.setOnSearchViewListener(this);
        /*mETSearchText = (EditText) findViewById(com.claudiodegio.msv.R.id.ed_search_text);
        mETSearchText.setOnEditorActionListener(this);*/

        ////shadowView = findViewById(R.id.shadow_view);

        frameLayout = findViewById(R.id.flContent);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment tabsFragment = fragmentManager.findFragmentByTag(TabsFragment.class.getName());
        // TODO: wrong logic, redo
        if (tabsFragment == null) {
            tabsFragment = new TabsFragment();
            // При повороте экрана не разрушает фрагмент
            tabsFragment.setRetainInstance(true);
            fragmentManager.beginTransaction().replace(R.id.flContent, tabsFragment, TabsFragment.class.getName()).commit();
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flContent);
            if (currentFragment == null) {
                fragmentManager.beginTransaction().replace(R.id.flContent, tabsFragment, TabsFragment.class.getName()).commit();
            }
        }
        removeToolbarShadow();
        handleLink(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleLink(intent);
    }

    private boolean handleLink(Intent appLinkIntent) {
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if (appLinkData != null) {
            ZanozaUtils.handleLink(appLinkData.toString(), this);
            return true;
        }
        return false;
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

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Zanoza");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (searchView.isShown()) {
            searchView.closeSearch();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
            this.getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE); // Показать экранные меню, назад и домой (если они есть)
            if (this.getSupportActionBar() != null) this.getSupportActionBar().show(); // Показать тулбар
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Показать статус-бар
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            ////if (Build.VERSION.SDK_INT < 21) shadowView.setVisibility(View.VISIBLE);
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            StyleableToast
                .makeText(this, getString(R.string.one_more_to_exit), R.style.StyleableToast)
                .show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        } else {
            // Если в стеке нет фрагмента и кнопка "Назад" нажата 2 раза
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        searchView.setMenuItem(menu.findItem(R.id.action_search));
        menu.findItem(R.id.action_share).setVisible(false);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class fragmentClass = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            /*case R.id.nav_actual: {
                fragmentClass = ActualFragment.class;
                break;
            }
            case R.id.nav_popular: {
                fragmentClass = PopularFragment.class;
                break;
            }*/
            case R.id.nav_tabs: {
                fragmentClass = TabsFragment.class;
                break;
            }
            /*case R.id.nav_call_center: {
                fragmentClass = CallCenterFragment.class;
                break;
            }*/
            case R.id.nav_about: {
                fragmentClass = AboutFragment.class;
                break;
            }
            default: {
                break;
            }
        }

        Class finalFragmentClass = fragmentClass;
        drawer.closeDrawer(GravityCompat.START);
        new Handler().postDelayed(() -> setFragment(finalFragmentClass, item), 300);
        return true;
    }

    private void setFragment(Class fragmentClass, MenuItem item) {
        Fragment fragment;
        if (fragmentClass != null) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE); // Показать экранные меню, назад и домой (если они есть)
            if (this.getSupportActionBar() != null) this.getSupportActionBar().show(); // Показать тулбар
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Показать статус-бар

            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.flContent);

            // Если на экране уже этот фрагмент, то ничего не делать
            if (fragmentClass != currentFragment.getClass()) {
                String className = fragmentClass.getName();

                fragment = fragmentManager.findFragmentByTag(className);
                if (fragment == null) {
                    /*if (fragmentClass == ActualFragment.class) fragment = new ActualFragment();
                    else if (fragmentClass == PopularFragment.class) fragment = new PopularFragment();*/
                    if (fragmentClass == TabsFragment.class) fragment = new TabsFragment();
                    //else if (fragmentClass == CallCenterFragment.class) fragment = new CallCenterFragment();
                    else if (fragmentClass == AboutFragment.class) fragment = new AboutFragment();
                    // Передан неверный класс
                    else return;
                    fragment.setRetainInstance(true);
                }
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment, className).addToBackStack(null).commit();
                // Выделяем выбранный пункт меню в шторке
                item.setChecked(true);
            }
        }
        else {
            MenuItem menuItem = navigationView.getMenu().getItem(0);
            menuItem.setChecked(true);
        }
    }

    @Override
    public void onSearchViewShown() {
    }

    @Override
    public void onSearchViewClosed() {
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!Utils.isInternetAvailable(this)) {
            StyleableToast
                .makeText(this, getString(R.string.no_internet), R.style.StyleableToast)
                .show();
            return false;
        }

        setTitle("Поиск: " + query);
        FragmentManager fragmentManager = getSupportFragmentManager();
        SearchFragment searchFragment = SearchFragment.newInstance(query);
        searchFragment.setRetainInstance(true);
        fragmentManager
            .beginTransaction()
            .replace(R.id.flContent, searchFragment, SearchFragment.class.getName())
            .addToBackStack(null)
            .commit();
        return false;
    }

    @Override
    public void onQueryTextChange(String newText) {

    }

    private void handleSubmitQuery(){
        if (mListener != null) {
            mListener.onQueryTextSubmit(mETSearchText.getText().toString());
        }
    }
}
