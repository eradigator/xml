package kz.epam.javalab22.entity.bankAccount;


public abstract class BankAccount {

    private int bankAccountID;
    private long customerID;
    private double amount;
    private BankAccountStatus status;

    public BankAccount(int bankAccountID,
                       long customerID,
                       double amount,
                       BankAccountStatus status) {
        this.bankAccountID = bankAccountID;
        this.customerID = customerID;
        this.amount = amount;
        this.status = status;
    }

    public int getBankAccountID() {
        return this.bankAccountID;
    }

    public long getCustomerID() {
        return customerID;
    }

    public double getAmount() {
        return amount;
    }

    public BankAccountStatus getStatus() {
        return status;
    }

    public void setStatus(BankAccountStatus status) {
        this.status = status;
    }

    public abstract String getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;

        BankAccount that = (BankAccount) o;

        if (getBankAccountID() != that.getBankAccountID()) return false;
        if (getCustomerID() != that.getCustomerID()) return false;
        if (Double.compare(that.getAmount(), getAmount()) != 0) return false;
        return getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getBankAccountID();
        result = 31 * result + (int) (getCustomerID() ^ (getCustomerID() >>> 32));
        temp = Double.doubleToLongBits(getAmount());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }
}
