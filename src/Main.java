public class Main {

    public static void main(String[] args) throws Exception {

        CircularBuffer<String> buffer1 = new CircularBuffer<>(10);
        CircularBuffer<String> buffer2 = new CircularBuffer<>(10);

        for (int i = 1; i <= 5; i++) {
            Thread t = new Thread(new Producer(buffer1, i));
            t.setDaemon(true);
            t.start();
        }

        for (int i = 1; i <= 2; i++) {
            Thread t = new Thread(new Translator(buffer1, buffer2, i));
            t.setDaemon(true);
            t.start();
        }

        for (int i = 0; i < 100; i++) {
            String msg = buffer2.take();
            System.out.println(msg);
        }

        System.out.println("Main thread finished.");
    }
}

