package com.siweisoft.service.user.userlist;

//by summer on 2017-07-04.

import android.content.Context;

import com.android.lib.base.ope.BaseDAOpe;
import com.android.lib.bean.databean.BaseDABean;
import com.example.anychatfeatures.RoleInfo;

public class UserListDAOpe extends BaseDAOpe{

    RoleInfo roleInfo;

    public UserListDAOpe(Context context) {
        super(context);
    }

    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfo roleInfo) {
        this.roleInfo = roleInfo;
    }
}
