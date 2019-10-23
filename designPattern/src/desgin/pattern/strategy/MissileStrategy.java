package desgin.pattern.strategy;

public class MissileStrategy implements AttackStrategy{
    @Override
    public void attack() {
        System.out.println("Missile");
    }
}
