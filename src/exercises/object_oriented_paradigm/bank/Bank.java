package exercises.object_oriented_paradigm.bank;

import java.util.ArrayList;

public class Bank {
    private ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public double totalAssets() {
        double sum = 0;
        for (Account account : accounts) {
            sum += account.getBalance();
        }
        return sum;
    }

    public void addInterest() {
        for (Account account : accounts) {
            if (account instanceof InterestBearingAccount iba) {
                iba.addInterest();
            }
        }
    }
}
