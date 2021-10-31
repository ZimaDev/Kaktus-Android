package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import kg.zima.gesvitaly.zanozakg.R;

class VrezVH extends RecyclerView.ViewHolder {
    RecyclerView vrezRV;
    VrezVH(View itemView) {
        super(itemView);
        vrezRV = (RecyclerView) itemView.findViewById(R.id.vrez_rv);
    }
}
