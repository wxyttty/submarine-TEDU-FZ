package cn.tedu.submarine;
import javax.swing.JFrame;
import javax.swing.JPanel; //1.
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** 整个游戏世界 */
public class World extends JPanel { //2.
    public static final int WIDTH = 641;  //窗口的宽
    public static final int HEIGHT = 479; //窗口的高

    public static final int RUNNING = 0;   //运行状态
    public static final int GAME_OVER = 1; //游戏结束状态
    private int state = RUNNING; //当前状态(默认为启动状态)

    //如下这一堆就是窗口中所显示的对象
    private Battleship ship = new Battleship(); //战舰
    private SeaObject[] submarines = {}; //潜艇(侦察潜艇、鱼雷潜艇、水雷潜艇)数组
    private Mine[] mines = {}; //水雷数组
    private Bomb[] bombs = {}; //深水炸弹数组

    /** 生成潜艇(侦察潜艇、鱼雷潜艇、水雷潜艇)对象 */
    public SeaObject nextSubmarine(){
        Random rand = new Random(); //随机数对象
        int type = rand.nextInt(20); //0到19之间的随机数
        if(type<10){ //0到9时，返回侦察潜艇对象
            return new ObserveSubmarine();
        }else if(type<15){ //10到14时，返回鱼雷潜艇对象
            return new TorpedoSubmarine();
        }else{ //15到19时，返回水雷潜艇对象
            return new MineSubmarine();
        }
    }

    private int subEnterIndex = 0; //潜艇入场计数
    /** 潜艇(侦察潜艇、鱼雷潜艇、水雷潜艇)入场 */
    public void submarineEnterAction(){ //每10毫秒走一次
        subEnterIndex++; //每10毫秒增1
        if(subEnterIndex%40==0){ //每400(40*10)毫秒走一次
            SeaObject obj = nextSubmarine(); //获取潜艇对象
            submarines = Arrays.copyOf(submarines,submarines.length+1); //扩容
            submarines[submarines.length-1] = obj; //将obj添加到submarines的最后一个元素上
        }
    }

    private int mineEnterIndex = 0; //水雷入场计数
    /** 水雷入场 */
    public void mineEnterAction(){ //每10毫秒走一次
        mineEnterIndex++; //每10毫秒增1
        if(mineEnterIndex%100==0){ //每1000(100*10)毫秒走一次
            for(int i=0;i<submarines.length;i++){ //遍历所有潜艇
                if(submarines[i] instanceof MineSubmarine){ //若潜艇为水雷潜艇
                    MineSubmarine ms = (MineSubmarine)submarines[i]; //将潜艇强转为水雷潜艇
                    Mine obj = ms.shootMine(); //获取水雷对象
                    mines = Arrays.copyOf(mines,mines.length+1); //扩容
                    mines[mines.length-1] = obj; //将obj添加到最后一个元素上
                }
            }
        }
    }

    /** 海洋对象移动 */
    public void moveAction(){ //每10毫秒走一次
        for(int i=0;i<submarines.length;i++){ //遍历所有潜艇
            submarines[i].move(); //潜艇移动
        }
        for(int i=0;i<mines.length;i++){ //遍历所有水雷
            mines[i].move(); //水雷移动
        }
        for(int i=0;i<bombs.length;i++){ //遍历所有深水炸弹
            bombs[i].move(); //深水炸弹动
        }
    }

    /** 删除越界海洋对象----避免内存泄漏 */
    public void outOfBoundsAction(){ //每10毫秒走一次
        for(int i=0;i<submarines.length;i++){ //遍历所有潜艇
            if(submarines[i].isOutOfBounds() || submarines[i].isDead()){ //若越界了或者死了的
                submarines[i] = submarines[submarines.length-1]; //修改越界元素为最后一个元素
                submarines = Arrays.copyOf(submarines,submarines.length-1); //缩容
            }
        }

        for(int i=0;i<mines.length;i++){ //遍历所有水雷
            if(mines[i].isOutOfBounds() || mines[i].isDead()){ //若越界了或者死了的
                mines[i] = mines[mines.length-1]; //修改越界元素为最后一个元素
                mines = Arrays.copyOf(mines,mines.length-1); //缩容
            }
        }

        for(int i=0;i<bombs.length;i++){ //遍历所有深水炸弹
            if(bombs[i].isOutOfBounds() || bombs[i].isDead()){ //若越界了或者死了的
                bombs[i] = bombs[bombs.length-1]; //修改越界元素为最后一个元素
                bombs = Arrays.copyOf(bombs,bombs.length-1); //缩容
            }
        }
    }

    private int score = 0; //玩家得分
    /** 深水炸弹与潜艇的碰撞 */
    public void bombBangAction(){ //每10毫秒走一次
        for(int i=0;i<bombs.length;i++){ //遍历所有炸弹
            Bomb b = bombs[i]; //获取每个炸弹
            for(int j=0;j<submarines.length;j++){ //遍历所有潜艇
                SeaObject s = submarines[j]; //获取每个潜艇
                if(b.isLive() && s.isLive() && s.isHit(b)){ //若都活着，并且还撞上了
                    s.goDead(); //潜艇去死
                    b.goDead(); //炸弹去死

                    if(s instanceof EnemyScore){ //若被撞潜艇为分
                        EnemyScore es = (EnemyScore)s; //将被撞潜艇强转为得分接口
                        score += es.getScore(); //玩家得分
                    }
                    if(s instanceof EnemyLife){ //若被撞潜艇为命
                        EnemyLife el = (EnemyLife)s; //将被撞潜艇强转为得命接口
                        int num = el.getLife(); //获取命数
                        ship.addLife(num); //战舰增命
                    }
                }
            }
        }
    }

    /** 水雷与战舰的碰撞 */
    public void mineBangAction(){ //每10毫秒走一次
        for(int i=0;i<mines.length;i++) { //遍历所有水雷
            Mine m = mines[i]; //获取每一个水雷
            if (m.isLive() && ship.isLive() && m.isHit(ship)) { //若都活着并且还撞上了
                m.goDead(); //水雷去死
                ship.subtractLife(); //战舰减命
            }
        }
    }

    /** 检测游戏结束 */
    public void checkGameOverAction(){ //每10毫秒走一次
        if(ship.getLife()<=0){ //若战舰的命数<=0，表示游戏结束了
            state = GAME_OVER; //则将当前状态修改为游戏结束状态
        }
    }

    /** 启动程序的执行 */
    public void action(){
        KeyAdapter k = new KeyAdapter() {  //--不要求掌握
            /** 重写keyReleased()按键抬起事件 */
            public void keyReleased(KeyEvent e) { //当按键抬起时会自动执行--不要求掌握
                if(e.getKeyCode()==KeyEvent.VK_SPACE){ //不要求掌握--若按键是空格键
                    Bomb obj = ship.shootBomb(); //获取深水炸弹对象
                    bombs = Arrays.copyOf(bombs,bombs.length+1); //扩容
                    bombs[bombs.length-1] = obj; //将obj添加到bombs的最后一个元素上
                }
                if(e.getKeyCode()==KeyEvent.VK_LEFT){ //不要求掌握--若按键是左键头
                    ship.moveLeft();
                }
                if(e.getKeyCode()==KeyEvent.VK_RIGHT){ //不要求掌握--若按键是右键头
                    ship.moveRight();
                }
            }
        }; //键盘适配器
        this.addKeyListener(k); //添加侦听--不要求掌握

        Timer timer = new Timer(); //定时器对象
        int interval = 10; //定时间隔(以毫秒为单位)
        timer.schedule(new TimerTask() {
            public void run() { //定时干的事(每10毫秒自动执行)
                submarineEnterAction(); //潜艇(侦察潜艇、鱼雷潜艇、水雷潜艇)入场
                mineEnterAction();      //水雷入场
                moveAction();           //海洋对象移动
                outOfBoundsAction();    //删除越界海洋对象
                bombBangAction();       //深水炸弹与潜艇的碰撞
                mineBangAction();       //水雷与战舰的碰撞
                checkGameOverAction();  //检测游戏结束
                repaint();              //重画(重新调用paint()方法)-----不要求掌握
            }
        }, interval, interval); //定时计划表
    }

    /** 重写paint()画 g:画笔-----------不要求掌握 */
    public void paint(Graphics g){ //每10毫秒走一次
        switch (state){ //根据当前状态做不同的处理
            case GAME_OVER: //游戏结束状态时，画游戏结束图
                Images.gameover.paintIcon(null,g,0,0); //画游戏结束图
                break;
            case RUNNING: //运行状态时
                Images.sea.paintIcon(null,g,0,0); //画海洋图
                ship.paintImage(g); //画战舰
                for(int i=0;i<submarines.length;i++){ //遍历所有潜艇
                    submarines[i].paintImage(g); //画潜艇
                }
                for(int i=0;i<mines.length;i++){ //遍历所有水雷
                    mines[i].paintImage(g); //画水雷
                }
                for(int i=0;i<bombs.length;i++){ //遍历所有深水炸弹
                    bombs[i].paintImage(g); //画深水炸弹
                }
                g.drawString("SCORE: "+score,200,50); //不要求掌握
                g.drawString("LIFE: "+ship.getLife(),400,50); //不要求掌握
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame(); //3.
        World world = new World();
        world.setFocusable(true);
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH+16, HEIGHT+39);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); //1)设置窗口可见  2)尽快调用paint()方法

        world.action(); //启动程序的执行
    }
}















