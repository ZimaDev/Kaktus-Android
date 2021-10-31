package kg.zima.gesvitaly.zanozakg;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ContentTypes {
    public static final int TITLE = 0;
    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    public static final int YOUTUBE_VIDEO = 3;
    public static final int IFRAME = 4;
    public static final int CONTENT_HTML = 5;
    public static final int BULLET_LIST_ITEM = 6;
    public static final int QUIZ = 7;
    public static final int VREZ = 8;
    public static final int SUBTITLE = 9;
    public static final int CAROUSEL = 10;
    public static final int COMMENT = 11;
    public static final int DATE_AND_AUTHORS = 12;
    public static final int QUOTE = 13;
    public static final int QUOTE_IN_COMMENT = 14;
    public static final int TOPIC_PREVIEW = 15;
    public static final int TAGS_CLOUD = 16;
    public static final int COMMENTS_TITLE = 17;
    public static final int CARD = 18;
    public static final int SPOILER = 19;

    @IntDef({
            TITLE,
            TEXT,
            IMAGE,
            YOUTUBE_VIDEO,
            IFRAME,
            CONTENT_HTML,
            BULLET_LIST_ITEM,
            QUIZ,
            VREZ,
            SUBTITLE,
            CAROUSEL,
            COMMENT,
            DATE_AND_AUTHORS,
            QUOTE,
            QUOTE_IN_COMMENT,
            TOPIC_PREVIEW,
            TAGS_CLOUD,
            COMMENTS_TITLE,
            CARD,
            SPOILER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentType {
    }
}
