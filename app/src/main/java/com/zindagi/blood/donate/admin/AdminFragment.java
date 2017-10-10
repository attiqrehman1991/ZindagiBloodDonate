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

package com.zindagi.blood.donate.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.admin.adapter.UserDataAdapter;
import com.zindagi.blood.donate.base.helps.UIHelper;
import com.zindagi.blood.donate.base.ui.BaseApplication;
import com.zindagi.blood.donate.base.ui.BaseFragment;
import com.zindagi.blood.donate.base.view.AnyAutoCompleteTextView;
import com.zindagi.blood.donate.base.view.AnyEditText;
import com.zindagi.blood.donate.database.UserProfileTable;
import com.zindagi.blood.donate.login.model.UserInfo;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class AdminFragment extends BaseFragment implements AdminView {
    @Inject
    AdminPresenter adminPresenter;
    UserInfo userInfo;
    private AnyAutoCompleteTextView searchUser;
    private AnyEditText ed_name;
    private AnyEditText ed_nic;
    private AnyEditText ed_added_by;
    private AnyEditText ed_verified_by;
    private AnyEditText ed_duration;
    private AnyEditText ed_address;
    private AnyEditText ed_cell_number;
    private AnyEditText ed_email;
    private Spinner spinner_blood_group;
    private RadioGroup radioGroupBreak;
    private RadioGroup radioGroupActivated;
    private RadioButton yesSwitchBreak;
    private RadioButton noSwitchBreak;
    private RadioButton yesSwitchActivated;
    private RadioButton noSwitchActivated;
    private FloatingActionButton floatingActionButtonBlock;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_admin;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void fragmentData(View parent, Bundle savedInstanceState) {
        adminPresenter.setView(this);
    }

    @Override
    public void initListeners() {
        searchUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = parent.getItemAtPosition(position);
                if (object instanceof UserInfo) {
                    userInfo = (UserInfo) object;
                    searchUser.setText(userInfo.getName());
                    fillFormData(userInfo);
                    floatingActionButtonBlock.setVisibility(View.VISIBLE);
                }
            }
        });
        floatingActionButtonBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int message;
                if (userInfo.isActivated())
                    message = R.string.do_you_want_to_block_this_user;
                else
                    message = R.string.do_you_want_to_unblock_this_user;
                UIHelper.showAlertDialog(R.string.confirm, message, activity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adminPresenter.blockUser(userInfo);
                    }
                });
            }
        });
    }

    @Override
    public void initLocalization() {
        searchUser.setHint(activity.getString(R.string.enter_user_name));
    }

    @Override
    protected void initViews() {
        ((BaseApplication) getBaseActivity().getApplication()).getAppComponent().inject(this);
        floatingActionButtonBlock = baseView.findViewById(R.id.floatingActionButtonBlock);
        radioGroupActivated = baseView.findViewById(R.id.radioGroupActivated);
        yesSwitchActivated = baseView.findViewById(R.id.yesSwitchActivated);
        noSwitchActivated = baseView.findViewById(R.id.noSwitchActivated);
        radioGroupBreak = baseView.findViewById(R.id.radioGroupBreak);
        yesSwitchBreak = baseView.findViewById(R.id.yesSwitchBreak);
        noSwitchBreak = baseView.findViewById(R.id.noSwitchBreak);
        ed_name = baseView.findViewById(R.id.ed_name);
        ed_cell_number = baseView.findViewById(R.id.ed_cell_number);
        ed_address = baseView.findViewById(R.id.ed_address);
        ed_email = baseView.findViewById(R.id.ed_email);
        spinner_blood_group = baseView.findViewById(R.id.spinner_blood_group);
        ed_nic = baseView.findViewById(R.id.ed_nic);
        ed_duration = baseView.findViewById(R.id.ed_duration);
        ed_added_by = baseView.findViewById(R.id.ed_added_by);
        ed_verified_by = baseView.findViewById(R.id.ed_verified_by);

        ed_name.setClickable(false);
        ed_cell_number.setClickable(false);
        ed_address.setClickable(false);
        ed_nic.setClickable(false);
        ed_email.setClickable(false);
        ed_duration.setClickable(false);
        ed_added_by.setClickable(false);
        ed_verified_by.setClickable(false);
        yesSwitchActivated.setClickable(false);
        noSwitchActivated.setClickable(false);
        yesSwitchBreak.setClickable(false);
        noSwitchBreak.setClickable(false);

        ed_name.setFocusable(false);
        ed_cell_number.setFocusable(false);
        ed_address.setFocusable(false);
        ed_nic.setFocusable(false);
        ed_email.setFocusable(false);
        ed_duration.setFocusable(false);
        ed_added_by.setFocusable(false);
        ed_verified_by.setFocusable(false);
        yesSwitchActivated.setFocusable(false);
        noSwitchActivated.setFocusable(false);
        yesSwitchBreak.setFocusable(false);
        noSwitchBreak.setFocusable(false);
        spinner_blood_group.setEnabled(false);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.blood_group));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_group.setAdapter(dataAdapter);

        searchUser = baseView.findViewById(R.id.searchUser);
        searchUser.setThreshold(0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserProfileTable userProfileTable = new UserProfileTable();
                final List<UserInfo> userInfoList = userProfileTable.fetchAllData();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserDataAdapter adapter = new UserDataAdapter(activity, userInfoList);
                        searchUser.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    @Override
    public void refreshView(final UserInfo userInfo) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fillFormData(userInfo);
            }
        });
    }

    private void fillFormData(UserInfo userInfo) {
        baseView.findViewById(R.id.layout).setVisibility(View.VISIBLE);
        ed_name.setText(userInfo.getName());
        ed_cell_number.setText(userInfo.getCellNumber());
        ed_address.setText(userInfo.getAddress());
        ed_nic.setText(userInfo.getNic());
        ed_email.setText(userInfo.getEmailID());
        ed_duration.setText(String.valueOf(userInfo.getDurationDonor()));
        ed_added_by.setText(userInfo.getAddedBy());
        ed_verified_by.setText(userInfo.getVerifiedBy());
        if (userInfo.isOnBreak())
            radioGroupBreak.check(R.id.yesSwitchBreak);
        else
            radioGroupBreak.check(R.id.noSwitchBreak);
        if (userInfo.isActivated())
            radioGroupActivated.check(R.id.yesSwitchActivated);
        else
            radioGroupActivated.check(R.id.noSwitchActivated);

        String[] blood_group = getResources().getStringArray(R.array.blood_group);
        for (int i = 0; i < blood_group.length; i++)
            if (userInfo.getBlood_group().equals(blood_group[i]))
                spinner_blood_group.setSelection(i);
    }
}
