package desgin.pattern.strategy;

public class FlyingStrategy implements MovingStrategy {
    @Override
    public void move() {
        System.out.println("Fly");
    }
}
