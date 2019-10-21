package desgin.pattern.decorator;

public class Cream extends CondimentDecorator {
    private Beverage beverage;

    public Cream(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 크림";
    }

    @Override
    public int cost() {
        return beverage.cost() + 500;
    }
}
