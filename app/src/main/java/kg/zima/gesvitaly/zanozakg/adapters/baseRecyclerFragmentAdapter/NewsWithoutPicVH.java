package kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class NewsWithoutPicVH extends RecyclerView.ViewHolder {
    TextView newsTV;
    TextView dateTV;
    TextView countOfViewsTV;
    TextView countOfCommentsTV;
    ImageView countOfViewsIV;
    ImageView countOfCommentsIV;

    NewsWithoutPicVH(View itemView, Typeface font) {
        super(itemView);
        newsTV = (TextView) itemView.findViewById(R.id.news_tv);
        dateTV = (TextView) itemView.findViewById(R.id.date_tv);
        countOfViewsTV = (TextView) itemView.findViewById(R.id.count_of_views_tv);
        countOfCommentsTV = (TextView) itemView.findViewById(R.id.count_of_comments_tv);
        countOfViewsIV = (ImageView) itemView.findViewById(R.id.count_of_views_iv);
        countOfCommentsIV = (ImageView) itemView.findViewById(R.id.count_of_comments_iv);
        newsTV.setTypeface(font);
    }
}
