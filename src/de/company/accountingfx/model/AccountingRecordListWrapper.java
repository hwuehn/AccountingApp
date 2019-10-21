package de.company.accountingfx.model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Helper class to wrap a list of records. This is used for saving the
 * list of records to XML.
 *
 * @author Henning Wuehn
 */
@XmlRootElement(name = "accountingRecords")
public class AccountingRecordListWrapper {

    private List<AccountingRecord> accountingRecords;

    @XmlElement(name = "accountingRecord")
    public List<AccountingRecord> getAccountingRecords() {
        return accountingRecords;
    }

    public void setAccountingRecords(List<AccountingRecord> accountingRecords) {
        this.accountingRecords = accountingRecords;
    }
}
