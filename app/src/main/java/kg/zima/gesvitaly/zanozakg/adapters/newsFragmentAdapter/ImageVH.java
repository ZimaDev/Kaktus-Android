package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import kg.zima.gesvitaly.zanozakg.R;

class ImageVH extends RecyclerView.ViewHolder {
    ImageView iv;
    ImageVH(View itemView) {
        super(itemView);
        iv = (ImageView) itemView.findViewById(R.id.iv);
    }
}
