package kg.zima.gesvitaly.zanozakg.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vansuita.materialabout.builder.AboutBuilder;

import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;

public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.fragment_about, container, false);
        rootView.addView(
                AboutBuilder.with(getActivity())
                        .setDividerColor(R.color.whitePrimary)
                        .setAppIcon(R.drawable.ic_zanoza)
                        .setAppName(R.string.app_name)
                        .setLinksAnimated(true)
                        .addFiveStarsAction()
                        .addMoreFromMeAction("Zima.kg")
                        .setVersionNameAsAppSubTitle()
                        .addShareAction(R.string.app_name)
                        .setActionsColumnsCount(2)
                        .addFeedbackAction("zima.webstudio@gmail.com")
//                        .addChangeLogAction((Intent) null)
                        .setWrapScrollView(true)
                        .addAction(R.mipmap.ic_call_center, "Контакты редакции", v -> {
                            ContactsDialogFragment contactsDialogFragment = new ContactsDialogFragment();
                            contactsDialogFragment.show(getFragmentManager(), "contactsDialog");
                        })
                        .build());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("О приложении");
        ((MainActivity) getActivity()).checkNavigationItem(R.id.nav_about);
        ((MainActivity) getActivity()).showToolbar(false);
    }
}
