package de.company.accountingfx.model;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public interface IAppState {


    ObservableList<Record> getRecords();

    ObservableValue<? extends ObservableList<Record>> recordsProperty();

    ObservableList<Account> getAccounts();

    ObservableValue<? extends ObservableList<Account>> accountsProperty();
}
