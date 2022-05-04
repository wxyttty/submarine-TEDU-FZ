package cn.tedu.submarine;
import javax.swing.ImageIcon;
/** 深水炸弹:是海洋对象 */
public class Bomb extends SeaObject {
    /** 构造方法 */
    public Bomb(int x,int y){ //每个深水炸弹的x和y是不同的
        super(9,12,x,y,3);
    }

    /** 重写move()移动 */
    public void move(){
        y += speed; //y+(向下)
    }

    public void moveLeft(){
        y += speed;
        x -= speed;
    }
    public void moveRight(){
        y += speed;
        x += speed;
    }

    /** 重写getImage()获取图片 */
    public ImageIcon getImage(){
        return Images.bomb; //返回深水炸弹图片
    }

    /** 重写isOutOfBounds()检测潜艇越界 */
    public boolean isOutOfBounds(){
        return this.y>=World.HEIGHT || this.x>=World.WIDTH ||this.x<=0-this.width; //深水炸弹越界了
    }
}























