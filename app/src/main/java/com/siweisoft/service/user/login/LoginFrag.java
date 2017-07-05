package com.siweisoft.service.user.login;

//by summer on 2017-07-03.

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.siweisoft.service.Constant.Value;
import com.siweisoft.service.R;
import com.siweisoft.service.chat.chatutil.ChatInit;

import butterknife.OnClick;

public class LoginFrag extends BaseUIFrag<LoginUIBean,LoginDAOpe> {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.button})
    public void onClickEvent(View view){
        ChatInit.getInstance().doLogin(Value.IP,Value.PROT,getOpes().getUi().viewDataBinding.username.getText().toString());
    }
}
