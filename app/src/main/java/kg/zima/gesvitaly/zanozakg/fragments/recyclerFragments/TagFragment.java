package kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import kg.zima.gesvitaly.zanozakg.BuildConfig;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;
import kg.zima.gesvitaly.zanozakg.models.NewsTag;

import static com.google.firebase.analytics.FirebaseAnalytics.Event.VIEW_ITEM;

public class TagFragment extends BaseRecyclerFragment {
    private NewsTag tag;
    protected int loadingType = TAG;

    @Override
    public boolean isChild() {
        return false;
    }

    public void setTag(NewsTag tag) {
        this.tag = tag;
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
        clearListOfNews();
        loadNews(true);
    }

    @Override
    public void loadNews(boolean needToRemoveLastElementInList) {
        loadNews(startIndex, 20, tag, needToRemoveLastElementInList);

        if (!BuildConfig.DEBUG) {
            final Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Новости начиная с " + startIndex);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Экран \"Теги\"");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Экран");
            FirebaseAnalytics.getInstance(requireContext()).logEvent(VIEW_ITEM, bundle);
        }
    }

    public void loadNews(int start, int limit, NewsTag tag, final boolean needToRemoveLastElementInList) {
        loadingType = TAG;
        this.tag = tag;
        String query = buildQueryWithLabel(tag.getId(), start, limit);
        loadNews(query, needToRemoveLastElementInList);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            tag = savedInstanceState.getParcelable("tag");
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
        outState.putParcelable("tag", tag);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Тег: " + tag.getName());
    }
}
