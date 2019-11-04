package desgin.pattern.singleton;

public class Singleton {
    private Singleton() {

    }

    public static Singleton getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder  {
        public static final Singleton INSTANCE = new Singleton();

    }
}
