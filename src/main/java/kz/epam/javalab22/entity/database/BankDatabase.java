package kz.epam.javalab22.entity.database;

import kz.epam.javalab22.entity.bankAccount.BankAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erad on 19.06.2017.
 */

public class BankDatabase {

    private List<BankAccount> database = new ArrayList<>();

    public List<BankAccount> getDatabase() {
        return database;
    }

    @Override
    public String toString() {
        return database.toString();
    }

    public void printToScreen() {
        for (BankAccount ba : database) {
            System.out.println(ba);
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankDatabase)) return false;

        BankDatabase database1 = (BankDatabase) o;

        return getDatabase().equals(database1.getDatabase());
    }

    @Override
    public int hashCode() {
        return getDatabase().hashCode();
    }
}
