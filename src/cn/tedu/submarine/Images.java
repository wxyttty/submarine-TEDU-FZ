package cn.tedu.submarine;
import javax.swing.ImageIcon;
/** 图片类 */
public class Images {
//  公开的  静态的  图片数据类型   变量名
    public static ImageIcon battleship; //战舰图
    public static ImageIcon obsersubm;  //侦察潜艇图
    public static ImageIcon torpesubm;  //鱼雷潜艇图
    public static ImageIcon minesubm;   //水雷潜艇图
    public static ImageIcon mine;       //水雷图
    public static ImageIcon bomb;       //深水炸弹图
    public static ImageIcon sea;        //海洋图
    public static ImageIcon boss;
    public static ImageIcon cover;
    public static ImageIcon gameover;
    public static ImageIcon start;
    public static ImageIcon laser;

    static{ //初始化静态图片
        battleship = new ImageIcon("img/battleship.png");
        obsersubm = new ImageIcon("img/obsersubm.png");
        torpesubm = new ImageIcon("img/torpesubm.png");
        minesubm = new ImageIcon("img/minesubm.png");
        bomb = new ImageIcon("img/bomb.png");
        torpesubm = new ImageIcon("img/torpesubm.png");
        mine = new ImageIcon("img/mine.png");
        sea = new ImageIcon("img/sea.png");
        boss = new ImageIcon("img/boss.png");
        cover = new ImageIcon("img/cover.png");
        gameover = new ImageIcon("img/gameover.png");
        start = new ImageIcon("img/start.png");
        laser = new ImageIcon("img/laser.png");
    }

    public static void main(String[] args) {
        System.out.println(battleship.getImageLoadStatus()); //若返回8表示读取正确，否则表示读取失败
        System.out.println(obsersubm.getImageLoadStatus());
        System.out.println(torpesubm.getImageLoadStatus());
        System.out.println(minesubm.getImageLoadStatus());
        System.out.println(mine.getImageLoadStatus());
        System.out.println(bomb.getImageLoadStatus());
        System.out.println(sea.getImageLoadStatus());
        System.out.println(start.getImageLoadStatus());
        System.out.println(gameover.getImageLoadStatus());
        System.out.println(boss.getImageLoadStatus());
        System.out.println(fired.getImageLoadStatus());
    }

}














