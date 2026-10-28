package LAB1;
public class WalletAccount extends Account {
    private boolean instantTransfersEnabled;

    public WalletAccount(int accountId, int customerId,
                         double balance,
                         boolean instantTransfersEnabled) {
        super(accountId, customerId, "WALLET", balance);
        this.instantTransfersEnabled = instantTransfersEnabled;
    }

    public boolean isInstantTransfersEnabled() {

        return instantTransfersEnabled;
    }

    public void setInstantTransfersEnabled(
            boolean instantTransfersEnabled) {
        this.instantTransfersEnabled = instantTransfersEnabled;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Instant Transfers Enabled: "
                + instantTransfersEnabled;
    }
}
