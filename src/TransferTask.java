import java.util.List;
import java.util.concurrent.RecursiveAction;

public class TransferTask extends RecursiveAction {

    private static final int THRESHOLD = 100;

    private final List<Transfer> transfers;
    private final int start;
    private final int end;
    private final Bank bank;

    public TransferTask(List<Transfer> transfers, int start, int end, Bank bank) {
        this.transfers = transfers;
        this.start = start;
        this.end = end;
        this.bank = bank;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                Transfer t = transfers.get(i);
                try {
                    bank.transfer(t.from, t.to, t.amount);
                } catch (IllegalArgumentException ignored) {
                }
            }
        } else {
            int mid = (start + end) / 2;
            invokeAll(
                    new TransferTask(transfers, start, mid, bank),
                    new TransferTask(transfers, mid, end, bank)
            );
        }
    }
}
