package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class TopicPreviewVH extends RecyclerView.ViewHolder {
    ImageView topicPreviewImage;
    TextView topicPreviewTV;

    TopicPreviewVH(View itemView) {
        super(itemView);
        topicPreviewImage = itemView.findViewById(R.id.topic_preview_image);
        topicPreviewTV = itemView.findViewById(R.id.topic_preview_tv);
    }
}
