package kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinuscxj.ellipsize.EllipsizeTextView;

import kg.zima.gesvitaly.zanozakg.R;

class NewsWithPicVH extends RecyclerView.ViewHolder {
    ImageView newsImageView;
    EllipsizeTextView newsTV;
    TextView dateTV;
    TextView countOfViewsTV;
    TextView countOfCommentsTV;
    ImageView countOfViewsIV;
    ImageView countOfCommentsIV;

    NewsWithPicVH(View itemView, Typeface font) {
        super(itemView);
        newsImageView = (ImageView) itemView.findViewById(R.id.newsImageView);
        newsTV = (EllipsizeTextView) itemView.findViewById(R.id.news_tv);
        dateTV = (TextView) itemView.findViewById(R.id.date_tv);
        countOfViewsTV = (TextView) itemView.findViewById(R.id.count_of_views_tv);
        countOfCommentsTV = (TextView) itemView.findViewById(R.id.count_of_comments_tv);
        countOfViewsIV = (ImageView) itemView.findViewById(R.id.count_of_views_iv);
        countOfCommentsIV = (ImageView) itemView.findViewById(R.id.count_of_comments_iv);
        newsTV.setTypeface(font);
    }
}
