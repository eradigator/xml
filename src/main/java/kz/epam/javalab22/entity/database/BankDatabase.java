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

    public void printToScreen() {
        for (BankAccount ba : database) {
            System.out.println(ba);
        }
    }

}
