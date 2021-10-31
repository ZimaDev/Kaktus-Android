package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.fragments.NewsFragment;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.PieceOfArticle;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.SubtitleVM;

public class CardAdapter extends NewsFragmentAdapter {
    public CardAdapter(ArrayList<PieceOfArticle> piecesOfArticle, Typeface font, Context context, NewsFragment newsFragment) {
        super(piecesOfArticle, font, context, newsFragment);
    }

    protected void show(SubtitleVH holder, SubtitleVM subtitleVM) {
        super.show(holder, subtitleVM);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            holder.subtitleTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            holder.subtitleTV.setGravity(Gravity.CENTER);
        }
    }
}
