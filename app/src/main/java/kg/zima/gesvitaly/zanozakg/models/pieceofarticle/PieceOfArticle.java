package kg.zima.gesvitaly.zanozakg.models.pieceofarticle;

import android.os.Parcelable;

import kg.zima.gesvitaly.zanozakg.ContentTypes.ContentType;

public interface PieceOfArticle extends Parcelable {
    @ContentType int getType();
}
