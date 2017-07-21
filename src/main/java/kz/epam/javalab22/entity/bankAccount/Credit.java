package kz.epam.javalab22.entity.bankAccount;

/**
 * Created by erad on 18.06.2017.
 */
public class Credit extends BankAccount {

    private BankAccountType bankAccountType = BankAccountType.CREDIT;
    private double limit;

    public Credit(int bankAccountID, long customerID, double amount, BankAccountStatus status, double limit) {
        super(bankAccountID, customerID, amount, status);
        this.limit = limit;
    }

    @Override
    public String getType() {
        return bankAccountType.toString();
    }

    @Override
    public String toString() {
        return "Customer " + super.getCustomerID() +
                "\t Bank Account Number " + super.getBankAccountID() +
                "\t Status " + super.getStatus() +
                "\t Type " + getType() +
                "\t Amount " + super.getAmount() +
                "\t Limit " + limit;
    }

}
