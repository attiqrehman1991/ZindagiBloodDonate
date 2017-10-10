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

package com.zindagi.blood.donate.requestBlood;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.base.helps.UIHelper;
import com.zindagi.blood.donate.base.ui.BaseApplication;
import com.zindagi.blood.donate.base.ui.BaseFragment;
import com.zindagi.blood.donate.base.view.AnyAutoCompleteTextView;
import com.zindagi.blood.donate.database.HospitalTable;
import com.zindagi.blood.donate.requestBlood.adapter.RequestBloodDataAdapter;
import com.zindagi.blood.donate.requestBlood.model.Hospital;
import com.zindagi.blood.donate.requestBlood.model.RequestBlood;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class RequestBloodFragment extends BaseFragment implements RequestBloodView {

    @Inject
    RequestBloodPresenter presenter;
    private Spinner spinner_blood_group;
    private Spinner spinner_age;
    private Spinner spinner_ta;
    private Spinner spinner_no_bottles;
    private AnyAutoCompleteTextView searchHospital;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_request_blood;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void fragmentData(View parent, Bundle savedInstanceState) {
        presenter.setView(this);
    }

    @Override
    public void initListeners() {
        searchHospital.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = parent.getItemAtPosition(position);
                if (object instanceof Hospital) {
                    Hospital userInfo = (Hospital) object;
                    searchHospital.setText(userInfo.getHospitalName());
                }
            }
        });
        baseView.findViewById(R.id.button_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBlood requestBlood = new RequestBlood();
                requestBlood.setAgeDiff(spinner_age.getSelectedItem().toString());
                requestBlood.setBloodGroup(spinner_blood_group.getSelectedItem().toString());
                if (searchHospital.getText().toString().isEmpty()) {
                    UIHelper.showToast(activity, getString(R.string.enter_hospital_name), 2);
                    return;
                }
                requestBlood.setHospitalName(searchHospital.getText().toString());
                requestBlood.setNo_of_bottles(spinner_no_bottles.getSelectedItem().toString());
                requestBlood.setTa_da(spinner_ta.getSelectedItem().toString());
                presenter.requestBlood(requestBlood);
            }
        });
    }

    @Override
    public void initLocalization() {

    }

    @Override
    protected void initViews() {
        ((BaseApplication) getBaseActivity().getApplication()).getAppComponent().inject(this);
        spinner_blood_group = baseView.findViewById(R.id.spinner_blood_group);
        spinner_age = baseView.findViewById(R.id.spinner_age);
        spinner_ta = baseView.findViewById(R.id.spinner_ta);
        searchHospital = baseView.findViewById(R.id.searchHospital);
        spinner_no_bottles = baseView.findViewById(R.id.spinner_no_bottles);

        ArrayAdapter<String> dataAdapterBG = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.blood_group));
        dataAdapterBG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_group.setAdapter(dataAdapterBG);

        ArrayAdapter<String> dataAdapterAge = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.age_diff));
        dataAdapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_age.setAdapter(dataAdapterAge);

        ArrayAdapter<String> dataAdapterTA = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.pf));
        dataAdapterTA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ta.setAdapter(dataAdapterTA);

        ArrayAdapter<String> dataAdapterBottles = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bottles));
        dataAdapterBottles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_no_bottles.setAdapter(dataAdapterBottles);

        new Thread(new Runnable() {
            @Override
            public void run() {
                HospitalTable hospitalTable = new HospitalTable();
                final List<Hospital> hospitalList = hospitalTable.fetchData();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RequestBloodDataAdapter adapter = new RequestBloodDataAdapter(activity, hospitalList);
                        searchHospital.setAdapter(adapter);
                    }
                });
            }
        }).start();
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
