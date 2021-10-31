package kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Arrays;

import kg.zima.gesvitaly.zanozakg.BuildConfig;

import static com.google.firebase.analytics.FirebaseAnalytics.Event.VIEW_ITEM;

public class TagsFragment extends BaseRecyclerFragment {
    private boolean isFirstLaunch = true;
    protected int loadingType = TAGS;

    @Override
    public boolean isChild() {
        return true;
    }

    public static TagsFragment newInstance(int[] tags) {
        TagsFragment fragment = new TagsFragment();
        Bundle args = new Bundle();
        args.putIntArray("tags", tags);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loadNews(boolean needToRemoveLastElementInList) {
        int[] tags = getArguments().getIntArray("tags");
        loadNews(startIndex, 20, tags, needToRemoveLastElementInList);

        String tagsString = Arrays.toString(tags);
        tagsString = tagsString.substring(1, tagsString.length() - 1);

        if (!BuildConfig.DEBUG) {
            final Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Новости начиная с " + startIndex);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Экран \"Теги: " + tagsString + "\"");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Экран");
            FirebaseAnalytics.getInstance(requireContext()).logEvent(VIEW_ITEM, bundle);
        }
    }

    private void loadNews(int start, int limit, int[] tags, final boolean needToRemoveLastElementInList) {
        String query = buildQueryWithLabels(tags, start, limit);
        loadNews(query, needToRemoveLastElementInList);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

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
