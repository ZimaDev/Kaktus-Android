package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import kg.zima.gesvitaly.zanozakg.R;

class QuoteInCommentVH extends RecyclerView.ViewHolder {
    RecyclerView quoteRV;

    QuoteInCommentVH(View itemView) {
        super(itemView);
        quoteRV = (RecyclerView) itemView.findViewById(R.id.quote_rv);
    }
}
