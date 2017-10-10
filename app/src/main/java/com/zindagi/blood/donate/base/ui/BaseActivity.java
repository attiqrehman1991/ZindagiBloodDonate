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

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.base.view.AnyToolbarManager;
import com.zindagi.blood.donate.base.view.SimpleProgress;
import com.zindagi.blood.donate.home.HomeFragment;
import com.zindagi.blood.donate.login.LoginFragment;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /* A HashMap of stacks, where we use tab identifier as keys..*/
    public static HashMap<String, Stack<BaseFragment>> mStacks;
    public static BaseFragment baseFragment;
    public static String mCurrentTab;
    private Dialog progressDialog;
    private View dialogView;
    private AnyToolbarManager toolbarManager;
    private SimpleProgress progress;
    private Toolbar toolbar;

    protected void showProgress2(final String title, final String message) {
        dismissProgress2();
        final Activity activity = this;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress = new SimpleProgress(activity, title, message);
                    progress.show();
                }
            });
        }
    }

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    protected void showProgress2(int titleId, int messageID) {
        Context c = getApplicationContext();
        showProgress2(getString(titleId), getString(messageID));
    }

    protected void dismissProgress2() {
        Activity activity = this;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progress != null)
                        progress.dismiss();
                }
            });

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        // setStartLanguage();
        setupToolbar();
        setUpStack();

//        LocalizationHelper.loadDefaultLanguage(getApplicationContext());
        initViews();
    }

    protected void initViews() {
        addFragment(getString(R.string.module_login), new LoginFragment());
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setContentInsetsAbsolute(0, 0);
            toolbarManager = new AnyToolbarManager(toolbar);
        }
    }

    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public int getBaseFrameLayoutId() {
        return R.id.mainFrameLayout;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUpStack() {
        if (mStacks == null) {
            mStacks = new HashMap<>();
            mStacks.put(getString(R.string.module_login), new Stack<BaseFragment>());
            mStacks.put(getString(R.string.module_home), new Stack<BaseFragment>());

        }
    }

    public AnyToolbarManager getToolbarManager() {
        return toolbarManager;
    }

    public void showProgress(int titleRes, int msgRes) {
        if (progressDialog != null && progressDialog.isShowing())
            dismissProgress();

        progressDialog = ProgressDialog
                .show(this, getResources().getString(titleRes),
                        getResources().getString(msgRes));
    }

    public void dismissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private void initMaterialWheel() {
        dialogView = getLayoutInflater().inflate(R.layout.progressbar_circular, null);
        progressDialog = new Dialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(dialogView);

    }

    public void showMaterialWheel() {


        if (null != progressDialog && isMaterialWheelShowing()) {
            stopMaterialWheel();
            progressDialog = null;
        }
        initMaterialWheel();

        progressDialog.show();
        clearParentsBackgrounds(dialogView);
    }

    public boolean isMaterialWheelShowing() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void stopMaterialWheel() {
        if (this != null) {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    @Override
    public void onBackPressed() {
//        dismissProgress();
        if (baseFragment instanceof HomeFragment || baseFragment instanceof LoginFragment) {
            super.onBackPressed();
        } else if (mCurrentTab != null && mStacks != null) {
            if (!mStacks.get(mCurrentTab).isEmpty()) {
                popStackFragment();
            } else {
            /*super.onBackPressed();*/
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    super.onBackPressed();
                } else {
                    // Restrict or warning box
                }
            }
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                super.onBackPressed();
            } else {
                // Restrict or warning box
            }
        }
    }

    private void clearParentsBackgrounds(View view) {
        while (view != null) {
            final ViewParent parent = view.getParent();
            if (parent instanceof View) {
                view = (View) parent;
                view.setBackgroundResource(android.graphics.Color.TRANSPARENT);
            } else {
                view = null;
            }
        }
    }

    public void setFragment(String tagKey, BaseFragment fragment) {
        mStacks.get(tagKey).pop();
        mStacks.get(tagKey).push(fragment);
        baseFragment = fragment;
        mCurrentTab = tagKey;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(getBaseFrameLayoutId(), fragment, null).commit();
    }

    public void popStackFragment() {
        if (mStacks.get(mCurrentTab).size() > 1) {
            BaseFragment stackFragment = mStacks.get(mCurrentTab).lastElement();

            if (stackFragment.allowBackPress()) {
                BaseFragment previousFragment = mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);
                if (previousFragment != stackFragment) {
                    mStacks.get(mCurrentTab).pop();
      /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.setCustomAnimations(R.anim.exit, R.anim.enter, R.anim.pop_enter, R.anim.pop_exit);
                    ft.replace(getBaseFrameLayoutId(), previousFragment);
                    ft.commitAllowingStateLoss();
                }
            }
        } else {
            if (mStacks.get(mCurrentTab).lastElement().allowBackPress()) {
                mStacks.get(mCurrentTab).removeAllElements();
                if (mStacks.get(getString(R.string.module_home)) != null && mStacks.get(getString(R.string.module_home)).size() >= 1) {
                    setFragment(getString(R.string.module_home), mStacks.get(getString(R.string.module_home)).lastElement());
                } else {
                    addFragment(getString(R.string.module_home), new HomeFragment());
                }
                mStacks.get(mCurrentTab).removeAllElements();
                mCurrentTab = null;
                popBackStackTillEntry(0);
            }
        }

    }

    /**
     * @param entryIndex is the index of fragment to be popped to, for example the
     *                   first fragment will have a index 0;
     */
    public void popBackStackTillEntry(int entryIndex) {
        if (getSupportFragmentManager() == null)
            return;
        if (getSupportFragmentManager().getBackStackEntryCount() <= entryIndex)
            return;
        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(entryIndex);
        if (entry != null) {
            getSupportFragmentManager().popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void addFragment(String tagKey, BaseFragment fragment) {
        Fragment stackFragment = null;
        if (mCurrentTab != null && mStacks != null) {
            if (mStacks.get(mCurrentTab).size() > 0) {
                stackFragment = mStacks.get(mCurrentTab).lastElement();
            }
        }
        if (stackFragment == null || fragment != stackFragment && mStacks.containsKey(tagKey)) {
            mStacks.get(tagKey).push(fragment);
            baseFragment = fragment;
            mCurrentTab = tagKey;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(getBaseFrameLayoutId(), fragment);
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            setFragment(tagKey, fragment);
        }
    }

//    private TimerTask timerTask = new TimerTask() {
//        @Override
//        public void run() {
//            Log.d("Timer Diff: ", "" + (System.currentTimeMillis() - lastExecutionTimeInMillis));
//            if (System.currentTimeMillis() - lastExecutionTimeInMillis < Constants.FETCH_BUSES_INTERVAL) {
//                return;
//            }
//
//            lastExecutionTimeInMillis = System.currentTimeMillis();
//            Intent busesUpdater = new Intent(BaseActivity.this, LiveBusesDataBackgroundService.class);
//            busesUpdater.putExtra("currLat", Constants.lat);
//            busesUpdater.putExtra("currLng", Constants.lng);
//            startService(busesUpdater);
//        }
//    };

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
