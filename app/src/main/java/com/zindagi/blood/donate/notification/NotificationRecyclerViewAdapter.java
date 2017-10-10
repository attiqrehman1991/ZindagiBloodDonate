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

package com.zindagi.blood.donate.notification;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zindagi.blood.donate.R;
import com.zindagi.blood.donate.base.view.AnyTextView;
import com.zindagi.blood.donate.notification.model.Notification;

import java.util.List;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */
public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {

    private final List<Notification> mValues;
    private NotificationFragment notificationFragment;

    public NotificationRecyclerViewAdapter(List<Notification> items, NotificationFragment notificationFragment) {
        mValues = items;
        this.notificationFragment = notificationFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_collection, parent, false);
        return new ViewHolder(view);
    }

    public void update(List<Notification> notificationList) {
        mValues.clear();
        mValues.addAll(notificationList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_name.setText(mValues.get(position).getName());
        holder.tv_blood_group.setText(mValues.get(position).getBloodGroup());
        holder.img_approve.setTag(mValues.get(position));
        holder.img_delete.setTag(mValues.get(position));
        holder.img_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification notification = (Notification) v.getTag();
                notificationFragment.approveRequest(notification);
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification notification = (Notification) view.getTag();
                notificationFragment.rejectRequest(notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final AnyTextView tv_blood_group;
        public final AnyTextView tv_name;
        public final ImageView img_delete;
        public final ImageView img_approve;
//        public Collection mItem;

        public ViewHolder(View view) {
            super(view);
            tv_blood_group = view.findViewById(R.id.tv_blood_group);
            tv_name = view.findViewById(R.id.tv_name);
            img_delete = view.findViewById(R.id.img_delete);
            img_approve = view.findViewById(R.id.img_approve);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_name.getText() + "'";
        }
    }
}
