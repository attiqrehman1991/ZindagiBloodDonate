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

package com.zindagi.blood.donate.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.base.view.AnyTextView;
import com.zindagi.blood.donate.login.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */
public class UserDataAdapter extends BaseAdapter implements Filterable {

    List<UserInfo> items;
    private Context mContext;
    private List<UserInfo> resultList = new ArrayList<>();

    public UserDataAdapter(Context context, List<UserInfo> items) {
        mContext = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public UserInfo getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_row, parent, false);
        }
        ((AnyTextView) convertView.findViewById(R.id.textID)).setText(getItem(position).getName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<UserInfo> buses = findUser(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = buses;
                    filterResults.count = buses.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<UserInfo>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    /**
     * Returns a search result for the given vehicle Number Plate or Id.
     */
    private List<UserInfo> findUser(String enteredText) {
        List<UserInfo> tempList = new ArrayList<>();
        // Adding predicate for custom object
        for (UserInfo vehicleBasicDataDTO : items) {
            if (vehicleBasicDataDTO.getName().toLowerCase().contains(enteredText.toString().toLowerCase()) || vehicleBasicDataDTO.getEmailID().toLowerCase().contains(enteredText.toString().toLowerCase())) {
                tempList.add(vehicleBasicDataDTO);
            }
        }
        return tempList;
    }
}