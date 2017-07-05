package com.siweisoft.service.chat.chatutil;

//by summer on 2017-07-04.

import android.content.Context;
import android.view.SurfaceView;

import com.android.lib.util.LogUtil;
import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.bairuitech.anychat.AnyChatObjectEvent;
import com.example.anychatfeatures.FuncMenu;
import com.example.anychatfeatures.RoleInfo;
import com.example.config.ConfigEntity;
import com.example.config.ConfigService;

import java.util.ArrayList;
import java.util.Objects;

public class ChatInit  {

    private final int LOCALVIDEOAUTOROTATION = 1; 	// 本地视频自动旋转控制

    private static ChatInit instance;

    AnyChatCoreSDK anyChatSDK;


    private String mStrIP = "demo.anychat.cn";


    private int mSPort = 8906;

    public static ChatInit getInstance(){
        if(instance == null){
            instance = new ChatInit();
        }
        return instance;
    }

    // 初始化SDK
    public void initSDK(Context context) {
        if (anyChatSDK == null) {
            anyChatSDK = AnyChatCoreSDK.getInstance(context);
            anyChatSDK.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
            AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION,LOCALVIDEOAUTOROTATION);
            LogUtil.E(anyChatSDK.AudioGetVolume(0)+""+anyChatSDK.AudioGetVolume(1));
            anyChatSDK.AudioSetVolume(0,100);
            anyChatSDK.AudioSetVolume(1,100);
            ConfigEntity configEntity = ConfigService.LoadConfig(context);
            configEntity.mVideoQuality = 4;
            configEntity.mResolutionWidth = 1080;
            configEntity.mResolutionHeight = 720;
            ConfigService.SaveConfig(context,configEntity);

        }
    }

    public void initListener(AnyChatBaseEvent baseEvent, AnyChatObjectEvent objectEvent){
        if(anyChatSDK!=null){
            anyChatSDK.SetBaseEvent(baseEvent);
            anyChatSDK.SetObjectEvent(objectEvent);
        }
    }


    public void doLogin(String ip,int port,String name){
        anyChatSDK.Connect(ip, port);
        /***
         * AnyChat支持多种用户身份验证方式，包括更安全的签名登录，
         * 详情请参考：http://bbs.anychat.cn/forum.php?mod=viewthread&tid=2211&highlight=%C7%A9%C3%FB
         */
        anyChatSDK.Login(name, "");
    }

    public void doLoginOut(){
        anyChatSDK.Logout();
    }
    public void clear(Object o){
        anyChatSDK.LeaveRoom(-1);
        anyChatSDK.Logout();
        anyChatSDK.removeEvent(o);
        anyChatSDK.Release();
    }


    public void enterRoom(int roomID,String pwd){
        anyChatSDK.EnterRoom(roomID,pwd);
    }

    public void leaveRoom(int roomID){
        anyChatSDK.LeaveRoom(roomID);
    }

    public  ArrayList<RoleInfo> getUserList(int myid){
        ArrayList<RoleInfo> roleInfos = new ArrayList<>();
        int[] userID = anyChatSDK.GetRoomOnlineUsers(1234);
        RoleInfo userselfInfo = new RoleInfo();
        roleInfos.add(userselfInfo);
        userselfInfo.setUserID(String.valueOf(myid));
        userselfInfo.setName(anyChatSDK.GetUserName(myid) + "(自己)");

        for (int index = 0; index < userID.length; ++index) {
            RoleInfo info = new RoleInfo();
            info.setName(anyChatSDK.GetUserName(userID[index]));
            info.setUserID(String.valueOf(userID[index]));
            roleInfos.add(info);
            LogUtil.E(info.toString());
        }
        return roleInfos;
    }

    public void openLocalCamera(){
        //打开本地视频, 第一个参数用-1 表示本地，也可以用本地的真实 userid
        getAnyChatSDK().UserCameraControl(-1, 1);
        //打开本地音频
        getAnyChatSDK().UserSpeakControl(-1, 1);
    }



    public void loadRemoveVideo(SurfaceView mSurfaceRemote,int removeid){
        // mRemoteUserid 为通话目标对象的 userId;
        int index = getAnyChatSDK().mVideoHelper.bindVideo(mSurfaceRemote.getHolder());
        getAnyChatSDK().mVideoHelper.SetVideoUser(index, removeid);
        getAnyChatSDK().UserCameraControl(removeid, 1);
        getAnyChatSDK().UserSpeakControl(removeid, 1);
    }

    public void closeRemoveVideo(int removeid){
        //关闭远程视频, mRemoteUserid 为通话目标的 userid
        getAnyChatSDK().UserCameraControl(removeid, 0);
        //关闭远程音频,
        getAnyChatSDK().UserSpeakControl(removeid, 0);
    }


    public void startRecordVideo(){
       int mdwFlags = 0;					// 本地视频录制参数标致
        mdwFlags = AnyChatDefine.BRAC_RECORD_FLAGS_AUDIO
                + AnyChatDefine.BRAC_RECORD_FLAGS_VIDEO
                + AnyChatDefine.ANYCHAT_RECORD_FLAGS_LOCALCB
                + AnyChatDefine.ANYCHAT_RECORD_FLAGS_SERVER;
        anyChatSDK.StreamRecordCtrlEx(-1, 1, mdwFlags, 0, "开始录制");
    }

    public void stopRecordVideo(){
        int mdwFlags = 0;					// 本地视频录制参数标致
        mdwFlags = AnyChatDefine.BRAC_RECORD_FLAGS_AUDIO
                + AnyChatDefine.BRAC_RECORD_FLAGS_VIDEO
                + AnyChatDefine.ANYCHAT_RECORD_FLAGS_LOCALCB
                + AnyChatDefine.ANYCHAT_RECORD_FLAGS_SERVER;
        anyChatSDK.StreamRecordCtrlEx(-1, 0, mdwFlags, 0,
                "关闭服务器视频录制");
    }


    public AnyChatCoreSDK getAnyChatSDK() {
        return anyChatSDK;
    }

    public String getmStrIP() {
        return mStrIP;
    }

    public int getmSPort() {
        return mSPort;
    }
}
