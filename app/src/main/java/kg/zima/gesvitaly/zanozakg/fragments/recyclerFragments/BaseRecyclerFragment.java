package kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.muddzdev.styleabletoast.StyleableToast;

import org.jsoup.internal.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import kg.zima.gesvitaly.zanozakg.BuildConfig;
import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;
import kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter.BaseRecyclerFragmentAdapter;
import kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter.LoadMoreActualVH;
import kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter.LoadMorePopularVH;
import kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter.LoadMoreTagVH;
import kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter.LoadMoreZanozaTVVH;
import kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter.SearchMoreVH;
import kg.zima.gesvitaly.zanozakg.fragments.BaseFragment;
import kg.zima.gesvitaly.zanozakg.models.NewsPreview;
import kg.zima.gesvitaly.zanozakg.models.NewsTag;
import kg.zima.gesvitaly.zanozakg.models.exchangeRates.ExchangeRates;
import kg.zima.gesvitaly.zanozakg.models.exchangeRates.ExchangeRatesPair;
import kg.zima.gesvitaly.zanozakg.models.exchangeRates.ExchangeRatesVM;
import kg.zima.gesvitaly.zanozakg.restApi.kursRestApi.KursApiClient;
import kg.zima.gesvitaly.zanozakg.restApi.kursRestApi.KursApiService;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.ResponseForLoadNewsPreviewsList;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.ZanozaApiClient;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.ZanozaApiService;
import kg.zima.gesvitaly.zanozakg.utils.Triple;
import kg.zima.gesvitaly.zanozakg.utils.Utils;
import kg.zima.gesvitaly.zanozakg.utils.ui.SeparatorDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.firebase.analytics.FirebaseAnalytics.Event.VIEW_ITEM;

public abstract class BaseRecyclerFragment extends BaseFragment {
    // this variable will use to show progressbar below to recyclerview in your xml file  when you scroll your recyclerview list
    protected boolean isLoading = false;
    protected boolean hasMoreNews = true;
    //region Views
    protected LinearLayout nothingLayout;
    protected TextView nothingTV;
    protected FloatingActionButton fab;
    protected BaseRecyclerFragmentAdapter baseRecyclerFragmentAdapter;
    protected MaterialRefreshLayout refreshLayout;
    protected CustomLinearLayoutManager layoutManager;
    protected ProgressBar mainProgressBar;
    protected RecyclerView newsListRecyclerView;
    protected View rootView;
    //endregion

    protected ArrayList<Object> mainList = new ArrayList<>();
    protected ArrayList<NewsTag> mainNewsTagsList = new ArrayList<>();
    protected long loadedTime = 0;

    protected int startIndex = 0;

    protected static final int ACTUAL = 0;
    protected static final int SEARCH = 1;
    protected static final int POPULAR = 2;
    protected static final int TAG = 3;
    protected static final int TAGS = 4;
    protected static final int ZANOZA_TV = 7312;
    protected int loadingType;

    // The Native Express ad height.
    private static final int NATIVE_EXPRESS_AD_HEIGHT = 150;

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void clearListOfNews() {
        mainList.clear();
    }

    protected void addTestNews() {
        // Трансляция
//         int testTagId = 347249;
        // Список li + крутилка с картинками
//         int testTagId = 348551;
        // Вставка с Facebook
//        int testTagId = 347649;
        // Жирный текст, ссылки
//        int testTagId = 347730;
        // Голосовалка
//        int testTagId = 354258;
        // Популярные политики
//        int testTagId = 335926;
        // Вставка с playbuzz
//        int testTagId = 349721;
        // Тестовая новость
//        int testTagId = 323941;
        // Крутилка картинок (много)
//        int testTagId = 350094;
        // Крутилка картинок (без автора)
//        int testTagId = 350040;
        // Крутилка картинок (с автором)
//        int testTagId = 349807;
        // Комментарии
//        int testTagId = 350202;
        // Цитаты
//        int testTagId = 347540;
        // Врез из нескольких абзацев
//        int testTagId = 351075;
        // Вложенные цитаты в комментариях
//        int testTagId = 350851;
        // Инфографика
//        int testTagId = 349116;
        // Видео (загруженное)
//        int testTagId = 351401;
        // Видео (с Youtube)
//        int testTagId = 351417;
        // Инфографика картинкой
//        int testTagId = 351503;
        // Гифка
//        int testTagId = 351135;
        // Твиттер
//        int testTagId = 353760;
        // Facebook
//        int testTagId = 353802;
        // Цитата
//        int testTagId = 355859;
        // Много гифок
//        int testTagId = 357020;
        // Крутилки внутри статьи
//        int testTagId = 357053;
//        int testTagId = 354662;
        // Много картинок
//        int testTagId = 357031;
        // Карточки
//        int testTagId = 359539;
        // Спойлер
        int testTagId = 356850;
        // Вертикальное видео с Facebook
//        int testTagId = 363940;
        int testTagId0 = 350240;
        int testTagDate = 1440758679;
        int testTagDate0 = 1440738679;
        String testTagTitle = "Какая-то очень интересная и познавательная новость. Читать её мы, конечно же, не будем";
        String testTagImg = "http://data.kaktus.media/image/medium/2015-08-28_16-40-42_614934.jpg";
        ArrayList<Integer> testTagsIds = new ArrayList<>();
        testTagsIds.add(669);
        testTagsIds.add(6878);
        mainNewsTagsList.add(new NewsTag(669, 10, "Николай Пихота"));
        mainNewsTagsList.add(new NewsTag(6878, 2, "Мануалы"));
        mainList.addAll(Arrays.asList(
                new NewsPreview(testTagId, testTagDate, testTagTitle, testTagImg, testTagsIds, 666, 666),
                new NewsPreview(testTagId0, testTagDate0, testTagTitle, "", testTagsIds, 666, 666)
        ));
    }

    private class CustomLinearLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public CustomLinearLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }
        @Override
        public boolean canScrollVertically() {
            return isScrollEnabled && super.canScrollVertically();
        }
    }

    public void hideFab() {
        if (fab != null && fab.isShown())
            fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(FloatingActionButton fab) {
                    super.onHidden(fab);
                    // В отличии от View.GONE, изменение положения View возможно, когда оно невидимо
                    fab.setVisibility(View.INVISIBLE);
                }
            });
    }

    public void showFab() {
        if (layoutManager != null && fab != null
                && !(layoutManager.findFirstCompletelyVisibleItemPosition() < 2)
                && !(layoutManager.findLastCompletelyVisibleItemPosition() == mainList.size() - 1)) fab.show();
    }

    public void trackNewsListScreenView() {
        if (!BuildConfig.DEBUG) {
            final Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Новости начиная с " + startIndex);
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Экран \"Все новости\"");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Экран");
            FirebaseAnalytics.getInstance(requireContext()).logEvent(VIEW_ITEM, bundle);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        newsListRecyclerView = rootView.findViewById(R.id.news_list_recycler_view);
        layoutManager = new CustomLinearLayoutManager(getContext());
        nothingLayout = rootView.findViewById(R.id.nothing_layout);
        nothingTV = rootView.findViewById(R.id.nothing_tv);
        fab = rootView.findViewById(R.id.fab);
        refreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        final TextView refreshClickableTV = rootView.findViewById(R.id.refresh_clickable_tv);
        mainProgressBar = rootView.findViewById(R.id.main_progress_bar);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SegoeUISemilight.ttf");
        Typeface iconFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome.ttf");
        Typeface kursFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Comfortaa-Regular.ttf");
        Typeface kursTitleFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Comfortaa-Bold.ttf");

        newsListRecyclerView.addItemDecoration(new SeparatorDecoration(getContext(), ContextCompat.getColor(getContext(), R.color.materialGrey200), 1));
        newsListRecyclerView.setItemAnimator(null);

        baseRecyclerFragmentAdapter = new BaseRecyclerFragmentAdapter(mainList, mainNewsTagsList, getActivity(), font, iconFont, kursFont, kursTitleFont, isChild(), this);
        newsListRecyclerView.setAdapter(baseRecyclerFragmentAdapter);

        fab.setOnClickListener(view -> {
            layoutManager.scrollToPosition(0);
            ((MainActivity) getActivity()).showToolbar(false);
        });

        /*CoordinatorLayout rootLayout = (CoordinatorLayout) rootView.findViewById(R.id.root_layout);
        ((MainActivity) getActivity()).bindFabToToolbar(fab, rootLayout);*/

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                if (!Utils.isInternetAvailable(requireContext())) {
                    refreshLayout.finishRefresh();
                    Utils.unlockOrientation(getActivity());
                    layoutManager.setScrollEnabled(true);
                    StyleableToast
                        .makeText(requireContext(), getString(R.string.no_internet), R.style.StyleableToast)
                        .show();
                    showNothingTVifEmpty(getString(R.string.no_internet));
                    return;
                }

                Utils.lockOrientation(getActivity());

                nothingLayout.setVisibility(View.GONE);
                mainList.clear();
                // Для теста
                if (BuildConfig.DEBUG && BaseRecyclerFragment.this.getClass() == ActualFragment.class) {
                    addTestNews();
                }
                startIndex = 0;
                loadNews(false);
            }
        });

        refreshClickableTV.setOnClickListener(view -> {
            nothingLayout.setVisibility(View.GONE);
            mainProgressBar.setVisibility(View.VISIBLE);
            loadNews(false);
        });

        newsListRecyclerView.setLayoutManager(layoutManager);
        newsListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                /*//To check if  recycler is on top
                if (layoutManager.findFirstCompletelyVisibleItemPosition() < 2 || layoutManager.findLastCompletelyVisibleItemPosition() == mainList.size() - 1) {
                    fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                        @Override
                        public void onHidden(FloatingActionButton fab) {
                            super.onHidden(fab);
                            // В отличии от View.GONE, изменение положения View возможно, когда оно невидимо
                            fab.setVisibility(View.INVISIBLE);
                        }
                    });
                } else fab.show();*/

                int viewItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (dy > 0 && !isLoading && hasMoreNews && viewItemCount <= lastVisibleItem + 1) {
                    if (Utils.isInternetAvailable(getContext())) {
                        isLoading = true;
                        startIndex += 20;
                        if (loadingType == ACTUAL) mainList.add(LoadMoreActualVH.class);
                        else if (loadingType == POPULAR) mainList.add(LoadMorePopularVH.class);
                        else if (loadingType == SEARCH) mainList.add(SearchMoreVH.class);
                        else if (loadingType == TAG) mainList.add(LoadMoreTagVH.class);
                        else if (loadingType == ZANOZA_TV) mainList.add(LoadMoreZanozaTVVH.class);
                        // Нельзя запустить этот метод непосредственно в onScrolled. Выдает предупреждение:
                        // Cannot call this method in a scroll callback. Scroll callbacks might be run during
                        // a measure & layout pass where you cannot change the RecyclerView data. Any method call
                        // that might change the structure of the RecyclerView or the adapter contents should be postponed to the next frame.
                        recyclerView.post(() -> baseRecyclerFragmentAdapter.notifyItemInserted(mainList.size() - 1));
                    } else {
                        StyleableToast
                            .makeText(requireContext(), getString(R.string.no_internet), R.style.StyleableToast)
                            .show();
                    }
                }
            }
        });

        if (!Utils.isInternetAvailable(getContext())) {
            showNothingTVifEmpty("Нет подключения к интернету");
        }

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideFab();
    }

    public abstract boolean isChild();

    public abstract void loadNews(final boolean needToRemoveLastElementInList);

    protected void loadNews(String category, int loadingType, int start, int limit, final boolean needToRemoveLastElementInList) {
        this.loadingType = loadingType;
        String query = buildStandardQuery(category, start, limit);
        loadNews(query, needToRemoveLastElementInList);
    }

    protected void loadNews(ArrayList<Triple<String, Integer, Integer>> queriesTuples, int loadingType, final boolean needToRemoveLastElementInList, final boolean needToLoadExchangesRates) {
        this.loadingType = loadingType;
        String query = buildComplexQuery(queriesTuples);
        loadNews(query, needToRemoveLastElementInList, needToLoadExchangesRates);
    }

    public void loadZanozaTV(int start, int limit, final boolean needToRemoveLastElementInList) {
        loadingType = ZANOZA_TV;
        String query = buildQueryWithLabel(ZANOZA_TV, start, limit);
        loadNews(query, needToRemoveLastElementInList);
    }

    private String buildStandardQuery(String category, int start, int limit) {
        return "query0:" + category + "#start:" + start + "#limit:" + limit;
    }

    protected String buildQueryWithLabel(int tag, int start, int limit) {
        return "query0:" + "lable:" + tag + "#start:" + start + "#limit:" + limit;
    }

    protected String buildQueryWithLabels(int[] tags, int start, int limit) {
        StringBuilder tagsString = new StringBuilder();
        for (int tagId : tags) {
            tagsString.append(tagId).append("|");
        }
        tagsString = new StringBuilder(tagsString.substring(0, tagsString.length() - 1));
        return "query0:" + "lable:" + tagsString + "#start:" + start + "#limit:" + limit;
    }

    private String buildComplexQuery(ArrayList<Triple<String, Integer, Integer>> queriesTuples) {
        ArrayList<String> queries = new ArrayList<>();
        for (int i = 0; i < queriesTuples.size(); i++) {
            Triple<String, Integer, Integer> queryTuple = queriesTuples.get(i);
            String query = "query" + i + ":" + queryTuple.first + "#start:" + queryTuple.second + "#limit:" + queryTuple.third;
            queries.add(query);
        }
        return StringUtil.join(queries, "&");
    }

    public void showNothingTVifEmpty(String text) {
        if (mainList.isEmpty()) {
            nothingLayout.setVisibility(View.VISIBLE);
            nothingTV.setText(text);
        }
        else nothingLayout.setVisibility(View.GONE);
    }

    public void hideNothingLayout() {
        if (nothingLayout != null) nothingLayout.setVisibility(View.GONE);
    }

    protected void loadNews(String query, final boolean needToRemoveLastElementInList) {
        loadNews(query, needToRemoveLastElementInList, false);
    }

    protected void loadNews(String query, final boolean needToRemoveLastElementInList, boolean needToLoadExchangeRates) {
        if (getContext() != null && !Utils.isInternetAvailable(getContext())) {
            if (mainProgressBar != null) mainProgressBar.setVisibility(View.GONE);
            if (rootView.isShown()) {
                StyleableToast
                    .makeText(requireContext(), getString(R.string.no_internet), R.style.StyleableToast)
                    .show();
            }
            showNothingTVifEmpty(getString(R.string.no_internet));
            return;
        }

        hasMoreNews = true;
        hideNothingLayout();

        ZanozaApiService zanozaApiService = ZanozaApiClient.getClient().create(ZanozaApiService.class);
        Call<ResponseForLoadNewsPreviewsList> call = zanozaApiService.loadNewsPreviewsList(query);

        call.enqueue(new Callback<ResponseForLoadNewsPreviewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseForLoadNewsPreviewsList> call, @NonNull Response<ResponseForLoadNewsPreviewsList> response) {
                try {
                    mainProgressBar.setVisibility(View.GONE);
                    if ("false".equals(response.body().getSuccess())) {
                        StyleableToast
                            .makeText(requireContext(), getString(R.string.server_error), R.style.StyleableToast)
                            .show();
                        new Handler().postDelayed(() -> showNothingTVifEmpty(getString(R.string.server_error_placeholder)), 500);
                        isLoading = false;
                        refreshLayout.finishRefresh();
                        Utils.unlockOrientation(getActivity());
                        return;
                    }
                    ArrayList<NewsPreview> newsPreviews = new ArrayList<>();
                    ArrayList<NewsPreview> query0 = response.body().getTopics().getQuery0();
                    if (query0 != null && !query0.isEmpty()) newsPreviews.addAll(query0);
                    ArrayList<NewsPreview> query1 = response.body().getTopics().getQuery1();
                    if (query1 != null && !query1.isEmpty()) newsPreviews.addAll(query1);
                    // Если список закончился
                    if ((query0 == null || query0.isEmpty()) && (query1 == null || query1.isEmpty())) {
                        if (!mainList.isEmpty()) {
                            if (needToRemoveLastElementInList) mainList.remove(mainList.size() - 1);
                            baseRecyclerFragmentAdapter.notifyDataSetChanged();
                            hasMoreNews = false;
                            StyleableToast
                                .makeText(requireContext(), getString(R.string.nothing_more), R.style.StyleableToast)
                                .show();
                        } else {
                            new Handler().postDelayed(() -> showNothingTVifEmpty(getString(R.string.nothing_found)), 500);
                        }

                    } else {
                        ArrayList<NewsTag> newsTags = response.body().getNewsTags();
                        // Прохожу по полученным тегам, если такого тега нет, то добавляю в список
//                        long time = System.currentTimeMillis();
                        for (int i = 0; i < newsTags.size(); i++) {
                            NewsTag newsTag = newsTags.get(i);
                            if (!mainNewsTagsList.contains(newsTag)) {
                                mainNewsTagsList.add(newsTag);
                            }
                        }
//                        time = System.currentTimeMillis() - time;
//                        Log.e("Run time", String.valueOf(time));

                        // Если в списке лежит Progress Bar, то перед добавлением новостей его надо удалить
                        if (!mainList.isEmpty() && needToRemoveLastElementInList) {
                            mainList.remove(mainList.size() - 1);
                            baseRecyclerFragmentAdapter.notifyItemRemoved(mainList.size() - 1);
                        }
                        if (!newsPreviews.isEmpty()) {
                            mainList.addAll(newsPreviews);
                            loadedTime = System.currentTimeMillis();

                            baseRecyclerFragmentAdapter.notifyDataSetChanged();
                        }
                    }
                    isLoading = false;
                    refreshLayout.finishRefresh();

                    if (needToLoadExchangeRates) loadExchangeRates();

                } catch (Exception e) {
                    Context context = getContext();
                    if (context != null) {
                        StyleableToast
                            .makeText(requireContext(), getString(R.string.nothing_found), R.style.StyleableToast)
                            .show();
                    }
                    new Handler().postDelayed(() -> showNothingTVifEmpty(getString(R.string.nothing_found)), 500);

                } finally {
                    Utils.unlockOrientation(getActivity());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseForLoadNewsPreviewsList> call, @NonNull Throwable t) {
                new Handler().postDelayed(() -> {
                    mainProgressBar.setVisibility(View.GONE);
                    if (getContext() != null) {
                        StyleableToast
                            .makeText(requireContext(), getString(R.string.troubles_connecting_server), R.style.StyleableToast)
                            .show();
                    }
                    showNothingTVifEmpty(getString(R.string.troubles_connecting_server_placeholder));
                    fab.hide();
                    if (!mainList.isEmpty() && needToRemoveLastElementInList) {
                        mainList.remove(mainList.size() - 1);
                        baseRecyclerFragmentAdapter.notifyItemRemoved(mainList.size() - 1);
                    }
                    isLoading = false;
                    refreshLayout.finishRefresh();
                    Utils.unlockOrientation(getActivity());
                }, 500);

            }
        });
    }

    private void loadExchangeRates() {
        KursApiService kursApiService = KursApiClient.getClient().create(KursApiService.class);
        Call<ExchangeRatesPair> call = kursApiService.loadExchangeRates();

        call.enqueue(new Callback<ExchangeRatesPair>() {
            @Override
            public void onResponse(@NonNull Call<ExchangeRatesPair> call, @NonNull Response<ExchangeRatesPair> response) {
                if (response.body() == null) return;
                ExchangeRates banksRates = response.body().getBanks();
                ExchangeRates nbkrRates = response.body().getNbkr();
                ExchangeRatesVM exchangeRatesVM = new ExchangeRatesVM.Builder()
                        .usdNbkr(round(nbkrRates.getUsd().getNewRate().getSell(), 2))
                        .eurNbkr(round(nbkrRates.getEur().getNewRate().getSell(), 2))
                        .rubNbkr(round(nbkrRates.getRub().getNewRate().getSell(), 3))
                        .kztNbkr(round(nbkrRates.getKzt().getNewRate().getSell(), 3))
                        .usdBanksSell(round(banksRates.getUsd().getNewRate().getSell(), 2))
                        .usdBanksBuy(round(banksRates.getUsd().getNewRate().getBuy(), 2))
                        .eurBanksSell(round(banksRates.getEur().getNewRate().getSell(), 2))
                        .eurBanksBuy(round(banksRates.getEur().getNewRate().getBuy(), 2))
                        .rubBanksSell(round(banksRates.getRub().getNewRate().getSell(), 3))
                        .rubBanksBuy(round(banksRates.getRub().getNewRate().getBuy(), 3))
                        .kztBanksSell(round(banksRates.getKzt().getNewRate().getSell(), 3))
                        .kztBanksBuy(round(banksRates.getKzt().getNewRate().getBuy(), 3))
                        .build();
                mainList.add(5, exchangeRatesVM);
                baseRecyclerFragmentAdapter.notifyItemInserted(5);
            }

            @Override
            public void onFailure(@NonNull Call<ExchangeRatesPair> call, @NonNull Throwable t) {

            }
        });
    }

    /**
     * Округляет десятичную дробь с заданной точностью
     *
     * @param d Десятичная дробь в виде строки
     * @param decimalPlace Количество знаков после запятой
     * @return Десятичная дробь с заданной точностью в виде строки
     */
    public String round(String d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}
