package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class TitleVH extends RecyclerView.ViewHolder {
    TextView titleTV;

    TitleVH(View itemView) {
        super(itemView);
        titleTV = (TextView) itemView.findViewById(R.id.title_tv);
    }
}
