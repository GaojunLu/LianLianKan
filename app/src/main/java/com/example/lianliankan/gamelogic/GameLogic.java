package com.example.lianliankan.gamelogic;

import com.example.lianliankan.view.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
public class GameLogic {
    List<int[][]> rows1, rows2;//上下左右四个方向的射线段
    Piece[][] pieces;
    List<int[][]> linkPoints = new ArrayList<int[][]>();//可连线的点坐标，四个点一组
    int[] selectPoint1, selectPoint2;//所选图片的坐标

    public boolean canLink(List<Piece> selectPieces, Piece[][] pieces) {
        selectPoint1 = new int[]{selectPieces.get(0).getIndexX(), selectPieces.get(0).getIndexY()};
        selectPoint2 = new int[]{selectPieces.get(1).getIndexX(), selectPieces.get(1).getIndexY()};
        this.pieces = pieces;
        rows1 = getRows(selectPieces.get(0));
        rows2 = getRows(selectPieces.get(1));
        if (canLink(rows1, rows2)) {
            return true;
        }
        return false;
    }

    //获取四方向射线段
    private List<int[][]> getRows(Piece piece) {
        List<int[][]> rows = new ArrayList<int[][]>();
        List<int[]> oneRow = new ArrayList<int[]>();
        int x, y;
        //上
        x = piece.getIndexX();
        y = piece.getIndexY();
        do {
            int[] temp = new int[2];
            temp[0] = x;
            temp[1] = y;
            oneRow.add(temp);
            y--;
        } while (y >= 0 && pieces[x][y] == null || y == -1);//范围内遇到空位置、或超出顶部一格
        int[][] rowUp = new int[oneRow.size()][2];
        for (int i = 0; i < oneRow.size(); i++) {
            rowUp[i] = oneRow.get(i);
        }
        oneRow.clear();
        //下
        x = piece.getIndexX();
        y = piece.getIndexY();
        do {
            int[] temp = new int[2];
            temp[0] = x;
            temp[1] = y;
            oneRow.add(temp);
            y++;
        }
        while (y < pieces[0].length && pieces[x][y] == null || y == pieces[0].length);//范围内遇到空位置、或超出底部一格
        int[][] rowDown = new int[oneRow.size()][2];
        for (int i = 0; i < oneRow.size(); i++) {
            rowDown[i] = oneRow.get(i);
        }
        oneRow.clear();
        //左
        x = piece.getIndexX();
        y = piece.getIndexY();
        do {
            int[] temp = new int[2];
            temp[0] = x;
            temp[1] = y;
            oneRow.add(temp);
            x--;
        } while (x >= 0 && pieces[x][y] == null || x == -1);//范围内遇到空位置、或超出左边一格
        int[][] rowLeft = new int[oneRow.size()][2];
        for (int i = 0; i < oneRow.size(); i++) {
            rowLeft[i] = oneRow.get(i);
        }
        oneRow.clear();
        //右
        x = piece.getIndexX();
        y = piece.getIndexY();
        do {
            int[] temp = new int[2];
            temp[0] = x;
            temp[1] = y;
            oneRow.add(temp);
            x++;
        } while (x < pieces.length && pieces[x][y] == null || x == pieces.length);//范围内遇到空位置、或超出右边一格
        int[][] rowRight = new int[oneRow.size()][2];
        for (int i = 0; i < oneRow.size(); i++) {
            rowRight[i] = oneRow.get(i);
        }
        oneRow.clear();
        rows.add(rowUp);
        rows.add(rowDown);
        rows.add(rowLeft);
        rows.add(rowRight);
        return rows;
    }

    //四射线段是否可连
    private boolean canLink(List<int[][]> rows1, List<int[][]> rows2) {
        for (int i = 0; i < rows1.size(); i++) {
            for (int j = 0; j < rows2.size(); j++) {
                if (canLink(rows1.get(i), rows2.get(j))) {

                }
            }
        }
        if (linkPoints.size() >= 1) {
            return true;
        }
        return false;
    }

    //单条之间是否可连
    private boolean canLink(int[][] row1, int[][] row2) {///////////////////////////////////////////
        for (int i = 0; i < row1.length; i++) {
            for (int j = 0; j < row2.length; j++) {
                if (canLink(row1[i], row2[j])) {
                    int[][] temp = new int[][]{selectPoint1, row1[i], row2[j], selectPoint2};
                    boolean exist2 = false, exist1 = true;
                    for (int m = 0; m < linkPoints.size(); m++) {
                        for (int n = 0; n < 4; n++) {
                            if (!Arrays.equals(temp[n], linkPoints.get(m)[n])) {
                                exist1 = false;
                            }
                        }
                        if(exist1){
                            exist2 = true;
                        }else{
                            exist1 = true;
                        }
                    }
                    if (!exist2||linkPoints.size()==0) {
                        linkPoints.add(temp);
                    }
                }
            }
        }
        if (linkPoints.size() >= 1) {
            return true;
        }
        return false;
    }

    //单点之间是否可连
    private boolean canLink(int[] p1, int[] p2) {
        int point1X = p1[0], point1Y = p1[1], point2X = p2[0], point2Y = p2[1];
        if (Arrays.equals(p1, p2)) {//同一点
            return true;
        } else if (point1X == point2X) {//同列
            if (point1X < 0 || point1X >= pieces.length) {//外围空间
                return true;
            } else {
                if (point1Y > point2Y) {//要令p1<=p2
                    int temp;
                    temp = point2Y;
                    point2Y = point1Y;
                    point1Y = temp;
                }
                while (point1Y < point2Y - 1) {//向下走
                    if (point1Y<0||pieces[point1X][point1Y+1] == null) {
                        point1Y++;
                    } else {
                        break;
                    }
                }
                if (point1Y == point2Y - 1) {//能走到头就是可连
                    return true;
                }
            }
        } else if (point1Y == point2Y) {//同行
            if (point1Y < 0 || point1Y >= pieces[0].length) {
                return true;
            } else {
                if (point1X > point2X) {
                    int temp;
                    temp = point2X;
                    point2X = point1X;
                    point1X = temp;
                }
                while (point1X < point2X -1) {//向右走
                    if (point1X<0||pieces[point1X+1][point1Y] == null) {
                        point1X++;
                    } else {
                        break;
                    }
                }
                if (point1X == point2X - 1) {//能走到头就是可连
                    return true;
                }
            }
        }
        return false;
    }

    public List<int[][]> getLinkPoints() {
        return linkPoints;
    }
}
