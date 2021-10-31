package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class CommentVH extends RecyclerView.ViewHolder {
    ImageView avatarIV;
    TextView nameTV;
    TextView dateTV;
    RecyclerView contentRV;

    CommentVH(View itemView) {
        super(itemView);
        avatarIV = (ImageView) itemView.findViewById(R.id.comment_avatar_iv);
        nameTV = (TextView) itemView.findViewById(R.id.comment_name_tv);
        dateTV = (TextView) itemView.findViewById(R.id.comment_date_tv);
        contentRV = (RecyclerView) itemView.findViewById(R.id.comment_content_rv);
    }
}
