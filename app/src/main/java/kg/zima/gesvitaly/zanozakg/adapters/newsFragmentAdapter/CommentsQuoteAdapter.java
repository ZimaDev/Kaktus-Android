package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import android.content.Context;
import android.graphics.Typeface;

import java.util.ArrayList;

import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.fragments.NewsFragment;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.PieceOfArticle;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.TextVM;

class CommentsQuoteAdapter extends NewsFragmentAdapter {
    CommentsQuoteAdapter(ArrayList<PieceOfArticle> piecesOfArticle, Typeface font, Context context, NewsFragment newsFragment) {
        super(piecesOfArticle, font, context, newsFragment);
    }

    protected void show(TextVH holder, TextVM textVM) {
        super.show(holder, textVM);
        holder.textTV.setTextColor(context.getResources().getColor(R.color.materialGrey500));
    }
}
