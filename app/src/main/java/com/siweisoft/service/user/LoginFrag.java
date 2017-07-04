package com.siweisoft.service.user;

//by summer on 2017-07-03.

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.siweisoft.service.nim.user.UserMng;

public class LoginFrag extends BaseUIFrag<LoginUIBean,LoginDAOpe> {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getOpes().getUi().viewDataBinding.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        UserMng userMng  = new UserMng();
        userMng.doRegist(getOpes().getUi().viewDataBinding.username.getText().toString(),getOpes().getUi().viewDataBinding.pwd.getText().toString());
    }
}
