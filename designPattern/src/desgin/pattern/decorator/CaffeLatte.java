package desgin.pattern.decorator;

public class CaffeLatte extends Beverage {
    public CaffeLatte() {
        this.description = "카페라떼";
    }

    @Override
    public int cost() {
        return 5000;
    }
}
