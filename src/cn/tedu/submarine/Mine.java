package cn.tedu.submarine;
import javax.swing.ImageIcon;
/** 水雷:是海洋对象 */
public class Mine extends SeaObject {
    /** 构造方法 */
    public Mine(int x,int y){
        super(11,11,x,y,1);
    }

    /** 重写move()移动 */
    public void move(){
        y -= speed; //y-(向上)
    }

    /** 重写getImage()获取图片 */
    public ImageIcon getImage(){
        return Images.mine; //返回水雷图片
    }

    /** 重写isOutOfBounds()检测潜艇越界 */
    public boolean isOutOfBounds(){
        return y<=150-height; //水雷的y<=150-水雷的高，即为越界了
    }
}





















