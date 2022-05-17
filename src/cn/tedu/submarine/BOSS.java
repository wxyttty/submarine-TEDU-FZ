package cn.tedu.submarine;

import javax.swing.*;
import java.awt.*;

public class BOSS extends SeaObject implements EnemyLife,EnemyScore{
    private int blood;
    public BOSS() {
        super(100, 50);
        blood = 20;
        speed = 3;
        y = 350;
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

    public Laser[] shootLaser(){
        Laser[] l = new Laser[3];
        l[0] = new Laser(this.x,this.y);
        l[1] = new Laser(this.x,this.y);
        l[2] = new Laser(this.x,this.y);
        return l;
    }


    @Override
    public ImageIcon getImage() {
        return Images.boss;//先不加
    }
}
