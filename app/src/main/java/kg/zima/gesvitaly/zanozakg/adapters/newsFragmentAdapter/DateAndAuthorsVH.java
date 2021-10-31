package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class DateAndAuthorsVH extends RecyclerView.ViewHolder {
    TextView authorTV;

    DateAndAuthorsVH(View itemView) {
        super(itemView);
        authorTV = (TextView) itemView.findViewById(R.id.author_tv);
    }
}
