package laboratory_problems.problems_01;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

class Account {
    private static final Random RNG = new Random();
    private static final Set<Long> USED_IDS = new HashSet<>();

    private final long id;
    private final String name;
    private double balance;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.id = generateUniqueId();
    }

    private static long generateUniqueId() {
        long id;
        synchronized (USED_IDS) {
            do {
                id = RNG.nextLong();
            } while (USED_IDS.contains(id));
            USED_IDS.add(id);
        }
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nBalance: %.2f$\n", name, balance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;
        return id == account.id &&
                Double.compare(account.balance, balance) == 0 &&
                Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance);
    }
}

abstract class Transaction {
    private final long fromId;
    private final long toId;
    private final String description;
    private final double amount;

    public Transaction(long fromId, long toId, String description, double amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.description = description;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public long getFromId() {
        return fromId;
    }

    public long getToId() {
        return toId;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Return the provision amount for this transaction (in dollars).
     * Subclasses should override.
     */
    public abstract double getProvision();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;
        return fromId == that.fromId &&
                toId == that.toId &&
                Double.compare(that.amount, amount) == 0 &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId, description, amount);
    }
}

class FlatAmountProvisionTransaction extends Transaction {
    private final double flatAmount;

    public FlatAmountProvisionTransaction(long fromId, long toId, double amount, double flatProvision) {
        super(fromId, toId, "FlatAmount", amount);
        this.flatAmount = flatProvision;
    }

    public double getFlatAmount() {
        return flatAmount;
    }

    @Override
    public double getProvision() {
        return flatAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlatAmountProvisionTransaction)) return false;
        if (!super.equals(o)) return false;
        FlatAmountProvisionTransaction that = (FlatAmountProvisionTransaction) o;
        return Double.compare(that.flatAmount, flatAmount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), flatAmount);
    }
}

class FlatPercentProvisionTransaction extends Transaction {
    private final int percent;

    public FlatPercentProvisionTransaction(long fromId, long toId, double amount, int centsPerDolar) {
        super(fromId, toId, "FlatPercent", amount);
        this.percent = centsPerDolar;
    }

    public int getPercent() {
        return percent;
    }

    @Override
    public double getProvision() {
        // percent is integer percentage (e.g., 20 -> 20%)
        return (getAmount() * percent) / 100.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlatPercentProvisionTransaction)) return false;
        if (!super.equals(o)) return false;
        FlatPercentProvisionTransaction that = (FlatPercentProvisionTransaction) o;
        return percent == that.percent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), percent);
    }
}

class Bank {
    private final String name;
    private final Account[] accounts;
    private double totalTransfers;
    private double totalProvision;

    public Bank(String name, Account[] accounts) {
        this.name = name;
        this.accounts = Arrays.copyOf(accounts, accounts.length);
        this.totalTransfers = 0.0;
        this.totalProvision = 0.0;
    }

    /**
     * Returns a shallow copy of the accounts array so callers can't mutate internal array.
     */
    public Account[] getAccounts() {
        return Arrays.copyOf(accounts, accounts.length);
    }

    /**
     * Try to perform the transaction.
     * Returns true if successful (both accounts in bank and sender has enough funds),
     * otherwise false.
     */
    public boolean makeTransaction(Transaction t) {

        Account from = findAccountById(t.getFromId());
        Account to = findAccountById(t.getToId());
        if (from == null || to == null) return false;

        double provision = t.getProvision();
        double totalDebit = t.getAmount() + provision;

        if (from.getBalance() < totalDebit) {
            return false;
        }

        from.setBalance(from.getBalance() - totalDebit);
        to.setBalance(to.getBalance() + t.getAmount());

        totalTransfers += t.getAmount();
        totalProvision += provision;

        return true;
    }

    private Account findAccountById(long id) {
        for (Account a : accounts) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    public double totalTransfers() {
        return totalTransfers;
    }

    public double totalProvision() {
        return totalProvision;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Name: %s\n\n", name));
        for (Account a : accounts) {
            sb.append(a.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bank bank = (Bank) o;
        return Objects.equals(name, bank.name) &&
                Double.compare(bank.totalTransfers, totalTransfers) == 0 &&
                Double.compare(bank.totalProvision, totalProvision) == 0 &&
                Arrays.equals(accounts, bank.accounts);
    }

    public void forEachConditional(Predicate<Account> predicate, Consumer<Account> consumer) {
        for (Account a : accounts) {
            if (predicate.test(a))
                consumer.accept(a);
        }
    }
}

// ... (omitting existing imports and classes: Account, Transaction, FlatAmountProvisionTransaction, FlatPercentProvisionTransaction)

// The Bank class is assumed to have the forEachConditional method already:
/*
class Bank {
    // ... existing fields and methods ...

    public void forEachConditional(Predicate<Account> predicate, Consumer<Account> consumer) {
        for (Account a : accounts) {
            if (predicate.test(a))
                consumer.accept(a);
        }
    }
    // ... existing methods ...
}
*/

public class BankTester {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        String test_type = jin.nextLine();
        switch (test_type) {
            case "conditional_bonus": // <-- NEW CASE
                testForEachConditional();
                break;
        }
        jin.close();
    }

    private static double parseAmount(String amount) {
        return Double.parseDouble(amount.replace("$", ""));
    }

    // ... (omitting testEquals, testTypicalUsage, and getTransaction)

    private static void testForEachConditional() {
        System.out.println("--- Testing forEachConditional (Loyalty Bonus) ---");

        // 1. Setup accounts
        Account account1 = new Account("Ivan", 5000.0);
        Account account2 = new Account("Petar", 12500.0); // Will receive bonus
        Account account3 = new Account("Dragan", 10001.0); // Will receive bonus
        Account account4 = new Account("Elena", 9999.99);
        Account account5 = new Account("Marko", 25000.0); // Will receive bonus

        Account[] accounts = {account1, account2, account3, account4, account5};
        Bank bank = new Bank("LoyaltyBank", accounts);

        System.out.println("Initial State:");
        System.out.println(bank.toString());

        // 2. Define the Predicate (Condition: Balance > $10000)
        Predicate<Account> richAccountPredicate = account -> account.getBalance() > 10000.0;

        // 3. Define the Consumer (Action: Add $100 to balance)
        double bonusAmount = 100.0;
        Consumer<Account> loyaltyBonusConsumer = account -> {
            account.setBalance(account.getBalance() + bonusAmount);
        };

        // 4. Execute the method
        bank.forEachConditional(richAccountPredicate, loyaltyBonusConsumer);

        // 5. Verify results
        System.out.println("State After Applying Loyalty Bonus (Balance > $10000 gets $100):");
        System.out.println(bank.toString());

        // Check for specific accounts
        System.out.println("Verification:");
        // account1 (5000.0) should be unchanged
        System.out.println(String.format("Ivan's final balance: %.2f$ (Expected: 5000.00$)", account1.getBalance()));
        // account2 (12500.0) should increase by 100
        System.out.println(String.format("Petar's final balance: %.2f$ (Expected: 12600.00$)", account2.getBalance()));
        // account4 (9999.99) should be unchanged
        System.out.println(String.format("Elena's final balance: %.2f$ (Expected: 9999.99$)", account4.getBalance()));

        if (account1.getBalance() == 5000.0 &&
                account2.getBalance() == 12600.0 &&
                account4.getBalance() == 9999.99) {
            System.out.println("forEachConditional executed successfully.");
        } else {
            System.out.println("forEachConditional failed verification.");
        }
    }
}