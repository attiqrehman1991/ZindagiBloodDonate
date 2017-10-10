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

package com.zindagi.blood.donate.base.misc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.login.model.UserInfo;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */
public class SharedPreferencesManager {

    private static SharedPreferencesManager instance;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor preferencesEditor;
    private UserInfo userInfo;

    private SharedPreferencesManager(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.app_shared_prefs), Activity.MODE_PRIVATE);
        preferencesEditor = sharedPreferences.edit();
        instance = this;
    }

    public static SharedPreferencesManager getInstance(Context mContext) {
        if (instance == null) {
            instance = new SharedPreferencesManager(mContext);
        }
        return instance;
    }

    public UserInfo getUserInfo() {
        String userInfoS = sharedPreferences.getString("UserInfo", null);
        if (userInfoS != null)
            return new Gson().fromJson(userInfoS, UserInfo.class);
        else
            return null;
    }

    public void setUserInfo(UserInfo userInfo) {
        preferencesEditor.putString("UserInfo", new Gson().toJson(userInfo));
        preferencesEditor.commit();
    }
}
