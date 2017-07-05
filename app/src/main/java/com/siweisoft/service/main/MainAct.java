package com.siweisoft.service.main;

//by summer on 2017-07-03.

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.lib.base.activity.BaseUIActivity;
import com.android.lib.constant.ValueConstant;
import com.android.lib.util.FragmentUtil;
import com.android.lib.util.LogUtil;
import com.android.lib.view.bottommenu.MessageEvent;
import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatObjectEvent;
import com.siweisoft.service.R;
import com.siweisoft.service.chat.chatutil.ChatInit;
import com.siweisoft.service.user.login.LoginFrag;
import com.siweisoft.service.user.userlist.UserListFrag;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class  MainAct extends BaseUIActivity<MainUIBean,MainDAOpe> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChatInit.getInstance().initSDK(activity);
        ChatInit.getInstance().initListener(
                new AnyChatBaseEvent() {
                    @Override
                    public void OnAnyChatConnectMessage(boolean bSuccess) {
                        LogUtil.E("OnAnyChatConnectMessage"+bSuccess);
                    }

                    @Override
                    public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
                        LogUtil.E("OnAnyChatLoginMessage"+dwUserId+""+dwErrorCode);
                        ChatInit.getInstance().enterRoom(1234,"");
                        Fragment fragment = new UserListFrag();
                        Bundle bundle = new Bundle();
                        bundle.putInt(ValueConstant.DATA_DATA,dwUserId);
                        fragment.setArguments(bundle);
                        FragmentUtil.getInstance().add(activity,R.id.serviceroot,fragment);
                    }

                    @Override
                    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
                        LogUtil.E("OnAnyChatEnterRoomMessage"+dwRoomId+""+dwErrorCode);
                        ChatInit.getInstance().openLocalCamera();
                    }

                    @Override
                    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
                        LogUtil.E("OnAnyChatOnlineUserMessage"+dwUserNum+""+dwRoomId);
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.sender = MainAct.class.getName();
                        messageEvent.dealer = UserListFrag.class.getName();
                        EventBus.getDefault().post(messageEvent);
                    }

                    @Override
                    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
                        LogUtil.E("OnAnyChatUserAtRoomMessage"+dwUserId+""+bEnter);
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.sender = MainAct.class.getName();
                        messageEvent.dealer = UserListFrag.class.getName();
                        messageEvent.data = dwUserId;
                        EventBus.getDefault().post(messageEvent);
                    }

                    @Override
                    public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
                        LogUtil.E("OnAnyChatLinkCloseMessage"+dwErrorCode);
                    }
                },
                new AnyChatObjectEvent() {
                    @Override
                    public void OnAnyChatObjectEvent(int dwObjectType, int dwObjectId, int dwEventType, int dwParam1, int dwParam2, int dwParam3, int dwParam4, String strParam) {

                    }
                });
        FragmentUtil.getInstance().add(activity,R.id.serviceroot,new LoginFrag());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChatInit.getInstance().doLoginOut();
        ChatInit.getInstance().clear(this);
    }

    @Override
    public void onBackPressed() {
        FragmentUtil.getInstance().removeTop(activity);
    }
}
