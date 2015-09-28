package com.example.lianliankan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lianliankan.R;
import com.example.lianliankan.gamelogic.GameStatus;
import com.example.lianliankan.view.GameView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2015/9/17.
 */
public class GameActivity extends Activity implements View.OnTouchListener {
    TextView textview_time;
    Button button_start;
    GameView gameView;
    Timer timer;
    public GameStatus gameStatus = new GameStatus();
    AlertDialog.Builder failDialog, successDialog;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String time = (String) msg.obj;
            if (time.equals("0")) {
                stopTimer();
                failDialog.create().show();
            } else {
                textview_time.setText(time);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        textview_time = (TextView) findViewById(R.id.textview_time);
        button_start = (Button) findViewById(R.id.button_start);
        textview_time = (TextView) findViewById(R.id.textview_time);
        gameView = (GameView) findViewById(R.id.gameview);
        gameView.setGameStatus(gameStatus);
        //触摸监听
        gameView.setOnTouchListener(this);
        //开始按钮监听
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始游戏，包括加载图片、计时器开始
                starteGame();
            }
        });
        failDialog = new AlertDialog.Builder(this).setTitle("失败").setMessage("再来一次").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                starteGame();
            }
        }).setIcon(R.drawable.lost);
        successDialog = new AlertDialog.Builder(this).setTitle("成功").setMessage("再来一次").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                starteGame();
            }
        }).setIcon(R.drawable.success);
    }

    private void starteGame() {
        stopTimer();
        gameStatus.setPieces(GameActivity.this, gameView, 6, 7);
        gameView.postInvalidate();
        startTimer();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        根据位置
        if (gameStatus.getPieces() != null) {
            gameStatus.putMotionEvent(event);
            if(gameStatus.getPiecesNumer() == 0&&event.getAction() == MotionEvent.ACTION_DOWN){
                successDialog.create().show();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (gameStatus.isPlaying) {
            startTimer();
        }
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {

            public void run() {
                Message message = new Message();
                message.obj = String.valueOf(gameStatus.restTime--);
                handler.sendMessage(message);
            }
        }, 0, 1000);
    }

    public void stopTimer() {
        if (timer != null)
            timer.cancel();
        timer = null;
    }
}
