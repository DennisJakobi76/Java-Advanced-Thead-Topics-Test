package concurrencydesignpatterns;

public class ThreadPerMessagePatternExample {

    public static void main(String[] args) {
        String[] messages ={"Message1", "Message2", "Message3", "Message4", "Message5"};

        for (String message : messages) {
            Thread thread = new Thread(new PrintRequestHandler(message));
            thread.start();
        }
    }
}
