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

package com.zindagi.blood.donate.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.base.helps.UIHelper;
import com.zindagi.blood.donate.base.ui.BaseApplication;
import com.zindagi.blood.donate.base.ui.BaseFragment;
import com.zindagi.blood.donate.base.view.AnyEditText;
import com.zindagi.blood.donate.login.model.UserInfo;

import javax.inject.Inject;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class ProfileFragment extends BaseFragment implements ProfileView {

    @Inject
    ProfilePresenter profilePresenter;
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void fragmentData(View parent, Bundle savedInstanceState) {
        profilePresenter.setView(this);
        profilePresenter.fetchUserDetail();
    }

    @Override
    public void initListeners() {
        baseView.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo userInfo = new UserInfo();
                userInfo.setActivated(true);
                if (ed_name.getText().toString().isEmpty()) {
                    UIHelper.showToast(activity, getString(R.string.enter_name), 2);
                    return;
                }
                userInfo.setName(ed_name.getText().toString());
                if (ed_cell_number.getText().toString().isEmpty()) {
                    UIHelper.showToast(activity, getString(R.string.enter_cell_number), 2);
                    return;
                }
                userInfo.setCellNumber(ed_cell_number.getText().toString());
                if (ed_address.getText().toString().isEmpty()) {
                    UIHelper.showToast(activity, getString(R.string.enter_address), 2);
                    return;
                }
                userInfo.setAddress(ed_address.getText().toString());
                if (ed_nic.getText().toString().isEmpty()) {
                    UIHelper.showToast(activity, getString(R.string.enter_nic), 2);
                    return;
                }
                userInfo.setNic(ed_nic.getText().toString());

                if (ed_email.getText().toString().isEmpty()) {
                    UIHelper.showToast(activity, getString(R.string.enter_email), 2);
                    return;
                }
                userInfo.setEmailID(ed_email.getText().toString());

                if (ed_duration.getText().toString().isEmpty()) {
                    UIHelper.showToast(activity, getString(R.string.enter_preferred_duration), 2);
                    return;
                }
                userInfo.setBlood_group(spinner_blood_group.getSelectedItem().toString());
                userInfo.setDurationDonor(Integer.valueOf(ed_duration.getText().toString()));

                userInfo.setOnBreak(yesSwitchBreak.isChecked());
                profilePresenter.updateUserDetail(userInfo);
            }
        });
    }

    @Override
    public void initLocalization() {

    }

    @Override
    protected void initViews() {
        ((BaseApplication) getBaseActivity().getApplication()).getAppComponent().inject(this);
        radioGroupActivated = baseView.findViewById(R.id.radioGroupActivated);
        radioGroupActivated.setVisibility(View.GONE);
        yesSwitchBreak = baseView.findViewById(R.id.yesSwitchBreak);
        radioGroupBreak = baseView.findViewById(R.id.radioGroupBreak);
        ed_name = baseView.findViewById(R.id.ed_name);
        ed_cell_number = baseView.findViewById(R.id.ed_cell_number);
        ed_address = baseView.findViewById(R.id.ed_address);
        ed_email = baseView.findViewById(R.id.ed_email);
        spinner_blood_group = baseView.findViewById(R.id.spinner_blood_group);
        ed_nic = baseView.findViewById(R.id.ed_nic);
        ed_duration = baseView.findViewById(R.id.ed_duration);
        ed_added_by = baseView.findViewById(R.id.ed_added_by);
        ed_verified_by = baseView.findViewById(R.id.ed_verified_by);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.blood_group));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_group.setAdapter(dataAdapter);
    }

    @Override
    public void loadUserDetail(final UserInfo userInfo) {
        if (activity != null)
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
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

                    String[] blood_group = getResources().getStringArray(R.array.blood_group);
                    for (int i = 0; i < blood_group.length; i++)
                        if (userInfo.getBlood_group().equals(blood_group[i]))
                            spinner_blood_group.setSelection(i);
                }
            });
    }

    @Override
    public void showMessage(final String saveSuccessfully, final int code) {
        if (activity != null)
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    UIHelper.showToast(activity, saveSuccessfully, code);
                }
            });
    }
}
