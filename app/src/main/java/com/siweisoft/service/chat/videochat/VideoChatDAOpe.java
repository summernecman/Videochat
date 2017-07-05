package com.siweisoft.service.chat.videochat;

//by summer on 2017-07-04.

import android.content.Context;

import com.android.lib.base.ope.BaseDAOpe;
import com.example.anychatfeatures.RoleInfo;

public class VideoChatDAOpe extends BaseDAOpe {

    RoleInfo roleInfo;

    public VideoChatDAOpe(Context context) {
        super(context);
    }

    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfo roleInfo) {
        this.roleInfo = roleInfo;
    }
}
