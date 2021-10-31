package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import kg.zima.gesvitaly.zanozakg.models.ImgUrls;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.TOPIC_PREVIEW;

public class TopicPreviewVM implements PieceOfArticle {
    private String name;
    private String url;
    private ImgUrls imgUrls;
    private int commentsCount;

    public TopicPreviewVM(String name, String url, ImgUrls imgUrls, int commentsCount) {
        this.name = name;
        this.url = url;
        this.imgUrls = imgUrls;
        this.commentsCount = commentsCount;
    }

    private TopicPreviewVM(Builder builder) {
        name = builder.name;
        url = builder.url;
        imgUrls = builder.imgUrls;
        commentsCount = builder.commentsCount;
    }

    @Override
    public int getType() {
        return TOPIC_PREVIEW;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ImgUrls getImgUrls() {
        return imgUrls;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public String getBestImgUrl() {
        if (imgUrls.getBigImgUrl() != null) return imgUrls.getBigImgUrl();
        else if (imgUrls.getMediumImgUrl() != null) return imgUrls.getMediumImgUrl();
        else if (imgUrls.getSmallImgUrl() != null) return imgUrls.getSmallImgUrl();
        else return null;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeParcelable(this.imgUrls, flags);
        dest.writeInt(this.commentsCount);
    }

    protected TopicPreviewVM(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.imgUrls = in.readParcelable(ImgUrls.class.getClassLoader());
        this.commentsCount = in.readInt();
    }

    public static final Creator<TopicPreviewVM> CREATOR = new Creator<TopicPreviewVM>() {
        @Override
        public TopicPreviewVM createFromParcel(Parcel source) {
            return new TopicPreviewVM(source);
        }

        @Override
        public TopicPreviewVM[] newArray(int size) {
            return new TopicPreviewVM[size];
        }
    };

    public interface IBuild {
        TopicPreviewVM build();
    }

    public interface ICommentsCount {
        IBuild commentsCount(int val);
    }

    public interface IImgUrls {
        ICommentsCount imgUrls(ImgUrls val);
    }

    public interface IUrl {
        IImgUrls url(String val);
    }

    public interface IName {
        IUrl name(String val);
    }

    public static final class Builder implements ICommentsCount, IImgUrls, IUrl, IName, IBuild {
        private int commentsCount;
        private ImgUrls imgUrls;
        private String url;
        private String name;

        public Builder() {
        }

        @Override
        public IBuild commentsCount(int val) {
            commentsCount = val;
            return this;
        }

        @Override
        public ICommentsCount imgUrls(ImgUrls val) {
            imgUrls = val;
            return this;
        }

        @Override
        public IImgUrls url(String val) {
            url = val;
            return this;
        }

        @Override
        public IUrl name(String val) {
            name = val;
            return this;
        }

        public TopicPreviewVM build() {
            return new TopicPreviewVM(this);
        }
    }
}
