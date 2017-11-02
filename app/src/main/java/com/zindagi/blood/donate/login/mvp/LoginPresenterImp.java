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

package com.zindagi.blood.donate.login.mvp;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.admin.model.User;
import com.zindagi.blood.donate.database.HospitalTable;
import com.zindagi.blood.donate.database.NotificationTable;
import com.zindagi.blood.donate.database.UserProfileTable;
import com.zindagi.blood.donate.interactors.Interactor;
import com.zindagi.blood.donate.login.LoginInterpreter;
import com.zindagi.blood.donate.login.dataLayer.LoginRetrofitService;
import com.zindagi.blood.donate.login.dataLayer.LoginService;
import com.zindagi.blood.donate.login.model.LoginRequest;
import com.zindagi.blood.donate.login.model.UserInfo;
import com.zindagi.blood.donate.notification.model.Notification;
import com.zindagi.blood.donate.requestBlood.model.Hospital;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class LoginPresenterImp implements LoginPresenter {
    private LoginView view;
    private LoginInterpreter interactor;

    @Override
    public void setView(LoginView view, LoginInterpreter interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void checkAlreadyLoggedIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fetchUserDetail();
            }
        }).start();
    }

    @Override
    public boolean isEmailValid(String email) {
        String EMAIL_PATTERN = "(?:.+)(?:[^\\%\\^\\)\\(]*)(?:\\@)(?:.+)(?:[^\\%\\^\\)\\(]*)(?:\\.)(?:[^\\%\\^\\)\\(]*)(?:.+)";
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }

    @Override
    public boolean isPasswordValid(String password) {
        String PASSWORD_PATTERN = "(?=.*[A-Z]).{6,23}";
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }

    @Override
    public void attemptLogin() {
        final String email = view.getEmail();
        final String password = view.getPassword();
        if (email.isEmpty()) {
            view.requiredEmail();
            return;
        }
        if (!isEmailValid(email)) {
            view.emailNotValid();
            return;
        }
        if (password.isEmpty()) {
            view.requiredPassword();
            return;
        }
        if (!isPasswordValid(password)) {
            view.passwordNotValid();
            return;
        }

        interactor = new LoginInterpreter();
        interactor.doLogin(new Interactor.InteractorCallBack() {
            @Override
            public void onSuccess(Object posts) {
                try {
                    JSONObject jsonObject = (JSONObject) posts;
                    User user = new User();
                    user.setName(jsonObject.getString("name"));
                    user.setCell_Number(jsonObject.getString("age"));
                    view.setUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                view.successfullyLoggedIn();
            }

            @Override
            public void onFailure(Object posts) {
                view.failedToLoggedIn();
            }
        }, email, password, view.getActivity());
    }

    private void doLogin(final String username, final String password) {
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserId(username);
        loginRequest.setPassword(password);


        view.showProgress2(R.string.please_wait, R.string.signing_in);

        // implement network call
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Attiq Ur Rehman");
        userInfo.setCellNumber("+92000000000");
        userInfo.setUserId(1);
        userInfo.setAddress("Allah ke duniya");
        userInfo.setBlood_group("AB+");
        userInfo.setNic("35301XXXXXXXX");
        userInfo.setEmailID("attiq@zindgi.com");
        userInfo.setDurationDonor(4);
        userInfo.setAddedBy("Developer");
        userInfo.setVerifiedBy("Developer");
        userInfo.setRole(1);
        userInfo.setOnBreak(false);
        userInfo.setActivated(true);

        UserProfileTable userProfileTable = new UserProfileTable();
        userProfileTable.insertData(userInfo);


        // Supposed to be from Push Notifications
        List<Notification> notificationList = new ArrayList<>();
        Notification notification = new Notification();
        notification.setName("Attiq Ur Rehman");
        notification.setBloodGroup("AB+");
        notification.setDurationBlood(3);
        notification.setEmailID("attiq@zindgi.com");
        notification.setRoles("Donor");
        notification.setApproved(3);
        notificationList.add(notification);

        notification = new Notification();
        notification.setName("Bilal Rao");
        notification.setBloodGroup("AB-");
        notification.setDurationBlood(4);
        notification.setApproved(3);
        notification.setEmailID("bilal@zindgi.com");
        notification.setRoles("volunteer");
        notificationList.add(notification);

        notification = new Notification();
        notification.setName("Hassan");
        notification.setBloodGroup("0-");
        notification.setApproved(3);
        notification.setDurationBlood(3);
        notification.setEmailID("hassan@zindgi.com");
        notification.setRoles("admin");
        notificationList.add(notification);

        // Need to load according to your requirements
        List<Hospital> hospitalList = new ArrayList<>();
        Hospital hospital = new Hospital();
        hospital.setHospitalID(1);
        hospital.setHospitalName("Ganga Ram");
        hospital.setPlace("Lahore");
        hospitalList.add(hospital);

        hospital = new Hospital();
        hospital.setHospitalID(2);
        hospital.setHospitalName("Jinnah");
        hospital.setPlace("Lahore");

        hospital = new Hospital();
        hospital.setHospitalID(3);
        hospital.setHospitalName("National");
        hospital.setPlace("Lahore");
        hospitalList.add(hospital);

        hospital = new Hospital();
        hospital.setHospitalID(4);
        hospital.setHospitalName("Sharif");
        hospital.setPlace("Lahore");
        hospitalList.add(hospital);

        HospitalTable hospitalTable = new HospitalTable();
        hospitalTable.insertData(hospitalList);

        NotificationTable notificationTable = new NotificationTable();
        notificationTable.insertData(notificationList);

        view.dismissProgress2();
        fetchUserDetail();
    }

    @Override
    public void fetchUserDetail() {
        UserProfileTable userProfileTable = new UserProfileTable();
        final UserInfo userInfo = userProfileTable.fetchData();
        if (userInfo != null) {
            view.loginResults(userInfo);
        }
    }
}
