package exercises.object_oriented_paradigm.bank;

public abstract class Account {
    private String name;
    private static int counter = 0;
    private int ID;
    private double balance;

    Account(String name, int balance) {
        this.name = name;
        this.balance = balance;
        ID = ++counter;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
