package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class TextVH extends RecyclerView.ViewHolder {
    TextView textTV;

    TextVH(View itemView, Typeface font) {
        super(itemView);
        textTV = (TextView) itemView.findViewById(R.id.text_tv);
        textTV.setTypeface(font);
    }

    TextVH(View itemView) {
        super(itemView);
        textTV = (TextView) itemView.findViewById(R.id.text_tv);
    }
}
