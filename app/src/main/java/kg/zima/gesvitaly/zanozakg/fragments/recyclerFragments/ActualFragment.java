package kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import kg.zima.gesvitaly.zanozakg.BuildConfig;
import kg.zima.gesvitaly.zanozakg.utils.Triple;

import static com.google.firebase.analytics.FirebaseAnalytics.Event.VIEW_ITEM;

public class ActualFragment extends BaseRecyclerFragment {
    private boolean isFirstLaunch = true;
    protected int loadingType = ACTUAL;

    @Override
    public boolean isChild() {
        return true;
    }

    public void loadNews(final boolean needToRemoveLastElementInList) {
        if (startIndex == 0) {
            ArrayList<Triple<String, Integer, Integer>> queriesTuples = new ArrayList<>();
            queriesTuples.add(new Triple<>("day_news", startIndex, 10));
            queriesTuples.add(new Triple<>("actual", startIndex, 20));
            loadNews(queriesTuples, loadingType, needToRemoveLastElementInList, true);
        }
        else loadNews("actual", loadingType, startIndex, 20, needToRemoveLastElementInList);

        if (!BuildConfig.DEBUG) {
            final Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Новости начиная с " + startIndex);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Экран \"Актуальное\"");
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
        mTracker.setScreenName("Экран 'Актуальное'");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/

        if (savedInstanceState != null) {
            isFirstLaunch = savedInstanceState.getBoolean("isFirstLaunch");
        }
        if (isFirstLaunch || mainList.isEmpty()) {
            isFirstLaunch = false;
            // Для теста
            if (BuildConfig.DEBUG) {
                addTestNews();
            }
            startIndex = 0;
            loadNews(false);
        // 3600000 мс = 1 час
        } else if ((System.currentTimeMillis() - loadedTime) > 3600000) {
            nothingLayout.setVisibility(View.GONE);
            mainProgressBar.setVisibility(View.VISIBLE);
            mainList.clear();
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
