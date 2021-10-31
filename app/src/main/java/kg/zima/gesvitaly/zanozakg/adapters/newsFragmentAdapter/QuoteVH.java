package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class QuoteVH extends RecyclerView.ViewHolder {
    TextView quoteTV;

    QuoteVH(View itemView, Typeface font) {
        super(itemView);
        quoteTV = (TextView) itemView.findViewById(R.id.quote_tv);
        quoteTV.setTypeface(font);
    }

    QuoteVH(View itemView) {
        super(itemView);
        quoteTV = (TextView) itemView.findViewById(R.id.quote_tv);
    }
}
