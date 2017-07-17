package kz.epam.javalab22.entity.bankAccount;

/**
 * Created by erad on 18.06.2017.
 */
public class Debet extends BankAccount {
    public Debet(int bankAccountID,
                 long customerID,
                 double amount,
                 BankAccountStatus status) {
        super(bankAccountID, customerID, amount, status);
    }

    @Override
    public String getType() {

        return BankAccountType.DEBET.toString();
    }

    @Override
    public String toString() {
        return "Customer " + super.getCustomerID() +
                "\t Bank Account Number " + super.getBankAccountID() +
                "\t Status " + super.getStatus() +
                "\t Type " + getType() +
                "\t Amount " + super.getAmount();

    }

}
