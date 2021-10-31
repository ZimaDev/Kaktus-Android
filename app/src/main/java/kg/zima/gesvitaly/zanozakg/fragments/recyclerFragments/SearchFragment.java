package kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.UnsupportedEncodingException;

import kg.zima.gesvitaly.zanozakg.BuildConfig;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;

import static com.google.firebase.analytics.FirebaseAnalytics.Event.VIEW_ITEM;

public class SearchFragment extends BaseRecyclerFragment {
    protected int loadingType = SEARCH;
    private static final String SEARCH_ARG = "search";
    private String search = "";

    @Override
    public boolean isChild() {
        return false;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public static SearchFragment newInstance(String search) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_ARG, search);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search = getArguments().getString(SEARCH_ARG);
        loadNews(true);
    }

    @Override
    public void loadNews(boolean needToRemoveLastElementInList) {
        search(startIndex, 20, search, needToRemoveLastElementInList);

        if (!BuildConfig.DEBUG) {
            final Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Новости начиная с " + startIndex);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Экран \"Поиск\"");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Экран");
            FirebaseAnalytics.getInstance(requireContext()).logEvent(VIEW_ITEM, bundle);
        }
    }

    private void search(int start, int limit, String search, final boolean needToRemoveLastElementInList) {
        loadingType = SEARCH;
        this.search = search;
        String query = buildSearchQuery(start, limit, search);
        loadNews(query, needToRemoveLastElementInList);
    }

    private String buildSearchQuery(int start, int limit, String search) {
        String base64;
        try {
            byte[] data = search.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.DEFAULT).trim();
        } catch (UnsupportedEncodingException e) {
            base64 = "";
        }
        return "query0:start:" + start + "#limit:" + limit + "#search:" + base64;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*// Obtain the shared Tracker instance.
        MainApp application = (MainApp) getActivity().getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Экран 'Поиск'");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/

        ((MainActivity) getActivity()).addToolbarShadow();

        if (savedInstanceState != null) {
            search = savedInstanceState.getString("search");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        changeDrawerIconToBackArrow(mainActivity);
        changeArrowColor(mainActivity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("search", search);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Поиск: " + search);
    }
}