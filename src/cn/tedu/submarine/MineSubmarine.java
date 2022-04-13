package cn.tedu.submarine;
import javax.swing.ImageIcon;
/** 水雷潜艇:是海洋对象，也是命 */
public class MineSubmarine extends SeaObject implements EnemyLife {
    /** 构造方法 */
    public MineSubmarine(){
        super(63,19);
    }

    /** 重写move()移动 */
    public void move(){
        x += speed; //x+(向右)
    }

    /** 重写getImage()获取图片 */
    public ImageIcon getImage(){
        return Images.minesubm; //返回水雷潜艇图片
    }

    /** 水雷潜艇发射水雷---生成水雷对象 */
    public Mine shootMine(){
        int x = this.x+this.width; //x:水雷潜艇的x+水雷潜艇的宽
        int y = this.y-5;          //y:水雷潜艇的y-固定的5
        return new Mine(x,y); //返回水雷对象
    }

    /** 重写getLife()得命 */
    public int getLife(){
        return 1; //打掉水雷潜艇，战舰得1条命
    }

}

















