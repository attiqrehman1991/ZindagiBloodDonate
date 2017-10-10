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

package com.zindagi.blood.donate.notification;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.base.helps.RealmHelper;
import com.zindagi.blood.donate.base.helps.UIHelper;
import com.zindagi.blood.donate.base.ui.BaseApplication;
import com.zindagi.blood.donate.base.ui.BaseFragment;
import com.zindagi.blood.donate.database.NotificationTable;
import com.zindagi.blood.donate.notification.model.Notification;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class NotificationFragment extends BaseFragment implements NotificationView, NotificationInterface {
    NotificationRecyclerViewAdapter notificationRecyclerViewAdapter;
    Realm realm;
    @Inject
    NotificationPresenter notificationPresenter;
    private RecyclerView recyclerViewNotifications;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void fragmentData(View parent, Bundle savedInstanceState) {
        notificationPresenter.setView(this);
        realm = new RealmHelper().realm();
        updateAdapter();

        realm.where(Notification.class).equalTo("isApproved", 3).findAll().addChangeListener(new RealmChangeListener<RealmResults<Notification>>() {
            @Override
            public void onChange(RealmResults<Notification> notificationRealmResults) {
                try {
                    List<Notification> notificationList = new ArrayList<>();
                    for (Notification notification : notificationRealmResults)
                        notificationList.add(Notification.buildNotification(notification));
                    notificationRecyclerViewAdapter.update(notificationList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateAdapter() {
        RealmResults<Notification> notificationRealmResults = realm.where(Notification.class).equalTo("isApproved", 3).findAll();
        List<Notification> notificationList = new ArrayList<>();
        for (Notification notification : notificationRealmResults)
            notificationList.add(Notification.buildNotification(notification));
        notificationRecyclerViewAdapter.update(notificationList);
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
        recyclerViewNotifications = baseView.findViewById(R.id.recyclerViewNotifications);
        notificationRecyclerViewAdapter = new NotificationRecyclerViewAdapter(new ArrayList<Notification>(), this);
        recyclerViewNotifications.setAdapter(notificationRecyclerViewAdapter);
    }

    @Override
    public void approveRequest(Notification notification) {
        notificationPresenter.approveRequest(notification);
    }

    @Override
    public void rejectRequest(Notification notification) {
        notificationPresenter.rejectRequest(notification);
    }

    @Override
    public void showMessage(int code) {

    }

    @Override
    public void updateUI(final String message, final boolean isApproved, final Notification notification) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NotificationTable notificationTable = new NotificationTable();
                if (isApproved) {
                    notificationTable.approveRequest(notification);
                    UIHelper.showToast(activity, message, 1);
                } else {
                    notificationTable.rejectRequest(notification);
                    UIHelper.showToast(activity, message, 2);
                }
                updateAdapter();
            }
        });
    }
}
