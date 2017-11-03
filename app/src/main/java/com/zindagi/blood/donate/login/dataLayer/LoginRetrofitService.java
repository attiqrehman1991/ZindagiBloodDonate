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

package com.zindagi.blood.donate.login.dataLayer;

import org.json.JSONObject;

/**
 * Created by Chilindo User on 11/2/2017 AD.
 */

public class
LoginRetrofitService implements LoginService {
    @Override
    public void doLogin(PostServiceCallBack callBack, String email, String password) {
        // retrofit call
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "name");
            jsonObject.put("age", "27");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (email.equalsIgnoreCase("attiq@gmail.com"))
            callBack.onFailure(jsonObject);
        else
            callBack.onSuccess(jsonObject);
    }
}
