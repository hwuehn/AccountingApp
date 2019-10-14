package de.company.accountingfx.model;

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

    private final IntegerProperty iD;
    private final DoubleProperty amount;
    private Account debitAccID;
    private final IntegerProperty docNum;
    private final ObjectProperty<LocalDate> date;
    private final IntegerProperty creditAcc;
    private final StringProperty tags;

     /**
     * Constructor with some initial data.
     *
     * @param iD
     * @param amount
     * @param debitAccID
     * @param docNum
     * @param date
     * @param creditAcc
     * @param tags
     */
    public AccountingRecord(Integer iD, Double amount, Account debitAccID, Integer docNum, LocalDate date,
                            Integer creditAcc, String tags) {
        this.iD = new SimpleIntegerProperty(iD);
        this.amount = new SimpleDoubleProperty(amount);
        this.debitAccID = debitAccID;
        this.docNum = new SimpleIntegerProperty(docNum);
        this.date = new SimpleObjectProperty(date);
        //TODO
        this.creditAcc = new SimpleIntegerProperty(creditAcc);
        this.tags = new SimpleStringProperty(tags);
    }

    public Integer getiD() {
        return iD.get();
    }

    public IntegerProperty iDProperty() {
        return iD;
    }

    public void setiD(Integer numberID) {
        this.iD.set(numberID);
    }

    public Double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount.set(amount);
    }

    public Account getDebitAccID() {
        return debitAccID.get();
    }

    public Account debitAccProperty() {
        return debitAccID;
    }

    public void setDebitAcc(Integer debitAcc) {
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

    public Integer getCreditAcc() {
        return creditAcc.get();
    }

    public IntegerProperty creditAccProperty() {
        return creditAcc;
    }

    public void setCreditAcc(Integer creditAcc) {
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
