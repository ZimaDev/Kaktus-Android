package kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import kg.zima.gesvitaly.zanozakg.BuildConfig;

import static com.google.firebase.analytics.FirebaseAnalytics.Event.VIEW_ITEM;

public class PopularFragment extends BaseRecyclerFragment {
    private boolean isFirstLaunch = true;
    protected int loadingType = POPULAR;

    @Override
    public boolean isChild() {
        return true;
    }

    public void loadNews(final boolean needToRemoveLastElementInList) {
        loadNews("popular", loadingType, startIndex, 20, needToRemoveLastElementInList);

        if (!BuildConfig.DEBUG) {
            final Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Новости начиная с " + startIndex);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Экран \"Популярное\"");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Экран");
            FirebaseAnalytics.getInstance(requireContext()).logEvent(VIEW_ITEM, bundle);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        /*// Obtain the shared Tracker instance.
        MainApp application = (MainApp) getActivity().getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Экран 'Популярное'");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/

        if (savedInstanceState != null) {
            isFirstLaunch = savedInstanceState.getBoolean("isFirstLaunch");
        }
        if (isFirstLaunch || mainList.isEmpty()) {
            isFirstLaunch = false;
            startIndex = 0;
            loadNews(false);
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFirstLaunch", isFirstLaunch);
    }
}
