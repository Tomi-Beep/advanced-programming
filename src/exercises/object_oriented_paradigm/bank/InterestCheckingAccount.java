package exercises.object_oriented_paradigm.bank;

public class InterestCheckingAccount extends Account implements InterestBearingAccount {

    public static final double INTEREST_RATE = 0.03;

    InterestCheckingAccount(String name, int balance) {
        super(name, balance);
    }

    @Override
    public void addInterest() {
        deposit(getBalance() * INTEREST_RATE);
    }
}
