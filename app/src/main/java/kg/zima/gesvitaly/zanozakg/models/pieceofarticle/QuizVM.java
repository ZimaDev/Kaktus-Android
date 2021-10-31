package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcel;

import static kg.zima.gesvitaly.zanozakg.ContentTypes.QUIZ;

public class QuizVM implements PieceOfArticle {
    private int id;

    public QuizVM(int id) {
        this.id = id;
    }

    @Override
    public int getType() {
        return QUIZ;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
    }

    protected QuizVM(Parcel in) {
        this.id = in.readInt();
    }

    public static final Creator<QuizVM> CREATOR = new Creator<QuizVM>() {
        @Override
        public QuizVM createFromParcel(Parcel source) {
            return new QuizVM(source);
        }

        @Override
        public QuizVM[] newArray(int size) {
            return new QuizVM[size];
        }
    };
}
