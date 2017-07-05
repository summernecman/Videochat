package com.siweisoft.service.user.login;

//by summer on 2017-07-03.

import com.android.lib.bean.BaseBean;

public class UserInfo  extends BaseBean{

    private String name;

    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
