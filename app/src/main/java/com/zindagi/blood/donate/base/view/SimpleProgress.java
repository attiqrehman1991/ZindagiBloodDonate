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

/**
 * Created by hassanshakeel on 7/13/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.zindagi.blood.donate.R;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */
public class SimpleProgress {

    private Context mActivity;
    private KProgressHUD progress;

    public SimpleProgress(Context mActivity, String title, String message) {
        this.mActivity = mActivity;
        LayoutInflater li = LayoutInflater.from(mActivity);
        View customView = li.inflate(R.layout.custom_progress_view, null);

        if (title == null && message == null) {
            progress = KProgressHUD.create(mActivity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setCustomView(customView);
        }
        progress = KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(title)
                .setDetailsLabel(message)
                .setCancellable(false)
                .setCustomView(customView);


    }

    public KProgressHUD getProgress() {
        return progress;
    }

    public void setCustomView(View view) {
        progress.setCustomView(view);
    }


    public void show() {
        progress.show();
    }

    public void dismiss() {
        if (progress != null)
            progress.dismiss();
    }
}