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

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.database.UserProfileTable;
import com.zindagi.blood.donate.login.model.UserInfo;
import com.zindagi.blood.donate.notification.model.Notification;
import com.zindagi.blood.donate.notification.model.NotificationRequest;
import com.zindagi.blood.donate.notification.model.NotificationResponse;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class NotificationPresenterImp implements NotificationPresenter {
    private NotificationView view;

    @Override
    public void setView(NotificationView view) {
        this.view = view;
    }

    @Override
    public void rejectRequest(final Notification notification) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                processNotification(notification, false);
            }
        }).start();
    }

    @Override
    public void approveRequest(final Notification notification) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                processNotification(notification, true);
            }
        }).start();
    }

    private void processNotification(final Notification notification, boolean isApproved) {
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setApproved(isApproved);
        notificationRequest.setEmailId(notification.getEmailID());

        // Network call

        view.showProgress2(R.string.please_wait, R.string.notifying_backend_server);
        UserProfileTable userProfileTable = new UserProfileTable();
        int max = userProfileTable.maxUserId();

        NotificationResponse notificationResponse = new NotificationResponse();
        if (isApproved)
            notificationResponse.setMessage("Successfully Approved");
        else
            notificationResponse.setMessage("Successfully Rejected");

        // implement call
        UserInfo userInfo = new UserInfo();
        userInfo.setName(notification.getName());
        userInfo.setCellNumber("+92000000000");
        userInfo.setUserId(max + 1);
        userInfo.setAddress("Allah ke duniya");
        userInfo.setBlood_group(notification.getBloodGroup());
        userInfo.setNic("35301XXXXXXXX");
        userInfo.setEmailID(notification.getEmailID());
        userInfo.setDurationDonor(notification.getDurationBlood());
        userInfo.setAddedBy("Developer");
        userInfo.setVerifiedBy("Developer");
        userInfo.setRole(1);
        userInfo.setOnBreak(false);
        userInfo.setActivated(true);

        userProfileTable.insertData(userInfo);

        view.dismissProgress2();
        view.updateUI(notificationResponse.getMessage(), isApproved, notification);
    }


}
