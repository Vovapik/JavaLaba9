public class Transfer {
    public final Account from;
    public final Account to;
    public final int amount;

    public Transfer(Account from, Account to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}