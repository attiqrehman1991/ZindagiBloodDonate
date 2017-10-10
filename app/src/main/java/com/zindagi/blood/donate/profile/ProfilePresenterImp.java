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

package com.zindagi.blood.donate.profile;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.database.UserProfileTable;
import com.zindagi.blood.donate.login.model.UserInfo;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class ProfilePresenterImp implements ProfilePresenter {
    private ProfileView view;

    @Override
    public void setView(ProfileView view) {
        this.view = view;
    }

    @Override
    public void fetchUserDetail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.showProgress2(R.string.please_wait, R.string.loading_user_information);
                // Network call
                UserProfileTable userProfileTable = new UserProfileTable();
                final UserInfo userInfo = userProfileTable.fetchData();
                if (userInfo != null) {
                    view.loadUserDetail(userInfo);
                }
                view.dismissProgress2();
            }
        }).start();
    }

    @Override
    public void updateUserDetail(final UserInfo userInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.showProgress2(R.string.please_wait, R.string.saving_detail);
                // Make Network call
                userInfo.setUserId(1);
                UserProfileTable userProfileTable = new UserProfileTable();
                userProfileTable.insertData(userInfo);
                view.dismissProgress2();
                view.showMessage("Save Successfully", 1);
            }
        }).start();
    }
}
