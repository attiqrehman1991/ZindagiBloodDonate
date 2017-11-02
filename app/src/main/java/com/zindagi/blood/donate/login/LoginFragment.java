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

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.base.helps.OSHelper;
import com.zindagi.blood.donate.base.helps.UIHelper;
import com.zindagi.blood.donate.base.ui.BaseApplication;
import com.zindagi.blood.donate.base.ui.BaseFragment;
import com.zindagi.blood.donate.base.view.AnyEditText;
import com.zindagi.blood.donate.base.view.AnyTextView;
import com.zindagi.blood.donate.home.HomeFragment;
import com.zindagi.blood.donate.login.model.UserInfo;

import javax.inject.Inject;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class LoginFragment extends BaseFragment implements LoginView {

    @Inject
    LoginPresenter presenter;
    private AnyTextView tv_forget;
    private AnyTextView tv_sign_up;
    private AnyEditText et_email;
    private AnyEditText et_password;
    private Button button_login;

    @Override
    public String getEmail() {
        return et_email.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void successfullyLoggedIn() {
        Toast.makeText(activity, getString(R.string.successfully_logged_in), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requiredPassword() {
        Toast.makeText(activity, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requiredEmail() {
        Toast.makeText(activity, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailNotValid() {
        Toast.makeText(activity, getString(R.string.email_not_valid), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void passwordNotValid() {
        Toast.makeText(activity, getString(R.string.password_not_valid), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                if (OSHelper.isInternetAvailable(activity)) {
                    if (validateInput())
                        presenter.attemptLogin();
                    else
                        UIHelper.showToast(getActivity().getApplicationContext(), activity.getString(R.string.invalid_username_or_password), 2);
                } else
                    UIHelper.showToast(getActivity().getApplicationContext(), activity.getString(R.string.no_internet), 2);
                break;
            case R.id.tv_forget:
//                toggleLanguage(R.id.bt_english);
                break;
            case R.id.tv_sign_up:
//                toggleLanguage(R.id.bt_arabic);
                break;
        }
    }

    @Override
    public void loginResults(UserInfo userInfo) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getBaseActivity().addFragment(getString(R.string.module_home), new HomeFragment());
//
//                Intent intent = new Intent(activity, MainActivity.class);
//                activity.startActivity(intent);
//                activity.finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.login_screen;
    }

    @Override
    protected void initToolBar() {
        getToolbarManager().setTitle(activity.getString(R.string.login));
    }

    @Override
    public void fragmentData(View parent, Bundle savedInstanceState) {
        presenter.setView(this);
        presenter.checkAlreadyLoggedIn();
    }

    @Override
    public void initListeners() {
        button_login.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
    }

    @Override
    public void initLocalization() {
        et_email.setHint(activity.getString(R.string.email_hint));
        et_password.setHint(activity.getString(R.string.password_hint));
        button_login.setText(activity.getString(R.string.login));
        tv_sign_up.setText(activity.getString(R.string.sign_up_now));

        String sign_up_now = activity.getString(R.string.sign_up_now);
        String dont_have_account = activity.getString(R.string.dont_have_account);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(dont_have_account);

        SpannableString ss1 = new SpannableString(sign_up_now);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        stringBuffer.append(ss1);

        tv_sign_up.setText(stringBuffer.toString());
    }

    @Override
    protected void initViews() {
        ((BaseApplication) getBaseActivity().getApplication()).getAppComponent().inject(this);
        button_login = baseView.findViewById(R.id.button_login);
        tv_forget = baseView.findViewById(R.id.tv_forget);
        tv_sign_up = baseView.findViewById(R.id.tv_sign_up);
        et_email = baseView.findViewById(R.id.et_email);
        et_password = baseView.findViewById(R.id.et_password);
    }

    private boolean validateInput() {
        boolean validate = true;
        if (et_email.getText().toString() == null || et_email.getText().toString().length() <= 0) {
            validate = false;
        }

        if (et_password.getText().toString() == null || et_password.getText().toString().length() <= 0) {
            validate = false;
        }

        return validate;
    }
}
