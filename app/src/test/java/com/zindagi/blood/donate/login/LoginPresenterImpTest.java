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

import com.zindagi.blood.donate.admin.model.User;
import com.zindagi.blood.donate.login.dataLayer.LoginService;
import com.zindagi.blood.donate.login.mvp.LoginPresenterImp;
import com.zindagi.blood.donate.login.mvp.LoginView;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ChilindoUser on 11/1/2017 AD.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImpTest {

    @Mock
    LoginView view;
    @Mock
    LoginInterpreter loginInterpreter;
    LoginPresenterImp presenter;

    @Before
    public void setUp() throws Exception {
        this.presenter = new LoginPresenterImp();
        this.presenter.setView(view, loginInterpreter);
    }

    @Test
    public void test011() throws Exception {
        when(view.getEmail()).thenReturn("");
        when(view.getPassword()).thenReturn("");
        presenter.attemptLogin();
        verify(view).requiredEmail();
    }

    @Test
    public void test020() throws Exception {
        when(view.getEmail()).thenReturn("attiqgmail.com");
        when(view.getPassword()).thenReturn("");
        presenter.attemptLogin();
        verify(view).emailNotValid();
    }

    @Test
    public void test021() throws Exception {
        when(view.getEmail()).thenReturn("attiq-gmail.com");
        when(view.getPassword()).thenReturn("");
        presenter.attemptLogin();
        verify(view).emailNotValid();
    }

    @Test
    public void test022() throws Exception {
        when(view.getEmail()).thenReturn("@gmail.com");
        when(view.getPassword()).thenReturn("");
        presenter.attemptLogin();
        verify(view).emailNotValid();
    }

    @Test
    public void test030() throws Exception {
        when(view.getEmail()).thenReturn("attiq@gmail.com");
        when(view.getPassword()).thenReturn("");
        presenter.attemptLogin();
        verify(view).requiredPassword();
    }

    @Test
    public void test031() throws Exception {
        when(view.getEmail()).thenReturn("attiq@gmail.com");
        when(view.getPassword()).thenReturn("abc");
        presenter.attemptLogin();
        verify(view).passwordNotValid();
    }

    @Test
    public void test032() throws Exception {
        when(view.getEmail()).thenReturn("attiq@gmail.com");
        when(view.getPassword()).thenReturn("abc134");
        presenter.attemptLogin();
        verify(view).passwordNotValid();
    }

    @Test
    public void test033() throws Exception {
        when(view.getEmail()).thenReturn("attiq@gmail.com");
        when(view.getPassword()).thenReturn("ab34@b");
        presenter.attemptLogin();
        verify(view).passwordNotValid();
    }

    @Test
    public void test040() throws Exception {
        when(view.getEmail()).thenReturn("attiq@gmail.com");
        when(view.getPassword()).thenReturn("mkyong1A@");
        presenter.attemptLogin();
        verify(view).failedToLoggedIn();
    }

    @Test
    public void test041() throws Exception {
        when(view.getEmail()).thenReturn("attiq@chilindo.com");
        when(view.getPassword()).thenReturn("mkyong1A@");
        presenter.attemptLogin();
        verify(view).successfullyLoggedIn();
    }
}