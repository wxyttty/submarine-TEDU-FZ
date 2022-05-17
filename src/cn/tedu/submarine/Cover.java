package cn.tedu.submarine;

import javax.swing.*;

public class Cover extends SeaObject{
    public Cover(int x,int y,int speed){
        super(84,35,x,y,speed);
    }
    @Override
    public void move() {

    }
    public ImageIcon getImage(){
        return Images.cover;
    }
}
