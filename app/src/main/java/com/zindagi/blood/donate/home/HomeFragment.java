/*
 * MIT License
 * Copyright (c) 2017. Attiq ur Rehman
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED
 * "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM,OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.zindagi.blood.donate.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.admin.AdminFragment;
import com.zindagi.blood.donate.base.ui.BaseApplication;
import com.zindagi.blood.donate.base.ui.BaseFragment;
import com.zindagi.blood.donate.login.model.UserInfo;
import com.zindagi.blood.donate.notification.NotificationFragment;
import com.zindagi.blood.donate.profile.ProfileFragment;
import com.zindagi.blood.donate.requestBlood.RequestBloodFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import devlight.io.library.ntb.NavigationTabBar;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class HomeFragment extends BaseFragment implements HomeView {
    @Inject
    HomePresenter homePresenter;

    private FragmentStatePagerAdapter getTabAdapter() {
        return new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new RequestBloodFragment();
                    case 1:
                        return new NotificationFragment();
                    case 2:
                        return new AdminFragment();
                    case 3:
                        return new ProfileFragment();
                    default:
                        return null;
                }
            }


            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_screen;
    }

    @Override
    protected void initToolBar() {
        getToolbarManager().setTitle(activity.getString(R.string.app_name));
    }

    @Override
    public void fragmentData(View parent, Bundle savedInstanceState) {
        homePresenter.setView(this);
        homePresenter.loadUser();
    }

    @Override
    public void userLoaded(final UserInfo userInfo) {
        if (activity != null)
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    tv_user.setText(" " + userInfo.getFirstName() + " " + userInfo.getLastName());
                }
            });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initLocalization() {
    }

    @Override
    protected void initViews() {
        ((BaseApplication) getBaseActivity().getApplication()).getAppComponent().inject(this);
        setupTabs();
    }

    private void setupTabs() {
        final String[] colors = getResources().getStringArray(R.array.vertical_ntb);

        final ViewPager viewPager = baseView.findViewById(R.id.vp_horizontal_ntb);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(getTabAdapter());

        getToolbarManager().setTitle(activity.getString(R.string.title_request_blood));

        final NavigationTabBar navigationTabBar = baseView.findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.tab_blood),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.tab_blood))
                        .title(activity.getString(R.string.title_request_blood))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.tab_notifications),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.tab_notifications))
                        .title(activity.getString(R.string.title_notifications))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.tab_admin),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.tab_admin))
                        .title(activity.getString(R.string.title_admin))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.tab_profile),
                        Color.parseColor(colors[3]))
                        .selectedIcon(getResources().getDrawable(R.drawable.tab_profile))
                        .title(activity.getString(R.string.title_profile))
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                if (index == 0) {
                    getToolbarManager().setTitle(activity.getString(R.string.title_request_blood));
                } else if (index == 1) {
                    getToolbarManager().setTitle(activity.getString(R.string.title_notifications));
                } else if (index == 2) {
                    getToolbarManager().setTitle(activity.getString(R.string.title_admin));
                } else if (index == 3) {
                    getToolbarManager().setTitle(activity.getString(R.string.title_profile));
                }
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });
    }

}
