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

package com.zindagi.blood.donate.base.view;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zindagi.blood.donate.R;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class AnyToolbarManager {

    public AnyTextView tv_title;
    public ImageView iv_back;
    public ImageView iv_setting;
    private Toolbar toolbar;
    private LinearLayout layoutCancel;
    private LinearLayout layoutSubmit;

    public AnyToolbarManager(Toolbar toolbar) {
        this.toolbar = toolbar;
        initViews();
    }

    private void initViews() {
        iv_setting = toolbar.findViewById(R.id.iv_setting);
        tv_title = toolbar.findViewById(R.id.tv_title);
        iv_back = toolbar.findViewById(R.id.iv_back);
        layoutCancel = toolbar.findViewById(R.id.layoutCancel);
        layoutSubmit = toolbar.findViewById(R.id.layoutSubmit);

        tv_title.setText(layoutCancel.getContext().getString(R.string.app_name));
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void setSubmitVisibility(boolean isVisible) {
        layoutSubmit.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    public void setCancelVisibility(boolean isVisible) {
        layoutCancel.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }
}
