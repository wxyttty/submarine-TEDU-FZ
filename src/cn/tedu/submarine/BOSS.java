package cn.tedu.submarine;

import javax.swing.*;
import java.awt.*;

public class BOSS extends SeaObject implements EnemyLife,EnemyScore{
    private int blood;
    public BOSS(int width, int height, int x, int y, int speed) {
        super(width, height, x, y, 3);
        blood = 20;
    }

    @Override
    public int getLife() {
        return 10;
    }

    @Override
    public int getScore() {
        return 1000;
    }
    private int enter=0;
    @Override
    public void move() {
        //在游戏界面中不断移动
        x += speed;
        if (x>=500){
            enter++;
            speed = -speed;
        }
        if (x<=0  && enter!=0)
            speed = -speed;
    }
    public void lostLife() {
        blood --;
    }

    public int getBlood(){
        return blood;
    }

    public void bossDead(){
        if (blood<=0)
            state = DEAD;
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
        return Images.boss;//先不加
    }
}
