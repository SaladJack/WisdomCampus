package com.kai.wisdom_scut.view.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.speech.VoiceRecognitionService;
import com.kai.wisdom_scut.R;
import com.kai.wisdom_scut.db.Constants;
import com.kai.wisdom_scut.network.api.SimsimiApi;
import com.kai.wisdom_scut.utils.ActivityUtils;
import com.orhanobut.logger.Logger;
import java.io.IOException;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.os.MessageQueue.OnFileDescriptorEventListener.EVENT_ERROR;
import static com.kai.wisdom_scut.db.Constants.BaiduYuYin.*;
import static com.kai.wisdom_scut.db.RealmDb.findResponse;
import static com.kai.wisdom_scut.db.RealmDb.realm;


public class BaiduYuyinActivity extends Activity implements RecognitionListener {
    @BindView(R.id.guide_title)
    TextView guideTitle;
    @BindView(R.id.guide_ll)
    LinearLayout guideLl;
    @BindView(R.id.guide)
    TextView guide;
    @BindView(R.id.say)
    ImageView say;
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.problem)
    TextView problem;
    @BindView(R.id.yuyin_send_content)
    TextView yuyinSendContent;
    @BindView(R.id.yuyin_receive_content)
    TextView yuyinReceiveContent;

    private SpeechRecognizer speechRecognizer;
    private long speechEndTime = -1;
    private Retrofit retrofit;
    private SimsimiApi simsimiApi;
    private String sendContent;
    private boolean isSaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_baiduyuyin);
        ButterKnife.bind(this);
        initView();


    }


    private void initView() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName(this, VoiceRecognitionService.class));
        speechRecognizer.setRecognitionListener(this);
        say.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isSaying = true;
                        setChange(true);

                        speechRecognizer.cancel();
                        Intent intent = new Intent();
                        bindParams(intent);
                        intent.putExtra("vad", "touch");
                        speechRecognizer.startListening(intent);
                        return true;
                    case MotionEvent.ACTION_UP:
                        speechRecognizer.stopListening();
                        break;
                }
                return false;
            }
        });
    }

    public void bindParams(Intent intent) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getBoolean("tips_sound", true)) {
            intent.putExtra(EXTRA_SOUND_START, R.raw.bdspeech_recognition_start);
            intent.putExtra(EXTRA_SOUND_END, R.raw.bdspeech_speech_end);
            intent.putExtra(EXTRA_SOUND_SUCCESS, R.raw.bdspeech_recognition_success);
            intent.putExtra(EXTRA_SOUND_ERROR, R.raw.bdspeech_recognition_error);
            intent.putExtra(EXTRA_SOUND_CANCEL, R.raw.bdspeech_recognition_cancel);
        }
        if (sp.contains(EXTRA_INFILE)) {
            String tmp = sp.getString(EXTRA_INFILE, "").replaceAll(",.*", "").trim();
            intent.putExtra(EXTRA_INFILE, tmp);
        }
        if (sp.getBoolean(EXTRA_OUTFILE, false)) {
            intent.putExtra(EXTRA_OUTFILE, "sdcard/outfile.pcm");
        }
        if (sp.contains(EXTRA_SAMPLE)) {
            String tmp = sp.getString(EXTRA_SAMPLE, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(EXTRA_SAMPLE, Integer.parseInt(tmp));
            }
        }
        if (sp.contains(EXTRA_LANGUAGE)) {
            String tmp = sp.getString(EXTRA_LANGUAGE, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(EXTRA_LANGUAGE, tmp);
            }
        }
        if (sp.contains(EXTRA_NLU)) {
            String tmp = sp.getString(EXTRA_NLU, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(EXTRA_NLU, tmp);
            }
        }

        if (sp.contains(EXTRA_VAD)) {
            String tmp = sp.getString(EXTRA_VAD, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(EXTRA_VAD, tmp);
            }
        }
        String prop = null;
        if (sp.contains(EXTRA_PROP)) {
            String tmp = sp.getString(EXTRA_PROP, "").replaceAll(",.*", "").trim();
            if (null != tmp && !"".equals(tmp)) {
                intent.putExtra(EXTRA_PROP, Integer.parseInt(tmp));
                prop = tmp;
            }
        }
        // offline asr
        {
            intent.putExtra(EXTRA_OFFLINE_ASR_BASE_FILE_PATH, "/sdcard/easr/s_1");
            intent.putExtra(EXTRA_LICENSE_FILE_PATH, "/sdcard/easr/license-tmp-20150530.txt");
            if (null != prop) {
                int propInt = Integer.parseInt(prop);
                if (propInt == 10060) {
                    intent.putExtra(EXTRA_OFFLINE_LM_RES_FILE_PATH, "/sdcard/easr/s_2_Navi");
                } else if (propInt == 20000) {
                    intent.putExtra(EXTRA_OFFLINE_LM_RES_FILE_PATH, "/sdcard/easr/s_2_InputMethod");
                }
            }
//            intent.putExtra(EXTRA_OFFLINE_SLOT_DATA, buildTestSlotData());
        }
    }



    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        StringBuilder sb = new StringBuilder();
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                sb.append("音频问题");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                sb.append("没有语音输入");
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                sb.append("其它客户端错误");
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                sb.append("权限不足");
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                sb.append("网络问题");
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                sb.append("没有匹配的识别结果");
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                sb.append("引擎忙");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                sb.append("服务端错误");
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                sb.append("连接超时");
                break;
        }
        sb.append(":" + error);

    }

    @Override
    public void onResults(Bundle results) {
        long end2finish = System.currentTimeMillis() - speechEndTime;
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String strEnd2Finish = "";
        if (end2finish < 60 * 1000) {
            strEnd2Finish = "(waited " + end2finish + "ms)";
        }

        sendContent = nbest.get(0) + strEnd2Finish;
        Logger.d(sendContent);
        yuyinSendContent.setText(sendContent);
        setNetResponse();
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> nbest = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (nbest.size() > 0) {
            sendContent = nbest.get(0);
            yuyinSendContent.setText(sendContent);
        }
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        switch (eventType) {
            case EVENT_ERROR:
                String reason = params.get("reason") + "";
                Logger.e(reason);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void setChange(boolean isSaying){
        if (isSaying){
            guideLl.setVisibility(View.GONE);
            guideTitle.setVisibility(View.GONE);
            yuyinSendContent.setVisibility(View.VISIBLE);
            yuyinReceiveContent.setVisibility(View.VISIBLE);
            yuyinSendContent.setText("");
            yuyinReceiveContent.setText("");
        }else{
            guideLl.setVisibility(View.VISIBLE);
            guideTitle.setVisibility(View.VISIBLE);
            yuyinSendContent.setVisibility(View.GONE);
            yuyinReceiveContent.setVisibility(View.GONE);

        }
    }

    private void setNetResponse() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Constants.Api.simiSimiBaseUrl)
                    .build();
            simsimiApi = retrofit.create(SimsimiApi.class);
        }
        simsimiApi.getResponse(sendContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        yuyinReceiveContent.setText(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, error -> setMachineResponse(sendContent));
    }


    private void setMachineResponse(String sendContent) {
        Observable.just(sendContent)
                .map(ask -> findResponse(ask))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> yuyinReceiveContent.setText(response));
    }

    @OnClick({R.id.say, R.id.close, R.id.problem})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                ActivityUtils.finishActivity(this);
                break;
            case R.id.problem:
                break;
        }
    }

}
