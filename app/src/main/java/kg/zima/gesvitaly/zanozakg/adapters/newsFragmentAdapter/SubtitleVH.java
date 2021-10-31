package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class SubtitleVH extends RecyclerView.ViewHolder {
    TextView subtitleTV;

    SubtitleVH(View itemView) {
        super(itemView);
        subtitleTV = (TextView) itemView.findViewById(R.id.subtitle_tv);
    }
}
