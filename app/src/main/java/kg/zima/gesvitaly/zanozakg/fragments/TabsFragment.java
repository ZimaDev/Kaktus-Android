package kg.zima.gesvitaly.zanozakg.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.activities.MainActivity;
import kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments.ActualFragment;
import kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments.BaseRecyclerFragment;
import kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments.PopularFragment;
import kg.zima.gesvitaly.zanozakg.fragments.recyclerFragments.TagsFragment;
import kg.zima.gesvitaly.zanozakg.utils.Utils;

public class TabsFragment extends BaseFragment {
    private boolean isFirstLaunch = true;
    private boolean isSliding = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isFirstLaunch = savedInstanceState.getBoolean("isFirstLaunch");
        }
        View rootView = inflater.inflate(R.layout.fragment_tabs, container, false);
        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager mViewPager = rootView.findViewById(R.id.fragment_pager);
        CustomPagerAdapter mPagerAdapter = new CustomPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.setOffscreenPageLimit(6);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                String fragmentName = mPagerAdapter.getFragmentName(position);
                ((MainActivity) requireActivity()).showToolbar(true);
                /*// Показываю кнопку наверх при переходе к фрагменту
                mPagerAdapter.getItem(position).showFab();
                isSliding = false;*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*// Скрываю кнопку наверх, при переходе от фрагмента к другому
                if (state == SCROLL_STATE_DRAGGING && !isSliding) {
                    mPagerAdapter.getItem(mViewPager.getCurrentItem()).hideFab();
                    isSliding = true;
                }*/
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        changeDrawerIconToHamburger((MainActivity) getActivity());
        super.onActivityCreated(savedInstanceState);
        if (isFirstLaunch && getActivity() != null && Utils.isInternetAvailable(getActivity())) {
            isFirstLaunch = false;
            AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(requireContext());
            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
            appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    VersionDialogFragment versionDialogFragment = VersionDialogFragment.newInstance("");
                    versionDialogFragment.show(getFragmentManager(), "versionDialog");
                } else {
                    new RatingDialogFragment().show(getFragmentManager(), "RatingDialogFragment");
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Все новости");
        //((MainActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.toolbar_zanoza);
        ((MainActivity) getActivity()).checkNavigationItem(R.id.nav_tabs);
        ((MainActivity) getActivity()).removeToolbarShadow();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFirstLaunch", isFirstLaunch);
    }

    /*public class CustomPagerAdapter extends FragmentStatePagerAdapter {
        private final List<String> tabTitles = new ArrayList<String>() {{
            add("Актуальное");
            add("Полезное");
            add("Истории");
            add("Новости");
            add("Популярное");
        }};

        public CustomPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public BaseRecyclerFragment getItem(int i) {
            switch (i) {
                case 0: {
                    ActualFragment actualFragment = new ActualFragment();
                    actualFragment.setRetainInstance(true);
                    return actualFragment;
                }
                case 1: {
                    TagsFragment helpfulFragment = TagsFragment.newInstance(new int[]{7612});
                    helpfulFragment.setRetainInstance(true);
                    return helpfulFragment;
                }
                case 2: {
                    TagsFragment storiesFragment = TagsFragment.newInstance(new int[]{7613});
                    storiesFragment.setRetainInstance(true);
                    return storiesFragment;
                }
                case 3: {
                    TagsFragment allNewsFragment = TagsFragment.newInstance(new int[]{8,11,7611,7612,7613});
                    allNewsFragment.setRetainInstance(true);
                    return allNewsFragment;
                }
                case 4: {
                    PopularFragment popularFragment = new PopularFragment();
                    popularFragment.setRetainInstance(true);
                    return popularFragment;
                }
                default: {
                    ActualFragment actualFragment = new ActualFragment();
                    actualFragment.setRetainInstance(true);
                    return actualFragment;
                }
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
    }*/

    public class CustomPagerAdapter extends FragmentStatePagerAdapter {
        List<FragmentWithName> fragments = new ArrayList<>();

        public String getFragmentName(int position) {
            return fragments.get(position).getName();
        }

        public CustomPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            addFragments();
        }

        @Override
        public int getCount() {
            if (fragments.isEmpty()) {
                addFragments();
            }
            return fragments.size();
        }

        @Override
        public BaseRecyclerFragment getItem(int i) {
            if (fragments.isEmpty()) {
                addFragments();
            }
            return fragments.get(i).getFragment();
        }

        @Override
        public CharSequence getPageTitle(int i) {
            return fragments.get(i).getName();
        }

        private void addFragments() {
            ActualFragment actualFragment = new ActualFragment();
            actualFragment.setRetainInstance(true);
            fragments.add(new FragmentWithName("Актуальное", actualFragment));
            /*TagsFragment electionsFragment = TagsFragment.newInstance(new int[]{40212});
            electionsFragment.setRetainInstance(true);
            fragments.add(new FragmentWithName("Выборы-2021", electionsFragment));*/
            TagsFragment helpfulFragment = TagsFragment.newInstance(new int[]{7612});
            helpfulFragment.setRetainInstance(true);
            fragments.add(new FragmentWithName("Полезное", helpfulFragment));
            TagsFragment storiesFragment = TagsFragment.newInstance(new int[]{7613});
            storiesFragment.setRetainInstance(true);
            fragments.add(new FragmentWithName("Истории", storiesFragment));
            TagsFragment allNewsFragment = TagsFragment.newInstance(new int[]{8,11,7611,7612,7613});
            allNewsFragment.setRetainInstance(true);
            fragments.add(new FragmentWithName("Новости", allNewsFragment));
            PopularFragment popularFragment = new PopularFragment();
            popularFragment.setRetainInstance(true);
            fragments.add(new FragmentWithName("Популярное", popularFragment));
        }

        private class FragmentWithName {
            private String name;
            private BaseRecyclerFragment fragment;

            public String getName() {
                return name;
            }

            public BaseRecyclerFragment getFragment() {
                return fragment;
            }

            private FragmentWithName(String name, BaseRecyclerFragment fragment) {
                this.name = name;
                this.fragment = fragment;
            }
        }
    }
}
