package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.IFRAME;

public class IFrameVM implements PieceOfArticle {
    private String html;

    public IFrameVM(String html) {
        this.html = html;
    }

    @Override
    public int getType() {
        return IFRAME;
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

    protected IFrameVM(Parcel in) {
        this.html = in.readString();
    }

    public static final Creator<IFrameVM> CREATOR = new Creator<IFrameVM>() {
        @Override
        public IFrameVM createFromParcel(Parcel source) {
            return new IFrameVM(source);
        }

        @Override
        public IFrameVM[] newArray(int size) {
            return new IFrameVM[size];
        }
    };
}
