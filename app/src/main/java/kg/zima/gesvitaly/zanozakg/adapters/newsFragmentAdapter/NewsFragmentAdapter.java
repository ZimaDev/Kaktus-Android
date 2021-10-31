package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.flyco.banner.anim.select.ZoomInEnter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.muddzdev.styleabletoast.StyleableToast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kg.zima.gesvitaly.zanozakg.BuildConfig;
import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.flycobannerimpl.SliderItem;
import kg.zima.gesvitaly.zanozakg.fragments.CarouselFragment;
import kg.zima.gesvitaly.zanozakg.fragments.FullscreenImageFragment;
import kg.zima.gesvitaly.zanozakg.fragments.NewsFragment;
import kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments.TagFragment;
import kg.zima.gesvitaly.zanozakg.models.Media;
import kg.zima.gesvitaly.zanozakg.models.NewsTag;
import kg.zima.gesvitaly.zanozakg.models.Quiz;
import kg.zima.gesvitaly.zanozakg.models.Topic;
import kg.zima.gesvitaly.zanozakg.models.Variant;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.BulletListItemVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.CardVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.CarouselVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.CommentVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.ContentHtmlVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.DateAndAuthorsVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.IFrameVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.ImageVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.PieceOfArticle;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.QuizVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.QuoteInCommentVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.QuoteVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.SpoilerVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.SubtitleVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.TagsCloudVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.TextVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.TitleVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.TopicPreviewVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.VrezVM;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.YouTubeVideoVM;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.QuizApiClient;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.QuizApiService;
import kg.zima.gesvitaly.zanozakg.utils.GlideImageGetterForTextView;
import kg.zima.gesvitaly.zanozakg.utils.NewsLoader;
import kg.zima.gesvitaly.zanozakg.utils.Resolution;
import kg.zima.gesvitaly.zanozakg.utils.Utils;
import kg.zima.gesvitaly.zanozakg.utils.spanutils.BulletSpanWithRadius;
import kg.zima.gesvitaly.zanozakg.utils.spanutils.CalligraphyTypefaceSpan;
import kg.zima.gesvitaly.zanozakg.utils.spanutils.CustomLineHeight;
import kg.zima.gesvitaly.zanozakg.utils.spanutils.LinkSpan;
import kg.zima.gesvitaly.zanozakg.utils.spanutils.SuperscriptSpanAdjuster;
import kg.zima.gesvitaly.zanozakg.utils.spanutils.TypefaceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kg.zima.gesvitaly.zanozakg.CarouselFlags.CarouselFlag;
import static kg.zima.gesvitaly.zanozakg.CarouselFlags.SHOW_AUTHORS;
import static kg.zima.gesvitaly.zanozakg.CarouselFlags.SHOW_DESCRIPTION;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.BULLET_LIST_ITEM;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.CARD;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.CAROUSEL;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.COMMENT;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.COMMENTS_TITLE;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.CONTENT_HTML;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.ContentType;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.DATE_AND_AUTHORS;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.IFRAME;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.IMAGE;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.QUIZ;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.QUOTE;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.QUOTE_IN_COMMENT;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.SPOILER;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.SUBTITLE;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.TAGS_CLOUD;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.TEXT;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.TITLE;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.TOPIC_PREVIEW;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.VREZ;
import static kg.zima.gesvitaly.zanozakg.ContentTypes.YOUTUBE_VIDEO;
import static kg.zima.gesvitaly.zanozakg.typeof.TypeOf.whenTypeOf;

public class NewsFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context context;
    private ArrayList<PieceOfArticle> piecesOfArticle;
    private Typeface font;
    private NewsFragment newsFragment;
    private HashMap<String, Resolution> imageResolutions = new HashMap<>();

    public NewsFragmentAdapter(ArrayList<PieceOfArticle> piecesOfArticle, Typeface font, Context context, NewsFragment newsFragment) {
        this.piecesOfArticle = piecesOfArticle;
        this.font = font;
        this.context = context;
        this.newsFragment = newsFragment;
    }

    public static HashMap<String, String> getDataUrlSource(String str) {
        String[] imgURLsArr = str.replace("[", "").replace("]", "").split(", ");
        ArrayList<String> imgURLs = new ArrayList<>(Arrays.asList(imgURLsArr));
        HashMap<String, String> url_maps = new HashMap<>();
        for (String url : imgURLs) {
            url_maps.put("Фото: Kaktus.media", url);
        }
        return url_maps;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @ContentType int viewType) {
        switch (viewType) {
            case TITLE: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_title, parent, false);
                return new TitleVH(itemView);
            }
            case TEXT: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_text, parent, false);
                return new TextVH(itemView, font);
            }
            case IMAGE: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_image, parent, false);
                return new ImageVH(itemView);
            }
            case YOUTUBE_VIDEO: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_youtube, parent, false);
                return new YoutubeVH(itemView);
            }
            case IFRAME: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_iframe, parent, false);
                return new IFrameVH(itemView);
            }
            case CONTENT_HTML: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_iframe, parent, false);
                return new ContentHtmlVH(itemView);
            }
            case BULLET_LIST_ITEM: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_text, parent, false);
                return new BulletListItemVH(itemView, font);
            }
            case QUIZ: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_quiz, parent, false);
                return new QuizVH(itemView);
            }
            case VREZ: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_vrez, parent, false);
                return new VrezVH(itemView);
            }
            case SUBTITLE: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_subtitle, parent, false);
                return new SubtitleVH(itemView);
            }
            case CAROUSEL: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_carousel, parent, false);
                return new CarouselVH(itemView);
            }
            case COMMENT: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_comment, parent, false);
                return new CommentVH(itemView);
            }
            case DATE_AND_AUTHORS: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_date_and_authors, parent, false);
                return new DateAndAuthorsVH(itemView);
            }
            case QUOTE: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_quote, parent, false);
                return new QuoteVH(itemView, font);
            }
            case QUOTE_IN_COMMENT: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_quote_comments, parent, false);
                return new QuoteInCommentVH(itemView);
            }
            case TOPIC_PREVIEW: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_vrez_topicpreview, parent, false);
                return new TopicPreviewVH(itemView);
            }
            case TAGS_CLOUD: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_tags_cloud, parent, false);
                return new TagsCloudVH(itemView);
            }
            case COMMENTS_TITLE: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_comments_title, parent, false);
                return new CommentsTitleVH(itemView);
            }
            case CARD: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_card, parent, false);
                return new CardVH(itemView);
            }
            case SPOILER: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_spoiler, parent, false);
                return new SpoilerVH(itemView);
            }
            default: {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_text, parent, false);
                return new TextVH(itemView, font);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        PieceOfArticle pieceOfArticle = piecesOfArticle.get(i);
        whenTypeOf(holder)
                .is(TextVH.class).then(h -> show(h, (TextVM) pieceOfArticle))
                .is(TitleVH.class).then(h -> show(h, (TitleVM) pieceOfArticle))
                .is(ImageVH.class).then(h -> show(h, (ImageVM) pieceOfArticle))
                .is(YoutubeVH.class).then(h -> show(h, (YouTubeVideoVM) pieceOfArticle))
                .is(IFrameVH.class).then(h -> show(h, (IFrameVM) pieceOfArticle))
                .is(ContentHtmlVH.class).then(h -> show(h, (ContentHtmlVM) pieceOfArticle))
                .is(BulletListItemVH.class).then(h -> show(h, (BulletListItemVM) pieceOfArticle))
                .is(QuizVH.class).then(h -> show(h, (QuizVM) pieceOfArticle))
                .is(VrezVH.class).then(h -> show(h, (VrezVM) pieceOfArticle))
                .is(SubtitleVH.class).then(h -> show(h, (SubtitleVM) pieceOfArticle))
                .is(CarouselVH.class).then(h -> show(h, (CarouselVM) pieceOfArticle))
                .is(CommentVH.class).then(h -> show(h, (CommentVM) pieceOfArticle))
                .is(DateAndAuthorsVH.class).then(h -> show(h, (DateAndAuthorsVM) pieceOfArticle))
                .is(QuoteVH.class).then(h -> show(h, (QuoteVM) pieceOfArticle))
                .is(QuoteInCommentVH.class).then(h -> show(h, (QuoteInCommentVM) pieceOfArticle))
                .is(TopicPreviewVH.class).then(h -> show(h, (TopicPreviewVM) pieceOfArticle))
                .is(TagsCloudVH.class).then(h -> show(h, (TagsCloudVM) pieceOfArticle))
                .is(CardVH.class).then(h -> show(h, (CardVM) pieceOfArticle))
                .is(SpoilerVH.class).then(h -> show(h, (SpoilerVM) pieceOfArticle));
    }

    private void buildTextWithUrls(Spannable spannable) {
        URLSpan[] spans = spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (URLSpan urlSpan : spans) {
            LinkSpan linkSpan = new LinkSpan(urlSpan.getURL(), newsFragment);
            int spanStart = spannable.getSpanStart(urlSpan);
            int spanEnd = spannable.getSpanEnd(urlSpan);
            spannable.setSpan(linkSpan, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.removeSpan(urlSpan);
        }
    }

    private boolean buildTextWithImages(Context context, Spannable spannable) {
        Pattern refImg = Pattern.compile("img.*?src=('|\")(.*?)\\1");
        boolean hasChanges = false;

        String s = ("<p><a data-stat='none' class='pics pics-' href='http://databank.worldbank.org/data/images/dark-eye.png'>" +
                "<img src='http://databank.worldbank.org/data/images/dark-eye.png' align=\"\" border=\"0\"/></a></p>");
        String s1 = s.replaceAll("img.*?src=('|\")(.*?)\\1", "$2");

        Matcher matcher = refImg.matcher(spannable);
        while (matcher.find()) {
            boolean set = true;
            for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class)) {
                if (spannable.getSpanStart(span) >= matcher.start()
                        && spannable.getSpanEnd(span) <= matcher.end()
                        ) {
                    spannable.removeSpan(span);
                } else {
                    set = false;
                    break;
                }
            }
            String resname = spannable.subSequence(matcher.start(1), matcher.end(1)).toString().trim();
            Uri uri = new Uri.Builder().path(resname).build();
            if (set) {
                hasChanges = true;
                spannable.setSpan(new ImageSpan(context, uri),
                        matcher.start(),
                        matcher.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        return hasChanges;
    }

    private void load(final ImageView imageView, String url) {
        if (imageView.getHeight() == 0) {
            // wait for layout, same as Glide's SizeDeterminer does
            imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    // re-query viewTreeObserver, because the one used to add the listener may be dead: http://stackoverflow.com/a/29172475/253468
                    imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    // call the same method, but now we can be sure getHeight() has a value
                    load(imageView, url);
                    return true; // == allow drawing
                }
            });
        } else {
            Glide
                    .with(imageView.getContext())
                    .load(url)
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL, imageView.getHeight())
                    .into(imageView);
        }
    }

    protected void show(TextVH holder, TextVM textVM) {
        String text = textVM.getText();
        Spannable spannable = new SpannableString(Html.fromHtml(text, new GlideImageGetterForTextView(context, holder.textTV), null));
        buildTextWithUrls(spannable);
        holder.textTV.setText(spannable, TextView.BufferType.SPANNABLE);
        holder.textTV.setMovementMethod(LinkMovementMethod.getInstance());


        /*Spannable spannable = new SpannableString(Html.fromHtml(textVM.getText()));

        buildTextWithImages(context, spannable);
        buildTextWithUrls(spannable);

        holder.textTV.setText(spannable, TextView.BufferType.SPANNABLE);
        holder.textTV.setMovementMethod(LinkMovementMethod.getInstance());*/
    }

    protected void show(TitleVH holder, TitleVM titleVM) {
        holder.titleTV.setText(titleVM.getText());
    }

    protected void show(ImageVH holder, ImageVM imageVM) {
//        load(holder.iv, imageVM.getBigImgUrl());
        /*Glide.with(context)
                .load(imageVM.getBigImgUrl())
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv);*/
        String imgUrl;
        if (imageVM.getBigImgUrl() != null && !imageVM.getBigImgUrl().isEmpty())
            imgUrl = imageVM.getBigImgUrl();
        else if (imageVM.getMediumImgUrl() != null && !imageVM.getMediumImgUrl().isEmpty())
            imgUrl = imageVM.getMediumImgUrl();
        else if (imageVM.getSmallImgUrl() != null && !imageVM.getSmallImgUrl().isEmpty())
            imgUrl = imageVM.getSmallImgUrl();
        else return;

        ImageView imageView = holder.iv;

        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                int widthInt;
                int heightInt;

                if (imageResolutions.containsKey(imgUrl)) {
                    Resolution imageResolution = imageResolutions.get(imgUrl);
                    widthInt = imageResolution.getWidth();
                    heightInt = imageResolution.getHeight();
                } else {
                    float width;
                    float height;
                    int imgWidth = resource.getIntrinsicWidth();
                    int viewWidth = getView().getWidth();

                    // Если ширина картинка больше ширины вьюшки, то уменьшить ее до ширины вьюшки
                    if (imgWidth >= viewWidth) {
                        float downScale = (float) imgWidth / viewWidth;
                        width = (float) imgWidth / downScale;
                        height = (float) resource.getIntrinsicHeight() / downScale;
                    } else {
                        float multiplier = (float) viewWidth / imgWidth;
                        width = (float) imgWidth * multiplier;
                        height = (float) resource.getIntrinsicHeight() * multiplier;
                    }
                    widthInt = Math.round(width);
                    heightInt = Math.round(height);
                    imageResolutions.put(imgUrl, new Resolution(widthInt, heightInt));
                }

                Rect rect = new Rect(0, 0, widthInt, heightInt);
                ViewGroup.LayoutParams params = getView().getLayoutParams();
                params.height = heightInt;
                params.width = widthInt;
                getView().setLayoutParams(params);

                resource.setBounds(rect);

                if (resource.isAnimated()) {
                    // set callback to drawable in order to
                    // signal its container to be redrawn
                    // to show the animated GIF
                    resource.setLoopCount(GlideDrawable.LOOP_FOREVER);
                    resource.start();
                }
            }
        };

        /*if (imageResolutions.containsKey(imgUrl)) {
            Resolution imageResolution = imageResolutions.get(imgUrl);
            SizedColorDrawable drawable = new SizedColorDrawable(
                    context.getResources().getColor(R.color.materialGrey200),
                    imageResolution.getWidth(),
                    imageResolution.getHeight());
            Glide.with(context)
                    .load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(drawable)
                    .into(target);
        } else {*/
        Glide.with(context)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(target);
//        }

        holder.iv.setOnClickListener(v -> {
            FullscreenImageFragment fragment = new FullscreenImageFragment();
            fragment.setSrc(imgUrl);
            FragmentManager fragmentManager = newsFragment.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.flContent, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    protected void show(YoutubeVH holder, YouTubeVideoVM youTubeVideoVM) {
        ViewGroup.LayoutParams params = holder.youtubeCard.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        newsFragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = dm.widthPixels;
        params.height = params.width * 9 / 16;
        holder.youtubeCard.setLayoutParams(params);

        String youTubeVideoId = youTubeVideoVM.getVideoId();
//        FullScreenManager fullScreenManager = new FullScreenManager(activity);
        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };
        holder.youTubeThumbnailView.initialize(BuildConfig.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                newsFragment.addYoutubeToList(youTubeThumbnailLoader);
                youTubeThumbnailLoader.setVideo(youTubeVideoId);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });

        holder.playButton.setOnClickListener(v -> {
            if (YouTubeIntents.canResolveChannelIntent(context)) {
                if (youTubeVideoId != null) {
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, BuildConfig.YOUTUBE_API_KEY, youTubeVideoId);
                    context.startActivity(intent);
                }
            }
            //TODO: Make youtube work without youtube app
            else {
                Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + youTubeVideoId);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                context.startActivity(intent);
            }

        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void show(IFrameVH holder, IFrameVM iFrameVM) {
        String iFrameHtml = iFrameVM.getHtml();
        holder.iFrameWebView.getSettings().setJavaScriptEnabled(true);
        holder.iFrameWebView.setWebChromeClient(new WebChromeClient());
        holder.iFrameWebView.loadDataWithBaseURL("http://kaktus.media", iFrameHtml, "text/html", "UTF-8", null);
        newsFragment.addWebViewToList(holder.iFrameWebView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void show(ContentHtmlVH holder, ContentHtmlVM contentHtmlVM) {
        String url = contentHtmlVM.getHtml();
        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.setWebChromeClient(new WebChromeClient() {
        });
        holder.webView.loadDataWithBaseURL("http://kaktus.media", url, "text/html", "UTF-8", null);
        newsFragment.addWebViewToList(holder.webView);
    }

    protected void show(BulletListItemVH holder, BulletListItemVM bulletListItemVM) {
        String text = bulletListItemVM.getText().subSequence(4, bulletListItemVM.getText().length() - 5).toString();

        SpannableString bulletedText = new SpannableString(Html.fromHtml(text, new GlideImageGetterForTextView(context, holder.textTV), null));

        URLSpan[] spans = bulletedText.getSpans(0, bulletedText.length(), URLSpan.class);
        for (URLSpan urlSpan : spans) {
            LinkSpan linkSpan = new LinkSpan(urlSpan.getURL(), newsFragment);
            int spanStart = bulletedText.getSpanStart(urlSpan);
            int spanEnd = bulletedText.getSpanEnd(urlSpan);
            bulletedText.setSpan(linkSpan, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            bulletedText.removeSpan(urlSpan);
        }

        int bulletRadius = context.getResources().getDimensionPixelSize(R.dimen.bulleted_text_bullet_radius);
        int gapWidth = context.getResources().getDimensionPixelSize(R.dimen.bulleted_text_gap_width);
        bulletedText.setSpan(new BulletSpanWithRadius(bulletRadius, gapWidth), 0, bulletedText.length(), 0);
        holder.textTV.setText(bulletedText, TextView.BufferType.SPANNABLE);
        holder.textTV.setMovementMethod(LinkMovementMethod.getInstance());
    }

    protected void show(QuizVH holder, QuizVM quizVM) {
        QuizApiService quizApiService = QuizApiClient.getClient().create(QuizApiService.class);
        Call<Quiz> loadQuizCall = quizApiService.loadQuiz(quizVM.getId());
        Quiz quiz = null;
        try {
            quiz = new LoadQuizAsync().execute(loadQuizCall).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (quiz != null) {
            ArrayList<Variant> variants = quiz.getVariants();
            holder.quizTitleTV.setText(quiz.getName());
            if (quiz.getIsEnded() == 1) {
                buildChart(holder, variants);
                holder.quizVoteBtn.setVisibility(View.GONE);
                holder.quizShowResultsBtn.setVisibility(View.GONE);
            } else {
                // Проверяю, загружены ли варианты ответа
                if (holder.quizCard.getTag() != null) return;

                ArrayList<Integer> chosenVariants = new ArrayList<>();
                if (quiz.getType().equals("only")) {
                    for (Variant variant : variants) {
                        RadioButton radioButton = new RadioButton(context);
                        radioButton.setText(variant.getName());
                        radioButton.setTag(variant);
                        holder.quizRadioGroup.addView(radioButton);
                    }
                    // Устанавливаю, что варианты ответа уже добавлены, и не нужно добавлять их снова при скролле
                    holder.quizCard.setTag(true);

                    holder.quizVoteBtn.setOnClickListener(v -> {
                        int checkedRBId = holder.quizRadioGroup.getCheckedRadioButtonId();
                        RadioButton checkedRB = holder.quizRadioGroup.findViewById(checkedRBId);
                        if (checkedRB != null) {
                            int chosenVariant = ((Variant) checkedRB.getTag()).getId();
                            chosenVariants.clear();
                            chosenVariants.add(chosenVariant);
                            Call<Quiz> voteCall = quizApiService.vote("", chosenVariants);
                            voteCall.enqueue(new Callback<Quiz>() {
                                @Override
                                public void onResponse(@NonNull Call<Quiz> call, @NonNull Response<Quiz> response) {
                                    buildChart(holder, variants);
                                    holder.quizVoteBtn.setVisibility(View.GONE);
                                    holder.quizShowResultsBtn.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(@NonNull Call<Quiz> call, @NonNull Throwable t) {
                                    String error = t.getLocalizedMessage();

                                }
                            });
                        } else {
                            StyleableToast
                                .makeText(context, context.getString(R.string.choose_answer), R.style.StyleableToast)
                                .show();
                        }
                    });



                } else {
                    for (Variant variant : quiz.getVariants()) {
                        CheckBox checkBox = new CheckBox(context);
                        checkBox.setText(variant.getName());
                        checkBox.setTag(variant);
                        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                            if (isChecked) chosenVariants.add(variant.getId());
                            else chosenVariants.remove(Integer.valueOf(variant.getId()));
                        });
                        holder.quizRadioGroup.addView(checkBox);
                    }
                    // Устанавливаю, что варианты ответа уже добавлены, и не нужно добавлять их снова при скролле
                    holder.quizCard.setTag(true);
                    //TODO: повтор - отрефакторить
                    holder.quizVoteBtn.setOnClickListener(v -> {
                        if (!chosenVariants.isEmpty()) {
                            Call<Quiz> voteCall = quizApiService.vote("", chosenVariants);
                            voteCall.enqueue(new Callback<Quiz>() {
                                @Override
                                public void onResponse(@NonNull Call<Quiz> call, @NonNull Response<Quiz> response) {
                                    buildChart(holder, variants);
                                    holder.quizVoteBtn.setVisibility(View.GONE);
                                    holder.quizShowResultsBtn.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(@NonNull Call<Quiz> call, @NonNull Throwable t) {
                                    String error = t.getLocalizedMessage();

                                }
                            });
                        } else {
                            StyleableToast
                                .makeText(context, context.getString(R.string.choose_answer), R.style.StyleableToast)
                                .show();
                        }
                    });
                }

                holder.quizShowResultsBtn.setOnClickListener(v -> {
                    buildChart(holder, variants);
                    holder.quizVoteBtn.setVisibility(View.GONE);
                    holder.quizShowResultsBtn.setVisibility(View.GONE);
                });

            }
        } else {
            holder.quizTitleTV.setVisibility(View.GONE);
            holder.quizButtonLayout.setVisibility(View.GONE);
            TextView notLoadedTV = new TextView(context);
            notLoadedTV.setText("Голосование не было загружено");
            notLoadedTV.setGravity(Gravity.CENTER);
            notLoadedTV.setPadding(8, 32, 8, 32);
            holder.quizCard.addView(notLoadedTV);
        }
    }

    protected void show(VrezVH holder, VrezVM vrezVM) {
        Topic topic = vrezVM.getTopic();
        ArrayList<PieceOfArticle> piecesOfArticle = NewsLoader.buildPiecesOfArticleList(topic, null, true);
        NewsFragmentAdapter vrezAdapter = new NewsFragmentAdapter(piecesOfArticle, font, context, newsFragment);
        holder.vrezRV.setAdapter(vrezAdapter);
    }

    protected void show(SubtitleVH holder, SubtitleVM subtitleVM) {
        holder.subtitleTV.setText(subtitleVM.getText());
    }

    protected void show(CarouselVH holder, CarouselVM carouselVM) {
        ArrayList<SliderItem> sliderList = buildSliderList(carouselVM.getMedias(), carouselVM.getFlag());

        if (sliderList.size() < 10) {
            holder.imageSlider.setSource(sliderList)
                    .setSelectAnimClass(ZoomInEnter.class)
                    .startScroll();
            holder.sliderTextIndicator.setVisibility(View.GONE);
        } else {
            holder.imageSlider.setSource(sliderList)
                    .setIndicatorShow(false)
                    .setSelectAnimClass(ZoomInEnter.class)
                    .startScroll();
            holder.sliderTextIndicator.setVisibility(View.VISIBLE);
            String indicatorText = holder.imageSlider.getCurrentPosition() + 1 + " / " + sliderList.size();
            holder.sliderTextIndicator.setText(indicatorText);
            holder.imageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    String s = position + 1 + " / " + sliderList.size();
                    holder.sliderTextIndicator.setText(s);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        holder.imageSlider.setOnItemClickL(position -> {
            FragmentManager fragmentManager = newsFragment.getFragmentManager();
            CarouselFragment carouselFragment = new CarouselFragment();
            int sliderPosition = holder.imageSlider.getCurrentPosition();
            carouselFragment.setSliderList(sliderList);
            carouselFragment.setPosition(sliderPosition);
            fragmentManager.beginTransaction().replace(R.id.flContent, carouselFragment).addToBackStack(null).commit();
        });
    }

    protected void show(CommentVH holder, CommentVM commentVM) {
        if (commentVM.getPhoto().isEmpty()) {
            Drawable avatar = ContextCompat.getDrawable(context, R.drawable.ic_user);
            holder.avatarIV.setImageDrawable(avatar);
        } else {
            Glide.with(context)
                    .load(commentVM.getPhoto())
                    .fitCenter()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.avatarIV);
        }

        holder.nameTV.setText(commentVM.getUser());
        String timePassedString = Utils.getTimePassedString(commentVM.getDate());
        holder.dateTV.setText(timePassedString);
        ArrayList<PieceOfArticle> piecesOfArticle = NewsLoader.buildBody(new Topic(commentVM.getText()));
        NewsFragmentAdapter adapter = new NewsFragmentAdapter(piecesOfArticle, font, context, newsFragment);
        holder.contentRV.setAdapter(adapter);
        holder.contentRV.setLayoutManager(new LinearLayoutManager(context));
    }

    protected void show(DateAndAuthorsVH holder, DateAndAuthorsVM dateAndAuthorsVM) {
        holder.authorTV.setText(dateAndAuthorsVM.getText());
    }

    protected void show(QuoteVH holder, QuoteVM quoteVM) {
        // Добавляю кавычку
        String text = "&#8220 " + quoteVM.getText();
        Spannable spannable = new SpannableString(Html.fromHtml(text, new GlideImageGetterForTextView(context, holder.quoteTV), null));
        buildTextWithUrls(spannable);

        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(context.getAssets(), "fonts/FrankRuhlLibreBlack.ttf"));
        // Устанавливаю для кавычки свой шрифт
        spannable.setSpan(typefaceSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Увеличиваю размер кавычки
        spannable.setSpan(new RelativeSizeSpan(4f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Опускаю кавычку вниз
        spannable.setSpan(new SuperscriptSpanAdjuster(0.46), 0, 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new CustomLineHeight(16), 0, 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.quoteTV.setText(spannable, TextView.BufferType.SPANNABLE);
        holder.quoteTV.setMovementMethod(LinkMovementMethod.getInstance());
    }

    protected void show(QuoteInCommentVH holder, QuoteInCommentVM quoteInCommentVM) {
        Topic topic = quoteInCommentVM.getTopic();
        topic.setText(quoteInCommentVM.getText());
        ArrayList<PieceOfArticle> piecesOfArticle = NewsLoader.buildPiecesOfArticleList(topic, null, true);
        CommentsQuoteAdapter vrezAdapter = new CommentsQuoteAdapter(piecesOfArticle, font, context, newsFragment);
        holder.quoteRV.setAdapter(vrezAdapter);
    }

    protected void show(TopicPreviewVH holder, TopicPreviewVM topicPreviewVM) {
        String imgUrl = topicPreviewVM.getBestImgUrl();
        String articleUrl = topicPreviewVM.getUrl();

        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.drawable.image_placeholder_small)
                .into(holder.topicPreviewImage);

        /*if (imgUrl != null) {
            String imgHtml = "<a href='" + articleUrl + "'><img src=\"" + imgUrl + "\" align=\"\" border=\"0\"></a>";

            Spannable spannableImage = new SpannableString(Html.fromHtml(imgHtml, new GlideImageGetterForTextView(context, holder.topicPreviewImage, false), null));
            buildTextWithUrls(spannableImage);
            holder.topicPreviewImage.setText(spannableImage, TextView.BufferType.SPANNABLE);
            holder.topicPreviewImage.setMovementMethod(LinkMovementMethod.getInstance());
            holder.topicPreviewImage.setPaintFlags(holder.topicPreviewImage.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.topicPreviewImage.setLinkTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.topicPreviewImage.setVisibility(View.GONE);
        }*/

        String topicPreviewHtml = "<a href='" + articleUrl + "'>" + topicPreviewVM.getName() + "</a>";

        Spannable spannableText = new SpannableString(Html.fromHtml(topicPreviewHtml));
        buildTextWithUrls(spannableText);
        holder.topicPreviewTV.setText(spannableText, TextView.BufferType.SPANNABLE);
        holder.topicPreviewTV.setMovementMethod(LinkMovementMethod.getInstance());
        holder.topicPreviewTV.setPaintFlags(holder.topicPreviewTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.topicPreviewTV.setLinkTextColor(ContextCompat.getColor(context, R.color.black));

        //region С использованием ImageView
        /*int topicId = Integer.parseInt(topicPreviewVM.getText());
        Topic topic = topicPreviewVM.getTopic();
        if (topic == null) return;
        ArrayList<TopicPreview> topicPreviews = topicPreviewVM.getTopic().getTopicPreviews();
        if (topicPreviews == null) return;
        Optional<TopicPreview> topicPreviewOptional = Stream.of(topicPreviews)
                .filter(tp -> tp.getId() == topicId)
                .findFirst();

        if (!topicPreviewOptional.isPresent()) return;
        TopicPreview topicPreview = topicPreviewOptional.get();

        Glide.with(context)
                .load(topicPreview.getImgUrls().getBigImgUrl())
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.topicPreviewIV);

        String text = topicPreview.getName();
        holder.topicPreviewTV.setText(text);
        holder.topicPreviewTV.setPaintFlags(holder.topicPreviewTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        holder.topicPreviewTV.setOnClickListener(v -> NewsLoader.loadArticle(topicId, newsFragment));
        holder.topicPreviewIV.setOnClickListener(v -> NewsLoader.loadArticle(topicId, newsFragment));*/
        //endregion
    }

    protected void show(TagsCloudVH holder, TagsCloudVM tagsCloudVM) {
        ArrayList<NewsTag> tagsToDisplay = tagsCloudVM.getNewsTags();
        if (tagsToDisplay == null || tagsToDisplay.isEmpty()) return;

        ChipCloudConfig config = new ChipCloudConfig()
                .selectMode(ChipCloud.SelectMode.single)
                .checkedChipColor(ContextCompat.getColor(context, R.color.accent))
                .checkedTextColor(ContextCompat.getColor(context, R.color.whitePrimary))
                .uncheckedChipColor(ContextCompat.getColor(context, R.color.materialGrey200))
                .uncheckedTextColor(ContextCompat.getColor(context, R.color.materialGrey600));

        holder.tagsCloudFlexbox.removeAllViews();
        ChipCloud chipCloud = new ChipCloud(context, holder.tagsCloudFlexbox, config);
        for (NewsTag newsTag : tagsToDisplay) {
            chipCloud.addChip(newsTag.getName());
        }
        chipCloud.setListener((index, checked, userClick) -> {
            if (checked) {
                FragmentManager fragmentManager = newsFragment.getFragmentManager();
                TagFragment tagFragment = new TagFragment();
                tagFragment.setRetainInstance(true);
                tagFragment.setTag(tagsToDisplay.get(index));
                fragmentManager.beginTransaction().replace(R.id.flContent, tagFragment, "TagFragment").addToBackStack(null).commit();
            }
        });
    }

    protected void show(CardVH holder, CardVM cardVM) {
        if (cardVM.needTopMargin()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(holder.cardRoot.getLayoutParams());
            params.setMargins(0, context.getResources().getDimensionPixelSize(R.dimen.small_margin), 0, context.getResources().getDimensionPixelSize(R.dimen.small_margin));
            holder.cardRoot.setLayoutParams(params);
        }
        Topic topic = cardVM.getTopic();
        if (topic != null) {
            topic.setText(cardVM.getText());
            holder.cardNumberTV.setText(cardVM.getCardNumber());
            ArrayList<PieceOfArticle> piecesOfArticle = NewsLoader.buildPiecesOfArticleList(topic, null, true);
            CardAdapter cardAdapter = new CardAdapter(piecesOfArticle, font, context, newsFragment);
            holder.cardRV.setAdapter(cardAdapter);
        }
    }

    protected void show(SpoilerVH holder, SpoilerVM spoilerVM) {
        holder.spoilerTitleTV.setText(spoilerVM.getTitle());
        ArrayList<PieceOfArticle> piecesOfArticle = NewsLoader.buildPiecesOfArticleList(spoilerVM.getTopic(), null, true);
        NewsFragmentAdapter spoilerAdapter = new NewsFragmentAdapter(piecesOfArticle, font, context, newsFragment);
        holder.spoilerRV.setAdapter(spoilerAdapter);
        holder.spoilerCard.setOnClickListener(v -> {
            if (holder.expandableLayout.isExpanded()) {
                holder.expandableLayout.collapse();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    holder.spoilerOpenIV.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_spoiler_arrow_down, null));
                }
            }
            else {
                holder.expandableLayout.expand();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    holder.spoilerOpenIV.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_spoiler_arrow_up, null));
                }
            }
        });
    }

    private ArrayList<SliderItem> buildSliderList(ArrayList<Media> medias, @CarouselFlag int flag) {
        ArrayList<SliderItem> sliderList = new ArrayList<>();

        for (Media media : medias) {
            // Проверки нужны для гифок, т.к. они не ресайзятся на сервере и их может не быть в большом размере
            String imgUrl = "";
            if (media.getBigImgUrl() != null) imgUrl = media.getBigImgUrl();
            else if (media.getMediumImgUrl() != null) imgUrl = media.getMediumImgUrl();
            else if (media.getSmallImgUrl() != null) imgUrl = media.getSmallImgUrl();

            if (imgUrl.isEmpty()) continue;

            if (flag == SHOW_AUTHORS) {
                StringBuilder authors = new StringBuilder("Фото: ");
                int authorsCount = media.getAuthors().size();
                for (int i = 0; i < authorsCount; i++) {
                    authors.append(media.getAuthors().get(i).getName());
                    if (i < authorsCount - 1) authors.append(", ");
                }
                sliderList.add(new SliderItem(imgUrl, authors.toString()));
            } else if (flag == SHOW_DESCRIPTION) {
                String description = media.getDescription() != null ? media.getDescription() : "";
                sliderList.add(new SliderItem(imgUrl, description));
            }
        }

        return sliderList;
    }

    private void buildChart(QuizVH holder, ArrayList<Variant> variants) {
        holder.quizCard.removeAllViews();
        LinearLayout quizCardLinearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams quizCardLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        quizCardLinearLayout.setLayoutParams(quizCardLinearLayoutParams);

        /*HorizontalBarChartView chart = new HorizontalBarChartView(context);
        BarSet dataSet = new BarSet();*/

        int totalVotesCount = 0;
        int max = 0;
        for (Variant variant : variants) {
            int votesCount = variant.getVotesCount();
            totalVotesCount += votesCount;
            if (votesCount > max) max = votesCount;
        }

        // Получение размера экрана
        DisplayMetrics dm = new DisplayMetrics();
        newsFragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        double oneStepWidth = (double) width / totalVotesCount;
        int maxWidth = (int) (width * 0.6);
//        double oneStepWidth = (double) holder.quizCard.getWidth() / totalVotesCount;
//        double oneStepWidth = (double) holder.quizCard.getWidth() / max;

        LinearLayout cardLinearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams cardLinearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardLinearLayout.setLayoutParams(cardLinearLayoutParams);
        cardLinearLayout.setOrientation(LinearLayout.VERTICAL);
        cardLinearLayout.setPadding(20, 20, 20, 20);
        holder.quizCard.addView(cardLinearLayout);

        int variantsCount = variants.size();
        for (int i = 0; i < variantsCount; i++) {
            int votesCount = variants.get(i).getVotesCount();
            double votesPercent = votesCount * 100 / (double) totalVotesCount;
            BigDecimal votesPercentRounded = new BigDecimal(votesPercent).setScale(2, RoundingMode.HALF_EVEN);
            votesPercent = votesPercentRounded.doubleValue();

            if (i < variantsCount - 1)
                buildChartEntry(cardLinearLayout, oneStepWidth, maxWidth, variants.get(i).getName(), votesCount, votesPercent, true);
            else
                buildChartEntry(cardLinearLayout, oneStepWidth, maxWidth, variants.get(i).getName(), votesCount, votesPercent, false);

            /*BigDecimal votesPercentRounded = new BigDecimal(votesPercent).setScale(2, RoundingMode.HALF_EVEN);
            votesPercent = votesPercentRounded.doubleValue();
            String label = variants.get(i).getName() + " - " + votesPercent + "% (" + votesCount + ")";
            Bar bar = new Bar(label, votesCount);
            bar.setColor(context.getResources().getColor(R.color.accent));
            dataSet.addBar(bar);*/
        }
    }

    private void buildChartEntry(LinearLayout cardLinearLayout, double oneStepWidth, int maxWidth, String name, int votesCount, double votesPercent, boolean needAddSpaceAfter) {
        TextView label = new TextView(context);
        label.setText(name);
        label.setTypeface(null, Typeface.BOLD);
        cardLinearLayout.addView(label);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);

        TextView textView = new TextView(context);
        textView.setText(votesPercent + "% (" + votesCount + ")");

        CardView card = new CardView(context);
        card.setCardElevation(0);
        CardView.LayoutParams params = new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT);
        params.width = (int) (votesCount * oneStepWidth);
        if (params.width < context.getResources().getDimensionPixelSize(R.dimen.quiz_strip_min_width))
            params.width = context.getResources().getDimensionPixelSize(R.dimen.quiz_strip_min_width);
        else {
            //int maxWidth = (int) (900 * oneStepWidth);
            if (params.width > maxWidth) params.width = maxWidth;
        }
        params.height = context.getResources().getDimensionPixelSize(R.dimen.quiz_strip_height);
        card.setLayoutParams(params);
        card.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_rectangle));

        linearLayout.addView(card);
        linearLayout.addView(Utils.createDummyView(context,
                context.getResources().getDimensionPixelSize(R.dimen.quiz_dummy_view_size),
                ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(textView);

        cardLinearLayout.addView(linearLayout);

        if (needAddSpaceAfter)
            cardLinearLayout.addView(Utils.createDummyView(context,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    context.getResources().getDimensionPixelSize(R.dimen.quiz_dummy_view_size)));
    }

    @Override
    public int getItemCount() {
        return piecesOfArticle.size();
    }

    @Override
    public
    @ContentType
    int getItemViewType(int position) {
        return piecesOfArticle.get(position).getType();
    }

    private class LoadQuizAsync extends AsyncTask<Call<Quiz>, Void, Quiz> {
        @SafeVarargs
        @Override
        protected final Quiz doInBackground(Call<Quiz>... params) {
            try {
                Call<Quiz> call = params[0];
                Response response = call.execute();
                return ((Response<Quiz>) response).body();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
