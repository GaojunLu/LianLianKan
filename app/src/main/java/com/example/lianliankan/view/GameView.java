package com.example.lianliankan.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.View;

import com.example.lianliankan.R;
import com.example.lianliankan.gamelogic.GameStatus;

/**
 * Created by Administrator on 2015/9/17.
 */
public class GameView extends View {

    Context context;
    Piece[][] pieces;
    GameStatus gameStatus;
    SoundPool pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
    int poolId;
    Paint paint = new Paint();

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        poolId = pool.load(context, R.raw.dis, 1);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), R.drawable.heart), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pieces = gameStatus.getPieces();
        if (pieces != null) {
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[0].length; j++) {
                    if (pieces[i][j] != null) {
                        RectF dst = new RectF(pieces[i][j].getX(), pieces[i][j].getY(), pieces[i][j].getX() + pieces[i][j].getWidth(), pieces[i][j].getY() + pieces[i][j].getHeight());
                        canvas.drawBitmap(pieces[i][j].getBitmap(), null, dst, null);
                    }
                }
            }
        }
        if(gameStatus.selectPieces.size()==2&&gameStatus.link){
            float[][] linePoints = gameStatus.indexToPts(gameStatus.getshortLine());
            float[] pts = new float[12];
            pts[0] = linePoints[0][0];
            pts[1] = linePoints[0][1];
            pts[10] = linePoints[3][0];
            pts[11] = linePoints[3][1];
            for(int i = 1; i < 3; i++){
                pts[4*i-2] = linePoints[i][0];
                pts[4*i-1] = linePoints[i][1];
                pts[4*i] = linePoints[i][0];
                pts[4*i+1] = linePoints[i][1];
            }
            canvas.drawLines(pts, paint);
            gameStatus.selectPieces.clear();
            pool.play(poolId,1, 1, 0, 0, 1);
        }
    }

}
