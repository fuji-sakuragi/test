package com.example.myapplication0617allgo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int FLG_ON;
    int FLG_OFF;
    int FLG_WAIT;
    int SLEEP_TIME;
    int MODE_P_DELAY;
    int MODE_NOMAL;
    int MODE_OSU;

    int ram;
    int bonusMode;
    int bonusFlg;
    int pushFlg;
    int pushCnt;

    Context con;
    ImageView roGO;
    ImageView pushButton;
    TextView textCnt;
    Random rand;
    MediaPlayer mediaPlayer ;
    RelativeLayout linearLayout;
    RelativeLayout.LayoutParams roGOParam;
    RelativeLayout.LayoutParams pushbuttonParam;
    RelativeLayout.LayoutParams textParam;
    private Handler mHandler = new Handler();
    private Runnable makeview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con=this;
        Initialize();
        /*makeview = new Runnable() {
            public void run() {
                // ボーナス終了後、画面初期化
                if (pushFlg == FLG_ON)
                {
                    Main_Controller();
                }
                mHandler.removeCallbacks(makeview);
                mHandler.postDelayed(makeview,100);
            }
        };
        //only first delay
        mHandler.postDelayed(makeview, 100);*/
    }
    /*
     * 初期化処理
     *
     *
     *
     * */
    public void Initialize(){
        FLG_ON = 1;
        FLG_OFF = 0;
        FLG_WAIT = 2;
        SLEEP_TIME = 500;
        MODE_P_DELAY = 1;
        MODE_NOMAL = 0;
        MODE_OSU = 2;

        ram = 0;
        bonusMode = 0;
        bonusFlg = FLG_OFF;
        pushFlg = FLG_OFF;
        pushCnt = 0;

        Context con;
        roGO =new ImageView(this);
        roGO.setImageResource(R.drawable.beforego);
        pushButton = new ImageView(this);
        pushButton.setImageResource(R.drawable.beforebutton);

        textCnt = new TextView(this);
        textCnt.setText("START");
        textCnt.setTextSize(30);
        rand = new Random();
        mediaPlayer = MediaPlayer.create(this, R.raw.gako);
        //mediaPlayer.setOnPreparedListener(this);
        //mediaPlayer.prepareAsync(); // prepare async to not block main thread
        //mediaPlayer.prepare();
        linearLayout = new RelativeLayout(this);
        roGOParam =  new RelativeLayout.LayoutParams(600, 600);
        roGOParam.setMargins(250, 200, 0, 0);
        pushbuttonParam =  new RelativeLayout.LayoutParams(450, 450);
        pushbuttonParam.setMargins(300, 900, 0, 0);
        textParam=new RelativeLayout.LayoutParams(700, 150);
        textParam.setMargins(50, 10, 0, 0);
        roGO.setLayoutParams(roGOParam);
        linearLayout.addView(roGO);
        textCnt.setLayoutParams(textParam);
        linearLayout.addView(textCnt);
        View.OnTouchListener ont =new ImageView.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(mediaPlayer.isPlaying()){
                    return true;
                }
                else {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Action_Down_Controller();
                            break;
                        case MotionEvent.ACTION_UP:
                            Action_Up_Controller();
                            break;
                    }
                    return true;
                }
            }
        };
        pushButton.setOnTouchListener(ont);
        pushButton.setLayoutParams(pushbuttonParam);
        linearLayout.addView(pushButton);
        setContentView(linearLayout);
    }
    /*
     * Action_Up_Controller
     * メイン処理
     *
     * 引き数：なし
     * 戻り値：なし
     * */
    public void Action_Up_Controller() {
        if(pushFlg == FLG_ON)
            if (bonusFlg == FLG_WAIT) {
                if (bonusMode == MODE_NOMAL){
                    Set_Image_roGo();
                    Start_Music();
                    bonusFlg = FLG_ON;
                }
            }
            else if (bonusFlg == FLG_ON) {
                Start_Music();
                while(mediaPlayer.isPlaying()){}
                bonusFlg = FLG_OFF;
                bonusMode = MODE_NOMAL;
                Set_Image_roGo();
                pushCnt = 0;
                textCnt.setText("PUSH:" + String.valueOf(pushCnt));
            }
            else{
                pushCnt++;
                textCnt.setText("PUSH:" + String.valueOf(pushCnt));
            }
        Set_Image_pushButton();
        pushFlg = FLG_OFF;
    }

    /*
     * Action_Down_Controller
     * メイン処理
     *
     * 引き数：なし
     * 戻り値：なし
     * */
    public void Action_Down_Controller() {
        if(pushFlg == FLG_OFF){
            Set_Image_pushButton();
            if (bonusFlg == FLG_OFF) {
                Judgement();
            }
            pushFlg = FLG_ON;
        }
    }
    /*
     * Judgement
     * 判定処理
     *
     * 引き数：なし
     * 戻り値：なし
     * */
    public void Judgement() {
        ram = rand.nextInt(50) ;
        if(ram < 10){
            bonusFlg = FLG_WAIT;
            bonusMode = MODE_NOMAL;
            pushCnt++;
            textCnt.setText("BONUS:" + String.valueOf(pushCnt));
            /*if (ram < 5){
                bonusMode = MODE_P_DELAY;
                Set_Image_roGo();
                textCnt.setText("MODE_P_DELAY:" + String.valueOf(pushCnt));
            }*/
            /*if (ram < 2){
                bonusMode = MODE_OSU;
                Set_Image_pushButton();
                textCnt.setText("MODE_OSU:" + String.valueOf(pushCnt));
            }*/
        }
    }
    /*
     * Set_Image_roGo
     * 画面設定処理
     *
     * 引き数：なし
     * 戻り値：なし
     * */
    public void Set_Image_roGo(){
        if (bonusFlg == FLG_WAIT)
        {
            if(bonusMode == MODE_OSU){}
            roGO.setImageResource(R.drawable.aftergo);
        }
        else
        {
        roGO.setImageResource(R.drawable.beforego);
        }
    }
/*
 * Set_Image_pushButton
 * 画面設定処理
 *
 * 引き数：なし
 * 戻り値：なし
 * */
    public void Set_Image_pushButton(){
        if (pushFlg == FLG_OFF)
        {
        if(bonusMode == MODE_OSU){}
        pushButton.setImageResource(R.drawable.afterbutton);
        }
        else
        {
        if(bonusMode == MODE_OSU){}
        pushButton.setImageResource(R.drawable.beforebutton);
        }
        }
    /*
     * Start_Music
     * 音楽再生処理
     *
     * 引き数：なし
     * 戻り値：なし
     * */
    public void Start_Music(){
        if  (bonusFlg == FLG_WAIT)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.gako);
        }
        else
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.bigbig);
        }
        mediaPlayer.start();
        /*while(mediaPlayer.isPlaying()) {
            pushFlg = FLG_WAIT;
        }
        pushFlg = FLG_ON;
        */

        // mediaPlayer.stop();
        //mediaPlayer.reset();
        //mediaPlayer.release();
        //mediaPlayer = null;
    }


}