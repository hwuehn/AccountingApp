package de.company.accountingfx.store;

import de.company.accountingfx.store.util.CounterId;
import de.company.accountingfx.store.util.IAppState;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

import java.time.LocalDate;
import java.util.Comparator;

public class AppDB implements IAppState {

    private final ObjectProperty<Record> currentRecord = new SimpleObjectProperty<Record>();
    private final StringProperty title = new SimpleStringProperty();
    private final ObservableList<Record> records = FXCollections.observableArrayList();
    private final ObservableList<Account> accounts = FXCollections.observableArrayList();
    private final SortedList<Account> sortedAccounts = new SortedList<>(accounts);

    public ReadOnlyObjectProperty<ObservableList<Record>> recordsProperty() {
        return new SimpleObjectProperty<>(records);
    }

    public ReadOnlyObjectProperty<ObservableList<Account>> accountsProperty() {
        return new SimpleObjectProperty<>(accounts);
    }

    public ReadOnlyObjectProperty<ObservableList<Account>> sortedTasksProperty() {
        return new SimpleObjectProperty<>(sortedAccounts);
    }

    public ObjectProperty<Comparator<? super Account>> sortProperty() {
        return sortedAccounts.comparatorProperty();
    }

    public void add(Record record) {
        records.add(record);
    }

    public void add(Account account) {
        accounts.add(account);
    }

    public void remove(Account account) {
        accounts.remove(account);
    }

    @Override
    public ObservableList<Record> getRecords() {
        return records;
    }

    @Override
    public ObservableList<Account> getAccounts() {
        return accounts;
    }

    public SortedList<Account> getSortedAccounts() {
        return sortedAccounts;
    }

    public void setTitle(String s) {
        title.setValue(s);
    }

    public void loadTestData() {

        records.clear();
        accounts.clear();
        sortedAccounts.clear();

        // Add some accounts
        Account fuhrpark = new Account("700", "Fuhrpark");
        Account kasse = new Account("1600", "Kasse");
        Account bank = new Account("1800", "Bank");
        Account reiningung = new Account("6330", "Reinigung");
        Account buerobedarf = new Account("6815", "Buerobedarf");

        accounts.addAll(fuhrpark, kasse, bank, reiningung, buerobedarf);

        // Add some sample data
        records.add(new Record(new CounterId(), 10000.00, fuhrpark,
                123, LocalDate.of(2019, 10, 2), bank, "PKW"));
        records.add(new Record(new CounterId(), 30.00, reiningung,
                456, LocalDate.of(2019, 10, 4), kasse, "Reinigungsmittel"));
        records.add(new Record(new CounterId(), 45.00, buerobedarf,
                789, LocalDate.of(2019, 10, 6), bank, "Ordner"));
        records.add(new Record(new CounterId(), 160.00, buerobedarf,
                135, LocalDate.of(2019, 10, 9), bank, "Maus & Tastatur"));
        records.add(new Record(new CounterId(), 45.00, buerobedarf,
                999, LocalDate.of(2019, 10, 12), kasse, "Kasten Wasser"));
    }

    public void setCurrentTask(Record selectedTask) {
        currentRecord.setValue(selectedTask);
    }

    public Record getCurrentTask() {
        return currentRecord.get();
    }

    public ObjectProperty<Record> currentTaskProperty() {
        return currentRecord;
    }
}
