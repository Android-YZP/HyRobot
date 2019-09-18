package com.hy.robot.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.hy.robot.R;
import com.hy.robot.bean.MessageWrap;
import com.orhanobut.logger.Logger;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MusicActivity extends Activity {
    private TXCloudVideoView mVideo;
    private TXVodPlayer mLivePlayer;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_music);

        EventBus.getDefault().register(this);
        init();
    }

    private void init() {
//"http://od.open.qingting.fm/m4a/5800f3f87cb891101aeb0c1a_6108239_64.m4a?u=786&channelId=187352&programId=5640081"

        mVideo = findViewById(R.id.video_view);
        //创建 player 对象
        mLivePlayer = new TXVodPlayer(MusicActivity.this);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(mVideo);
        String flvUrl = "http://od.open.qingting.fm/m4a/583e3d5a7cb8913978625ca6_6389700_64.m4a?u=786&channelId=197972&programId=6010731";
        mLivePlayer.startPlay(flvUrl);
        //Android 示例代码

        // 设置填充模式
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        // 设置画面渲染方向
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle param) {
                Logger.e(event+"");
                if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    Logger.e("[AnswerRoom] 拉流失败：网络断开");
                    Logger.e("网络断开，拉流失败");

                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {//视频播放进入缓冲状态，缓冲结束之后会有 PLAY_BEGIN 事件

                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {//视频播放开始，如果您自己做 loading，会需要它

                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {//播放结束，HTTP-FLV 的直播流是不抛这个事件的
                }
            }

            @Override
            public void onNetStatus(Bundle status) {

            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mLivePlayer.stopPlay(true); // true 代表清除最后一帧画面
        mVideo.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageWrap event) {
        // 更新界面
        Logger.e(event.message);
        if (event.message.contains("语音激活")) {
            finish();
        }
    }


}
