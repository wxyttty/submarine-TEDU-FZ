package cn.tedu.submarine;

import javax.swing.*;

public class Laser extends SeaObject{

    public Laser(int x,int y){
        super(15,15,x,y,2);
    }

    @Override
    public void move() {
        y -= speed;
    }

    public void moveLeft(){
        y += speed;
        x -= speed;
    }
    public void moveRight(){
        y += speed;
        x += speed;
    }
    public int lostScore() {
        return -30;
    }

    public ImageIcon getImage(){
        return Images.laser; //返暂时不加
    }

    public boolean isOutOfBounds(){
        return y<=150-height|| this.x>=World.WIDTH ||this.x<=0-this.width; //水雷的y<=150-水雷的高，即为越界了
    }
}
