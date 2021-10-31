package kg.zima.gesvitaly.zanozakg.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.muddzdev.styleabletoast.StyleableToast;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.BuildConfig;
import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;
import kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter.NewsFragmentAdapter;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.PieceOfArticle;
import kg.zima.gesvitaly.zanozakg.utils.NewsLoader;
import kg.zima.gesvitaly.zanozakg.utils.Utils;
import kg.zima.gesvitaly.zanozakg.utils.ui.StatefulLayoutImpl;

import static com.google.firebase.analytics.FirebaseAnalytics.Event.SHARE;
import static com.google.firebase.analytics.FirebaseAnalytics.Event.VIEW_ITEM;

public class NewsFragment extends BaseFragment {
    private int newsId = 0;
    private ArrayList<PieceOfArticle> piecesOfArticle = new ArrayList<>();
    private StatefulLayoutImpl rootView;
    private ArrayList<WebView> listOfWebViews = new ArrayList<>();
    private ArrayList<YouTubePlayerView> listOfYouTubePlayerViews = new ArrayList<>();
    private ArrayList<YouTubeThumbnailLoader> listOfYouTubes = new ArrayList<>();
    private NewsFragmentAdapter newsFragmentAdapter;
    private String topicName = "";
    private String topicUrl = "";

//    Tracker mTracker;

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public void setPiecesOfArticle(ArrayList<PieceOfArticle> piecesOfArticle) {
        this.piecesOfArticle.clear();
        this.piecesOfArticle.addAll(piecesOfArticle);
        newsFragmentAdapter.notifyDataSetChanged();
        rootView.showContent();
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setTopicUrl(String topicUrl) {
        this.topicUrl = topicUrl;
    }

    public void addWebViewToList(WebView webView) {
        listOfWebViews.add(webView);
    }
    public void addYoutubeToList(YouTubeThumbnailLoader youtube) {
        listOfYouTubes.add(youtube);
    }

    public void addYouTubePlayerViewToList(YouTubePlayerView youTubePlayerView) {
        listOfYouTubePlayerViews.add(youTubePlayerView);
    }

    public void showEmpty() {
        if (piecesOfArticle.isEmpty()) rootView.showEmpty();
        else rootView.showContent();
    }

    public void trackNewsView() {
        if (!BuildConfig.DEBUG) {
            final Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Новость " + newsId + ": " + topicName);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Экран \"Новость\"");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Экран");
            FirebaseAnalytics.getInstance(requireContext()).logEvent(VIEW_ITEM, bundle);
        }
    }

    private void reloadNews() {
        if (!Utils.isInternetAvailable(getActivity().getApplicationContext())) {
            StyleableToast
                .makeText(requireContext(), getString(R.string.no_internet), R.style.StyleableToast)
                .show();
            rootView.showOffline();
        } else if (newsId == 0) {
            StyleableToast
                .makeText(requireContext(), getString(R.string.no_such_article), R.style.StyleableToast)
                .show();
            getActivity().onBackPressed();
        } else {
            rootView.showProgress();
            NewsLoader.loadArticle(newsId, this, getActivity().getApplicationContext());
        }
    }

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    @SuppressLint("PrivateResource")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        /*// Obtain the shared Tracker instance.
        MainApp application = (MainApp) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Экран новости");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/

        MainActivity mainActivity = (MainActivity) getActivity();
        changeDrawerIconToBackArrow(mainActivity);
        changeArrowColor(mainActivity);
        mainActivity.showToolbar(false);
        mainActivity.addToolbarShadow();

        if (savedInstanceState != null) {
            piecesOfArticle = savedInstanceState.getParcelableArrayList("piecesOfArticle");
        }
        rootView = (StatefulLayoutImpl) inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView piecesOfNewsRV = rootView.findViewById(R.id.pieces_of_news_rv);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Regular.ttf");
        newsFragmentAdapter = new NewsFragmentAdapter(piecesOfArticle, font, getContext(), this);
        piecesOfNewsRV.setAdapter(newsFragmentAdapter);

        rootView.getOfflineView().findViewById(R.id.state_button).setOnClickListener(view -> reloadNews());
        rootView.getErrorView().findViewById(R.id.state_button).setOnClickListener(view -> reloadNews());

        /*AdView adView = rootView.findViewById(R.id.ad_view);
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        if (BuildConfig.DEBUG) {
            adRequestBuilder
                    .addTestDevice("FDEDA9ECF5DD81A28D8A2592D4FE6265")
                    .addTestDevice("6AA35D5F405C630E88C2FE694182D20D");
        }
        adView.loadAd(adRequestBuilder.build());*/

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!Utils.isInternetAvailable(getContext())) {
            rootView.showOffline();
            return;
        }
        if (!piecesOfArticle.isEmpty()) return;
        if (newsId == 0) {
            StyleableToast
                .makeText(requireContext(), getString(R.string.no_such_article), R.style.StyleableToast)
                .show();
            getActivity().onBackPressed();
            return;
        }
        rootView.showProgress();
        /*new Handler().postDelayed(() -> {
            if (piecesOfArticle.isEmpty() && newsProgressBar != null)
                newsProgressBar.setVisibility(View.VISIBLE);
        }, 500);*/
        NewsLoader.loadArticle(newsId, this, getActivity().getApplicationContext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("piecesOfArticle", piecesOfArticle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            piecesOfArticle = savedInstanceState.getParcelableArrayList("piecesOfArticle");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem share = menu.findItem(R.id.action_share);
        share.setVisible(true);
        /*// Fetch and store ShareActionProvider
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(share);
        mShareActionProvider.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
                Log.e("Source", source.toString());
                Log.e("Intent", intent.toString());
                return false;
            }
        });*/
        share.setOnMenuItemClickListener(item -> {
            if (topicName.isEmpty() || topicUrl.isEmpty()) {
                StyleableToast
                    .makeText(requireContext(), getString(R.string.wait_for_loading), R.style.StyleableToast)
                    .show();
            } else {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = topicName + "\n" + topicUrl + "\n\nПриложение Kaktus.media https://play.google.com/store/apps/details?id=kg.zima.gesvitaly.zanozakg";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Приложение Kaktus.media");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                if (!BuildConfig.DEBUG) {
                    final Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Новость " + newsId);
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, topicName);
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Поделиться новостью");
                    FirebaseAnalytics.getInstance(requireContext()).logEvent(SHARE, bundle);
                }
            }
            return true;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!listOfYouTubes.isEmpty())
            Stream.of(listOfYouTubes).forEach(YouTubeThumbnailLoader::release);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!listOfWebViews.isEmpty())
            Stream.of(listOfWebViews).forEach(WebView::destroy);
        if (!listOfYouTubePlayerViews.isEmpty())
            Stream.of(listOfYouTubePlayerViews).forEach(YouTubePlayerView::release);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
