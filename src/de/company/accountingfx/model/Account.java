package de.company.accountingfx.model;

import javafx.beans.property.*;

/**
 * Model class for a Account.
 *
 * @author Henning Wuehn
 */
public class Account {
    private final IntegerProperty accID;
    private final StringProperty accTag;
    private final DoubleProperty initialBalance;

    public Account(Integer accID, String accTag) {
        this.accID = new SimpleIntegerProperty(accID);
        this.accTag = new SimpleStringProperty(accTag);
        this.initialBalance = new SimpleDoubleProperty(0.00);
    }

    public int getAccID() {
        return accID.get();
    }

    public IntegerProperty accIDProperty() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID.set(accID);
    }

    public String getAccTag() {
        return accTag.get();
    }

    public StringProperty accTagProperty() {
        return accTag;
    }

    public void setAccTag(String accTag) {
        this.accTag.set(accTag);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accID=" + accID +
                ", accTag=" + accTag +
                '}';
    }
}
