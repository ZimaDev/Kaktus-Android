package kg.zima.gesvitaly.zanozakg.adapters.newsFragmentAdapter;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class QuizVH extends RecyclerView.ViewHolder {
    TextView quizTitleTV;
    CardView quizCard;
    RadioGroup quizRadioGroup;
    LinearLayout quizButtonLayout;
    AppCompatButton quizVoteBtn;
    AppCompatButton quizShowResultsBtn;
    LinearLayout parentLinearLayout;

    QuizVH(View itemView) {
        super(itemView);
        quizTitleTV = (TextView) itemView.findViewById(R.id.quiz_title);
        quizCard = (CardView) itemView.findViewById(R.id.quiz_card);
        quizRadioGroup = (RadioGroup) itemView.findViewById(R.id.quiz_radio_group);
        quizButtonLayout = (LinearLayout) itemView.findViewById(R.id.quiz_button_layout);
        quizVoteBtn = (AppCompatButton) itemView.findViewById(R.id.quiz_vote_btn);
        quizShowResultsBtn = (AppCompatButton) itemView.findViewById(R.id.quiz_show_results_btn);
        parentLinearLayout = (LinearLayout) itemView.findViewById(R.id.parent_linear_layout);
    }
}
