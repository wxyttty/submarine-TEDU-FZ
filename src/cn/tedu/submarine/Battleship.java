package cn.tedu.submarine;
import javax.swing.ImageIcon;
/** 战舰:是海洋对象 */
public class Battleship extends SeaObject {
    private int life;   //命数
    /** 构造方法 */
    public Battleship(){
        super(66,26,270,124,20);
        life = 5;
    }

    /** 重写move()移动 */
    public void move(){
        //暂时搁置
    }

    /** 重写getImage()获取图片 */
    public ImageIcon getImage(){
        return Images.battleship; //返回战舰图片
    }
    //添加保护罩
    public Cover creatCover(){
        return new Cover(this.x-4,this.y+10,this.speed);
    }
    /** 战舰发射深水炸弹---生成深水炸弹对象 */
    public Bomb[] shootBomb(){
        Bomb[] b = new Bomb[3];
        b[0] = new Bomb(this.x,this.y);
        b[1] = new Bomb(this.x,this.y);
        b[2] = new Bomb(this.x,this.y);
        return b;
        //深水炸弹的初始坐标就是战舰的坐标
    }

    /** 战舰左移 */
    public void moveLeft(){
        x -= speed; //x-(向左)
    }

    /** 战舰右移 */
    public void moveRight(){
        x += speed; //x+(向右)
    }
    //战舰2移动
    public void move2(int x){
        this.x = x - this.width/2;
    }
    /** 战舰增命 */
    public void addLife(int num){
        life += num;
    }

    /** 获取战舰的命数 */
    public int getLife(){
        return life; //返回命数
    }

    /** 减命 */
    public void subtractLife(){
        life--; //命数减1
    }
}




















