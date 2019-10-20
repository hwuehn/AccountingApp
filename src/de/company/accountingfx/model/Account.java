package de.company.accountingfx.model;

import javafx.beans.property.*;

/**
 * Model class for a Account.
 *
 * @author Henning Wuehn
 */
public class Account {
    private StringProperty accID;
    private StringProperty accTag;
    private DoubleProperty initialBalance;

    public Account(String accID, String accTag) {
        this.accID = new SimpleStringProperty(accID);
        this.accTag = new SimpleStringProperty(accTag);
        this.initialBalance = new SimpleDoubleProperty(0.00);
    }

    public Account() {

    }

    public String getAccID() {
        return accID.get();
    }

    public StringProperty accIDProperty() {
        return accID;
    }

    public void setAccID(String accID) {
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
