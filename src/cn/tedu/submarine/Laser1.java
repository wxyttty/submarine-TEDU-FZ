package cn.tedu.submarine;

public class Laser1 extends Laser{

    public Laser1(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
            y -= speed; //y-(向上)
    }
}
