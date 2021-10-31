package kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

public class SearchMoreVH extends RecyclerView.ViewHolder {
    TextView loadMoreClickableTV;
    ProgressBar progressBar;

    SearchMoreVH(View itemView) {
        super(itemView);
        loadMoreClickableTV = (TextView) itemView.findViewById(R.id.load_more_clickable_tv);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
    }
}
