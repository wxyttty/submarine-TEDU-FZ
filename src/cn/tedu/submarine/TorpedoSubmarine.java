package cn.tedu.submarine;
import javax.swing.ImageIcon;
/** 鱼雷潜艇:是海洋对象，也是分 */
public class TorpedoSubmarine extends SeaObject implements EnemyScore {
    /** 构造方法 */
    public TorpedoSubmarine(){
        super(64,20);
    }

    /** 重写move()移动 */
    public void move(){
        x += speed; //x+(向右)
    }

    /** 重写getImage()获取图片 */
    public ImageIcon getImage(){
        return Images.torpesubm; //返回鱼雷潜艇图片
    }

    /** 重写getScore()得分 */
    public int getScore(){
        return 40; //打掉鱼雷潜艇，得40分
    }
}




















