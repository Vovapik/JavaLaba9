import java.util.*;
import java.util.concurrent.*;

public class BankTest {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of accounts: ");
        int accountNum = scanner.nextInt();

        System.out.print("Enter number of transfer tasks (threads): ");
        int threadNum = scanner.nextInt();

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

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        for (int i = 0; i < threadNum; i++) {
            pool.submit(() -> {
                Account from = accounts.get(rnd.nextInt(accountNum));
                Account to = accounts.get(rnd.nextInt(accountNum));

                if (from == to) return;

                int amount = rnd.nextInt(50);

                synchronized (from) {
                    if (from.getBalance() >= amount) {
                        bank.transfer(from, to, amount);
                    }
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        int finalSum = accounts.stream()
                .mapToInt(Account::getBalance)
                .sum();

        System.out.println("Final total: " + finalSum);
    }
}
