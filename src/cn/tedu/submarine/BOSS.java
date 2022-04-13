package cn.tedu.submarine;

import javax.swing.*;

public class BOSS extends SeaObject implements EnemyLife,EnemyScore{

    public BOSS(int width, int height, int x, int y, int speed) {
        super(width, height, x, y, speed);
    }

    @Override
    public int getLife() {
        return 10;
    }

    @Override
    public int getScore() {
        return 1000;
    }

    @Override
    public void move() {
        //暂时不动
    }

    public Laser shootLaser1(){
        int x = this.x+this.width/2; //x:水雷潜艇的x+水雷潜艇的宽
        int y = this.y-5;          //y:水雷潜艇的y-固定的5
        return new Laser1(x,y); //返回水雷对象
    }

    public Laser shootLaser2(){
        int x = this.x+; //x:水雷潜艇的x+水雷潜艇的宽
        int y = this.y-5;          //y:水雷潜艇的y-固定的5
        return new Laser2(x,y); //返回水雷对象
    }

    public Laser shootLaser3(){
        int x = this.x+this.width; //x:水雷潜艇的x+水雷潜艇的宽
        int y = this.y-5;          //y:水雷潜艇的y-固定的5
        return new Laser3(x,y); //返回水雷对象
    }

    @Override
    public ImageIcon getImage() {
        return null;//先不加
    }
}
