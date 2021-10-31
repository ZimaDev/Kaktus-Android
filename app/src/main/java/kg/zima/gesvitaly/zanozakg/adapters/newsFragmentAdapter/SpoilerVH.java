package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import kg.zima.gesvitaly.zanozakg.R;

public class SpoilerVH extends RecyclerView.ViewHolder {
    ConstraintLayout spoilerCard;
    TextView spoilerTitleTV;
    ImageView spoilerOpenIV;
    ExpandableLayout expandableLayout;
    RecyclerView spoilerRV;

    public SpoilerVH(View itemView) {
        super(itemView);
        spoilerCard = (ConstraintLayout) itemView.findViewById(R.id.spoiler_card);
        spoilerTitleTV = (TextView) itemView.findViewById(R.id.spoiler_title_tv);
        spoilerOpenIV = (ImageView) itemView.findViewById(R.id.spoiler_open_iv);
        expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
        spoilerRV = (RecyclerView) itemView.findViewById(R.id.spoiler_rv);
    }
}
