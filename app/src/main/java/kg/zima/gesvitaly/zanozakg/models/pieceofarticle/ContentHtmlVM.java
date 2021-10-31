package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.CONTENT_HTML;

public class ContentHtmlVM implements PieceOfArticle {
    private String html;

    public ContentHtmlVM(String html) {
        this.html = html;
    }

    @Override
    public int getType() {
        return CONTENT_HTML;
    }

    public String getHtml() {
        return html;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.html);
    }

    protected ContentHtmlVM(Parcel in) {
        this.html = in.readString();
    }

    public static final Creator<ContentHtmlVM> CREATOR = new Creator<ContentHtmlVM>() {
        @Override
        public ContentHtmlVM createFromParcel(Parcel source) {
            return new ContentHtmlVM(source);
        }

        @Override
        public ContentHtmlVM[] newArray(int size) {
            return new ContentHtmlVM[size];
        }
    };
}
