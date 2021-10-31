package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.youtube.player.YouTubeThumbnailView;

import kg.zima.gesvitaly.zanozakg.R;

class YoutubeVH extends RecyclerView.ViewHolder {
    CardView youtubeCard;
    RelativeLayout relativeLayoutOverYouTubeThumbnailView;
    YouTubeThumbnailView youTubeThumbnailView;
    ImageView playButton;

    YoutubeVH(View itemView) {
        super(itemView);
        youtubeCard = (CardView) itemView.findViewById(R.id.youtube_card);
        playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
        relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
        youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
    }
}
