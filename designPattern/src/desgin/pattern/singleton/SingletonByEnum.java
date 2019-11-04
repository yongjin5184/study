package desgin.pattern.singleton;

public enum SingletonByEnum {
    INSTANCE;

    public static SingletonByEnum getInstance() {
        return INSTANCE;
    }
}
