package desgin.pattern.decorator;

public class Main {
    public static void main(String[] args) {
        Beverage beverage = new Shot(new Shot(new Americano()));

        System.out.println("메뉴 : " + beverage.getDescription());
        System.out.println("가격 : " + beverage.cost());
    }
}
