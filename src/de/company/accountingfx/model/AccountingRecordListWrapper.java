package de.company.accountingfx.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Helper class to wrap a list of records. This is used for saving the
 * list of records to XML.
 *
 * @author Henning Wuehn
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "accountingrecords")
public class AccountingRecordListWrapper {

    private List<AccountingRecord> accountingRecords;

    //@XmlElement(name = "accountingrecord")
    public List<AccountingRecord> getAccountingRecords() {
        return accountingRecords;
    }

    public void setAccountingRecords(List<AccountingRecord> accountingRecords) {
        this.accountingRecords = accountingRecords;
    }
}
