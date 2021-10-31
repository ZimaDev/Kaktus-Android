package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class CardVH extends RecyclerView.ViewHolder {
    LinearLayout cardRoot;
    TextView cardNumberTV;
    RecyclerView cardRV;

    CardVH(View itemView) {
        super(itemView);
        cardRoot = (LinearLayout) itemView.findViewById(R.id.card_root);
        cardNumberTV = (TextView) itemView.findViewById(R.id.card_number_tv);
        cardRV = (RecyclerView) itemView.findViewById(R.id.card_rv);
    }
}
