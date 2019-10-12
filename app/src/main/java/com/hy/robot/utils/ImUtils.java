package com.hy.robot.utils;

import android.os.Environment;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.hy.robot.App;
import com.hy.robot.R;
import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriend;

import java.util.List;

public class ImUtils {
    private static ImUtils imUtils = null;
    private OnImListener onImListener;
    private String tag = "=========>";
    private static List<TIMFriend> mTimFriends = null;

    //单例化检查更新类
    private ImUtils() {
    }

    public void setOnImListener(OnImListener onImListener) {
        this.onImListener = onImListener;
    }

    public synchronized static ImUtils getInstance() {
        if (imUtils == null) {
            imUtils = new ImUtils();
        }
        return imUtils;
    }

    /**
     * IM初始化
     */
    public void IMInit() {
        //初始化 IM SDK 基本配置
        TIMSdkConfig config = new TIMSdkConfig(1400194913)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

        //初始化 SDK
        TIMManager.getInstance().init(App.getContext(), config);

        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.i(tag, "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新 userSig 重新登录 IM SDK
                        Log.i(tag, "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.i(tag, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i(tag, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i(tag, "onWifiNeedAuth");
                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                        Log.i(tag, "onGroupTipsEvent, type: " + elem.getTipsType());
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(tag, "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        Log.i(tag, "onRefreshConversation, conversation size: " + conversations.size());
                    }
                });

        //禁用本地所有存储
        userConfig.disableStorage();
        //开启消息已读回执
        userConfig.enableReadReceipt(true);

        //将用户配置与通讯管理器进行绑定
        TIMManager.getInstance().setUserConfig(userConfig);
        IMLogin("22", "22");


    }

    /**
     * IM登陆
     * 通过 TIMManager 成员方法 getLoginUser 可以获取当前用户名，也可以通过这个方法判断是否已经登录。
     */
    private void IMLogin(String identifier, String userSig) {
        // identifier为用户名，userSig 为用户登录凭证
        TIMManager.getInstance().login("8b21b9e1057640a6a7ca2500495c2782", "eJw1kNFOgzAUht*Fa6OnhbZg4sUwIE4Ws4hMrkgp3ay4rStVO43v7mzY7fedP*f85yeoyqdLrrXqW27b0PTBdQDBhcfSaWVky9dWmhNGhBAMcLaqlzur1sq7uMOoSyQCwmgEnHImOCYAUUIEZjGeMqPanIYX2fPtfbpyNLRO3RXYmRWL32zxmL1c5U7kQyVt3o0L9PltSjIcZyqbHb50vXWHeVoLcHuo*nFeaOPk5mHZvDZNnpaSvpeorrrlzXlZP7S*2f-tEQBKogSFk7RqK30nBjEFHLOJcyH2Hzvb2qOW-hW-fxdVV5s_", new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.d(tag, "login failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess() {
                Log.d(tag, "login succ");
                //登陆之后启动消息接收器
                IMReciveMsg();
                IMGetTXL();
            }
        });
    }

    /**
     * 判断是否登陆
     */
    public boolean isLogin() {
        return !TIMManager.getInstance().getLoginUser().isEmpty();
    }

    /**
     * IM获取通讯录
     */
    public void IMGetTXL() {

        //获取服务器保存的通讯录
        TIMFriendshipManager.getInstance().getFriendList(new TIMValueCallBack<List<TIMFriend>>() {
            @Override
            public void onError(int code, String desc) {
                Logger.e("getFriendList err code = " + code);
            }

            @Override
            public void onSuccess(List<TIMFriend> timFriends) {
                StringBuilder stringBuilder = new StringBuilder();
                for (TIMFriend timFriend : timFriends) {
                    stringBuilder.append(timFriend.getIdentifier() + timFriend.getRemark() + timFriend.getTimUserProfile().getNickName());
                }
                Logger.e(stringBuilder.toString());
                mTimFriends = timFriends;

            }
        });
    }

    /**
     * IM发送消息
     */
    public void IMSendMsg(String name, String txtMsg) {
        String peer = "";  //获取与用户 "sample_user_1" 的会话
        for (TIMFriend timFriend : mTimFriends) {
            if (name.equals(timFriend.getTimUserProfile().getNickName())) {
                peer = timFriend.getIdentifier();
            }
        }


        //获取单聊会话

        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C,    //会话类型：单聊
                peer);//会话对方用户帐号//对方ID

        //构造一条消息
        TIMMessage msg = new TIMMessage();

        //添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText(txtMsg);

        //将elem添加到消息
        if (msg.addElement(elem) != 0) {
            Log.d(tag, "addElement failed");
            return;
        }

        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log.d(tag, "send message failed. code: " + code + " errmsg: " + desc);
                ToastUtils.showCustomLong(R.layout.activity_main);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e(tag, "SendMsg ok");
            }
        });
    }


    /**
     * IM接收消息
     */
    private void IMReciveMsg() {

        //设置消息监听器，收到新消息时，通过此监听器回调
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {//消息监听器
            @Override
            public boolean onNewMessages(List<TIMMessage> msgs) {//收到新消息
                //消息的内容解析请参考消息收发文档中的消息解析说明

                if (msgs != null && msgs.size() == 0) return true;
                final TIMMessage msg = msgs.get(0);

                for (int i = 0; i < msg.getElementCount(); ++i) {
                    TIMElem elem = msg.getElement(i);

                    //获取当前元素的类型
                    TIMElemType elemType = elem.getType();
                    Log.d(tag, "elem type: " + elemType.name());
                    if (elemType == TIMElemType.Text) {
                        //处理文本消息

                        final TIMTextElem txtEle = (TIMTextElem) elem;
                        Logger.e(txtEle.getText());
                        msg.getSenderProfile(new TIMValueCallBack<TIMUserProfile>() {
                            @Override
                            public void onError(int i, String s) {

                            }

                            @Override
                            public void onSuccess(TIMUserProfile timUserProfile) {
                                onImListener.msg(timUserProfile.getNickName() + "发来一条新消息，内容是：" + txtEle.getText());
                            }
                        });


                    } else if (elemType == TIMElemType.Image) {
                        //处理图片消息

                    }//...处理更多消息
                }


                return true; //返回true将终止回调链，不再调用下一个新消息监听器
            }
        });
    }


    public interface OnImListener {
        void msg(String s);

        void txl(List<TIMFriend> s);
    }
}
