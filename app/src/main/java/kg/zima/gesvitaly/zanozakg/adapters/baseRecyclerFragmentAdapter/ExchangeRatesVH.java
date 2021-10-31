package kg.zima.gesvitaly.zanozakg.adapters.baseRecyclerFragmentAdapter;

import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import kg.zima.gesvitaly.zanozakg.R;

class ExchangeRatesVH extends RecyclerView.ViewHolder {
    TextView usdBanksBuyTV;
    TextView eurBanksBuyTV;
    TextView rubBanksBuyTV;
    TextView kztBanksBuyTV;
    TextView usdBanksSellTV;
    TextView eurBanksSellTV;
    TextView rubBanksSellTV;
    TextView kztBanksSellTV;
    TextView usdNbkrTV;
    TextView eurNbkrTV;
    TextView rubNbkrTV;
    TextView kztNbkrTV;

    TextView usdLabelTV;
    TextView eurLabelTV;
    TextView rubLabelTV;
    TextView kztLabelTV;
    TextView buyLabelTV;
    TextView sellLabelTV;
    TextView nbkrLabelTV;

    TextView kursLabelTV;

    ExchangeRatesVH(View itemView, Typeface font, Typeface titleFont) {
        super(itemView);

        usdBanksBuyTV = (TextView) itemView.findViewById(R.id.usd_banks_buy_tv);
        eurBanksBuyTV = (TextView) itemView.findViewById(R.id.eur_banks_buy_tv);
        rubBanksBuyTV = (TextView) itemView.findViewById(R.id.rub_banks_buy_tv);
        kztBanksBuyTV = (TextView) itemView.findViewById(R.id.kzt_banks_buy_tv);
        usdBanksSellTV = (TextView) itemView.findViewById(R.id.usd_banks_sell_tv);
        eurBanksSellTV = (TextView) itemView.findViewById(R.id.eur_banks_sell_tv);
        rubBanksSellTV = (TextView) itemView.findViewById(R.id.rub_banks_sell_tv);
        kztBanksSellTV = (TextView) itemView.findViewById(R.id.kzt_banks_sell_tv);
        usdNbkrTV = (TextView) itemView.findViewById(R.id.usd_nbkr_tv);
        eurNbkrTV = (TextView) itemView.findViewById(R.id.eur_nbkr_tv);
        rubNbkrTV = (TextView) itemView.findViewById(R.id.rub_nbkr_tv);
        kztNbkrTV = (TextView) itemView.findViewById(R.id.kzt_nbkr_tv);

        usdLabelTV = (TextView) itemView.findViewById(R.id.usd_label_tv);
        eurLabelTV = (TextView) itemView.findViewById(R.id.eur_label_tv);
        rubLabelTV = (TextView) itemView.findViewById(R.id.rub_label_tv);
        kztLabelTV = (TextView) itemView.findViewById(R.id.kzt_label_tv);
        buyLabelTV = (TextView) itemView.findViewById(R.id.buy_label_tv);
        sellLabelTV = (TextView) itemView.findViewById(R.id.sell_label_tv);
        nbkrLabelTV = (TextView) itemView.findViewById(R.id.nbkr_label_tv);

        kursLabelTV = (TextView) itemView.findViewById(R.id.kurs_label_tv);

        usdLabelTV.setTypeface(titleFont);
        eurLabelTV.setTypeface(titleFont);
        rubLabelTV.setTypeface(titleFont);
        kztLabelTV.setTypeface(titleFont);
        buyLabelTV.setTypeface(titleFont);
        sellLabelTV.setTypeface(titleFont);
        nbkrLabelTV.setTypeface(titleFont);
        kursLabelTV.setTypeface(font);
    }
}
