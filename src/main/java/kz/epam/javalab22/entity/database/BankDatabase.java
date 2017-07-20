package kz.epam.javalab22.entity.database;

import kz.epam.javalab22.entity.bankAccount.BankAccount;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erad on 19.06.2017.
 */

public class BankDatabase {
    private List<BankAccount> database = new ArrayList<BankAccount>();

    public List<BankAccount> getDatabase() {
        return database;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
