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

package com.zindagi.blood.donate.base.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.base.view.AnyToolbarManager;
import com.zindagi.blood.donate.base.view.SimpleProgress;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */
public abstract class BaseFragment extends Fragment implements OnClickListener {

    private static Object returnedData;
    protected View baseView;
    protected boolean isbaseViewCreated;
    protected BaseActivity activity;
    private View dialogView;
    private Dialog progressDialog;
    private SimpleProgress progress;


    protected void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.baseView == null) {
            isbaseViewCreated = true;
            baseView = inflater.inflate(getLayoutId(), container, false);
        } else
            isbaseViewCreated = false;
        return baseView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Fragment class", getClass() + "");
        activity = (BaseActivity) getActivity();
        initObjs();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isbaseViewCreated) {
            toolbar();
            initViews();
            initListeners();
            initLocalization();
            fragmentData(view, savedInstanceState);
        }
        initToolBar();
    }

    protected abstract void initToolBar();

    public abstract void fragmentData(View parent, Bundle savedInstanceState);

    public abstract void initListeners();

    public abstract void initLocalization();

    protected void toolbar() {

    }

    @Override
    public void onDestroy() {
        rollBackToolBar();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissProgress2();
    }

    protected abstract void initViews();

    protected void showProgress2(final String title, final String message) {
        try {
            dismissProgress2();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress = new SimpleProgress(getActivity(), title, message);
                        progress.show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgress2(int titleId, int messageID) {
        Context c = getActivity().getApplicationContext();
        showProgress2(getString(titleId), getString(messageID));
    }

    public void dismissProgress2() {
        try {
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progress != null)
                            progress.dismiss();
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initObjs() {

    }

    protected void rearrangeToolBar() {
        getToolbarManager().setSubmitVisibility(false);
        getToolbarManager().setCancelVisibility(false);
    }

    protected AnyToolbarManager getToolbarManager() {
        return activity.getToolbarManager();
    }

    public void showMaterialWheel() {
        activity.showMaterialWheel();
    }

    public boolean isMaterialWheelShowing() {
        return activity.isMaterialWheelShowing();
    }

//    public void setupToolbar(boolean isParent, int title) {
//        activity.setupToolbar(isParent, title);
//    }

    public void stopMaterialWheel() {
        activity.stopMaterialWheel();
    }

    /**
     * Override this method if need to handle back in child fragments
     *
     * @return
     */
    public boolean allowBackPress() {
        rollBackToolBar();
        return true;
    }

    protected void rollBackToolBar() {

    }

    private void initMaterialWheel(View baseView) {
        dialogView = activity.getLayoutInflater().inflate(R.layout.progressbar_circular, null);
        progressDialog = new Dialog(getActivity());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(dialogView);

    }

//    public void showProgress(final int titleRes, final int msgRes) {
//        if (activity == null)
//            return;
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (progressDialog != null && progressDialog.isShowing())
//                    dismissProgress();
//
//                progressDialog = ProgressDialog
//                        .show(getActivity(), getResources().getString(titleRes),
//                                getResources().getString(msgRes));
//            }
//        });
//    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }


//    public void dismissProgress() {
//        if (activity == null)
//            return;
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                }
//            }
//        });
//    }

    @Override
    public void onClick(View view) {
    }


    protected Object getPreviousData() {
        return returnedData;
    }

    protected void setPreviousData(Object returnedData) {
        this.returnedData = returnedData;
    }
}
