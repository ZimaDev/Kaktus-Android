package kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;
import kg.zima.gesvitaly.zanozakg.fragments.NewsFragment;
import kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments.BaseRecyclerFragment;
import kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments.SearchFragment;
import kg.zima.gesvitaly.zanozakg.models.NewsPreview;
import kg.zima.gesvitaly.zanozakg.models.NewsTag;
import kg.zima.gesvitaly.zanozakg.models.exchangeRates.ExchangeRatesVM;
import kg.zima.gesvitaly.zanozakg.utils.Utils;
import kg.zima.gesvitaly.zanozakg.utils.spanutils.CalligraphyTypefaceSpan;
import kg.zima.gesvitaly.zanozakg.utils.spanutils.LinkSpan;

import static kg.zima.gesvitaly.zanozakg.typeof.TypeOf.whenTypeOf;

public class BaseRecyclerFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> mainList;
    private ArrayList<NewsTag> mainNewsTagsList;
    private Context context;
    private Typeface font;
    private Typeface iconFont;
    private Typeface kursFont;
    private Typeface kursTitleFont;
    private boolean isChild;
    private BaseRecyclerFragment baseRecyclerFragment;
    private final int NEWS_WITH_PIC = 0;
    private final int NEWS_WITHOUT_PIC = 1;
    private final int LOAD_MORE_ACTUAL = 2;
    private final int SEARCH_MORE = 3;
    private final int VIEW_PROGRESS = 4;
    private final int LOAD_MORE_POPULAR = 5;
    private final int LOAD_MORE_TAG = 6;
    private final int EXCHANGE_RATES = 7;

    public BaseRecyclerFragmentAdapter(ArrayList<Object> mainList, ArrayList<NewsTag> mainNewsTagsList, Context context, Typeface font, Typeface iconFont, Typeface kursFont, Typeface kursTitleFont, boolean isChild, BaseRecyclerFragment baseRecyclerFragment) {
        this.mainList = mainList;
        this.mainNewsTagsList = mainNewsTagsList;
        this.context = context;
        this.font = font;
        this.iconFont = iconFont;
        this.kursFont = kursFont;
        this.kursTitleFont = kursTitleFont;
        this.isChild = isChild;
        this.baseRecyclerFragment = baseRecyclerFragment;
    }

    public void setMainList(ArrayList<Object> mainList) {
        this.mainList = mainList;
    }

    public int getMainListSize() {
        return mainList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NEWS_WITH_PIC: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_item_news_with_pic, parent, false);
                return new NewsWithPicVH(itemView, font);
            }
            case NEWS_WITHOUT_PIC: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_item_news_without_pic, parent, false);
                return new NewsWithoutPicVH(itemView, font);
            }
            case EXCHANGE_RATES: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_item_exchange_rates, parent, false);
                return new ExchangeRatesVH(itemView, kursFont, kursTitleFont);
            }
            case LOAD_MORE_ACTUAL: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_item_load_more, parent, false);
                return new LoadMoreActualVH(itemView);
            }
            case LOAD_MORE_POPULAR: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_item_load_more, parent, false);
                return new LoadMorePopularVH(itemView);
            }
            case SEARCH_MORE: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_item_load_more, parent, false);
                return new SearchMoreVH(itemView);
            }
            case LOAD_MORE_TAG: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_item_load_more, parent, false);
                return new LoadMoreTagVH(itemView);
            }
            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        whenTypeOf(holder)
                .is(NewsWithPicVH.class).then(h -> show(h, (NewsPreview) mainList.get(position)))
                .is(NewsWithoutPicVH.class).then(h -> show(h, (NewsPreview) mainList.get(position)))
                .is(ExchangeRatesVH.class).then(h -> show(h, (ExchangeRatesVM) mainList.get(position)))
                .is(LoadMoreActualVH.class).then(this::show)
                .is(LoadMorePopularVH.class).then(this::show)
                .is(SearchMoreVH.class).then(this::show)
                .is(LoadMoreTagVH.class).then(this::show);
    }

    private void show(NewsWithPicVH holder, NewsPreview newsPreview) {
        // Открытие новости при нажатии на карточку
        holder.itemView.setOnClickListener(v -> {
            int id = newsPreview.getId();
            NewsFragment newsFragment = new NewsFragment();
            newsFragment.setNewsId(id);
            FragmentManager fragmentManager;
            if (isChild) fragmentManager = baseRecyclerFragment.getParentFragment().getFragmentManager();
            else fragmentManager = baseRecyclerFragment.getFragmentManager();
            fragmentManager
                    .beginTransaction()
//                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.flContent, newsFragment)
                    .addToBackStack(null)
                    .commit();
            ((MainActivity) baseRecyclerFragment.getActivity()).uncheckAllNavItems();
        });


        ArrayList<Spanned> icons = new ArrayList<>();
        if (newsPreview.getTagsIds().contains(38)) icons.add(Html.fromHtml("&#xf021 ")); // обновлено
        if (newsPreview.getTagsIds().contains(6731)) icons.add(Html.fromHtml("&#xf29c ")); // опрос
        if (newsPreview.getTagsIds().contains(36)) icons.add(Html.fromHtml("&#xf15b ")); // документ
        if (newsPreview.getTagsIds().contains(17)) icons.add(Html.fromHtml("&#xf030 ")); // фото
        if (newsPreview.getTagsIds().contains(18)) icons.add(Html.fromHtml("&#xf03d ")); // видео
        if (newsPreview.getTagsIds().contains(19)) icons.add(Html.fromHtml("&#xf080 ")); // инфографика
        if (newsPreview.getTagsIds().contains(20)) icons.add(Html.fromHtml("&#xf03a ")); // список
        if (newsPreview.getTagsIds().contains(37)) icons.add(Html.fromHtml("&#xf028 ")); // аудио
        int length = 0;
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (Spanned icon : icons) {
            builder.append(icon).append(" ");
            length += icon.length() + 1;
        }
        builder.append(newsPreview.getName());

        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#4CAF50")), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new CalligraphyTypefaceSpan(iconFont), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new RelativeSizeSpan(0.8f), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //builder.setSpan(new SuperscriptSpanAdjuster(0.1), 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.newsTV.setText(builder, TextView.BufferType.SPANNABLE);

        Glide.with(context)
                .load(newsPreview.getImg())
                .placeholder(R.drawable.image_placeholder_small)
                .centerCrop()
                .into(holder.newsImageView);

        boolean isPR = Stream.of(newsPreview.getTagsIds()).anyMatch(n -> n == 6888);
        if (!isPR) {
            int countOfViews = newsPreview.getCountOfViews();
            holder.countOfViewsTV.setText(String.valueOf(countOfViews));
            holder.countOfViewsIV.setVisibility(View.VISIBLE);
        } else {
            holder.countOfViewsIV.setVisibility(View.GONE);
        }

        int countOfComments = newsPreview.getCountOfComments();
        if (countOfComments > 0) {
            holder.countOfCommentsTV.setText(String.valueOf(countOfComments));
            holder.countOfCommentsIV.setVisibility(View.VISIBLE);
            holder.countOfCommentsTV.setVisibility(View.VISIBLE);
        }
        else {
            holder.countOfCommentsIV.setVisibility(View.GONE);
            holder.countOfCommentsTV.setVisibility(View.GONE);
        }

        String timePassedString = Utils.getTimePassedString(newsPreview.getDate());
        holder.dateTV.setText(timePassedString);
    }
    private void show(NewsWithoutPicVH holder, NewsPreview newsPreview) {
        holder.newsTV.setText(newsPreview.getName());
        holder.newsTV.setOnClickListener(view -> {
            int id = newsPreview.getId();
            NewsFragment newsFragment = new NewsFragment();
            newsFragment.setNewsId(id);
            FragmentManager fragmentManager;
            if (isChild) fragmentManager = baseRecyclerFragment.getParentFragment().getFragmentManager();
            else fragmentManager = baseRecyclerFragment.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.flContent, newsFragment)
                    .addToBackStack(null)
                    .commit();
            ((MainActivity) baseRecyclerFragment.getActivity()).uncheckAllNavItems();
        });
        int countOfViews = newsPreview.getCountOfViews();
        holder.countOfViewsTV.setText(String.valueOf(countOfViews));
        int countOfComments = newsPreview.getCountOfComments();
        if (countOfComments > 0) holder.countOfCommentsTV.setText(String.valueOf(countOfComments));
        else holder.countOfCommentsIV.setVisibility(View.GONE);
        String timePassedString = Utils.getTimePassedString(newsPreview.getDate());
        holder.dateTV.setText(timePassedString);
    }
    private void show(ExchangeRatesVH holder, ExchangeRatesVM exchangeRatesVM) {
        holder.usdBanksBuyTV.setText(exchangeRatesVM.getUsdBanksBuy());
        holder.eurBanksBuyTV.setText(exchangeRatesVM.getEurBanksBuy());
        holder.rubBanksBuyTV.setText(exchangeRatesVM.getRubBanksBuy());
        holder.kztBanksBuyTV.setText(exchangeRatesVM.getKztBanksBuy());
        holder.usdBanksSellTV.setText(exchangeRatesVM.getUsdBanksSell());
        holder.eurBanksSellTV.setText(exchangeRatesVM.getEurBanksSell());
        holder.rubBanksSellTV.setText(exchangeRatesVM.getRubBanksSell());
        holder.kztBanksSellTV.setText(exchangeRatesVM.getKztBanksSell());
        holder.usdNbkrTV.setText(exchangeRatesVM.getUsdNbkr());
        holder.eurNbkrTV.setText(exchangeRatesVM.getEurNbkr());
        holder.rubNbkrTV.setText(exchangeRatesVM.getRubNbkr());
        holder.kztNbkrTV.setText(exchangeRatesVM.getKztNbkr());

        SpannableString kursCopyright = new SpannableString(Html.fromHtml("По данным <a href=\"https://kurs.kg\">Kurs.kg</a>"));
        URLSpan[] spans = kursCopyright.getSpans(0, kursCopyright.length(), URLSpan.class);
        for (URLSpan urlSpan : spans) {
            LinkSpan linkSpan = new LinkSpan(urlSpan.getURL(), baseRecyclerFragment);
            int spanStart = kursCopyright.getSpanStart(urlSpan);
            int spanEnd = kursCopyright.getSpanEnd(urlSpan);
            kursCopyright.setSpan(linkSpan, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            kursCopyright.removeSpan(urlSpan);
        }
        holder.kursLabelTV.setText(kursCopyright, TextView.BufferType.SPANNABLE);
        holder.kursLabelTV.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void show(LoadMoreActualVH holder) {
        holder.progressBar.setVisibility(View.GONE);
        holder.loadMoreClickableTV.setVisibility(View.VISIBLE);
        holder.loadMoreClickableTV.setOnClickListener(view -> {
            holder.loadMoreClickableTV.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
            baseRecyclerFragment.loadNews(true);
        });
    }
    private void show(LoadMorePopularVH holder) {
        holder.progressBar.setVisibility(View.GONE);
        holder.loadMoreClickableTV.setVisibility(View.VISIBLE);
        holder.loadMoreClickableTV.setOnClickListener(view -> {
            holder.loadMoreClickableTV.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
            baseRecyclerFragment.loadNews(true);
        });
    }
    private void show(LoadMoreTagVH holder) {
        holder.progressBar.setVisibility(View.GONE);
        holder.loadMoreClickableTV.setVisibility(View.VISIBLE);
        holder.loadMoreClickableTV.setOnClickListener(view -> {
            holder.loadMoreClickableTV.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
            baseRecyclerFragment.loadNews(true);
        });
    }
    private void show(SearchMoreVH holder) {
        holder.progressBar.setVisibility(View.GONE);
        holder.loadMoreClickableTV.setVisibility(View.VISIBLE);
        holder.loadMoreClickableTV.setOnClickListener(view -> {
            holder.loadMoreClickableTV.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
            String search = ((MainActivity) context).getSearch();
            ((SearchFragment) baseRecyclerFragment).setSearch(search);
            baseRecyclerFragment.loadNews(true);
        });
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = mainList.get(position);
        if (obj instanceof NewsPreview) {
            if (((NewsPreview) obj).getImg().equals("")) return NEWS_WITHOUT_PIC;
            return NEWS_WITH_PIC;
        }
        else if (obj instanceof ExchangeRatesVM) return EXCHANGE_RATES;
        else if (obj.equals(LoadMoreActualVH.class)) return LOAD_MORE_ACTUAL;
        else if (obj.equals(LoadMorePopularVH.class)) return LOAD_MORE_POPULAR;
        else if (obj.equals(SearchMoreVH.class)) return SEARCH_MORE;
        else if (obj.equals(LoadMoreTagVH.class)) return LOAD_MORE_TAG;
        else return VIEW_PROGRESS;
    }
}
