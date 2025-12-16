public class Translator implements Runnable {

    private final CircularBuffer<String> input;
    private final CircularBuffer<String> output;
    private final int translatorId;

    public Translator(CircularBuffer<String> input,
                      CircularBuffer<String> output,
                      int translatorId) {
        this.input = input;
        this.output = output;
        this.translatorId = translatorId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = input.take();
                String translated =
                        "Потік номер " + translatorId +
                                " переклав повідомлення: " + msg;
                output.put(translated);
            }
        } catch (InterruptedException ignored) {}
    }
}
