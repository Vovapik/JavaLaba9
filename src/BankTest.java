import java.util.*;
import java.util.concurrent.*;

public class BankTest {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of accounts: ");
        int accountNum = scanner.nextInt();

        System.out.print("Enter number of transfer tasks: ");
        int transferCount = scanner.nextInt();

        Bank bank = new Bank();
        Random rnd = new Random();

        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < accountNum; i++) {
            accounts.add(new Account(i, rnd.nextInt(1000)));
        }

        int initialSum = accounts.stream()
                .mapToInt(Account::getBalance)
                .sum();

        System.out.println("Initial total: " + initialSum);

        List<Transfer> transfers = new ArrayList<>();
        for (int i = 0; i < transferCount; i++) {
            Account from = accounts.get(rnd.nextInt(accountNum));
            Account to = accounts.get(rnd.nextInt(accountNum));
            if (from != to) {
                transfers.add(new Transfer(from, to, rnd.nextInt(50)));
            }
        }

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new TransferTask(transfers, 0, transfers.size(), bank));

        int finalSum = accounts.stream()
                .mapToInt(Account::getBalance)
                .sum();

        System.out.println("Final total: " + finalSum);
    }
}
