package lambda_expressions;

public class Java8Tester {
    final static String salutation = "Hello! ";

    public static void main(String args[]) {
        GreetingService greetService1 = message ->
                System.out.println(salutation + message);
        greetService1.sayMessage("yongjin");
    }

    interface GreetingService {
        void sayMessage(String message);
    }
}
