package cn.tedu.submarine;

public class Laser2 extends Laser{

    public Laser2(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
        x -= speed/2;//x-(向上)
        y -= speed/2; //y-(向上)
    }
}
