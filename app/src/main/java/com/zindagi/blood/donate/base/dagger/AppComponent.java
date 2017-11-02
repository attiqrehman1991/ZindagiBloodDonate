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

package com.zindagi.blood.donate.base.dagger;

import com.zindagi.blood.donate.admin.AdminFragment;
import com.zindagi.blood.donate.admin.dagger.AdminPresenterModule;
import com.zindagi.blood.donate.home.HomeFragment;
import com.zindagi.blood.donate.home.dagger.HomePresenterModule;
import com.zindagi.blood.donate.login.mvp.LoginFragment;
import com.zindagi.blood.donate.login.dagger.LoginPresenterModule;
import com.zindagi.blood.donate.notification.NotificationFragment;
import com.zindagi.blood.donate.notification.dagger.NotificationPresenterModule;
import com.zindagi.blood.donate.profile.ProfileFragment;
import com.zindagi.blood.donate.profile.dagger.ProfilePresenterModule;
import com.zindagi.blood.donate.requestBlood.RequestBloodFragment;
import com.zindagi.blood.donate.requestBlood.dagger.RequestBloodPresenterModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

@Singleton
@Component(modules = {AppModule.class, LoginPresenterModule.class, HomePresenterModule.class, AdminPresenterModule.class, NotificationPresenterModule.class, ProfilePresenterModule.class, RequestBloodPresenterModule.class})
public interface AppComponent {

    void inject(LoginFragment loginFragment);

    void inject(HomeFragment homeFragment);

    void inject(AdminFragment adminFragment);

    void inject(NotificationFragment notificationFragment);

    void inject(ProfileFragment profileFragment);

    void inject(RequestBloodFragment requestBloodFragment);
}