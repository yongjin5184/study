package desgin.pattern.decorator;

public class Shot extends CondimentDecorator {
    private Beverage beverage;

    public Shot(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 샷";
    }

    @Override
    public int cost() {
        return beverage.cost() + 400;
    }
}
