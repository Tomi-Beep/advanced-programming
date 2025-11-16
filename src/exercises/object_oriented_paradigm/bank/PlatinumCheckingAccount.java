package exercises.object_oriented_paradigm.bank;

public class PlatinumCheckingAccount extends InterestCheckingAccount {
    PlatinumCheckingAccount(String name, int balance) {
        super(name, balance);
    }

    @Override
    public void addInterest() {
        deposit(getBalance() * INTEREST_RATE * 2);
    }
}
