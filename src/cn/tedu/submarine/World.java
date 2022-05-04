package cn.tedu.submarine;
import javax.swing.JFrame;
import javax.swing.JPanel; //1.
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** 整个游戏世界 */
public class World extends JPanel { //2.
    AudioClip bgm;
    AudioClip bombMusic;
    public static final int WIDTH = 641;  //窗口的宽
    public static final int HEIGHT = 479; //窗口的高
    private static final int START = 0

    public static final int RUNNING = 1;   //运行状态
    public static final int GAME_OVER = -1; //游戏结束状态
    private int state = START; //当前状态(默认为启动状态)

    //如下这一堆就是窗口中所显示的对象
    private Battleship ship = new Battleship(); //战舰
    private Battleship ship2 = new Battleship(); //战舰2
    private SeaObject[] submarines = {}; //潜艇(侦察潜艇、鱼雷潜艇、水雷潜艇)数组
    private Mine[] mines = {}; //水雷数组
    private Bomb[] bombs = {}; //深水炸弹数组
    private Bomb[] bombs2 = {};
    private Cover cover;
    private BOSS[] bosses={};
    private Laser[] lasers={};
    
    public World(){
        try {
            bgm = Applet.newAudioClip(new File("./src/music/music.wav").toURI().toURL());
            bombMusic = Applet.newAudioClip(new File("./src/music/bombMusic.wav").toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
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
    
    //Boss进场
    private int bossEnterIndex = 400;
    public void BossEnterAction(){
        if (score>=bossEnterIndex){
            bossEnterIndex += 400;
            bosses = Arrays.copyOf(bosses,bosses.length+1);
            bosses[bosses.length-1] = new BOSS();

        }
    }
    //boss炸弹进场
    private int LasersEnterIndex = 0;
    public void LasersEnterAction(){
        LasersEnterIndex++;
        if (LasersEnterIndex%100==0){
            for (int i = 0; i < bosses.length; i++) {
                Laser laser = bosses[i].shootLaser1();
                lasers = Arrays.copyOf(lasers,lasers.length+1);
                lasers[lasers.length-1] = laser;
            }
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
    //炸弹入场
    public void bombEnterAction(){
        //单个炸弹
        /* Bomb object = ship.shootBomb();
        bombs = Arrays.copyOf(bombs,bombs.length+1);
        bombs[bombs.length-1] = object;*/
        //三个炸弹
        Bomb[] bomb = ship.shootBomb();
        bombs = Arrays.copyOf(bombs,bombs.length+bomb.length);
        System.arraycopy(bomb,0,bombs,bombs.length-bomb.length,bomb.length);

    }

    //二号机炸弹入场
    public void bombEnterAction2(){
        //单个炸弹
        /* Bomb object = ship.shootBomb();
        bombs = Arrays.copyOf(bombs,bombs.length+1);
        bombs[bombs.length-1] = object;*/
        //三个炸弹
        Bomb[] bomb = ship2.shootBomb();
        bombs2 = Arrays.copyOf(bombs2,bombs2.length+bomb.length);
        System.arraycopy(bomb,0,bombs2,bombs2.length-bomb.length,bomb.length);

    }
    /** 海洋对象移动 */
    public void moveAction(){ //每10毫秒走一次
        for(int i=0;i<submarines.length;i++){ //遍历所有潜艇
            submarines[i].move(); //潜艇移动
        }
        for(int i=0;i<mines.length;i++){ //遍历所有水雷
            mines[i].move(); //水雷移动
        }
        for (int i = 0; i < bosses.length; i++) {
            bosses[i].move();
        }
        for (int i = 0; i < lasers.length; i++) {
            lasers[i].move();
        }
        for (int i = 0; i < bombs.length; i++) {
            if (i%3==0){
                bombs[i].move();
            }else if (i%3==1){
                bombs[i].moveLeft();
            }else {
                bombs[i].moveRight();
            }
        }
        //第二架战机炸弹方法
        for (int i = 0; i < bombs2.length; i++) {
            if (i%3==0){
                bombs2[i].move();
            }else if (i%3==1){
                bombs2[i].moveLeft();
            }else {
                bombs2[i].moveRight();
            }
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

        //删除越界的二号炸弹
        for (int i = 0; i < bombs2.length; i+=3) {
            if (bombs2[i].isOutOfBounds() || bombs2[i].isDead()){
                bombs2[i] = bombs2[bombs2.length-3];
                bombs2[i+1] = bombs2[bombs2.length-2];
                bombs2[i+2] = bombs2[bombs2.length-1];
                bombs2 = Arrays.copyOf(bombs2,bombs2.length-3);
            }
        }
        //删除死亡的的boss
        for (int i = 0; i < bosses.length; i++) {
            if (bosses[i].isDead() ){
                bosses[i] = bosses[bosses.length-1];
                bosses = Arrays.copyOf(bosses,bosses.length-1);
            }
        }
        //删除越界的boss炸弹
        for (int i = 0; i < lasers.length; i++) {
            if (lasers[i].isDead() || lasers[i].isOutOfBounds()){
                lasers[i] = lasers[lasers.length-1];
                lasers = Arrays.copyOf(lasers,lasers.length-1);
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
                if (e.getKeyCode() == KeyEvent.VK_SPACE){
                    switch (state){
                        case START:
                            state = RUNNING;
                            bgm.play();
                            break;
                        case RUNNING:
                            bombEnterAction();
                            break;
                        case GAME_OVER:

                            score = 0;
                            ship = new BattleShip(170);
                            submarines = new SeaObject[0];
                            thunders = new SeaObject[0];
                            bombs = new Bomb[0];
                            bombs2 = new Bomb[0];
                            state = START;
                    }
                }
                if(e.getKeyCode()==KeyEvent.VK_LEFT){ //不要求掌握--若按键是左键头
                    ship.moveLeft();
                    if (null!=cover)
                        cover.x = ship.x;
                }
                if(e.getKeyCode()==KeyEvent.VK_RIGHT){ //不要求掌握--若按键是右键头
                    ship.moveRight();
                    if (null!=cover)
                        cover.x = ship.x;
                }
                if (e.getKeyCode() == KeyEvent.VK_Q && state==RUNNING){
                    clear();
                }
                if (e.getKeyCode() == KeyEvent.VK_W && state==RUNNING){
                    if (null==cover)
                        cover = ship.creatCover();
                }
            }
        }; //键盘适配器
        this.addKeyListener(k); //添加侦听--不要求掌握

        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ship2 != null){
                    bombEnterAction2();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                if (state==RUNNING && ship2!=null){
                    int x = e.getX();
                    ship2.move2(x);
                }
            }
        };
        this.addMouseListener(mouse);       //鼠标点击发射
        this.addMouseMotionListener(mouse); //跟随鼠标移动

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
            case START:
                Images.start.paintIcon(null,g,0,0);
                break;
            case GAME_OVER: //游戏结束状态时，画游戏结束图
                Images.gameover.paintIcon(null,g,0,0); //画游戏结束图
                break;
            case RUNNING: //运行状态时
                Images.sea.paintIcon(null,g,0,0); //画海洋图
                ship.paintImage(g); //画战舰

                if (cover!=null)
                    cover.paintImage(g);

                if (ship2!=null)
                    ship2.paintImage(g);

                for (int i = 0; i < submarines.length ; i++) {
                    submarines[i].paintImage(g);
                }
                for (int i = 0; i < bosses.length ; i++) {
                    bosses[i].paintImage(g);
                }
                for (int i = 0; i < lasers.length ; i++) {
                    lasers[i].paintImage(g);
                }
                for (int i = 0; i < mines.length ; i++) {
                    mines[i].paintImage(g);
                }
                for (int i = 0; i < bombs.length ; i++) {
                    bombs[i].paintImage(g);
                }
                for (int i = 0; i < bombs2.length ; i++) {
                    bombs2[i].paintImage(g);
                }
                g.drawString("SCORE: "+score,200,50); //不要求掌握
                g.drawString("LIFE: "+ship.getLife(),400,50); //不要求掌握
                //最高分
                try {
                    g.drawString("最高分: "+readScore(),400,100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //战舰的血条
                g.draw3DRect(100, 30, 100, 10, true);
                g.setColor(Color.red);
                g.fill3DRect(100, 30, ship.getLife() * 20, 10, true);
                if (ship2!=null){
                    g.draw3DRect(100, 30, 100, 10, true);
                    g.setColor(Color.red);
                    g.fill3DRect(380, 30, ship2.getLife() * 20, 10, true);
                }
                //boss的血条
                for (int i = 0; i < bosses.length; i++) {
                    g.draw3DRect(bosses[i].x, bosses[i].y-15, 100, 10, true);
                    g.setColor(Color.green);
                    g.fill3DRect(bosses[i].x, bosses[i].y-15, bosses[i].getBlood() * 5, 10, true);
                }
                break;
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















