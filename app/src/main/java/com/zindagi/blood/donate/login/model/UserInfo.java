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

package com.zindagi.blood.donate.login.model;
/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserInfo extends RealmObject {

    @PrimaryKey
    private Integer userId;
    private String name;
    private String cellNumber;
    private String address;
    private String blood_group;
    private String nic;
    private String emailID;
    private int durationDonor;
    private String addedBy;
    private String verifiedBy;
    private boolean isActivated;
    /**
     * Admin =1
     * Volunteer = 2
     * Simple User = 3
     */
    private int role;
    private boolean onBreak;

    public static UserInfo buildUserInfo(UserInfo userInfo) {
        UserInfo userInfo1 = new UserInfo();
        userInfo1.userId = userInfo.userId;
        userInfo1.name = userInfo.name;
        userInfo1.cellNumber = userInfo.cellNumber;
        userInfo1.address = userInfo.address;
        userInfo1.blood_group = userInfo.blood_group;
        userInfo1.nic = userInfo.nic;
        userInfo1.emailID = userInfo.emailID;
        userInfo1.durationDonor = userInfo.durationDonor;
        userInfo1.addedBy = userInfo.addedBy;
        userInfo1.verifiedBy = userInfo.verifiedBy;
        userInfo1.role = userInfo.role;
        userInfo1.onBreak = userInfo.onBreak;
        userInfo1.isActivated = userInfo.isActivated;
        return userInfo1;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public int getDurationDonor() {
        return durationDonor;
    }

    public void setDurationDonor(int durationDonor) {
        this.durationDonor = durationDonor;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isOnBreak() {
        return onBreak;
    }

    public void setOnBreak(boolean onBreak) {
        this.onBreak = onBreak;
    }
}