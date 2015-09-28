package com.example.lianliankan.gamelogic;

import android.content.Context;
import android.view.MotionEvent;

import com.example.lianliankan.util.ImageUtil;
import com.example.lianliankan.view.GameView;
import com.example.lianliankan.view.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public class GameStatus {
    Piece[][] pieces;
    public boolean link;
    public List<Piece> selectPieces = new ArrayList<Piece>();
    GameView gameView;
    private List<int[][]> linkPoints;
    public int restTime;
    public boolean isPlaying = false;

    public Piece[][] getPieces() {
        return pieces;
    }

    //  创建
    public void setPieces(Context context, GameView gameView, int x, int y) {
        if(x*y%2!=0){
            return;
        }
        this.pieces = ImageUtil.getPieces(context, gameView, x, y);
        this.gameView = gameView;
        restTime = 99;
        isPlaying = true;
    }

    //  清空
    public void setPieces(String option, int x, int y) {
        if (option.equalsIgnoreCase("clear") && pieces != null) {
            pieces[x][y] = null;
        }
    }

    //添加被选中图片
    public void addPiece(Piece piece) {
        if (selectPieces.size() < 2) {
            selectPieces.add(piece);
        } else if (selectPieces.size() == 2) {
            selectPieces.remove(0);
            selectPieces.add(piece);
        }
        link = false;
        if (selectPieces.size() == 2&&!selectPieces.get(0).getBitmap().equals(selectPieces.get(1).getBitmap())) {
            //消除
            GameLogic gameLogic = new GameLogic();
            if (selectPieces.get(0).equals(selectPieces.get(1)) && gameLogic.canLink(selectPieces, pieces)) {
                //画线
                if (gameView != null) {
                    link = true;
                    linkPoints = gameLogic.getLinkPoints();
                    gameView.postInvalidate();
                }
                //清除
                setPieces("clear", selectPieces.get(0).getIndexX(), selectPieces.get(0).getIndexY());
                setPieces("clear", selectPieces.get(1).getIndexX(), selectPieces.get(1).getIndexY());
            }
        }
    }

    public int[][] getshortLine() {
        int length = pieces.length*3+pieces[0].length*3;
        int shortIndex = 0;
        for(int i = 0; i < linkPoints.size(); i++){
            int[][] tempLine = linkPoints.get(i);
            int tempLength = Math.abs(tempLine[0][0]-tempLine[1][0])+Math.abs(tempLine[1][0]-tempLine[2][0])+Math.abs(tempLine[2][0]-tempLine[3][0])+
                    Math.abs(tempLine[0][1]-tempLine[1][1])+Math.abs(tempLine[1][1]-tempLine[2][1])+Math.abs(tempLine[2][1]-tempLine[3][1]);
            if(tempLength<length){
                length = tempLength;
                shortIndex = i;
            }
        }
        return linkPoints.get(shortIndex);
    }

    public float[][] indexToPts(int[][] indexLine){
        float[][] floatLine = new float[4][2];
        for(int i = 0; i < 4; i++){
            floatLine[i][0] = (indexLine[i][0]+1.5f)*Piece.width;
            floatLine[i][1] = (indexLine[i][1]+1.5f)*Piece.height;
        }
        return floatLine;
    }

    public void putMotionEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int i = (int) (x / Piece.width) - 1;
            int j = (int) (y / Piece.height) - 1;
            if (i >= 0 && j >= 0 && i < pieces.length && j < pieces[0].length) {
                if (pieces[i][j] != null) {
                    addPiece(pieces[i][j]);
                }
            }
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            gameView.postInvalidate();
        }
    }

    public int getPiecesNumer() {
        int number = 0;
        for(int i = 0; i < pieces.length; i++){
            for(int j = 0; j < pieces[0].length; j++){
                if(pieces[i][j]!=null){
                    number++;
                }
            }
        }
        return number;
    }

}
