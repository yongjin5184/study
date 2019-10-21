package desgin.pattern.decorator;

public class Americano extends Beverage {
    public Americano() {
        this.description = "에스프레소";
    }

    @Override
    public int cost() {
        return 4000;
    }
}
