package cn.tedu.submarine;

import javax.swing.*;

public abstract class Laser extends SeaObject{

    public Laser(int x,int y){
        super(15,15,x,y,2);
    }
    public int lostScore() {
        return -30;
    }

    public ImageIcon getImage(){
        return Images.laser; //返暂时不加
    }

    public boolean isOutOfBounds(){
        return y<=150-height; //水雷的y<=150-水雷的高，即为越界了
    }
}
