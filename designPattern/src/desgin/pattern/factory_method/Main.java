package desgin.pattern.factory_method;

public class Main {

    public static void main(String[] args) {

        RobotFactory rf = new SuperRobotFactory();
        Robot r = rf.createRobot("super");
        Robot r2 = rf.createRobot("power");

        System.out.println(r.getName());
        System.out.println(r2.getName());
    }
}
