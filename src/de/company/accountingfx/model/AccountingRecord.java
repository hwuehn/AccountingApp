package de.company.accountingfx.model;

import de.company.accountingfx.util.IdCounter;
import de.company.accountingfx.util.LocalDateAdapter;
import javafx.beans.property.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Model class for a AccountingRecord.
 *
 * @author Henning Wuehn
 */
public class AccountingRecord {

    private IntegerProperty iD;
    private SimpleDoubleProperty amount;
    private ObjectProperty<Account> debitAcc;
    private IntegerProperty docNum;
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<Account> creditAcc;
    private StringProperty tags;

    public AccountingRecord() {

    }

     /**
     * Constructor with some initial data.
     *
     * @param iD
     * @param amount
     * @param debitAcc
     * @param docNum
     * @param date
     * @param creditAcc
     * @param tags
     */
    public AccountingRecord(IdCounter idCounter, Double amount, Account debitAcc, Integer docNum, LocalDate date,
                            Account creditAcc, String tags) {
        this.iD = new SimpleIntegerProperty(idCounter.getCounter());
        this.amount = new SimpleDoubleProperty(amount);
        this.debitAcc = new SimpleObjectProperty<>(debitAcc);
        this.docNum = new SimpleIntegerProperty(docNum);
        this.date = new SimpleObjectProperty<LocalDate>(date);
        //this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        this.creditAcc = new SimpleObjectProperty<>(creditAcc);
        this.tags = new SimpleStringProperty(tags);
    }

    public Integer getiD() {
        return iD.get();
    }

    public IntegerProperty iDProperty() {
        return iD;
    }

    public Double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public Account getDebitAcc() {
        return debitAcc.get();
    }

    public ObjectProperty<Account> debitAccProperty() {
        return debitAcc;
    }

    public void setDebitAcc(Account debitAcc) {
        this.debitAcc.set(debitAcc);
    }

    public Integer getDocNum() {
        return docNum.get();
    }

    public IntegerProperty docNumProperty() {
        return docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum.set(docNum);
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public Account getCreditAcc() {
        return creditAcc.get();
    }

    public ObjectProperty<Account> creditAccProperty() {
        return creditAcc;
    }

    public void setCreditAcc(Account creditAcc) {
        this.creditAcc.set(creditAcc);
    }

    public String getTags() {
        return tags.get();
    }

    public StringProperty tagsProperty() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags.set(tags);
    }
}
