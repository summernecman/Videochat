package com.siweisoft.service.user.userlist;

//by summer on 2017-07-04.

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.constant.ValueConstant;
import com.android.lib.util.FragmentUtil;
import com.android.lib.util.LogUtil;
import com.android.lib.util.ToastUtil;
import com.android.lib.util.system.HandleUtil;
import com.android.lib.view.bottommenu.MessageEvent;
import com.bairuitech.anychat.AnyChatDefine;
import com.bairuitech.anychat.AnyChatVideoCallEvent;
import com.example.anychatfeatures.RoleInfo;
import com.example.anychatfeatures.RolesListActivity;
import com.example.common.DialogFactory;
import com.example.funcActivity.CallingCenter;
import com.example.funcActivity.VideoActivity;
import com.siweisoft.service.R;
import com.siweisoft.service.chat.chatutil.ChatInit;
import com.siweisoft.service.chat.videochat.VideoChatFrag;
import com.siweisoft.service.user.login.LoginFrag;

import java.io.Serializable;

public class UserListFrag extends BaseUIFrag<UserListUIBean,UserListDAOpe> implements AnyChatVideoCallEvent {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init(){
        getOpes().getUi().initList(ChatInit.getInstance().getUserList(getArguments().getInt(ValueConstant.DATA_DATA)),this);
        ChatInit.getInstance().getAnyChatSDK().SetVideoCallEvent(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        RoleInfo roleInfo = (RoleInfo) v.getTag(R.id.data);
        getOpes().getDa().setRoleInfo(roleInfo);
        LogUtil.E("oncllick"+roleInfo.getName());
        ChatInit.getInstance().getAnyChatSDK().VideoCallControl(AnyChatDefine.BRAC_VIDEOCALL_EVENT_REQUEST, Integer.parseInt(roleInfo.getUserID()), 0,  0, 0, "");
        LogUtil.E("oncllick"+roleInfo.getName());
//        Fragment videofragment = new VideoChatFrag();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(ValueConstant.DATA_DATA,getArguments().getInt(ValueConstant.DATA_DATA));
//        bundle.putSerializable(ValueConstant.DATA_DATA2, (Serializable) roleInfo);
//        videofragment.setArguments(bundle);
//        FragmentUtil.getInstance().add(activity,R.id.serviceroot,videofragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ChatInit.getInstance().leaveRoom(1234);
        ChatInit.getInstance().doLoginOut();;
    }

    @Override
    public void dealMesage(MessageEvent event) {
        super.dealMesage(event);
        init();
    }

    @Override
    public void OnAnyChatVideoCallEvent(int dwEventType, final int dwUserId, int dwErrorCode, int dwFlags, int dwParam, String userStr) {
        LogUtil.E(dwEventType+"-"+dwUserId+"-"+dwUserId+"-"+dwErrorCode+"-"+dwFlags+"-"+dwParam+"-"+"-"+userStr);
        final RoleInfo roleInfo = new RoleInfo();
        roleInfo.setName(ChatInit.getInstance().getAnyChatSDK().GetUserName(dwUserId));
        roleInfo.setUserID(dwUserId+"");
        switch (dwEventType) {
            case AnyChatDefine.BRAC_VIDEOCALL_EVENT_REQUEST:
                LogUtil.E("oncllick"+ChatInit.getInstance().getAnyChatSDK().GetUserName(dwUserId)+"向你发来视频请求");
                CallingCenter.getInstance().VideoCallControl(AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY, dwUserId,AnyChatDefine.BRAC_ERRORCODE_SUCCESS, 0, 0, "");
                break;
            case AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY:
                LogUtil.E("呼叫成功等待响应");
                break;
            case AnyChatDefine.BRAC_VIDEOCALL_EVENT_START:
                LogUtil.E("视频呼叫会话开始事件");
                Fragment videofragment = new VideoChatFrag();
                Bundle bundle = new Bundle();
                bundle.putSerializable(ValueConstant.DATA_DATA,getArguments().getInt(ValueConstant.DATA_DATA));
                bundle.putSerializable(ValueConstant.DATA_DATA2, roleInfo);
                videofragment.setArguments(bundle);
                FragmentUtil.getInstance().add(activity,R.id.serviceroot,videofragment);
                break;
            case AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH:
                LogUtil.E("挂断（结束）呼叫会话");
                break;
        }
    }
}
