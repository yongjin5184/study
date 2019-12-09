package desgin.pattern.enumeration;

import java.lang.*;

enum Mobile {

    SAMSUNG(400){
        @Override
        public int showPrice(){
            return price + 1000;
        }
    }, APPLE(250)
    , LG(325);
    public int price;
    Mobile(int p) {
        price = p;
    }

    public int showPrice() {
        return price;
    }
}

public class EnumDemo {

    public static void main(String args[]) {

        System.out.println("CellPhone List:");
        for(Mobile m : Mobile.values()) {
            System.out.println(m + " costs " + m.showPrice() + " dollars");
        }

        Mobile ret;
        ret = Mobile.valueOf("SAMSUNG");
        System.out.println("Selected : " + ret);
    }
}