package com.siweisoft.service.main;

//by summer on 2017-07-03.

import android.os.Bundle;

import com.android.lib.base.activity.BaseUIActivity;
import com.siweisoft.service.R;
import com.siweisoft.service.user.LoginFrag;

public class MainAct extends BaseUIActivity<MainUIBean,MainDAOpe> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.serviceroot,new LoginFrag()).commitAllowingStateLoss();
    }
}
