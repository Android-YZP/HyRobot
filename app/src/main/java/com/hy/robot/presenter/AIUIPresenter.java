package com.hy.robot.presenter;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;

import com.com1075.library.base.BasePresenter;
import com.hy.robot.MainActivity;
import com.hy.robot.bean.MessageWrap;
import com.hy.robot.contract.IAIUIContract;
import com.iflytek.aiui.AIUIAgent;
import com.iflytek.aiui.AIUIConstant;
import com.iflytek.aiui.AIUIEvent;
import com.iflytek.aiui.AIUIListener;
import com.iflytek.aiui.AIUIMessage;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AIUIPresenter extends BasePresenter<IAIUIContract, MainActivity> {
    private final String TAG = RobotPresenter.class.getSimpleName();

    private MainActivity activity;
    private AIUIAgent mAIUIAgent = null;
    //交互状态
    private int mAIUIState = AIUIConstant.STATE_IDLE;


    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 默认发音人
    private String voicer = "xiaoyan";

    public AIUIPresenter(IAIUIContract view, MainActivity activity) {
        super(view, activity);
        this.activity = activity;
    }

    public void startAIUI() {
        //创建AIUIAgent
        mAIUIAgent = AIUIAgent.createAgent(activity, getAIUIParams(), mAIUIListener);
        //开始录音
        String params = "sample_rate=16000,data_type=audio";
        AIUIMessage writeMsg = new AIUIMessage(AIUIConstant.CMD_START_RECORD, 0, 0, params, null);
        mAIUIAgent.sendMessage(writeMsg);
    }


    public void exit() {
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }


    /**
     * 读取AIUI配置
     */
    private String getAIUIParams() {
        String params = "";

        AssetManager assetManager = activity.getResources().getAssets();
        try {
            InputStream ins = assetManager.open("cfg/aiui_phone.cfg");
            byte[] buffer = new byte[ins.available()];
            ins.read(buffer);
            ins.close();
            params = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return params;
    }

    private boolean checkAIUIAgent() {
        if (null == mAIUIAgent) {
            mAIUIAgent = AIUIAgent.createAgent(activity, getAIUIParams(), mAIUIListener);
        }

        if (null == mAIUIAgent) {
            getView().error("初始化失败，请关机重试");
        }

        return null != mAIUIAgent;
    }


    //声音语义理解
    private void startVoiceNlp() {
        if (AIUIConstant.STATE_WORKING != this.mAIUIState) {
            AIUIMessage wakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
            mAIUIAgent.sendMessage(wakeupMsg);
        }
    }

    //文本语义理解
    private void startTextNlp(String text) {
        try {
            // 在输入参数中设置tag，则对应结果中也将携带该tag，可用于关联输入输出
            String params = "data_type=text,tag=text-tag";
            byte[] textData = text.getBytes("utf-8");
            AIUIMessage write = new AIUIMessage(AIUIConstant.CMD_WRITE, 0, 0, params, textData);
            mAIUIAgent.sendMessage(write);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //AIUI事件监听器
    private AIUIListener mAIUIListener = new AIUIListener() {

        @Override
        public void onEvent(AIUIEvent event) {
            switch (event.eventType) {
                case AIUIConstant.EVENT_WAKEUP: {
                    getView().eventWeakup(event.arg1);

                    Logger.e("进入识别状态");
                    if (event.arg1 == 0) {//语音激活
                        EventBus.getDefault().post(MessageWrap.getInstance("语音激活"));
                        if (checkAIUIAgent())
                            startTextNlp("你好");
                    }
                }
                break;
                case AIUIConstant.EVENT_RESULT:
                    //结果事件
                    Logger.e("结果事件 " + event.info);
                    try {
                        JSONObject bizParamJson = new JSONObject(event.info);
                        JSONObject data = bizParamJson.getJSONArray("data").getJSONObject(0);
                        JSONObject content = data.getJSONArray("content").getJSONObject(0);
                        if (content.has("cnt_id")) {
                            String cnt_id = content.getString("cnt_id");
                            JSONObject cntJson = new JSONObject(new String(event.data.getByteArray(cnt_id), "utf-8"));
                            getView().eventResult(cntJson.toString());
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    break;
                case AIUIConstant.EVENT_ERROR:
                    break;
                case AIUIConstant.EVENT_VAD:
                    break;
                case AIUIConstant.EVENT_START_RECORD:
                    break;
                case AIUIConstant.EVENT_STOP_RECORD:
                    break;
                case AIUIConstant.EVENT_STATE:
                    mAIUIState = event.arg1;
                    break;
                default:
                    break;
            }
        }

    };


    //语音播报配置
    public void startSpeaker() {
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(activity, mTtsInitListener);
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.pcm");
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Logger.e("InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Logger.e("初始化失败,错误码：" + code + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    public void speachText(String vodString) {
        // 设置参数
        int code = mTts.startSpeaking(vodString, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            Logger.e("语音合成失败,错误码: " + code + ",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
        }
    }


    /**
     * 语音合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            aiUiOff();
        }

        @Override
        public void onSpeakPaused() {
        }

        @Override
        public void onSpeakResumed() {
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                aiUiOn();
                getView().onSpeakCompleted();
            } else if (error != null) {
                getView().error("语音合成出错");
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    //关闭语义理解
    public void aiUiOn() {
        Logger.e("播放完成");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkAIUIAgent())
                    startVoiceNlp();
            }
        }, 200);
    }

    //开启语义理解
    public void aiUiOff() {
        Logger.e("开始播放");
        //关闭语义识别避免自己和自己对话
        AIUIMessage writeMsg = new AIUIMessage(AIUIConstant.CMD_RESET_WAKEUP, 0, 0, "", null);
        mAIUIAgent.sendMessage(writeMsg);
        getView().onSpeakBegin();
    }


    /**********************************************************************上传动态实体*********************************************************************************/

    private void uoLoad() {
        try {
            JSONObject syncSchemaJson = new JSONObject();
            JSONObject paramJson = new JSONObject();
            paramJson.put("id_name", "appid");
            paramJson.put("id_value", "");
            paramJson.put("res_name", "OS2451892976.test_yonghu");
            syncSchemaJson.put("param", paramJson);
            syncSchemaJson.put("data", Base64.encodeToString(uploadContacts().getBytes(), Base64.DEFAULT | Base64.NO_WRAP));
            Logger.e(syncSchemaJson.toString());
            // 传入的数据一定要为utf-8编码
            byte[] syncData = syncSchemaJson.toString().getBytes("utf-8");
            String dataTag = "data_tag_1";
            JSONObject params = new JSONObject();
            params.put("tag", dataTag);
            AIUIMessage syncAthenaMessage = new AIUIMessage(AIUIConstant.CMD_SYNC, AIUIConstant.SYNC_DATA_SCHEMA, 0, params.toString(), syncData);
            mAIUIAgent.sendMessage(syncAthenaMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    private String uploadContacts() {
        // 上传进度消息，后续根据进度进行更新
        List<String> contacts = new ArrayList<>();
        contacts.add("李三");
        contacts.add("王三");
        contacts.add("王大");
        contacts.add("三少");

        StringBuilder contactJson = new StringBuilder();
        for (String contact : contacts) {
            String[] nameNumber = contact.split("#");
            //联系人空号码
            if (nameNumber.length == 1) {
                contactJson.append(String.format("{\"name\": \"%s\"}",
                        nameNumber[0]));
            }
        }
        Logger.e(contactJson.toString());
        return contactJson.toString();

    }


    private void uploadEvent(AIUIEvent event) {
        switch (event.arg1) {
            case AIUIConstant.CMD_SYNC: {
                int dtype = event.data.getInt("sync_dtype");
                String sync_tag = event.data.getString("tag");
                Logger.e(dtype + "===" + sync_tag);


                if (0 == event.arg2) {  // 同步成功
                    if (AIUIConstant.SYNC_DATA_SCHEMA == dtype) {
                        Logger.e("schema数据同步成功，sid=");

                        String setParams = "{\"global\":{\"scene\":\"main_box\"}}";
                        AIUIMessage setMsg = new AIUIMessage(AIUIConstant.CMD_SET_PARAMS, 0, 0, setParams, null);
                        mAIUIAgent.sendMessage(setMsg);

                    }
                } else {
                    if (AIUIConstant.SYNC_DATA_SCHEMA == dtype) {
                        Logger.e("schema数据同步出错：" + event.arg2);
                    }
                }
            }
            break;

        }
    }
}
