package com.siweisoft.service.chat.videochat;

//by summer on 2017-07-04.

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.SurfaceHolder;
import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.constant.ValueConstant;
import com.android.lib.util.FragmentUtil;
import com.android.lib.util.LogUtil;
import com.android.lib.util.system.HandleUtil;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.bairuitech.anychat.AnyChatRecordEvent;
import com.bairuitech.anychat.AnyChatVideoCallEvent;
import com.example.anychatfeatures.RoleInfo;
import com.example.funcActivity.CallingCenter;
import com.siweisoft.service.R;
import com.siweisoft.service.chat.chatutil.ChatInit;

public class VideoChatFrag extends BaseUIFrag<VideoChatUIBean,VideoChatDAOpe> implements AnyChatVideoCallEvent ,AnyChatRecordEvent {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION, 1);
        // 启动 AnyChat 传感器监听
        ChatInit.getInstance().getAnyChatSDK().mSensorHelper.InitSensor(activity);
        // 初始化 Camera 上下文句柄
        AnyChatCoreSDK.mCameraHelper.SetContext(activity);
        //设置 SURFACE_TYPE_PUSH_BUFFERS 模式
        getOpes().getUi().viewDataBinding.surfaceviewLocal.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 打开本地视频预览，开始采集本地视频数据
        getOpes().getUi().viewDataBinding.surfaceviewLocal.getHolder().addCallback(AnyChatCoreSDK.mCameraHelper);
        getOpes().getDa().setRoleInfo((RoleInfo) getArguments().getSerializable(ValueConstant.DATA_DATA2));
        ChatInit.getInstance().openLocalCamera();
        ChatInit.getInstance().loadRemoveVideo(getOpes().getUi().viewDataBinding.surfaceviewRemote,Integer.parseInt(getOpes().getDa().getRoleInfo().getUserID()));
        ChatInit.getInstance().getAnyChatSDK().SetVideoCallEvent(this);
        HandleUtil.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                ChatInit.getInstance().startRecordVideo();
                ChatInit.getInstance().getAnyChatSDK().SetRecordSnapShotEvent(VideoChatFrag.this);
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ChatInit.getInstance().stopRecordVideo();
        CallingCenter.getInstance().VideoCallControl(
                AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY, Integer.parseInt(getOpes().getDa().getRoleInfo().getUserID()),
                AnyChatDefine.BRAC_ERRORCODE_SESSION_REFUSE, 0, 0,
                "");
        ChatInit.getInstance().closeRemoveVideo(Integer.parseInt(getOpes().getDa().getRoleInfo().getUserID()));

        ChatInit.getInstance().getAnyChatSDK().UserCameraControl(getArguments().getInt(ValueConstant.DATA_DATA), 0);
        ChatInit.getInstance().getAnyChatSDK().UserSpeakControl(getArguments().getInt(ValueConstant.DATA_DATA), 0);
        ChatInit.getInstance().getAnyChatSDK().UserCameraControl(-1, 0);
        ChatInit.getInstance().getAnyChatSDK().UserSpeakControl(-1, 0);
        ChatInit.getInstance().getAnyChatSDK().removeEvent(this);
        ChatInit.getInstance().getAnyChatSDK().mSensorHelper.DestroySensor();

    }

    @Override
    public void OnAnyChatVideoCallEvent(int dwEventType, int dwUserId, int dwErrorCode, int dwFlags, int dwParam, String userStr) {
        LogUtil.E(dwEventType+"-"+dwUserId+"-"+dwUserId+"-"+dwErrorCode+"-"+dwFlags+"-"+dwParam+"-"+"-"+userStr);
        final RoleInfo roleInfo = new RoleInfo();
        roleInfo.setName(ChatInit.getInstance().getAnyChatSDK().GetUserName(dwUserId));
        roleInfo.setUserID(dwUserId+"");
        switch (dwEventType) {
            case AnyChatDefine.BRAC_VIDEOCALL_EVENT_REQUEST:
                LogUtil.E("oncllick"+ChatInit.getInstance().getAnyChatSDK().GetUserName(dwUserId)+"向你发来视频请求");
                //CallingCenter.getInstance().VideoCallControl(AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY, dwUserId,AnyChatDefine.BRAC_ERRORCODE_SUCCESS, 0, 0, "");
                break;
            case AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY:
                LogUtil.E("呼叫成功等待响应");
                break;
            case AnyChatDefine.BRAC_VIDEOCALL_EVENT_START:
                LogUtil.E("视频呼叫会话开始事件");
//                Fragment videofragment = new VideoChatFrag();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(ValueConstant.DATA_DATA,getArguments().getInt(ValueConstant.DATA_DATA));
//                bundle.putSerializable(ValueConstant.DATA_DATA2, roleInfo);
//                videofragment.setArguments(bundle);
//                FragmentUtil.getInstance().add(activity, R.id.serviceroot,videofragment);
                break;
            case AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH:
                LogUtil.E("挂断（结束）呼叫会话");
                FragmentUtil.getInstance().removeTop(activity);
                break;
        }
    }

    @Override
    public void OnAnyChatRecordEvent(int dwUserId, int dwErrorCode, String lpFileName, int dwElapse, int dwFlags, int dwParam, String lpUserStr) {

    }

    @Override
    public void OnAnyChatSnapShotEvent(int dwUserId, int dwErrorCode, String lpFileName, int dwFlags, int dwParam, String lpUserStr) {

    }
}
