package cn.tedu.submarine;
import javax.swing.ImageIcon;
/** 侦察潜艇:是海洋对象，也是分 */
public class ObserveSubmarine extends SeaObject implements EnemyScore {
    /** 构造方法 */
    public ObserveSubmarine(){
        super(63,19);
    }

    /** 重写move()移动 */
    public void move(){
        x += speed; //x+(向右)
    }

    /** 重写getImage()获取图片 */
    public ImageIcon getImage(){
        return Images.obsersubm; //返回侦察潜艇图片
    }

    /** 重写getScore()得分 */
    public int getScore(){
        return 10; //打掉侦察潜艇，得10分
    }
}
















