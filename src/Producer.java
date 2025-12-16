public class Producer implements Runnable {

    private final CircularBuffer<String> buffer;
    private final int producerId;
    private int counter = 0;

    public Producer(CircularBuffer<String> buffer, int producerId) {
        this.buffer = buffer;
        this.producerId = producerId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = "Потік No " + producerId +
                        " згенерував повідомлення " + counter++;
                buffer.put(msg);
                Thread.sleep(10);
            }
        } catch (InterruptedException ignored) {}
    }
}
