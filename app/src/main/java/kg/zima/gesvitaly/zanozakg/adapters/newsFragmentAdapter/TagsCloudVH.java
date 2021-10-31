package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;

import kg.zima.gesvitaly.zanozakg.R;

class TagsCloudVH extends RecyclerView.ViewHolder {
    FlexboxLayout tagsCloudFlexbox;

    TagsCloudVH(View itemView) {
        super(itemView);
        tagsCloudFlexbox = (FlexboxLayout) itemView.findViewById(R.id.tags_cloud_flexbox);
    }
}
