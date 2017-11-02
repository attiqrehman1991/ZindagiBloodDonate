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

package com.zindagi.blood.donate.login;

import android.app.Activity;
import android.content.Context;

import com.zindagi.blood.donate.interactors.Interactor;
import com.zindagi.blood.donate.login.dataLayer.LoginRetrofitService;
import com.zindagi.blood.donate.login.dataLayer.LoginService;

/**
 * Created by ChilindoUser on 11/2/2017 AD.
 */

public class LoginInterpreter implements Interactor {

    public void doLogin(final Interactor.InteractorCallBack callBack, final String email, final String password, final Activity context) {
        LoginService loginService = new LoginRetrofitService();
        loginService.doLogin(new LoginService.PostServiceCallBack() {
            @Override
            public void onSuccess(final Object posts) {
                callBack.onSuccess(posts);
            }

            @Override
            public void onFailure(final Object posts) {
                callBack.onFailure(posts);
            }
        }, email, password);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LoginService loginService = new LoginRetrofitService();
//                loginService.doLogin(new LoginService.PostServiceCallBack() {
//                    @Override
//                    public void onSuccess(final Object posts) {
//                        context.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                callBack.onSuccess(posts);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFailure(final Object posts) {
//                        context.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                callBack.onFailure(posts);
//                            }
//                        });
//                    }
//                }, email, password);
//            }
//        }).start();
    }

}
