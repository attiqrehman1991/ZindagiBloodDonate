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

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.base.ui.BaseActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Attiq on 11/3/2017 AD.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginFragmentTest {

    @Rule
    public ActivityTestRule<BaseActivity> activityTestRule = new ActivityTestRule(BaseActivity.class);
    private String email;
    private String password;

    @Before
    public void initValidString() {
        email = "attiqrao@gmail.com";
        password = "1234$gA443h";
    }

    @Test
    public void loginTest() {
        onView(withId(R.id.et_email)).perform(typeText(email));
        onView(withId(R.id.et_password)).perform(typeText(password));

        onView(withId(R.id.button_login)).perform(click());
    }

}
