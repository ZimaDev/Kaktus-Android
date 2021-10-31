package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.fragments.NewsFragment;
import kg.zima.gesvitaly.zanozakg.models.Comment;
import kg.zima.gesvitaly.zanozakg.models.Topic;
import kg.zima.gesvitaly.zanozakg.models.pieceofarticle.PieceOfArticle;
import kg.zima.gesvitaly.zanozakg.utils.NewsLoader;
import kg.zima.gesvitaly.zanozakg.utils.Utils;

class CommentsVHAdapter extends RecyclerView.Adapter<CommentVH> {
    private Context context;
    private ArrayList<Comment> comments;
    private Typeface font;
    private NewsFragment newsFragment;

    CommentsVHAdapter(Context context, ArrayList<Comment> comments, Typeface font, NewsFragment newsFragment) {
        this.context = context;
        this.comments = comments;
        this.font = font;
        this.newsFragment = newsFragment;
    }

    @Override
    public CommentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_comment, parent, false);
        return new CommentVH(itemView);
    }

    @Override
    public void onBindViewHolder(CommentVH holder, int position) {
        Comment comment = comments.get(position);

        if (comment.getPhoto().isEmpty()) {
            Drawable avatar = ContextCompat.getDrawable(context, R.drawable.ic_user);
            holder.avatarIV.setImageDrawable(avatar);
        }
        else {
            Glide.with(context)
                    .load(comment.getPhoto())
                    .fitCenter()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.avatarIV);
        }

        holder.nameTV.setText(comment.getUser());
        String timePassedString = Utils.getTimePassedString(comment.getDate());
        holder.dateTV.setText(timePassedString);
        ArrayList<PieceOfArticle> piecesOfArticle = NewsLoader.buildBody(new Topic(comment.getText()));
        NewsFragmentAdapter adapter = new NewsFragmentAdapter(piecesOfArticle, font, context, newsFragment);
        holder.contentRV.setAdapter(adapter);
        holder.contentRV.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
